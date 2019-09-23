
    package generated;


    import com.mongodb.BasicDBObject;
    import com.mongodb.MongoClient;
    import com.mongodb.client.MongoCollection;
    import com.mongodb.client.MongoDatabase;
    import com.mongodb.client.model.CreateCollectionOptions;
    import com.mongodb.client.model.ValidationOptions;
    import org.bson.conversions.Bson;
    import com.mongodb.client.model.UpdateOptions;
    import com.mongodb.client.FindIterable;
    import com.mongodb.client.MongoCursor;
    import com.mongodb.client.model.IndexOptions;


    import org.bson.BsonType;
    import org.bson.Document;

    import com.mongodb.client.model.Filters;
    import com.mongodb.client.result.DeleteResult;
    import com.mongodb.client.result.UpdateResult;

    import java.util.List;
    import java.util.ArrayList;
    import java.util.stream.Collectors;

    
    import generated.*;
    
    public class MongoAux {
            private String mDbName;
            private String mDbHost;
            private int mDbPort;

            private MongoClient mClient;
            private MongoDatabase mDb;
            
           private MongoCollection mTestCollection;

            public MongoAux( String pDbName, String pDbHost, int pDbPort ) {
               mDbName = pDbName;
               mDbHost = pDbHost;
               mDbPort = pDbPort;
            }

            public void connectToDatabase() {
               try {
                 mClient = new MongoClient( mDbHost, mDbPort);
                 mDb = mClient.getDatabase( mDbName );
            
                 mTestCollection = mDb.getCollection("Test");
              
               }
               catch(Exception e ) {
                  e.printStackTrace();
               }
            }

            public void  dropDatabase()
            {
                MongoClient tClient = new MongoClient(mDbHost, mDbPort);
                MongoDatabase tDb = tClient.getDatabase( mDbName );


                try {
                    tDb.drop();
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                finally {
                   if (mClient != null) {
                        mClient.close();
                   }
                   mDb = null;
            
                    mTestCollection = null;
                
                 }
            }

            private void close() {
                if (mClient != null) {
                    mClient.close();
                    mDb = null;

            
                    mTestCollection = null;
                
                }
            }




            private void createCollection(String pCollectionName, List<DbKey> pKeys, Bson pValidator )
            {
                MongoClient tClient = new MongoClient(mDbHost, mDbPort);
                MongoDatabase db = tClient.getDatabase( mDbName );

                //ValidationOptions validationOptions = new ValidationOptions().validator(pValidator);
                //CreateCollectionOptions tOptions = new CreateCollectionOptions().validationOptions(validationOptions);
                //db.createCollection(pCollectionName, tOptions );
                db.createCollection(pCollectionName);

                MongoCollection tCollection = db.getCollection( pCollectionName );
                if (pKeys != null) {
                  for ( DbKey dbk : pKeys ) {
                       tCollection.createIndex(new BasicDBObject(dbk.mKeyName, 1), new IndexOptions().unique(dbk.mUnique));
                  }
                }
                tClient.close();
            }


            public void createDatabase() {
                this.close();
                MongoClient mClient = new MongoClient(mDbHost, mDbPort);
                MongoDatabase tDB = mClient.getDatabase( mDbName );
                
                        createTestCollection();
                   
            }


            
        private void createTestCollection() {
            ArrayList<DbKey> tKeys = new ArrayList<>();
            
                    tKeys.add( new DbKey("intValue", true));
                    tKeys.add( new DbKey("strValue", true));

            createCollection("Test", tKeys, null );
        }
    
        /**
        * CRUD DELETE methods
        */
        public long deleteTestMessage( Bson pFilter) {
           DeleteResult tResult = mTestCollection.deleteMany( pFilter );
           return tResult.getDeletedCount();
        }

        
            public long deleteTestMessage( int pIntValue, String pStrValue ) {
                Bson tKeyFilter= Filters.and( 
                    Filters.eq("intValue", pIntValue),
                    Filters.eq("strValue", pStrValue));

             DeleteResult tResult =  mTestCollection.deleteMany(tKeyFilter);
             return tResult.getDeletedCount();
            }
        
            public long deleteTestMessageByIntValue( int pIntValue ) {
                Bson tKeyFilter= Filters.eq("intValue", pIntValue);
                DeleteResult tResult =  mTestCollection.deleteMany(tKeyFilter);
                return tResult.getDeletedCount();
            }
        
            public long deleteTestMessageByStrValue( String pStrValue ) {
                Bson tKeyFilter= Filters.eq("strValue", pStrValue);
                DeleteResult tResult =  mTestCollection.deleteMany(tKeyFilter);
                return tResult.getDeletedCount();
            }
        
        /**
        * CRUD INSERT methods
        */
        public void insertTestMessage( TestMessage pTestMessage ) {
           mTestCollection.insertOne( pTestMessage.getMongoDocument());
        }

        public void insertTestMessage( List<TestMessage> pTestMessageList ) {
           List<Document> tList = pTestMessageList.stream().map( TestMessage::getMongoDocument).collect(Collectors.toList());
           mTestCollection.insertMany( tList );
        }

    
        /**
        * CRUD UPDATE (INSERT) methods
        */
        public UpdateResult updateTestMessage( int pIntValue, String pStrValue, TestMessage pTestMessage, boolean pUpdateAllowInsert ) {
          UpdateOptions tOptions = new UpdateOptions().upsert(pUpdateAllowInsert);
          Bson tFilter= Filters.and( 
             Filters.eq("intValue", pIntValue),
             Filters.eq("strValue", pStrValue));

           Document tDocSet = new Document("$set", pTestMessage.getMongoDocument());

           UpdateResult tUpdSts = mTestCollection.updateOne( tFilter, tDocSet, tOptions);
           return tUpdSts;
        }

        public UpdateResult updateTestMessage( Bson pFilter, TestMessage pTestMessage, boolean pUpdateAllowInsert ) {
           UpdateOptions tOptions = new UpdateOptions().upsert(pUpdateAllowInsert);
           Document tDocSet = new Document("$set", pTestMessage.getMongoDocument());
           UpdateResult tUpdSts = mTestCollection.updateOne( pFilter, tDocSet, tOptions);
           return tUpdSts;
        }
    
        /**
        * CRUD FIND methods
        */
        public List<TestMessage> findAllTestMessage()
        {
           List<TestMessage> tResult = new ArrayList<>();

           FindIterable<Document> tDocuments  = this.mTestCollection.find();
           MongoCursor<Document> tIter = tDocuments.iterator();
           while( tIter.hasNext()) {
               Document tDoc = tIter.next();
               TestMessage tTestMessage = new TestMessage();
               tTestMessage.decodeMongoDocument( tDoc );
               tResult.add(tTestMessage);
            }
            return tResult;
        }

        public List<TestMessage> findTestMessage( int pIntValue, String pStrValue ) {
        Bson tFilter= Filters.and( 
        Filters.eq("intValue", pIntValue),
        Filters.eq("strValue", pStrValue));


        FindIterable<Document> tDocuments = this.mTestCollection.find( tFilter );
        if (tDocuments == null) {
           return null;
        }

        List<TestMessage> tResult = new ArrayList<>();
        MongoCursor<Document> tIter = tDocuments.iterator();
        while ( tIter.hasNext()) {
           Document tDoc = tIter.next();
           TestMessage tTestMessage = new TestMessage();
           tTestMessage.decodeMongoDocument( tDoc );
           tResult.add( tTestMessage );
        }
        return tResult;
        }

        
            public List<TestMessage> findTestMessageByIntValue( int pIntValue ) {
            List<TestMessage> tResult = new ArrayList<>();
            Bson tFilter= Filters.eq("intValue", pIntValue);

            FindIterable<Document> tDocuments  = this.mTestCollection.find( tFilter );
            MongoCursor<Document> tIter = tDocuments.iterator();
            while( tIter.hasNext()) {
            Document tDoc = tIter.next();
            TestMessage tTestMessage = new TestMessage();
            tTestMessage.decodeMongoDocument( tDoc );
            tResult.add(tTestMessage);
            }
            return tResult;
            }
        
            public List<TestMessage> findTestMessageByStrValue( String pStrValue ) {
            List<TestMessage> tResult = new ArrayList<>();
            Bson tFilter= Filters.eq("strValue", pStrValue);

            FindIterable<Document> tDocuments  = this.mTestCollection.find( tFilter );
            MongoCursor<Document> tIter = tDocuments.iterator();
            while( tIter.hasNext()) {
            Document tDoc = tIter.next();
            TestMessage tTestMessage = new TestMessage();
            tTestMessage.decodeMongoDocument( tDoc );
            tResult.add(tTestMessage);
            }
            return tResult;
            }
        

            class DbKey {
                String      mKeyName;
                boolean     mUnique;

                DbKey( String pKeyName, boolean pUnique ) {
                    mKeyName = pKeyName;
                    mUnique = pUnique;
                }
            }

    

            }
        