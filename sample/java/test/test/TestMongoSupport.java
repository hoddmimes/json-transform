package test;
import com.mongodb.client.result.UpdateResult;
import generated.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class TestMongoSupport {
    private SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    private MongoAux mDbAux;




        //tm.setStrValue("string-key-" + String.valueOf( pKey));
        //tm.setIntValue( pKey );


    private void test() {
        mDbAux = new MongoAux("Test", "localhost", 27017);

        // Drop the database if exists
        mDbAux.dropDatabase();


        // Create the database with collections and keys
        mDbAux.createDatabase(true);

        // Connect to the databse
        mDbAux.connectToDatabase();

        /*
         * The test message has two db keys, the intValue and strValue fields.
         * see defintions ./sample/xml/TestMessages.xml
         *
         * Since we do not apply amy schema validation we will be allowed to set
         * the strValue to whatever we want.
         */
        TestMessage testMsg = CreateTestMessage.createRandomTestMessage();
        testMsg.setIntValue(100);
        testMsg.setStrValue("key-100");

        // Test Insert Methods
        mDbAux.insertTestMessage(testMsg);
        String tMongoObjectId = testMsg.getMongoId();

        testMsg = mDbAux.findTestMessageByMongoId(tMongoObjectId);
        if (testMsg != null) {
            System.out.println("Find a single message by Mongo-object-id (should find one)");
        } else {
            System.out.println("error: Did not find a single message by Mongo-object-id");
            System.exit(0);
        }


        List<TestMessage> tList = new ArrayList<>();
        for (int tKey = 101; tKey < 111; tKey++) {
            testMsg = CreateTestMessage.createRandomTestMessage();
            testMsg.setIntValue(tKey);
            testMsg.setStrValue("key-" + String.valueOf(tKey));
            tList.add(testMsg);
        }
        mDbAux.insertTestMessage(tList);


        // Test Find Methods

        List<TestMessage> tMsgs = mDbAux.findAllTestMessage();
        System.out.println("Find all messages, Retreived " + tMsgs.size() + " messages (should be 11)");

        List<TestMessage> tml = mDbAux.findTestMessage(102, "key-102");
        System.out.println("Find a single message by all (2) keys Size: " + tml.size() + " (should find one)");

        tml = mDbAux.findTestMessageByIntValue(103);
        System.out.println("Find a single message by single (int) key Size: " + tml.size() + " (should find one)");

        tml = mDbAux.findTestMessageByStrValue("key-103");
        System.out.println("Find a single message by single (String) key Size: " + tml.size() + " (should find one)");


        // Test Update Method
        tml = mDbAux.findTestMessage(102, "key-102");
        testMsg = tml.get(0);
        testMsg.setTimeString("1998-06-11 16:36:32.000");
        mDbAux.updateTestMessage(testMsg.getIntValue().get(), testMsg.getStrValue().get(), testMsg, false);
        tml = mDbAux.findTestMessage(102, "key-102");
        testMsg = tml.get(0);
        System.out.println("Update a single message \"1998-06-11 16:36:32.000\" == \"" + testMsg.getTimeString() + "\"");

        testMsg.setTimeString("1998-06-11 16:16:16.000");
        UpdateResult tRes = mDbAux.updateTestMessageByMongoId(testMsg.getMongoId(), testMsg);

        testMsg.setTimeString("1998-06-11 16:16:16.111");
        tRes = mDbAux.updateTestMessage(testMsg, false);
        if ((tRes.getMatchedCount() != 1) || (tRes.getModifiedCount() != 1)) {
            throw new RuntimeException("Failed to update TestMessage, simple update");
        }

        testMsg.setIntValue(666);
        tRes = mDbAux.updateTestMessage(testMsg, true);
        if ((tRes.getMatchedCount() != 0) || (tRes.getUpsertedId() == null)) {
            throw new RuntimeException("Failed to insert TestMessage, simple update");
        }




        // Test Delete Method
        mDbAux.deleteTestMessageByIntValue( 102 );
        tml = mDbAux.findTestMessageByIntValue(102);
        if ((tml == null) || (tml.size() == 0)) {
            System.out.println("Message sucessfully deleted");
        } else {
            System.out.println("Hmmmm, message NOT sucessfully deleted");
        }

        //
        tml = mDbAux.findTestMessage( 103, "key-103");
        testMsg = tml.get(0);

        long tCount = mDbAux.deleteTestMessageByMongoId( testMsg.getMongoId());
        if (tCount == 0) {
            System.out.println("Hmmmm, message (103) NOT sucessfully deleted");
        } else {
            System.out.println("Message (103) sucessfully deleted");
        }


        // Test CAR extention

        Car carMsg = CreateTestMessage.createRandomCarMessage();
        carMsg.setManufactor("SAAB");
        carMsg.setProductionYear(1967);

        // Test Insert Methods
        mDbAux.insertCar( carMsg );
        String carMongoId = carMsg.getMongoId();

        carMsg = mDbAux.findCarByMongoId(carMongoId);

        System.out.println("Car\n" + carMsg.toString());


    }




    public static void main(String[] pArgs) {
        TestMongoSupport t = new TestMongoSupport();
        t.test();

    }
}
