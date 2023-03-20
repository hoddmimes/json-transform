package com.hoddmimes.transform;


import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@SuppressWarnings({"WeakerAccess","unused","unchecked"})
public class JsonSchemaValidator
{
    private static Pattern JSON_MESSAGE_NAME_PATTERN = Pattern.compile("^\\s*\\{\\s*\"(\\w*)\"\\s*\\:\\s*\\{");

    private Map<String,Schema> mSchemaCache;
    private static String SCHEMA_TITLE = "title";
    private boolean mVerbose = false;



    public JsonSchemaValidator( String pJsonSchemaSource ) {
        this( pJsonSchemaSource, false);
    }


    public JsonSchemaValidator(String pJsonSchemaSource, boolean pVerbose ) {
        String tJsonSchemaSource = (pJsonSchemaSource == null) ? "./jsonSchemas" : pJsonSchemaSource;
        mVerbose = pVerbose;

        mSchemaCache = new HashMap<>();

        File tFile = new File(tJsonSchemaSource);
        if (tFile.isDirectory() && tFile.exists()) {
            loadSchemasFromDirectory(tJsonSchemaSource);
        } else {
            loadSchemasFromResource(tJsonSchemaSource);
        }
    }




    private void loadSchemaResource(InputStream pInputStream, String pSchemaSource ) {
        try {
            JSONObject tRawSchemaObject = new JSONObject(new JSONTokener(pInputStream));
            String tMessageName = tRawSchemaObject.getString(SCHEMA_TITLE);

            Schema tJsonSchema = SchemaLoader.builder().useDefaults(true).schemaJson(tRawSchemaObject).build().load().build();
            mSchemaCache.put(tMessageName, tJsonSchema);
            if (mVerbose) {
                System.out.println("Successfully loaded JSON Schema: " + pSchemaSource);
            }
        }
        catch(Exception e) {
            System.err.println("failed to load schema \"" + pSchemaSource + "\" reason: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private void loadSchemasFromResource( String pJsonSchemaSource ) {
        ResourceHandler tResourceHandler = new ResourceHandler( pJsonSchemaSource, ".+\\.json");
        List<Path> tSchemaResources = null;
        try {
            tSchemaResources = tResourceHandler.listResources();
        }
        catch(Exception e) {
            System.out.println("Can not find schema resource \"" + pJsonSchemaSource + "\"");
            System.exit(0);
        }
        for( Path tPath : tSchemaResources) {
            try {
                InputStream tInStream = tResourceHandler.getResourceAsStream(tPath);
                loadSchemaResource( tInStream, tPath.toString() );
            }
            catch( IOException e) {
                System.out.println("Can open schema file \"" + tPath.toString() + "\"");
            }

        }

    }


    private void loadSchemasFromDirectory(String pJsonSchemaDirectory) {
        File tFolder = new File(pJsonSchemaDirectory);
        if (!tFolder.exists()) {
            System.out.println("Can not find schema directory \"" + tFolder.getAbsolutePath() + "\"");
            System.exit(0);
        }
        File[] tListOfFiles = tFolder.listFiles( new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name)
            {
                //return (name.endsWith(".json") && (name.contains("_RSP") || name.contains("_RQST")));
                return (name.endsWith(".json"));
            }
        });

        for( int i = 0; i < tListOfFiles.length; i++) {
            try {
                FileInputStream tInStream = new FileInputStream( tListOfFiles[i] );
                loadSchemaResource( tInStream, tListOfFiles[i].getAbsolutePath() );
            }
            catch( IOException e) {
                System.out.println("Can open schema file \"" + tListOfFiles[i].getAbsolutePath() + "\"");
            }

        }
    }

    public void validate( String pJsonMsgObjectString ) throws Exception {
        Matcher tMatcher = JSON_MESSAGE_NAME_PATTERN.matcher( pJsonMsgObjectString);
        if (!tMatcher.find()) {
            throw new Exception("Could not find message identifier in the beging of the string i.e. \"{ <message-name> {\"");
        }
        validate(tMatcher.group(1), pJsonMsgObjectString);
    }


    public void validate( String pJsonMsgName, String pJsonMsgObjectString ) throws Exception {
        JSONObject tMsgJasonObject = null;
        Schema tSchema = null;

        // Parse Json message object
        try {
            tMsgJasonObject = new JSONObject( pJsonMsgObjectString );
        } catch( JSONException e1) {
            throw new Exception("failed to parse Json message string: " + pJsonMsgObjectString, e1 );
        }


        // Get the schema for the message object
        tSchema = mSchemaCache.get(pJsonMsgName);
        if (tSchema == null) {
            throw new Exception("schema for message/structure \"" + pJsonMsgName + "\", is not defined");
        }

        // Validate the message
        try {
            tSchema.validate(tMsgJasonObject);
        }
        catch( Exception e3 ) {
            throw new Exception("Invalid structure \"" + pJsonMsgName +"\" reason: " + e3.getMessage(), e3);
        }
    }
}
