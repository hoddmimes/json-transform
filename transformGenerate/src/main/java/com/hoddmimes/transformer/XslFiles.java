package com.hoddmimes.transformer;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;

import javax.xml.transform.stream.StreamSource;


public class XslFiles
{
    private String mXslDir;
    private boolean isLoadedFromJar() {
        String tSrc = this.getClass().getProtectionDomain().getCodeSource().getLocation().toString();
        if (tSrc.toLowerCase().endsWith(".jar")) {
            return true;
        } else {
            return false;
        }
    }

    public XslFiles( String pXslDir ) {
        mXslDir = pXslDir;
    }

    public  String convertStreamToString(InputStream is, boolean noControlChar) throws IOException {
        if (is != null) {
            Writer writer = new StringWriter();

            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader( new InputStreamReader(is, "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    if (noControlChar) {
                        String tString =  new String( buffer, 0, n );
                        String s = tString.replaceAll("\\p{Cntrl}", "");
                        writer.write( s );
                    } else {
                        writer.write(buffer, 0, n);
                    }
                }
            } finally {
                is.close();
            }
            return writer.toString();
        } else {
            return "";
        }
    }

    private byte[] readBufferFromInputStream( InputStream pStream ) throws IOException
    {
        byte[] tBuffer = null;
        int tSize = 0,tPos = 0;
        DataInputStream tIn = new DataInputStream( pStream );
        while (tIn.available() > 0) {
            tSize = tIn.available();
            if (tBuffer == null) {
                tBuffer = new byte[ tSize ];
            } else {
                byte[] b = new byte[ tSize + tBuffer.length ];
                System.arraycopy(tBuffer, 0, b, 0, tBuffer.length);
                tBuffer = b;
            }
            while( tPos != tBuffer.length) {
                int s = tIn.read(tBuffer, tPos, tBuffer.length - tPos);
                tPos += s;
            }
        }
        return tBuffer;
    }

    StreamSource getXslStreamSource( String pFilename ) {

        InputStream tInputStream = null;
        String tSystemId = null;
        String tXslContent = null;
        try {
            if (isLoadedFromJar()) {
                tInputStream = getClass().getClassLoader().getResourceAsStream( pFilename);
                //System.out.println("jar xsl url filename: " + pFilename);
                URL url = getClass().getClassLoader().getResource( pFilename);
                //System.out.println("jar xsl url: " + url.toExternalForm());
                tSystemId = url.toExternalForm();
                byte[] tBuffer = readBufferFromInputStream( tInputStream );
                tXslContent = new String( tBuffer );
                //tXslContent = new String( tBuffer ).replaceAll("\\p{Cntrl}", "");

            } else {
                File tFile = new File( mXslDir + pFilename );
                System.out.println( tFile.getAbsolutePath());
                tInputStream = new FileInputStream( mXslDir + pFilename ); // pFilename

                tSystemId = tFile.toURI().toURL().toExternalForm();
                tXslContent = convertStreamToString( tInputStream, false );
            }


            StreamSource tStreamSource = new StreamSource(new StringReader(tXslContent), tSystemId );
            return tStreamSource;
        }
        catch( IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
