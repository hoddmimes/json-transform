package test;

import com.google.gson.*;
import com.hoddmimes.jsontransform.JsonDecoder;
import com.hoddmimes.jsontransform.JsonEncoder;
import generated.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


public class TestEncodeDecode {



    private void test() {

        // Create a test message
        TestMessage testMsg = CreateTestMessage.createRandomTestMessage();

        // Create an Enoder and encode the created test message instance
        JsonEncoder tEncoder = new JsonEncoder();
        testMsg.encode( tEncoder );

        // Print the encoded (Json) message
        Gson tJsonPrinter = new GsonBuilder().setPrettyPrinting().create();
        System.out.println(tJsonPrinter.toJson( tEncoder.toJson()));

        // Create a decoder from a JSON string
        String testMsgAsJsonString = tEncoder.toJson().toString();
        JsonDecoder tDecoder = new JsonDecoder( testMsgAsJsonString );

        // Create a new instance of a TestMessage from a Json string
        testMsg = new TestMessage();
        testMsg.decode( tDecoder );

        // The message factor can based on the Json String try to
        // create and decode a new message instance based upon the JSON string structure and attributes
        MessageFactory tMsgFactory = new MessageFactory();
        TestMessage xtm = (TestMessage) tMsgFactory.getMessageInstance( tEncoder.toJson().toString() );

        // Just retreive some value from the test message
        int x = testMsg.getIntValue().get();
        List<Integer> iarr = testMsg.getIntArray().get();

        // Just and other itteration of the encoding of the test messahe
        tEncoder = new JsonEncoder();
        xtm.encode( tEncoder );

        System.out.println(tJsonPrinter.toJson( tEncoder.toJson()));

    }



    public static void main(String[] pArgs) {
        TestEncodeDecode t = new TestEncodeDecode();
        t.test();

    }
}
