import com.hoddmimes.transform.MongoDecoder;
import com.hoddmimes.transform.MongoEncoder;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.*;

public class MongoTest
{

    private MongoClient   mDbClient;
    private MongoDatabase mDb;

    private MongoCollection mTestCollection;
    public static void main(String[] args) {
        MongoTest mt = new MongoTest();
        mt.test();
    }


    private void test() {
        connectToDB( "localhost","Test", 27017 );
        //insertMap();
        getMap();
    }

    private void getMap() {
        FindIterable<Document> tDocuments = mTestCollection.find();
        MongoCursor<Document> tIter = tDocuments.iterator();
        while (tIter.hasNext()) {
            Document tDoc = tIter.next();
            MongoDecoder tDecoder = new MongoDecoder(tDoc);
            List<Map<String, String>> tMapList = tDecoder.readMapArray("mapValue");
            for (Map<String, String> tMap : tMapList) {
                for (Map.Entry<String, String> tEntry : tMap.entrySet()) {
                    System.out.println("key: " + tEntry.getKey() + " value: " + tEntry.getValue());
                }
                System.out.println("\n");
            }
        }
    }
    private void insertMap() {
        MongoEncoder tEncoder = new MongoEncoder();

        ArrayList<Map<String,String>> mapList = new ArrayList();
        for (int i = 0; i < 5; i++) {
            Map<String, String> tMap = new HashMap<>();
            for (int j = 0; j < 5; j++) {
                tMap.put("key-" + String.valueOf((i * 10) + j), "value-" + String.valueOf((i * 10) + j));
            }
            mapList.add(tMap);
        }


        tEncoder.addMapArray("mapValue", mapList );
        mTestCollection.insertOne( tEncoder.getDoc() );

        System.out.println("Document inserted id ");
    }

    private void connectToDB( String pDbHost, String pDbName, int pDbPort ) {
        try {
            mDbClient = new MongoClient( pDbHost, pDbPort);
            mDb = mDbClient.getDatabase( pDbName );

            mTestCollection = mDb.getCollection("test-collection");
        }
        catch(Exception e ) {
            e.printStackTrace();
        }
    }
}
