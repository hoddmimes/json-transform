
    package generated;


    import com.mongodb.BasicDBObject;
    import com.mongodb.MongoClient;
    import com.mongodb.client.MongoCollection;
    import com.mongodb.client.MongoDatabase;
    import com.mongodb.client.MongoIterable;
    import com.mongodb.client.model.CreateCollectionOptions;
    import com.mongodb.client.model.ValidationOptions;
    import org.bson.conversions.Bson;
    import com.mongodb.client.model.UpdateOptions;
    import com.mongodb.client.FindIterable;
    import com.mongodb.client.MongoCursor;
    import com.mongodb.client.model.IndexOptions;


    import org.bson.BsonType;
    import org.bson.Document;
    import org.bson.types.ObjectId;

    import com.mongodb.client.model.Filters;
    import com.mongodb.client.result.DeleteResult;
    import com.mongodb.client.result.UpdateResult;

    import java.util.List;
    import java.util.ArrayList;
    import java.util.stream.Collectors;

    
    import generated.*;
    
    import generated.*;
    
    public class MongoAux {
            private String mDbName;
            private String mDbHost;
            private int mDbPort;

            private MongoClient mClient;
            private MongoDatabase mDb;
            
           private MongoCollection mTestCollection;
           private MongoCollection mCarCollection;

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
              
                 mCarCollection = mDb.getCollection("Car");
              
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
                
                    mCarCollection = null;
                
                 }
            }

            private void close() {
                if (mClient != null) {
                    mClient.close();
                    mDb = null;

            
                    mTestCollection = null;
                
                    mCarCollection = null;
                
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


            private boolean collectionExit( String pCollectionName, MongoIterable<String> pCollectionNameNames ) {
              MongoCursor<String> tItr = pCollectionNameNames.iterator();
              while( tItr.hasNext()) {
                String tName = tItr.next();
                if (tName.compareTo( pCollectionName ) == 0) {
                  return true;
                }
              }
              return false;
            }


            public void createDatabase( boolean pReset ) {
                this.close();
                MongoClient mClient = new MongoClient(mDbHost, mDbPort);
                MongoDatabase tDB = mClient.getDatabase( mDbName );
                MongoIterable<String> tCollectionNames = tDB.listCollectionNames();

                
                       if ((pReset) || (!collectionExit("Test", tCollectionNames ))) {
                          createTestCollection();
                       }
                   
                       if ((pReset) || (!collectionExit("Car", tCollectionNames ))) {
                          createCarCollection();
                       }
                   
            }


            
        private void createTestCollection() {
            ArrayList<DbKey> tKeys = new ArrayList<>();
            
                tKeys.add( new DbKey("intValue", false));
                tKeys.add( new DbKey("strValue", false));

            createCollection("Test", tKeys, null );
        }
    
        private void createCarCollection() {
            ArrayList<DbKey> tKeys = new ArrayList<>();
            
                tKeys.add( new DbKey("manufactor", false));
                tKeys.add( new DbKey("productionYear", false));

            createCollection("Car", tKeys, null );
        }
    
                    public MongoCollection getTestCollection() {
                      return mTestCollection;
                    }
                
                    public MongoCollection getCarCollection() {
                      return mCarCollection;
                    }
                
        /**
        * CRUD DELETE methods
        */
        public long deleteTestMessage( Bson pFilter) {
           DeleteResult tResult = mTestCollection.deleteMany( pFilter );
           return tResult.getDeletedCount();
        }

        public long deleteTestMessageByMongoId( String pMongoObjectId) {
            Bson tFilter=  Filters.eq("_id", new ObjectId(pMongoObjectId));
            DeleteResult tResult = mTestCollection.deleteOne( tFilter );
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
            Document tDoc = pTestMessage.getMongoDocument();
            mTestCollection.insertOne( tDoc );
            ObjectId _tId = tDoc.getObjectId("_id");
            if (_tId != null) {
                pTestMessage.setMongoId( _tId.toString());
            }
        }

        public void insertTestMessage( List<TestMessage> pTestMessageList ) {
           List<Document> tList = pTestMessageList.stream().map( TestMessage::getMongoDocument).collect(Collectors.toList());
           mTestCollection.insertMany( tList );
           for( int i = 0; i < tList.size(); i++ ) {
             ObjectId _tId = tList.get(i).getObjectId("_id");
             if (_tId != null) {
                pTestMessageList.get(i).setMongoId( _tId.toString());
             }
           }
        }

    
        /**
        * CRUD UPDATE (INSERT) methods
        */
        public UpdateResult updateTestMessageByMongoId( String pMongoObjectId, TestMessage pTestMessage ) {
            Bson tFilter=  Filters.eq("_id", new ObjectId(pMongoObjectId));
            Document tDocSet = new Document("$set", pTestMessage.getMongoDocument());
            UpdateResult tUpdSts = mTestCollection.updateOne( tFilter, tDocSet, new UpdateOptions());
            return tUpdSts;
        }

        public UpdateResult updateTestMessage( TestMessage pTestMessage, boolean pUpdateAllowInsert ) {
        UpdateOptions tOptions = new UpdateOptions().upsert(pUpdateAllowInsert);
        Bson tFilter= Filters.and( 
        Filters.eq("intValue", pTestMessage.getIntValue().get()),
        Filters.eq("strValue", pTestMessage.getStrValue().get()));

        Document tDocSet = new Document("$set", pTestMessage.getMongoDocument());

        UpdateResult tUpdSts = mTestCollection.updateOne( tFilter, tDocSet, tOptions);
        return tUpdSts;
        }


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

        public List<TestMessage> findTestMessage( Bson pFilter  ) {
         return findTestMessage( pFilter, null );
        }

        public List<TestMessage> findTestMessage( Bson pFilter, Document pSortDoc  ) {

        FindIterable<Document> tDocuments = (pSortDoc == null) ? this.mTestCollection.find( pFilter ) :
        this.mTestCollection.find( pFilter ).sort( pSortDoc );


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

        public TestMessage findTestMessageByMongoId( String pMongoObjectId ) {
        Bson tFilter=  Filters.eq("_id", new ObjectId(pMongoObjectId));

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
        return (tResult.size() > 0) ? tResult.get(0) : null;
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
        
        /**
        * CRUD DELETE methods
        */
        public long deleteCar( Bson pFilter) {
           DeleteResult tResult = mCarCollection.deleteMany( pFilter );
           return tResult.getDeletedCount();
        }

        public long deleteCarByMongoId( String pMongoObjectId) {
            Bson tFilter=  Filters.eq("_id", new ObjectId(pMongoObjectId));
            DeleteResult tResult = mCarCollection.deleteOne( tFilter );
            return tResult.getDeletedCount();
        }

        
            public long deleteCar( String pManufactor, int pProductionYear ) {
                Bson tKeyFilter= Filters.and( 
                    Filters.eq("manufactor", pManufactor),
                    Filters.eq("productionYear", pProductionYear));

             DeleteResult tResult =  mCarCollection.deleteMany(tKeyFilter);
             return tResult.getDeletedCount();
            }
        
            public long deleteCarByManufactor( String pManufactor ) {
                Bson tKeyFilter= Filters.eq("manufactor", pManufactor);
                DeleteResult tResult =  mCarCollection.deleteMany(tKeyFilter);
                return tResult.getDeletedCount();
            }
        
            public long deleteCarByProductionYear( int pProductionYear ) {
                Bson tKeyFilter= Filters.eq("productionYear", pProductionYear);
                DeleteResult tResult =  mCarCollection.deleteMany(tKeyFilter);
                return tResult.getDeletedCount();
            }
        
        /**
        * CRUD INSERT methods
        */
        public void insertCar( Car pCar ) {
            Document tDoc = pCar.getMongoDocument();
            mCarCollection.insertOne( tDoc );
            ObjectId _tId = tDoc.getObjectId("_id");
            if (_tId != null) {
                pCar.setMongoId( _tId.toString());
            }
        }

        public void insertCar( List<Car> pCarList ) {
           List<Document> tList = pCarList.stream().map( Car::getMongoDocument).collect(Collectors.toList());
           mCarCollection.insertMany( tList );
           for( int i = 0; i < tList.size(); i++ ) {
             ObjectId _tId = tList.get(i).getObjectId("_id");
             if (_tId != null) {
                pCarList.get(i).setMongoId( _tId.toString());
             }
           }
        }

    
        /**
        * CRUD UPDATE (INSERT) methods
        */
        public UpdateResult updateCarByMongoId( String pMongoObjectId, Car pCar ) {
            Bson tFilter=  Filters.eq("_id", new ObjectId(pMongoObjectId));
            Document tDocSet = new Document("$set", pCar.getMongoDocument());
            UpdateResult tUpdSts = mCarCollection.updateOne( tFilter, tDocSet, new UpdateOptions());
            return tUpdSts;
        }

        public UpdateResult updateCar( Car pCar, boolean pUpdateAllowInsert ) {
        UpdateOptions tOptions = new UpdateOptions().upsert(pUpdateAllowInsert);
        Bson tFilter= Filters.and( 
        Filters.eq("manufactor", pCar.getManufactor().get()),
        Filters.eq("productionYear", pCar.getProductionYear().get()));

        Document tDocSet = new Document("$set", pCar.getMongoDocument());

        UpdateResult tUpdSts = mCarCollection.updateOne( tFilter, tDocSet, tOptions);
        return tUpdSts;
        }


        public UpdateResult updateCar( String pManufactor, int pProductionYear, Car pCar, boolean pUpdateAllowInsert ) {
          UpdateOptions tOptions = new UpdateOptions().upsert(pUpdateAllowInsert);
          Bson tFilter= Filters.and( 
             Filters.eq("manufactor", pManufactor),
             Filters.eq("productionYear", pProductionYear));

           Document tDocSet = new Document("$set", pCar.getMongoDocument());

           UpdateResult tUpdSts = mCarCollection.updateOne( tFilter, tDocSet, tOptions);
           return tUpdSts;
        }

        public UpdateResult updateCar( Bson pFilter, Car pCar, boolean pUpdateAllowInsert ) {
           UpdateOptions tOptions = new UpdateOptions().upsert(pUpdateAllowInsert);
           Document tDocSet = new Document("$set", pCar.getMongoDocument());
           UpdateResult tUpdSts = mCarCollection.updateOne( pFilter, tDocSet, tOptions);
           return tUpdSts;
        }
    
        /**
        * CRUD FIND methods
        */

        public List<Car> findCar( Bson pFilter  ) {
         return findCar( pFilter, null );
        }

        public List<Car> findCar( Bson pFilter, Document pSortDoc  ) {

        FindIterable<Document> tDocuments = (pSortDoc == null) ? this.mCarCollection.find( pFilter ) :
        this.mCarCollection.find( pFilter ).sort( pSortDoc );


        if (tDocuments == null) {
        return null;
        }

        List<Car> tResult = new ArrayList<>();
        MongoCursor<Document> tIter = tDocuments.iterator();
        while ( tIter.hasNext()) {
        Document tDoc = tIter.next();
        Car tCar = new Car();
        tCar.decodeMongoDocument( tDoc );
        tResult.add( tCar );
        }
        return tResult;
        }



        public List<Car> findAllCar()
        {
           List<Car> tResult = new ArrayList<>();

           FindIterable<Document> tDocuments  = this.mCarCollection.find();
           MongoCursor<Document> tIter = tDocuments.iterator();
           while( tIter.hasNext()) {
               Document tDoc = tIter.next();
               Car tCar = new Car();
               tCar.decodeMongoDocument( tDoc );
               tResult.add(tCar);
            }
            return tResult;
        }

        public Car findCarByMongoId( String pMongoObjectId ) {
        Bson tFilter=  Filters.eq("_id", new ObjectId(pMongoObjectId));

        FindIterable<Document> tDocuments = this.mCarCollection.find( tFilter );
        if (tDocuments == null) {
            return null;
        }

        List<Car> tResult = new ArrayList<>();
        MongoCursor<Document> tIter = tDocuments.iterator();
        while ( tIter.hasNext()) {
        Document tDoc = tIter.next();
        Car tCar = new Car();
        tCar.decodeMongoDocument( tDoc );
        tResult.add( tCar );
        }
        return (tResult.size() > 0) ? tResult.get(0) : null;
        }


        public List<Car> findCar( String pManufactor, int pProductionYear ) {
        Bson tFilter= Filters.and( 
        Filters.eq("manufactor", pManufactor),
        Filters.eq("productionYear", pProductionYear));


        FindIterable<Document> tDocuments = this.mCarCollection.find( tFilter );
        if (tDocuments == null) {
           return null;
        }

        List<Car> tResult = new ArrayList<>();
        MongoCursor<Document> tIter = tDocuments.iterator();
        while ( tIter.hasNext()) {
           Document tDoc = tIter.next();
           Car tCar = new Car();
           tCar.decodeMongoDocument( tDoc );
           tResult.add( tCar );
        }
        return tResult;
        }

        
            public List<Car> findCarByManufactor( String pManufactor ) {
            List<Car> tResult = new ArrayList<>();
            Bson tFilter= Filters.eq("manufactor", pManufactor);

            FindIterable<Document> tDocuments  = this.mCarCollection.find( tFilter );
            MongoCursor<Document> tIter = tDocuments.iterator();
            while( tIter.hasNext()) {
            Document tDoc = tIter.next();
            Car tCar = new Car();
            tCar.decodeMongoDocument( tDoc );
            tResult.add(tCar);
            }
            return tResult;
            }
        
            public List<Car> findCarByProductionYear( int pProductionYear ) {
            List<Car> tResult = new ArrayList<>();
            Bson tFilter= Filters.eq("productionYear", pProductionYear);

            FindIterable<Document> tDocuments  = this.mCarCollection.find( tFilter );
            MongoCursor<Document> tIter = tDocuments.iterator();
            while( tIter.hasNext()) {
            Document tDoc = tIter.next();
            Car tCar = new Car();
            tCar.decodeMongoDocument( tDoc );
            tResult.add(tCar);
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
        