package com.hoddmimes.jsontransform;


import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings({"WeakerAccess","unused","unchecked"})
public class JsonSchemaValidator
{
    private static Pattern JSON_MESSAGE_NAME_PATTERN = Pattern.compile("^\\s*\\{\\s*\"(\\w*)\"\\s*\\:\\s*\\{");

    private Map<String,Schema> mSchemaCache;
    private static String SCHEMA_TITLE = "title";



    public JsonSchemaValidator() {
        this( null );
    }

    public JsonSchemaValidator(String pJsonSchemaDirectory ) {
        String tJsonSchemaDirectory = (pJsonSchemaDirectory == null) ? "./jsonSchemas" : pJsonSchemaDirectory;

        mSchemaCache = new HashMap<>();
        loadSchemas( tJsonSchemaDirectory );
    }

    private void loadSchemaFile(File pFile ) {
        try {
            JSONObject tRawSchemaObject = new JSONObject(new JSONTokener(new FileInputStream(pFile)));
            String tMessageName = tRawSchemaObject.getString(SCHEMA_TITLE);

            Schema tJsonSchema = SchemaLoader.builder().useDefaults(true).schemaJson(tRawSchemaObject).build().load().build();
            mSchemaCache.put(tMessageName, tJsonSchema);
            System.out.println("Successfully loaded JSON Schema: " + pFile.getAbsolutePath());

        }
        catch(Exception e) {
            System.err.println("failed to load schema \"" + pFile.getAbsolutePath() + "\" reason: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadSchemas(String pJsonSchemaDirectory) {
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
            loadSchemaFile( tListOfFiles[i] );
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
