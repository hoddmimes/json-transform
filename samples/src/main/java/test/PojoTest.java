package test;


import com.hoddmimes.jsontransform.JsonDecoder;
import generated.BarMessage;
import generated.FieMessage;
import generated.FooMessage;


import java.util.*;

public class PojoTest {

    public static void main(String pArgs[]) {
        PojoTest t = new PojoTest();
        t.parseArguments(pArgs);
        t.test();
    }

    private void parseArguments(String pArgs[]) {
    }

    Map<String,String> getMap( int pSize) {
        Map<String,String> m = new HashMap<>();
        for (int i = 0; i < pSize; i++) {
            m.put( "prop-" + (i+1), "value-" + (i+1));
        }
        return m;
    }



    private void test() {
        String jString  = null;

        List<Map<String,String>> tMapList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
           tMapList.add( getMap(i+2) );
        }

        BarMessage bm = new BarMessage();
        bm.setBoolValue( true );
        bm.setIntValue(1234);
        bm.setStrValue("bar-message");
        bm.setDoubleValue(23.23d);

        List<BarMessage> tBarList = new ArrayList<>();
        tBarList.add( bm );

        FooMessage fm = new FooMessage();
        fm.setStrValue("foo-message");
        fm.setIntValue(123456);
        fm.setBarArray( tBarList );
        fm.setMapValue( getMap(4));
        fm.setMapArray( tMapList );


        // to Json string
        jString = fm.toJson().toString();
        System.out.println( "printing-jstring: " + jString );

        FooMessage fooMsg = new FooMessage();
        fooMsg.decode( new JsonDecoder( jString ));

        System.out.println( "printing-foo-decoded: " + fooMsg.toString() + "\n\n\n" );

        // Json String to object
        try {
            int cLoopCount = 100000;
            FooMessage fo = new FooMessage();
            fo.decode( new JsonDecoder( jString ));
            System.out.println("\n\n\n" + fm.toString());


            long tStartTime = System.nanoTime() / 1000L;
            for (int i = 0; i < cLoopCount; i++) {
                String tStr = fm.toJson().toString();
                FooMessage xfo = new FooMessage();
                xfo.decode( new JsonDecoder( jString ));
            }
            long tExecTime = (System.nanoTime()/ 1000L) - tStartTime;
            System.out.println(" Exec time: " + tExecTime + " ms, Per encode/decode: " + (tExecTime / cLoopCount) + " ms.");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
