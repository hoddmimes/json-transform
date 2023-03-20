package test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hoddmimes.transform.JsonSchemaValidator;
import generated.DateTimeMessage;
import generated.TestMessage;

public class TestSchema
{

    private void test() {
        JsonSchemaValidator tValidator = new JsonSchemaValidator("./samples/jsonSchemas");
        TestMessage testMsg = CreateTestMessage.createValidTestMessage();
        DateTimeMessage dateMsg = CreateDateTimeMessage.createMessage();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        //System.out.println(gson.toJson( testMsg.toJson()));
        //System.out.println(gson.toJson( dateMsg.toJson()));

        try {
            tValidator.validate( dateMsg.toJson().toString() );
            System.out.println("Successfully Validated DateTimeMessage\n");
        }
        catch(Exception e ) {
            e.printStackTrace();
        }

        try {
            tValidator.validate( testMsg.toJson().toString() );
            System.out.println("Successfully Validated TestMessage\n");
        }
        catch(Exception e ) {
            e.printStackTrace();
        }

        // Invalidate the intValue in one of the TestSubMessages in the message array
        testMsg.getMsgArray().get().get(0).setIntValue(13); // value must be between 42  and 84
        try {
            tValidator.validate( testMsg.toJson().toString() );
            System.out.println("Uhhh should not be a valid message");
        }
        catch(Exception e ) {
            System.out.println("Successfully detected invalid message");
            System.out.println("   " + e.getMessage());
            testMsg.getMsgArray().get().get(0).setIntValue(48); // value must be between 42  and 84
        }

        testMsg.setStrValue("fooKalle"); // Must match "foo-\w*-\d*"
        try {
            tValidator.validate( testMsg.toJson().toString() );
            System.out.println("Uhhh should not be a valid message");
        }
        catch(Exception e ) {
            System.out.println("Successfully detected invalid message");
            System.out.println("   " + e.getMessage());
            testMsg.setStrValue("foo-bar-123");
        }


    }

    public static void main( String[] pArgs ) {
        TestSchema st = new TestSchema();
        st.test();
    }

}
