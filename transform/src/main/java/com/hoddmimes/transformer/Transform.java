package com.hoddmimes.transformer;

import net.sf.saxon.s9api.*;

import javax.xml.XMLConstants;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidParameterException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Transform
{

    static final boolean cDegugTrace = false;
    static final SimpleDateFormat cSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    private final String mXSLFile = "MessageJSON.xsl";
    private final String mXSLMessageFactoryFile = "MessageFactoryJSON.xsl";
    private final String mXSLSchemaFactoryFile = "MessageJSONSchema.xsl";
    private final String mXSLMongoAuxFile = "MongoAux.xsl";

    private String mXslDir = "./xsl/";

    private boolean mComments = true; // Generate comments or not
    private String mSchemaDir = null;
    private String mSchemaOutputPath = ".";
    private String mWorkingDirectory;

    private String mXmlDefinitionSourceFile = null;
    private List<MessageSourceFile> mMessageFiles = new ArrayList<>();
    private List<MessageSourceFile> mMessageFactoryFiles = new ArrayList<>();
    private List<MessageSourceFile> mMongoAuxFiles = new ArrayList<>();

    private String mAllMessages = null;

    public static void main( String[] pArgs ) {
        Transform tGenerator = new Transform();
        tGenerator.parseParameters( pArgs  );


        try {
            for( int i = 0; i < tGenerator.mMessageFiles.size(); i++) {
                tGenerator.transformMessageFile( tGenerator.mMessageFiles.get(i));
            }

            tGenerator.mongoAuxTransform();
            tGenerator.messageFactoryTransform();
            tGenerator.schemaFactoryTransform();

        }
        catch( Exception e) {
            e.printStackTrace();
        }
    }


    private String getOutPath( String pPath ) {
        // Check if path is absolut or relative. If it is relative return the absolute path relative to the current
        // working directory otherwise return the absolute path
        Path p = Paths.get(pPath);
        if (p.isAbsolute()) {
            return pPath;
        }
        return mWorkingDirectory + pPath;
    }

    private void collectAllMessages() throws IOException{
       mAllMessages = this.mergeXMLFiles(true);
    }


    private void transformMessageFile( MessageSourceFile pSource) throws Exception
    {
        File tPath = new File( pSource.mXmlSourceFile );
        String tXmlSourcePath = tPath.getCanonicalFile().getParent().replace('\\','/');


        Processor processor = new Processor(false);
        XsltCompiler tCompiler = processor.newXsltCompiler();

        XsltExecutable stylesheet = tCompiler.compile(new XslFiles( mXslDir ).getXslStreamSource(mXSLFile));
        Serializer out = processor.newSerializer( System.out );
        out.setOutputProperty(Serializer.Property.METHOD, "text");
        out.setOutputProperty(Serializer.Property.INDENT, "no");

        String tDbMessages = findDbSupportMessages( loadXMLFromString( mAllMessages ));
        //System.out.println("ALL MSGS: " + tDbMessages );

        net.sf.saxon.s9api.DocumentBuilder tDocBuilder = processor.newDocumentBuilder();
        XdmNode tXdmNode = tDocBuilder.build(new StreamSource( new StringReader(tDbMessages)));



        Xslt30Transformer transformer = stylesheet.load30();
        Map<QName, XdmValue> tParameters = new HashMap<>();
        tParameters.put(new QName("outPath"), XdmValue.makeValue(getOutPath( pSource.mOutPath )));
        tParameters.put(new QName("package"), XdmValue.makeValue(pSource.mPackage ));
        tParameters.put(new QName("inputXml"), XdmValue.makeValue(pSource.mXmlSourceFile ));
        tParameters.put(new QName("inputXsl"),  XdmValue.makeValue(mXSLFile ));
        tParameters.put(new QName("inputXmlPath"), XdmValue.makeValue(tXmlSourcePath));
        tParameters.put(new QName("dbMessages"), tXdmNode );
        tParameters.put(new QName("comments"), XdmValue.makeValue(mComments));


        transformer.setStylesheetParameters( tParameters );
        transformer.transform(new StreamSource(new File(pSource.mXmlSourceFile)), out);

        /**

        TransformerFactory tFactory = TransformerFactory.newInstance();
        Transformer tTransformer = tFactory.newTransformer( new XslFiles( mXslDir ).getXslStreamSource(mXSLFile));


        tTransformer.transform(new StreamSource(new File(pSource.mXmlSourceFile)), new StreamResult(new NullStream()));
        **/
    }

    private void schemaFactoryTransform() throws Exception
    {
        if (this.mSchemaDir == null) {
            System.out.println("SchemaFactory not defined and will not be generated");
            return;
        }

        File tPath = new File( mXmlDefinitionSourceFile );
        String tSystemId = tPath.toURI().toURL().toExternalForm();
        String tXmlSourcePath = tPath.getCanonicalFile().getParent().replace('\\','/');
        if (mAllMessages != null) {
            XslFiles tXslFiles = new XslFiles( mXslDir);
            StreamSource tStreamSource = new XslFiles(mXslDir).getXslStreamSource(mXSLSchemaFactoryFile);

            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer tTransformer = tFactory.newTransformer( tStreamSource );
            tTransformer.setParameter("outPath", getOutPath( mSchemaOutputPath ));
            tTransformer.setParameter("schemaDir", mSchemaDir );

            tTransformer.transform(new StreamSource(new StringReader(mAllMessages), tSystemId), new StreamResult(new NullStream()));
        }
    }

    private void mongoAuxTransform() throws Exception
    {
        File tPath = new File( mXmlDefinitionSourceFile );
        String tSystemId = tPath.toURI().toURL().toExternalForm();
        String tXmlSourcePath = tPath.getCanonicalFile().getParent().replace('\\','/');

        for(int i = 0; i < mMongoAuxFiles.size(); i++ )
        {
            MessageSourceFile tSource = mMongoAuxFiles.get(i);

            if (mAllMessages != null) {
                XslFiles tXslFiles = new XslFiles( mXslDir);
                StreamSource tStreamSource = new XslFiles( mXslDir ).getXslStreamSource(mXSLMongoAuxFile);

                TransformerFactory tFactory = TransformerFactory.newInstance();
                Transformer tTransformer = tFactory.newTransformer( tStreamSource );
                tTransformer.setParameter("outPath", getOutPath(tSource.mOutPath ));
                tTransformer.setParameter("package", tSource.mPackage );
                tTransformer.transform(new StreamSource(new StringReader(mAllMessages), tSystemId), new StreamResult(new NullStream()));
            }
        }

    }
    private void messageFactoryTransform() throws Exception
    {
        File tPath = new File( mXmlDefinitionSourceFile );
        String tSystemId = tPath.toURI().toURL().toExternalForm();
        String tXmlSourcePath = tPath.getCanonicalFile().getParent().replace('\\','/');

        for(int i = 0; i < mMessageFactoryFiles.size(); i++ )
        {
            MessageSourceFile tSource = mMessageFactoryFiles.get(i);
            if (mAllMessages != null) {
                XslFiles tXslFiles = new XslFiles( mXslDir );
                StreamSource tStreamSource = new XslFiles( mXslDir ).getXslStreamSource(mXSLMessageFactoryFile);

                TransformerFactory tFactory = TransformerFactory.newInstance();
                Transformer tTransformer = tFactory.newTransformer( tStreamSource );
                tTransformer.setParameter("outPath", getOutPath(tSource.mOutPath ));
                tTransformer.setParameter("package", tSource.mPackage );
                tTransformer.transform(new StreamSource(new StringReader(mAllMessages), tSystemId), new StreamResult(new NullStream()));
            }
        }
    }

    private String mergeXMLFiles(boolean pOmitXmlDeclararion) throws IOException{
        StringBuilder tStringBuilder = new StringBuilder();
        if (!pOmitXmlDeclararion) {
            tStringBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        }
        tStringBuilder.append("<MessagesRoot>");
        boolean tAnyFileLoaded = false;
        for(int i = 0; i < mMessageFiles.size(); i++ ) {
            MessageSourceFile tSrc = mMessageFiles.get(i);
            tAnyFileLoaded = true;
            String tFileContents = readXMLFile( tSrc.mXmlSourceFile, tSrc.mPackage );
            tStringBuilder.append( tFileContents );
        }
        tStringBuilder.append("</MessagesRoot>");
        if (!tAnyFileLoaded) {
            return null;
        }
        //System.out.println( tStringBuilder.toString() );
        return tStringBuilder.toString();
    }

    private String getModulenameFromFilename( String pFilename ) {
        String tFilename = pFilename.replace("\\", "/");
        int tStart = (tFilename.lastIndexOf("/") < 0) ? 0 : tFilename.lastIndexOf("/") + 1;
        if (tFilename.lastIndexOf(".") > 0) {
            int tEnd = tFilename.lastIndexOf(".");
            return tFilename.substring(tStart, tEnd );
        } else {
            return tFilename.substring(tStart);
        }
    }
    private String readXMLFile( String pFilename, String pPackage ) throws IOException {
        InputStream tInputStream = null;

        tInputStream = new FileInputStream(pFilename);
        String tModule = getModulenameFromFilename( pFilename );
        String tString = new XslFiles( mXslDir ).convertStreamToString(tInputStream, true);
        tString = tString.replaceAll("<\\?[\\p{Print}]+\\?>", "");
        tString = tString.replaceAll("<Messages", "<Messages module=\"" + tModule +"\" package=\"" + pPackage + "\" ");
        return tString;

    }

    public void parseParameters( String[] pArgs )
    {

        // Find out what the current working directory is as absolute path
        String tCurrentPath = FileSystems.getDefault().getPath(".").toAbsolutePath().toString();
        this.mWorkingDirectory = (tCurrentPath.endsWith(".")) ? tCurrentPath.substring(0,tCurrentPath.length() - 1) : tCurrentPath;

        int i = 0;
        while( i < pArgs.length ) {
            if (pArgs[i].compareToIgnoreCase("-xml") == 0) {
                mXmlDefinitionSourceFile = pArgs[ i + 1 ].replace('\\','/');
                i++;
            }
            if (pArgs[i].compareToIgnoreCase("-xslDir") == 0) {
                mXslDir = pArgs[i + 1].replace('\\', '/');
                i++;
            }
            if (pArgs[i].compareToIgnoreCase("-comments") == 0) {
                mComments  = Boolean.parseBoolean( pArgs[ i + 1 ] );
                i++;
            }
            i++;
        }



        if (mXmlDefinitionSourceFile == null) {
            System.out.println("usage: Transform -xml <message-definition-source-file>.xml");
            System.exit(0);
        }

        Element tRoot = loadAndParseXml(mXmlDefinitionSourceFile).getDocumentElement();


        /**
         * Parse ordinary message file
         */
        NodeList tNodeList = tRoot.getElementsByTagName("MessageFile");
        if (tNodeList != null) {
            for( i = 0; i < tNodeList.getLength(); i++) {
                if (tNodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    Element tFileElement = (Element) tNodeList.item(i);
                    String tXmlFile = tFileElement.getAttribute("file");
                    String tOutPath =  tFileElement.getAttribute("outPath");
                    String tPackage = tFileElement.getAttribute("package");

                    boolean tDebugFlag = false;
                    if ((tFileElement.getAttribute("debug") != null) && (tFileElement.getAttribute("debug").length() > 0)) {
                        tDebugFlag = Boolean.parseBoolean(tFileElement.getAttribute("debug"));
                    }
                    mMessageFiles.add( new MessageSourceFile( tXmlFile, tOutPath, tPackage, tDebugFlag ));
                }
            }
        }





        /**
         * Parse schema factory entry
         */
        if ((tRoot.getElementsByTagName("SchemaFactory") != null) && (tRoot.getElementsByTagName("SchemaFactory").getLength() > 0)) {
            Element tSchemaRoot = (Element) tRoot.getElementsByTagName("SchemaFactory").item(0);
            this.mSchemaOutputPath = tSchemaRoot.getAttribute("outPath");
            this.mSchemaDir = tSchemaRoot.getAttribute("schemaDir");
        }

        /**
         * Parse MongoAux entry
         */
        if ((tRoot.getElementsByTagName("MongoAux") != null) && (tRoot.getElementsByTagName("MongoAux").getLength() > 0)) {
            tNodeList = tRoot.getElementsByTagName("MongoAux");
            if (tNodeList != null) {
                for (i = 0; i < tNodeList.getLength(); i++) {
                    if (tNodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                        Element tFileElement = (Element) tNodeList.item(i);
                        String tPackage = tFileElement.getAttribute("package");
                        String tOutPath = tFileElement.getAttribute("outPath");

                        boolean tDebugFlag = false;
                        if ((tFileElement.getAttribute("debug") != null) && (tFileElement.getAttribute("debug").length() > 0)) {
                            tDebugFlag = Boolean.parseBoolean(tFileElement.getAttribute("debug"));
                        }
                        mMongoAuxFiles.add(new MessageSourceFile(mXmlDefinitionSourceFile, tOutPath, tPackage, tDebugFlag));
                    }
                }
            }
        }

        /**
         * Parse message factory entries
         */
        if ((tRoot.getElementsByTagName("MessageFactory") != null) && (tRoot.getElementsByTagName("MessageFactory").getLength() > 0)) {
            tNodeList = tRoot.getElementsByTagName("MessageFactory");
            if (tNodeList != null) {
                for (i = 0; i < tNodeList.getLength(); i++) {
                    if (tNodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                        Element tFileElement = (Element) tNodeList.item(i);
                        String tPackage = tFileElement.getAttribute("package");
                        String tOutPath = tFileElement.getAttribute("outPath");

                        boolean tDebugFlag = false;
                        if ((tFileElement.getAttribute("debug") != null) && (tFileElement.getAttribute("debug").length() > 0)) {
                            tDebugFlag = Boolean.parseBoolean(tFileElement.getAttribute("debug"));
                        }
                        mMessageFactoryFiles.add(new MessageSourceFile(mXmlDefinitionSourceFile, tOutPath, tPackage, tDebugFlag));
                    }
                }
            }
        }
    }

    public void transform() throws Exception{
        try {
            this.collectAllMessages();
        }
        catch( IOException e) {
            e.printStackTrace();
            throw new IOException("could not find all XML definition, reason: " + e.getMessage());
        }

        try {
            this.checkForMessageDuplicates();
        }
        catch( Exception e) {
            e.printStackTrace();
            throw new Exception("Invalid XML syntax, reason: " + e.getMessage());
        }

        try {
            for (int i = 0; i < this.mMessageFiles.size(); i++) {
                this.transformMessageFile(this.mMessageFiles.get(i));
            }
            this.messageFactoryTransform();
            this.mongoAuxTransform();
            this.schemaFactoryTransform();

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("XSL transformation failed, reason: " + e.getMessage());
        }
    }

    private  String elementToString(Element node) {
        StringWriter sw = new StringWriter();
        try {
            Transformer tTransformer = TransformerFactory.newInstance().newTransformer();
            tTransformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            tTransformer.setOutputProperty(OutputKeys.INDENT, "no");
            tTransformer.transform(new DOMSource(node), new StreamResult(sw));
        } catch (TransformerException te) {
            te.printStackTrace();
        }
        return sw.toString();
    }

    private Document loadXMLFromString(String pXmlString) throws Exception
    {
        int idx = pXmlString.indexOf("<MessagesRoot>");
        if (idx > 0) {
            pXmlString = pXmlString.substring(idx);
        }


        DocumentBuilderFactory tFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = tFactory.newDocumentBuilder();
        InputSource tInputStream = new InputSource(new StringReader(pXmlString));
        return builder.parse(tInputStream);

    }


    private Document loadAndParseXml( String pXmlFilename ) {
        try {
            File tFile = new File( pXmlFilename );
            if (!tFile.exists()) {
                throw new InvalidParameterException("XML file \"" + tFile.getAbsolutePath() +"\" does not exists");
            }

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document tDocument = db.parse(tFile);
            return tDocument;
        }
        catch( Exception e ) {
            e.printStackTrace();
        }
        return null;
    }

    private void checkForMessageDuplicates() throws IOException {
        try {
            Document tDoc = loadXMLFromString(mAllMessages);
            Element tRoot = tDoc.getDocumentElement();
            NodeList tMessageCollections = tRoot.getElementsByTagName("Messages");
            for (int i = 0; i < tMessageCollections.getLength(); i++) {
                Element tCollecton = (Element)tMessageCollections.item(i);
                Map<String,String> tMap = new HashMap<>();
                NodeList tMessages = tCollecton.getElementsByTagName("Message");
                for (int j = 0; j < tMessages.getLength(); j++) {
                    Element tMsg = (Element) tMessages.item(j);
                    String tMsgName = tMsg.getAttribute("name");
                    if (tMap.containsKey( tMsgName)) {
                       Exception e = new Exception("Duplicate message definition \"" + tMsgName + "\" in message file \"" +
                               tCollecton.getAttribute("module") + "\"");
                       e.printStackTrace();
                    } else {
                        tMap.put( tMsgName, tMsgName);
                    }
                    //System.out.println("Module: " + tCollecton.getAttribute("module") + "  Message: " + tMsg.getAttribute("name"));
                }
            }
        }
        catch( Exception e) {
            e.printStackTrace();
            throw new IOException("Invalid XML syntax, reason: " + e.getMessage());
        }
    }

        private String findDbSupportMessages( Document  pMsgDocument ) throws Exception {
            HashMap<String, DbMessage> tMsgs = new  HashMap<>();

            // Get the root element "MessageRoot"
            Element tMsgRootElement = (Element) pMsgDocument.getDocumentElement();
            // Find all "Messages" sub elements
            NodeList tMessagesNodeList = tMsgRootElement.getElementsByTagName("Messages");
            // Loop over all messages collections and examine what messages having DB support
            for( int i = 0; i < tMessagesNodeList.getLength(); i++ ) {
                if (tMessagesNodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    NodeList tMsgNodeList = ((Element) tMessagesNodeList.item(i)).getElementsByTagName("Message");
                    for (int j = 0; j < tMsgNodeList.getLength(); j++) {
                        if (tMsgNodeList.item(j).getNodeType() == Node.ELEMENT_NODE) {
                            Element msg = (Element) tMsgNodeList.item(j);
                            boolean tDbSupport = (msg.hasAttribute("db")) ? Boolean.parseBoolean(msg.getAttribute("db")) : false;
                            String tMessageName = msg.getAttribute("name");
                            String tExtendMessageName = (msg.hasAttribute("db")) ? msg.getAttribute("extends") : null;
                            tMsgs.put(tMessageName, new DbMessage(tMessageName, tExtendMessageName, msg, tDbSupport));
                        }
                    }
                }
            }
            // Traverse all messages to see what messages require db support
            for( DbMessage dm : tMsgs.values()) {
               if (dm.mDbSupport) {
                  checkDbSupportForChildren( dm, tMsgs );
                  checkDbSupportForExtentions( dm, tMsgs );
               }
            }

            // Return an XML String with the messages implementing the Mongo interface
            // <MongoMessages>
            //      <Message name='<msg-name>' rootMessage='<true|false>'/>
            //
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dbf.newDocumentBuilder();
            Document tDoc = builder.newDocument();
            Element tRoot = tDoc.createElement("MongoMessages");

            for( DbMessage dm : tMsgs.values()) {
                if (dm.mDbSupport) {
                    Element me = tDoc.createElement("Message");
                    me.setAttribute("name", dm.mMessageName );
                    tRoot.appendChild( me );
                    //System.out.println("DB supported message: " +  dm.mMessageName);
                }
            }
            String tResult = elementToString( tRoot );
            //System.out.println( tResult );
            return tResult;
        }

        private void checkDbSupportForExtentions( DbMessage pDbMsg, HashMap<String, DbMessage> pDbMsgs) {
        if ((pDbMsg.mDbSupport) && (pDbMsg.mExtensionMessageName != null)) {
            DbMessage tExtendMsg = (DbMessage) pDbMsgs.get(pDbMsg.mExtensionMessageName );
            if (tExtendMsg != null) {
                tExtendMsg.mDbSupport = true;
            }
        }
    }


        private void checkDbSupportForChildren( DbMessage pDbMsg, HashMap<String, DbMessage> pDbMsgs) {
        NodeList tAttrList = pDbMsg.mMsgElement.getElementsByTagName("Attribute");
        for( int i = 0; i < tAttrList.getLength(); i++) {
            if (tAttrList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element tAttrElement = (Element) tAttrList.item(i);
                String tType = (tAttrElement.hasAttribute("type")) ? tAttrElement.getAttribute("type") : "";
                DbMessage tDbMsg = pDbMsgs.get( tType );
                if (tDbMsg != null) {
                    tDbMsg.mDbSupport = true;
                    checkDbSupportForChildren( tDbMsg, pDbMsgs );
                }
            }
        }
    }



    private static boolean isStartedFromJar()
    {
        String tResource =  Transform.class.getResource("Transform.class").toString();
        System.out.println("isStartedFromJar resource: " + tResource);
        return tResource.startsWith("jar");
    }


    class NullStream extends OutputStream
    {
        @Override
        public void write(byte[] b) throws IOException
        {
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException
        {
        }

        @Override
        public void flush() throws IOException
        {
        }

        @Override
        public void write(int b) throws IOException {
        }

        @Override
        public void close() throws IOException
        {
        }
    }

    static class DbMessage {
        String      mMessageName;
        boolean     mDbSupport;
        Element     mMsgElement;
        String      mExtensionMessageName;

        DbMessage( String pMsgName, String pExtentionMsgName, Element pMsgElement, boolean pDbSupport  ) {
            mDbSupport = pDbSupport;
            mMessageName = pMsgName;
            mMsgElement = pMsgElement;
            mExtensionMessageName = pExtentionMsgName;
        }
    }

    class MessageSourceFile
    {
        String 			mXmlSourceFile;
        String 			mOutPath;
        String 			mPackage;
        boolean			mDebug;

        MessageSourceFile( String pXmlSourceFile, String pOutPath,  String pPackage, boolean pDebug )
        {
            mXmlSourceFile = pXmlSourceFile;
            mOutPath = pOutPath;
            mDebug = pDebug;
            mPackage = pPackage;
        }
    }
}
