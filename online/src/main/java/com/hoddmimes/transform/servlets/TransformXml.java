package com.hoddmimes.transform.servlets;

import com.google.googlejavaformat.java.Formatter;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hoddmimes.transformer.Transform;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

@Path("/transform")
public class TransformXml extends HttpServlet {

    private static String cTransformBaseDir;
    private static AtomicInteger cSeqno = new AtomicInteger(0);


    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        cTransformBaseDir = servletConfig.getInitParameter("transformBaseDir");
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String transform(@Context HttpServletRequest servletRequest, String jXMLMessage) throws ServletException {

        // Parse request
        JsonObject jRequest = null;
        try {
            jRequest = JsonParser.parseString(jXMLMessage).getAsJsonObject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }

        // Create separate directory for
        File tFileDir = makeDir(cTransformBaseDir);

        // Create XML Message file
        createMessageXml(tFileDir.getAbsolutePath() + "/Messages.xml", jRequest.get("xml").getAsString());

        // Create XML File Set File
        createFileSetXml(tFileDir.getAbsolutePath() + "/MessageFileSet.xml",
                tFileDir.getAbsolutePath() + "/Messages.xml",
                tFileDir.getAbsolutePath());

        // Transform
        try {
            doTransform( tFileDir.getAbsolutePath() + "/MessageFileSet.xml" );
        }
        catch( Exception e) {
            return createStatus(false, e.getMessage()).toString();
        }

        // Format Generated Code
        String tFormatCode = doFormat( tFileDir );

        JsonObject jResponse = new JsonObject();
        jResponse.addProperty("src", tFormatCode);

        //deleteDir( tFileDir ); // Delete temporary dir

        return jResponse.toString();
    }

    private String doFormat( File pDirFile ) throws ServletException {
        File[] tFiles = pDirFile.listFiles( new JavaFileFile());
        StringBuilder sb = new StringBuilder();

        for( File f : tFiles ) {
            sb.append("\n\n// ======================= Message: " + f.getName().substring(0, f.getName().indexOf(".")) + " ==============================================\n\n");
            sb.append( formatFile( f ));
            sb.append("\n\n");
        }
        return sb.toString();
    }

    private String formatFile( File pFile  ) throws ServletException {
        try {
            Scanner sc = new Scanner(pFile);
            StringBuilder sb = new StringBuilder();
            while (sc.hasNext()) {
                sb.append(sc.nextLine() + "\n");
            }
            Formatter tFormatter = new Formatter();
            String tFormattedSrc = tFormatter.formatSource(sb.toString());
            return tFormattedSrc;
        }
        catch( Exception e) {
            throw new ServletException( e );
        }
    }
    private void doTransform( String pXmlFileSetName ) throws Exception {
        Transform tTransform = new Transform();
        String[] args = {"-xml", pXmlFileSetName ,
                         "-comments", "false" };
        tTransform.parseParameters(args);
        tTransform.transform();
    }

    private File makeDir( String pTransformBaseDirPath ) throws ServletException
    {
        // Create unique directory for this request
        String tRqstPattern = getRequestPattern();

        File tDirFile = new File(cTransformBaseDir + tRqstPattern);
        try {
            tDirFile.mkdir();
            return tDirFile;
        }
        catch( Exception e) {
           throw new ServletException( e );
        }
    }
    private void createFileSetXml( String pFileSetName, String pMessageXmlFile, String pOutPath ) throws ServletException {
        try {
            FileWriter fw = new FileWriter(pFileSetName);
            fw.write("<MessageFiles>\n");
            fw.write("<MessageFile file=\"" + pMessageXmlFile + "\" outPath=\"" + pOutPath + "/\" package=\"\"/>");
            fw.write("\n</MessageFiles>\n");
            fw.flush();
            fw.close();
        } catch (IOException e) {
            throw new ServletException(e);
        }
    }
    private void createMessageXml( String pFilename, String pXml ) throws ServletException {
        try {
            FileWriter fw = new FileWriter(pFilename);

            fw.write(pXml);
            fw.flush();
            fw.close();
        } catch (IOException e) {
            throw new ServletException(e);
        }
    }

    private String getRequestPattern() {
        SimpleDateFormat SDF = new SimpleDateFormat("MM_dd_HHmmss");
        String tPattern = SDF.format( System.currentTimeMillis()) + "_" + String.valueOf( cSeqno.getAndIncrement());
        return tPattern;
    }
    private JsonObject createStatus( boolean pSuccess, String pReason ) {
        JsonObject jStatus = new JsonObject();
        jStatus.addProperty("success", pSuccess);
        jStatus.addProperty("reason", pReason);
        return jStatus;
    }
    private void deleteDir( File pDirectoryFile ) {
        File[] tFilesInDir = pDirectoryFile.listFiles();
        for (File f : tFilesInDir) {
            f.delete();
        }
        pDirectoryFile.delete();
    }

    class JavaFileFile implements FilenameFilter {
        @Override
        public boolean accept(File dir, String name) {
            return name.endsWith("java");
        }
    }

}