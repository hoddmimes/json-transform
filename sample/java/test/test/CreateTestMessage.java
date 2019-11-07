package test;


import generated.CG1;
import generated.CG2;
import generated.TestMessage;
import generated.TestSubMessage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CreateTestMessage
{
    private static  SimpleDateFormat SDF = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss.SSS");
    private static Random cRand = new Random();

    private static byte[] createBytes( int pSize, int pValue ) {
        byte[] b = new byte[ pSize ];
        for( int i = 0; i < pSize; i++) {
            b[i] = (byte) pValue;
        }
        return b;
    }

    private static List<byte[]> createByteVectorList(int pListSize, int pVectorSize, int pValue) {
        ArrayList<byte[]> tList = new ArrayList<>();
        for( int i = 0; i < pListSize; i++ ) {
            tList.add( createBytes( pVectorSize, pValue ));
        }
        return tList;
    }

    private static List<CG2> createCG2List(int pSize ) {
        int z = CG2.values().length;
        List<CG2> tList = new ArrayList<>();
        for( int i = 0; i < pSize; i++ ) {
            tList.add( CG2.values()[ i % z]);
        }
        return tList;
    }

    static TestSubMessage createSubMessage() {
        TestSubMessage sm = new TestSubMessage();
        int tArrayValue = cRand.nextInt(22) + 200;
        sm.setIntArray( createIntegerArray(cRand.nextInt(3) + 3, cRand.nextInt(100)));
        sm.setIntValue(cRand.nextInt(22) + 200);
        sm.setStringArray( createStringArray(cRand.nextInt(3) + 3, "str-submsg-array"));
        sm.setStrValue("submsg-string-value");
        return sm;
    }

    static List<TestSubMessage> createMsgArray( int pSize ) {
        List<TestSubMessage> tList = new ArrayList<>();
        for( int i = 0; i < pSize; i++ ) {
            tList.add( createSubMessage());
        }
        return tList;
    }


    private static List<String> createStringArray( int pSize, String pPrefix ) {
        ArrayList<String> tList= new ArrayList( pSize );
        for( int i = 0; i < pSize; i++ ) {
           tList.add( new String( pPrefix) + "-" +String.valueOf((i+1)));
        }
        return tList;
    }

    private static List<Integer> createIntegerArray( int pSize, int pOffset ) {
        ArrayList<Integer> tList= new ArrayList( pSize );
        for( int i = 0; i < pSize; i++ ) {
            tList.add(pOffset + i);
        }
        return tList;
    }

    public static TestMessage createRandomTestMessage() {
        TestMessage testMsg = new TestMessage();
        testMsg.setBoolValue(true);
        testMsg.setBytesArrayValue(createByteVectorList(cRand.nextInt(5) + 1, cRand.nextInt(5) + 1, cRand.nextInt(56)));
        testMsg.setBytesValue(createBytes(cRand.nextInt(5) + 1, cRand.nextInt(7)));
        testMsg.setByteValue((byte) cRand.nextInt(100));
        testMsg.setConstArray(createCG2List(cRand.nextInt(5) + 1));
        testMsg.setConstValue(CG1.X1);
        testMsg.setIntArray(createIntegerArray(cRand.nextInt(5) + 1, new Integer(101)));
        testMsg.setIntValue(102);
        testMsg.setLongValue(1010101010101L);
        testMsg.setMsgArray(createMsgArray(5));
        testMsg.setMsgValue(createSubMessage());
        testMsg.setShortValue((short) 33);
        testMsg.setStringArray( createStringArray(cRand.nextInt(5) + 1, "str-åäö-array"));
        testMsg.setStrValue("string-value-åäö");
        testMsg.setTimeString( SDF.format(System.currentTimeMillis()));
        return testMsg;
    }

    public static TestMessage createValidTestMessage() {

        TestMessage testMsg = createRandomTestMessage();

        // Make sure that all fields apply to the schema rules defined on the
        // definition file ./sample/xml/TestMessage.xml
        testMsg.getMsgValue().get().setIntValue(44); // Value must me between 42 and 84
        testMsg.getMsgValue().get().setStrValue("fooXYZbar"); // value must match regexp "foo[X-Z]+bar"
        testMsg.setShortValue( (short) 66 ); // Value must be between 0 and 100 and be an multiple of 3
        testMsg.setStrValue("foo-bar-1234"); // value must match regexp "foo-\w*-\d*"
        testMsg.setTimeString( SDF.format(System.currentTimeMillis())); // Value must match regexp "\d+-\d+-\d+ \d+:\d+:\d+\.\d+"
        List<TestSubMessage> sml = testMsg.getMsgArray().get();
        for( TestSubMessage sm : sml ) {
            sm.setIntValue(42);
            sm.setStrValue("fooXXXbar");
        }

        return testMsg;
    }
}
