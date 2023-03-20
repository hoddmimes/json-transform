package test;
import com.hoddmimes.transform.DateUtils;
import com.mongodb.client.result.UpdateResult;
import generated.*;
import org.junit.Assert;
import org.junit.Test;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class TestMongoSupportTest {
    private SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    private MongoAux mDbAux;




        //tm.setStrValue("string-key-" + String.valueOf( pKey));
        //tm.setIntValue( pKey );


    @Test
    public void test() {
        mDbAux = new MongoAux("Test", "localhost", 27017);

        // Drop the database if exists
        mDbAux.dropDatabase();


        // Create the database with collections and keys
        mDbAux.createDatabase(true);

        // Connect to the databse
        mDbAux.connectToDatabase();

        // Test Date / Time attributes
        String tDateString = "2020-01-02";
        String tDateTimeString = "2020-01-02 01:02:03.456";

        /**
         * Insert  a record
         */
        DateTimeMessage dtm = new DateTimeMessage();
        dtm.setDate(DateUtils.stringToLocalDate( tDateString ));
        dtm.setDateTime(DateUtils.stringToLocalDateTime( tDateTimeString ));
        dtm.setValue( 42 );
        mDbAux.insertDateTimeMessage( dtm );

        /**
         * Read the record
         */
        List<DateTimeMessage> dtmLst = mDbAux.findAllDateTimeMessage();
        Assert.assertEquals(dtmLst.size(), 1);
        Assert.assertEquals( DateUtils.localDateToString(dtmLst.get(0).getDate().get()), tDateString );
        Assert.assertEquals( DateUtils.localDateTimeToString(dtmLst.get(0).getDateTime().get()), tDateTimeString );

        /**
         * Find the record based upon keys
         */
        dtmLst = mDbAux.findDateTimeMessage( DateUtils.stringToLocalDate( tDateString), DateUtils.stringToLocalDateTime( tDateTimeString ));
        Assert.assertEquals(dtmLst.size(), 1);
        Assert.assertEquals( DateUtils.localDateToString(dtmLst.get(0).getDate().get()), tDateString );
        Assert.assertEquals( DateUtils.localDateTimeToString(dtmLst.get(0).getDateTime().get()), tDateTimeString );

        /**
         * Find all messages
         */
        dtmLst = mDbAux.findAllDateTimeMessage();
        Assert.assertEquals(dtmLst.size(), 1);
        Assert.assertEquals(tDateString, DateUtils.localDateToString(dtmLst.get(0).getDate().get()));
        Assert.assertEquals(tDateTimeString, DateUtils.localDateTimeToString(dtmLst.get(0).getDateTime().get()));

        /**
         * Update DTM message
         */
        dtm.setValue( 100 );
        mDbAux.updateDateTimeMessage( dtm, true);
        dtmLst = mDbAux.findAllDateTimeMessage();
        Assert.assertEquals(dtmLst.size(), 1);
        Assert.assertEquals(tDateString, DateUtils.localDateToString(dtmLst.get(0).getDate().get()));
        Assert.assertEquals(tDateTimeString, DateUtils.localDateTimeToString(dtmLst.get(0).getDateTime().get()));


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
        Assert.assertNotNull( testMsg );


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
        Assert.assertEquals(tMsgs.size(), 11);

        List<TestMessage> tml = mDbAux.findTestMessage(102, "key-102");
        Assert.assertEquals(tml.size(), 1);
        Assert.assertEquals( (int) tml.get(0).getIntValue().get(), (int) 102 );
        Assert.assertEquals( tml.get(0).getStrValue().get(), "key-102" );

        tml = mDbAux.findTestMessageByIntValue(103);
        Assert.assertEquals(tml.size(), 1);
        Assert.assertEquals( (int) tml.get(0).getIntValue().get(), (int) 103 );

        // Test Update Method
        tml = mDbAux.findTestMessage(102, "key-102");
        testMsg = tml.get(0);
        testMsg.setTimeString("1998-06-11 16:36:32.000");
        mDbAux.updateTestMessage(testMsg.getIntValue().get(), testMsg.getStrValue().get(), testMsg, false);
        tml = mDbAux.findTestMessage(102, "key-102");
        testMsg = tml.get(0);
        Assert.assertEquals(testMsg.getTimeString().get(), "1998-06-11 16:36:32.000");

        testMsg.setTimeString("1998-06-11 16:16:16.000");
        UpdateResult tRes = mDbAux.updateTestMessageByMongoId(testMsg.getMongoId(), testMsg);

        testMsg.setTimeString("1998-06-11 16:16:16.111");
        tRes = mDbAux.updateTestMessage(testMsg, false);
        Assert.assertEquals(tRes.getMatchedCount(), 1 );
        Assert.assertEquals(tRes.getModifiedCount(), 1 );

        testMsg.setIntValue(666);
        tRes = mDbAux.updateTestMessage(testMsg, true);
        Assert.assertEquals(tRes.getMatchedCount(), 0 );
        Assert.assertNotNull(tRes.getUpsertedId());
        Assert.assertEquals(tRes.getModifiedCount(), 0 );


        // Test Delete Method
        mDbAux.deleteTestMessageByIntValue( 102 );
        tml = mDbAux.findTestMessageByIntValue(102);
        Assert.assertEquals( tml.size(), 0);

        //
        tml = mDbAux.findTestMessage( 103, "key-103");
        testMsg = tml.get(0);

        long tCount = mDbAux.deleteTestMessageByMongoId( testMsg.getMongoId());
        Assert.assertEquals( tCount, 1);

    }




    public static void main(String[] pArgs) {
        TestMongoSupportTest t = new TestMongoSupportTest();
        t.test();

    }
}
