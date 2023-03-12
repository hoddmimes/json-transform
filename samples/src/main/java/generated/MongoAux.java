
/*
 * Copyright (c)  Hoddmimes Solution AB 2021.
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package generated;


import com.hoddmimes.jsontransform.DateUtils;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MongoAux {
    private final String mDbName;
    private final String mDbHost;
    private final int mDbPort;

    private MongoClient mClient;
    private MongoDatabase mDb;

    private MongoCollection mDateTimeTestCollection;
    private MongoCollection mTestCollection;

    public MongoAux(String pDbName, String pDbHost, int pDbPort) {
        mDbName = pDbName;
        mDbHost = pDbHost;
        mDbPort = pDbPort;
    }

    public void connectToDatabase() {
        try {
            mClient = new MongoClient(mDbHost, mDbPort);
            mDb = mClient.getDatabase(mDbName);

            mDateTimeTestCollection = mDb.getCollection("DateTimeTest");

            mTestCollection = mDb.getCollection("Test");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dropDatabase() {
        MongoClient tClient = new MongoClient(mDbHost, mDbPort);
        MongoDatabase tDb = tClient.getDatabase(mDbName);


        try {
            tDb.drop();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mClient != null) {
                mClient.close();
            }
            mDb = null;

            mDateTimeTestCollection = null;

            mTestCollection = null;

        }
    }

    private void close() {
        if (mClient != null) {
            mClient.close();
            mDb = null;


            mDateTimeTestCollection = null;

            mTestCollection = null;

        }
    }


    private void createCollection(String pCollectionName, List<DbKey> pKeys, Bson pValidator) {
        MongoClient tClient = new MongoClient(mDbHost, mDbPort);
        MongoDatabase db = tClient.getDatabase(mDbName);

        //ValidationOptions validationOptions = new ValidationOptions().validator(pValidator);
        //CreateCollectionOptions tOptions = new CreateCollectionOptions().validationOptions(validationOptions);
        //db.createCollection(pCollectionName, tOptions );
        db.createCollection(pCollectionName);

        MongoCollection tCollection = db.getCollection(pCollectionName);
        if (pKeys != null) {
            for (DbKey dbk : pKeys) {
                tCollection.createIndex(new BasicDBObject(dbk.mKeyName, 1), new IndexOptions().unique(dbk.mUnique));
            }
        }
        tClient.close();
    }


    private boolean collectionExit(String pCollectionName, MongoIterable<String> pCollectionNameNames) {
        MongoCursor<String> tItr = pCollectionNameNames.iterator();
        while (tItr.hasNext()) {
            String tName = tItr.next();
            if (tName.compareTo(pCollectionName) == 0) {
                return true;
            }
        }
        return false;
    }


    public void createDatabase(boolean pReset) {
        this.close();
        MongoClient mClient = new MongoClient(mDbHost, mDbPort);
        MongoDatabase tDB = mClient.getDatabase(mDbName);
        MongoIterable<String> tCollectionNames = tDB.listCollectionNames();


        if ((pReset) || (!collectionExit("DateTimeTest", tCollectionNames))) {
            createDateTimeTestCollection();
        }

        if ((pReset) || (!collectionExit("Test", tCollectionNames))) {
            createTestCollection();
        }

    }


    private void createDateTimeTestCollection() {
        ArrayList<DbKey> tKeys = new ArrayList<>();

        tKeys.add(new DbKey("date", false));
        tKeys.add(new DbKey("dateTime", false));

        createCollection("DateTimeTest", tKeys, null);
    }

    private void createTestCollection() {
        ArrayList<DbKey> tKeys = new ArrayList<>();

        tKeys.add(new DbKey("intValue", false));
        tKeys.add(new DbKey("strValue", false));

        createCollection("Test", tKeys, null);
    }

    public MongoCollection getDateTimeTestCollection() {
        return mDateTimeTestCollection;
    }

    public MongoCollection getTestCollection() {
        return mTestCollection;
    }

    /**
     * CRUD DELETE methods
     */
    public long deleteDateTimeMessage(Bson pFilter) {
        DeleteResult tResult = mDateTimeTestCollection.deleteMany(pFilter);
        return tResult.getDeletedCount();
    }

    public long deleteDateTimeMessageByMongoId(String pMongoObjectId) {
        Bson tFilter = Filters.eq("_id", new ObjectId(pMongoObjectId));
        DeleteResult tResult = mDateTimeTestCollection.deleteOne(tFilter);
        return tResult.getDeletedCount();
    }


    public long deleteDateTimeMessage(LocalDate pDate, LocalDateTime pDateTime) {

        Bson tKeyFilter = Filters.and(
                Filters.eq("date", DateUtils.localDateToString(pDate)),
                Filters.eq("dateTime", DateUtils.localDateTimeToString(pDateTime)));

        DeleteResult tResult = mDateTimeTestCollection.deleteMany(tKeyFilter);
        return tResult.getDeletedCount();
    }

    public long deleteDateTimeMessageByDate(LocalDate pDate) {

        Bson tKeyFilter = Filters.eq("date", DateUtils.localDateToString(pDate));
        DeleteResult tResult = mDateTimeTestCollection.deleteMany(tKeyFilter);
        return tResult.getDeletedCount();
    }

    public long deleteDateTimeMessageByDateTime(LocalDateTime pDateTime) {

        Bson tKeyFilter = Filters.eq("dateTime", DateUtils.localDateTimeToString(pDateTime));
        DeleteResult tResult = mDateTimeTestCollection.deleteMany(tKeyFilter);
        return tResult.getDeletedCount();
    }

    /**
     * CRUD INSERT methods
     */
    public void insertDateTimeMessage(DateTimeMessage pDateTimeMessage) {
        Document tDoc = pDateTimeMessage.getMongoDocument();
        mDateTimeTestCollection.insertOne(tDoc);
        ObjectId _tId = tDoc.getObjectId("_id");
        if (_tId != null) {
            pDateTimeMessage.setMongoId(_tId.toString());
        }
    }

    public void insertDateTimeMessage(List<DateTimeMessage> pDateTimeMessageList) {
        List<Document> tList = pDateTimeMessageList.stream().map(DateTimeMessage::getMongoDocument).collect(Collectors.toList());
        mDateTimeTestCollection.insertMany(tList);
        for (int i = 0; i < tList.size(); i++) {
            ObjectId _tId = tList.get(i).getObjectId("_id");
            if (_tId != null) {
                pDateTimeMessageList.get(i).setMongoId(_tId.toString());
            }
        }
    }


    /**
     * CRUD UPDATE (INSERT) methods
     */
    public UpdateResult updateDateTimeMessageByMongoId(String pMongoObjectId, DateTimeMessage pDateTimeMessage) {
        Bson tFilter = Filters.eq("_id", new ObjectId(pMongoObjectId));
        Document tDocSet = new Document("$set", pDateTimeMessage.getMongoDocument());
        UpdateResult tUpdSts = mDateTimeTestCollection.updateOne(tFilter, tDocSet, new UpdateOptions());
        return tUpdSts;
    }

    public UpdateResult updateDateTimeMessage(DateTimeMessage pDateTimeMessage, boolean pUpdateAllowInsert) {
        UpdateOptions tOptions = new UpdateOptions().upsert(pUpdateAllowInsert);

        Bson tKeyFilter = Filters.and(
                Filters.eq("date", DateUtils.localDateToString(pDateTimeMessage.getDate().get())),
                Filters.eq("dateTime", DateUtils.localDateTimeToString(pDateTimeMessage.getDateTime().get())));


        Document tDocSet = new Document("$set", pDateTimeMessage.getMongoDocument());

        UpdateResult tUpdSts = mDateTimeTestCollection.updateOne(tKeyFilter, tDocSet, tOptions);
        return tUpdSts;
    }


    public UpdateResult updateDateTimeMessage(LocalDate pDate, LocalDateTime pDateTime, DateTimeMessage pDateTimeMessage, boolean pUpdateAllowInsert) {
        UpdateOptions tOptions = new UpdateOptions().upsert(pUpdateAllowInsert);

        Bson tKeyFilter = Filters.and(
                Filters.eq("date", DateUtils.localDateToString(pDate)),
                Filters.eq("dateTime", DateUtils.localDateTimeToString(pDateTime)));


        Document tDocSet = new Document("$set", pDateTimeMessage.getMongoDocument());

        UpdateResult tUpdSts = mDateTimeTestCollection.updateOne(tKeyFilter, tDocSet, tOptions);
        return tUpdSts;
    }

    public UpdateResult updateDateTimeMessage(Bson pFilter, DateTimeMessage pDateTimeMessage, boolean pUpdateAllowInsert) {
        UpdateOptions tOptions = new UpdateOptions().upsert(pUpdateAllowInsert);
        Document tDocSet = new Document("$set", pDateTimeMessage.getMongoDocument());
        UpdateResult tUpdSts = mDateTimeTestCollection.updateOne(pFilter, tDocSet, tOptions);
        return tUpdSts;
    }

    /**
     * CRUD FIND methods
     */

    public List<DateTimeMessage> findDateTimeMessage(Bson pFilter) {
        return findDateTimeMessage(pFilter, null);
    }

    public List<DateTimeMessage> findDateTimeMessage(Bson pFilter, Bson pSortDoc) {

        FindIterable<Document> tDocuments = (pSortDoc == null) ? this.mDateTimeTestCollection.find(pFilter) :
                this.mDateTimeTestCollection.find(pFilter).sort(pSortDoc);


        if (tDocuments == null) {
            return null;
        }

        List<DateTimeMessage> tResult = new ArrayList<>();
        MongoCursor<Document> tIter = tDocuments.iterator();
        while (tIter.hasNext()) {
            Document tDoc = tIter.next();
            DateTimeMessage tDateTimeMessage = new DateTimeMessage();
            tDateTimeMessage.decodeMongoDocument(tDoc);
            tResult.add(tDateTimeMessage);
        }
        return tResult;
    }


    public List<DateTimeMessage> findAllDateTimeMessage() {
        List<DateTimeMessage> tResult = new ArrayList<>();

        FindIterable<Document> tDocuments = this.mDateTimeTestCollection.find();
        MongoCursor<Document> tIter = tDocuments.iterator();
        while (tIter.hasNext()) {
            Document tDoc = tIter.next();
            DateTimeMessage tDateTimeMessage = new DateTimeMessage();
            tDateTimeMessage.decodeMongoDocument(tDoc);
            tResult.add(tDateTimeMessage);
        }
        return tResult;
    }

    public DateTimeMessage findDateTimeMessageByMongoId(String pMongoObjectId) {
        Bson tFilter = Filters.eq("_id", new ObjectId(pMongoObjectId));

        FindIterable<Document> tDocuments = this.mDateTimeTestCollection.find(tFilter);
        if (tDocuments == null) {
            return null;
        }

        List<DateTimeMessage> tResult = new ArrayList<>();
        MongoCursor<Document> tIter = tDocuments.iterator();
        while (tIter.hasNext()) {
            Document tDoc = tIter.next();
            DateTimeMessage tDateTimeMessage = new DateTimeMessage();
            tDateTimeMessage.decodeMongoDocument(tDoc);
            tResult.add(tDateTimeMessage);
        }
        return (tResult.size() > 0) ? tResult.get(0) : null;
    }


    public List<DateTimeMessage> findDateTimeMessage(LocalDate pDate, LocalDateTime pDateTime) {

        Bson tKeyFilter = Filters.and(
                Filters.eq("date", DateUtils.localDateToString(pDate)),
                Filters.eq("dateTime", DateUtils.localDateTimeToString(pDateTime)));


        FindIterable<Document> tDocuments = this.mDateTimeTestCollection.find(tKeyFilter);
        if (tDocuments == null) {
            return null;
        }

        List<DateTimeMessage> tResult = new ArrayList<>();
        MongoCursor<Document> tIter = tDocuments.iterator();
        while (tIter.hasNext()) {
            Document tDoc = tIter.next();
            DateTimeMessage tDateTimeMessage = new DateTimeMessage();
            tDateTimeMessage.decodeMongoDocument(tDoc);
            tResult.add(tDateTimeMessage);
        }
        return tResult;
    }


    public List<DateTimeMessage> findDateTimeMessageByDate(LocalDate pDate) {
        List<DateTimeMessage> tResult = new ArrayList<>();

        Bson tKeyFilter = Filters.eq("date", DateUtils.localDateToString(pDate));

        FindIterable<Document> tDocuments = this.mDateTimeTestCollection.find(tKeyFilter);
        MongoCursor<Document> tIter = tDocuments.iterator();
        while (tIter.hasNext()) {
            Document tDoc = tIter.next();
            DateTimeMessage tDateTimeMessage = new DateTimeMessage();
            tDateTimeMessage.decodeMongoDocument(tDoc);
            tResult.add(tDateTimeMessage);
        }
        return tResult;
    }

    public List<DateTimeMessage> findDateTimeMessageByDateTime(LocalDateTime pDateTime) {
        List<DateTimeMessage> tResult = new ArrayList<>();

        Bson tKeyFilter = Filters.eq("dateTime", DateUtils.localDateTimeToString(pDateTime));

        FindIterable<Document> tDocuments = this.mDateTimeTestCollection.find(tKeyFilter);
        MongoCursor<Document> tIter = tDocuments.iterator();
        while (tIter.hasNext()) {
            Document tDoc = tIter.next();
            DateTimeMessage tDateTimeMessage = new DateTimeMessage();
            tDateTimeMessage.decodeMongoDocument(tDoc);
            tResult.add(tDateTimeMessage);
        }
        return tResult;
    }

    /**
     * CRUD DELETE methods
     */
    public long deleteTestMessage(Bson pFilter) {
        DeleteResult tResult = mTestCollection.deleteMany(pFilter);
        return tResult.getDeletedCount();
    }

    public long deleteTestMessageByMongoId(String pMongoObjectId) {
        Bson tFilter = Filters.eq("_id", new ObjectId(pMongoObjectId));
        DeleteResult tResult = mTestCollection.deleteOne(tFilter);
        return tResult.getDeletedCount();
    }


    public long deleteTestMessage(int pIntValue, String pStrValue) {

        Bson tKeyFilter = Filters.and(
                Filters.eq("intValue", pIntValue),
                Filters.eq("strValue", pStrValue));

        DeleteResult tResult = mTestCollection.deleteMany(tKeyFilter);
        return tResult.getDeletedCount();
    }

    public long deleteTestMessageByIntValue(int pIntValue) {

        Bson tKeyFilter = Filters.eq("intValue", pIntValue);
        DeleteResult tResult = mTestCollection.deleteMany(tKeyFilter);
        return tResult.getDeletedCount();
    }

    public long deleteTestMessageByStrValue(String pStrValue) {

        Bson tKeyFilter = Filters.eq("strValue", pStrValue);
        DeleteResult tResult = mTestCollection.deleteMany(tKeyFilter);
        return tResult.getDeletedCount();
    }

    /**
     * CRUD INSERT methods
     */
    public void insertTestMessage(TestMessage pTestMessage) {
        Document tDoc = pTestMessage.getMongoDocument();
        mTestCollection.insertOne(tDoc);
        ObjectId _tId = tDoc.getObjectId("_id");
        if (_tId != null) {
            pTestMessage.setMongoId(_tId.toString());
        }
    }

    public void insertTestMessage(List<TestMessage> pTestMessageList) {
        List<Document> tList = pTestMessageList.stream().map(TestMessage::getMongoDocument).collect(Collectors.toList());
        mTestCollection.insertMany(tList);
        for (int i = 0; i < tList.size(); i++) {
            ObjectId _tId = tList.get(i).getObjectId("_id");
            if (_tId != null) {
                pTestMessageList.get(i).setMongoId(_tId.toString());
            }
        }
    }


    /**
     * CRUD UPDATE (INSERT) methods
     */
    public UpdateResult updateTestMessageByMongoId(String pMongoObjectId, TestMessage pTestMessage) {
        Bson tFilter = Filters.eq("_id", new ObjectId(pMongoObjectId));
        Document tDocSet = new Document("$set", pTestMessage.getMongoDocument());
        UpdateResult tUpdSts = mTestCollection.updateOne(tFilter, tDocSet, new UpdateOptions());
        return tUpdSts;
    }

    public UpdateResult updateTestMessage(TestMessage pTestMessage, boolean pUpdateAllowInsert) {
        UpdateOptions tOptions = new UpdateOptions().upsert(pUpdateAllowInsert);

        Bson tKeyFilter = Filters.and(
                Filters.eq("intValue", pTestMessage.getIntValue().get()),
                Filters.eq("strValue", pTestMessage.getStrValue().get()));


        Document tDocSet = new Document("$set", pTestMessage.getMongoDocument());

        UpdateResult tUpdSts = mTestCollection.updateOne(tKeyFilter, tDocSet, tOptions);
        return tUpdSts;
    }


    public UpdateResult updateTestMessage(int pIntValue, String pStrValue, TestMessage pTestMessage, boolean pUpdateAllowInsert) {
        UpdateOptions tOptions = new UpdateOptions().upsert(pUpdateAllowInsert);

        Bson tKeyFilter = Filters.and(
                Filters.eq("intValue", pIntValue),
                Filters.eq("strValue", pStrValue));


        Document tDocSet = new Document("$set", pTestMessage.getMongoDocument());

        UpdateResult tUpdSts = mTestCollection.updateOne(tKeyFilter, tDocSet, tOptions);
        return tUpdSts;
    }

    public UpdateResult updateTestMessage(Bson pFilter, TestMessage pTestMessage, boolean pUpdateAllowInsert) {
        UpdateOptions tOptions = new UpdateOptions().upsert(pUpdateAllowInsert);
        Document tDocSet = new Document("$set", pTestMessage.getMongoDocument());
        UpdateResult tUpdSts = mTestCollection.updateOne(pFilter, tDocSet, tOptions);
        return tUpdSts;
    }

    /**
     * CRUD FIND methods
     */

    public List<TestMessage> findTestMessage(Bson pFilter) {
        return findTestMessage(pFilter, null);
    }

    public List<TestMessage> findTestMessage(Bson pFilter, Bson pSortDoc) {

        FindIterable<Document> tDocuments = (pSortDoc == null) ? this.mTestCollection.find(pFilter) :
                this.mTestCollection.find(pFilter).sort(pSortDoc);


        if (tDocuments == null) {
            return null;
        }

        List<TestMessage> tResult = new ArrayList<>();
        MongoCursor<Document> tIter = tDocuments.iterator();
        while (tIter.hasNext()) {
            Document tDoc = tIter.next();
            TestMessage tTestMessage = new TestMessage();
            tTestMessage.decodeMongoDocument(tDoc);
            tResult.add(tTestMessage);
        }
        return tResult;
    }


    public List<TestMessage> findAllTestMessage() {
        List<TestMessage> tResult = new ArrayList<>();

        FindIterable<Document> tDocuments = this.mTestCollection.find();
        MongoCursor<Document> tIter = tDocuments.iterator();
        while (tIter.hasNext()) {
            Document tDoc = tIter.next();
            TestMessage tTestMessage = new TestMessage();
            tTestMessage.decodeMongoDocument(tDoc);
            tResult.add(tTestMessage);
        }
        return tResult;
    }

    public TestMessage findTestMessageByMongoId(String pMongoObjectId) {
        Bson tFilter = Filters.eq("_id", new ObjectId(pMongoObjectId));

        FindIterable<Document> tDocuments = this.mTestCollection.find(tFilter);
        if (tDocuments == null) {
            return null;
        }

        List<TestMessage> tResult = new ArrayList<>();
        MongoCursor<Document> tIter = tDocuments.iterator();
        while (tIter.hasNext()) {
            Document tDoc = tIter.next();
            TestMessage tTestMessage = new TestMessage();
            tTestMessage.decodeMongoDocument(tDoc);
            tResult.add(tTestMessage);
        }
        return (tResult.size() > 0) ? tResult.get(0) : null;
    }


    public List<TestMessage> findTestMessage(int pIntValue, String pStrValue) {

        Bson tKeyFilter = Filters.and(
                Filters.eq("intValue", pIntValue),
                Filters.eq("strValue", pStrValue));


        FindIterable<Document> tDocuments = this.mTestCollection.find(tKeyFilter);
        if (tDocuments == null) {
            return null;
        }

        List<TestMessage> tResult = new ArrayList<>();
        MongoCursor<Document> tIter = tDocuments.iterator();
        while (tIter.hasNext()) {
            Document tDoc = tIter.next();
            TestMessage tTestMessage = new TestMessage();
            tTestMessage.decodeMongoDocument(tDoc);
            tResult.add(tTestMessage);
        }
        return tResult;
    }


    public List<TestMessage> findTestMessageByIntValue(int pIntValue) {
        List<TestMessage> tResult = new ArrayList<>();

        Bson tKeyFilter = Filters.eq("intValue", pIntValue);

        FindIterable<Document> tDocuments = this.mTestCollection.find(tKeyFilter);
        MongoCursor<Document> tIter = tDocuments.iterator();
        while (tIter.hasNext()) {
            Document tDoc = tIter.next();
            TestMessage tTestMessage = new TestMessage();
            tTestMessage.decodeMongoDocument(tDoc);
            tResult.add(tTestMessage);
        }
        return tResult;
    }

    public List<TestMessage> findTestMessageByStrValue(String pStrValue) {
        List<TestMessage> tResult = new ArrayList<>();

        Bson tKeyFilter = Filters.eq("strValue", pStrValue);

        FindIterable<Document> tDocuments = this.mTestCollection.find(tKeyFilter);
        MongoCursor<Document> tIter = tDocuments.iterator();
        while (tIter.hasNext()) {
            Document tDoc = tIter.next();
            TestMessage tTestMessage = new TestMessage();
            tTestMessage.decodeMongoDocument(tDoc);
            tResult.add(tTestMessage);
        }
        return tResult;
    }


    class DbKey {
        String mKeyName;
        boolean mUnique;

        DbKey(String pKeyName, boolean pUnique) {
            mKeyName = pKeyName;
            mUnique = pUnique;
        }
    }

}
        