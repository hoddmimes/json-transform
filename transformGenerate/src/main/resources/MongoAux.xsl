<xsl:stylesheet version="3.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xs="http://www.w3.org/2001/XMLSchema"
                xmlns:map="http://www.w3.org/2005/xpath-functions/map"
                xmlns:functx="http://www.functx.com"
                exclude-result-prefixes="map">

    <xsl:output method="text"/>
    <xsl:param name="outPath"/>
    <xsl:param name="package"/>


    <xsl:include href="functx-1.0.1.xsl"/>



    <xsl:template match="/MessagesRoot">
        <xsl:variable name="file" select="concat('file://',$outPath,'MongoAux.java')"/>
        <xsl:result-document href="{$file}" method="text" omit-xml-declaration="yes" encoding="utf-8">
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
            <xsl:apply-templates mode="imports" select="."/>
    public class MongoAux {
            private String mDbName;
            private String mDbHost;
            private int mDbPort;

            private MongoClient mClient;
            private MongoDatabase mDb;
            <xsl:apply-templates mode="declareCollections" select="." />

            public MongoAux( String pDbName, String pDbHost, int pDbPort ) {
               mDbName = pDbName;
               mDbHost = pDbHost;
               mDbPort = pDbPort;
            }

            public void connectToDatabase() {
               try {
                 mClient = new MongoClient( mDbHost, mDbPort);
                 mDb = mClient.getDatabase( mDbName );
            <xsl:for-each select="Messages[@mongoSupport='true']">
              <xsl:for-each select="Message[(@db='true' and @rootMessage='true')]">
                 m<xsl:call-template name="getCollectionName"/>Collection = mDb.getCollection("<xsl:call-template name="getCollectionName"/>");
              </xsl:for-each>
             </xsl:for-each>
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
            <xsl:for-each select="Messages[@mongoSupport='true']">
                <xsl:for-each select="Message[(@db='true' and @rootMessage='true')]">
                    m<xsl:call-template name="getCollectionName"/>Collection = null;
                </xsl:for-each>
            </xsl:for-each>
                 }
            }

            private void close() {
                if (mClient != null) {
                    mClient.close();
                    mDb = null;

            <xsl:for-each select="Messages[@mongoSupport='true']">
                <xsl:for-each select="Message[(@db='true' and @rootMessage='true')]">
                    m<xsl:call-template name="getCollectionName"/>Collection = null;
                </xsl:for-each>
            </xsl:for-each>
                }
            }




            private void createCollection(String pCollectionName, List&lt;DbKey&gt; pKeys, Bson pValidator )
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


            private boolean collectionExit( String pCollectionName, MongoIterable&lt;String&gt; pCollectionNameNames ) {
              MongoCursor&lt;String&gt; tItr = pCollectionNameNames.iterator();
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
                MongoIterable&lt;String&gt; tCollectionNames = tDB.listCollectionNames();

                <xsl:for-each select="Messages[@mongoSupport='true']">
                   <xsl:for-each select="Message[(@db='true' and @rootMessage='true')]">
                       if ((pReset) || (!collectionExit("<xsl:call-template name="getCollectionName"/>", tCollectionNames ))) {
                          create<xsl:call-template name="getCollectionName"/>Collection();
                       }
                   </xsl:for-each>
                </xsl:for-each>
            }


            <xsl:for-each select="Messages[@mongoSupport='true']">
            <xsl:for-each select="Message[(@db='true' and @rootMessage='true')]">
                <xsl:apply-templates mode="createCollections" select="."/>
            </xsl:for-each>
            </xsl:for-each>

            <xsl:for-each select="Messages[@mongoSupport='true']">
                <xsl:for-each select="Message[(@db='true' and @rootMessage='true')]">
                    public MongoCollection get<xsl:call-template name="getCollectionName"/>Collection() {
                      return m<xsl:call-template name="getCollectionName"/>Collection;
                    }
                </xsl:for-each>
            </xsl:for-each>


            <xsl:for-each select="Messages[@mongoSupport='true']">
                <xsl:for-each select="Message[(@db='true' and @rootMessage='true')]">
                    <xsl:apply-templates mode="createCRUD" select="."/>
                </xsl:for-each>
            </xsl:for-each>

            class DbKey {
                String      mKeyName;
                boolean     mUnique;

                DbKey( String pKeyName, boolean pUnique ) {
                    mKeyName = pKeyName;
                    mUnique = pUnique;
                }
            }

            }
            <xsl:message>Created file <xsl:value-of select="$file"/></xsl:message>
        </xsl:result-document>
    </xsl:template>

    <xsl:template mode="createCollections" match="Message">
        private void create<xsl:call-template name="getCollectionName"/>Collection() {
            ArrayList&lt;DbKey&gt; tKeys = new ArrayList&lt;&gt;();
            <xsl:for-each select="Attribute[@dbKey]">
                <xsl:if test="@dbKey='unique'">
                    tKeys.add( new DbKey("<xsl:value-of select='@name'/>", true));</xsl:if>
                <xsl:if test="not(@dbKey='unique')">
                tKeys.add( new DbKey("<xsl:value-of select='@name'/>", false));</xsl:if>
                </xsl:for-each>

            createCollection("<xsl:call-template name="getCollectionName"/>", tKeys, null );
        }
    </xsl:template>


    <xsl:template name="getCollectionNameOneUp">
        <xsl:if test="../@dbCollection">
            <xsl:value-of select="../@dbCollection"/>
        </xsl:if>
        <xsl:if test="not(../@dbCollection)">
            <xsl:value-of select="../@name"/>
        </xsl:if>
    </xsl:template>

    <xsl:template name="getCollectionName">
        <xsl:if test="@dbCollection">
            <xsl:value-of select="@dbCollection"/>
        </xsl:if>
        <xsl:if test="not(@dbCollection)">
            <xsl:value-of select="@name"/>
        </xsl:if>
    </xsl:template>

<xsl:template mode="imports" match="MessagesRoot">
    package <xsl:value-of select="$package"/>;


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
    import java.util.Date;
    import java.time.LocalDate;
    import java.time.LocalDateTime;
    import java.time.LocalDateTime;
    import java.util.ArrayList;
    import java.util.stream.Collectors;
    import com.hoddmimes.transform.DateUtils;

    <xsl:for-each select="Messages[@mongoSupport='true']">
    import <xsl:value-of select="@package"/>.*;
    </xsl:for-each>

</xsl:template>

<xsl:template mode="declareCollections" match="MessagesRoot">
    <xsl:for-each select="Messages[@mongoSupport='true']">
       <xsl:for-each select="Message[(@db='true' and @rootMessage='true')]">
           private MongoCollection m<xsl:call-template name="getCollectionName"/>Collection;</xsl:for-each></xsl:for-each>
</xsl:template>

 <xsl:template mode="createCRUD" match="Message">
     <xsl:apply-templates mode="crudDelete" select="."/>
     <xsl:apply-templates mode="crudInsert" select="."/>
     <xsl:apply-templates mode="crudUpdateInsert" select="."/>
     <xsl:apply-templates mode="crudFind" select="."/>
 </xsl:template>

    <xsl:template name="filterSingle">
        <xsl:if test="@type='LocalDate'">
            Bson tKeyFilter= Filters.eq("<xsl:value-of select='@name'/>", DateUtils.localDateToString(p<xsl:value-of select="functx:capitalize-first(@name)"/>));</xsl:if>
        <xsl:if test="@type='LocalDateTime'">
            Bson tKeyFilter= Filters.eq("<xsl:value-of select='@name'/>", DateUtils.localDateTimeToString(p<xsl:value-of select="functx:capitalize-first(@name)"/>));</xsl:if>
        <xsl:if test="not(@type='LocalDateTime') and not(@type='LocalDate')">
            Bson tKeyFilter= Filters.eq("<xsl:value-of select='@name'/>", p<xsl:value-of select="functx:capitalize-first(@name)"/>); </xsl:if>
    </xsl:template>

    <xsl:template name="filtersParams">
        Bson tKeyFilter= Filters.and( <xsl:for-each select="Attribute[@dbKey]">
        <xsl:if test="@type='LocalDate'">
           Filters.eq("<xsl:value-of select='@name'/>", DateUtils.localDateToString(p<xsl:value-of select="functx:capitalize-first(@name)"/>))</xsl:if>
        <xsl:if test="@type='LocalDateTime'">
            Filters.eq("<xsl:value-of select='@name'/>", DateUtils.localDateTimeToString(p<xsl:value-of select="functx:capitalize-first(@name)"/>))</xsl:if>
        <xsl:if test="not(@type='LocalDateTime') and not(@type='LocalDate')">
            Filters.eq("<xsl:value-of select='@name'/>", p<xsl:value-of select="functx:capitalize-first(@name)"/>) </xsl:if><xsl:if test="not(position()=last())">,</xsl:if></xsl:for-each>);
    </xsl:template>

    <xsl:template name="filtersMessage">
        Bson tKeyFilter= Filters.and( <xsl:for-each select="Attribute[@dbKey]">
        <xsl:if test="@type='LocalDate'">
            Filters.eq("<xsl:value-of select='@name'/>", DateUtils.localDateToString(p<xsl:value-of select="../@name"/>.get<xsl:value-of select="functx:capitalize-first(@name)"/>().get()))</xsl:if>
        <xsl:if test="@type='LocalDateTime'">
            Filters.eq("<xsl:value-of select='@name'/>", DateUtils.localDateTimeToString(p<xsl:value-of select="../@name"/>.get<xsl:value-of select="functx:capitalize-first(@name)"/>().get()))</xsl:if>
        <xsl:if test="not(@type='LocalDateTime') and not(@type='LocalDate')">
            Filters.eq("<xsl:value-of select='@name'/>", p<xsl:value-of select="../@name"/>.get<xsl:value-of select="functx:capitalize-first(@name)"/>().get()) </xsl:if><xsl:if test="not(position()=last())">,</xsl:if></xsl:for-each>);
    </xsl:template>

    <xsl:template name="crudParams">
        <xsl:for-each select="Attribute[@dbKey]"><xsl:call-template name="crudParam"/><xsl:if test="not(position()=last())">, </xsl:if></xsl:for-each>
    </xsl:template>

    <xsl:template name="crudParam">
        <xsl:if test="@type='byte[]'">String pB64<xsl:value-of select="@name"/></xsl:if>
        <xsl:if test="not(@type='byte[]')"><xsl:value-of select="@type"/> p<xsl:value-of select="functx:capitalize-first(@name)"/></xsl:if>
    </xsl:template>



    <xsl:template mode="crudDelete" match="Message">
        /**
        * CRUD DELETE methods
        */
        public long delete<xsl:value-of select="functx:capitalize-first(@name)"/>( Bson pFilter) {
           DeleteResult tResult = m<xsl:call-template name="getCollectionName"/>Collection.deleteMany( pFilter );
           return tResult.getDeletedCount();
        }

        public long delete<xsl:value-of select="functx:capitalize-first(@name)"/>ByMongoId( String pMongoObjectId) {
            Bson tFilter=  Filters.eq("_id", new ObjectId(pMongoObjectId));
            DeleteResult tResult = m<xsl:call-template name="getCollectionName"/>Collection.deleteOne( tFilter );
            return tResult.getDeletedCount();
        }

        <xsl:if test="count(Attribute[@dbKey]) &gt; 1">
            public long delete<xsl:value-of select="functx:capitalize-first(@name)"/>( <xsl:call-template name="crudParams"/> ) {
             <xsl:call-template name="filtersParams"/>
             DeleteResult tResult =  m<xsl:call-template name="getCollectionName"/>Collection.deleteMany(tKeyFilter);
             return tResult.getDeletedCount();
            }
        </xsl:if>

        <xsl:for-each select="Attribute[@dbKey]">
            public long delete<xsl:value-of select="functx:capitalize-first(../@name)"/>By<xsl:value-of select="functx:capitalize-first(@name)"/>( <xsl:call-template name="crudParam"/> ) {
                <xsl:call-template name="filterSingle"/>
                DeleteResult tResult =  m<xsl:call-template name="getCollectionNameOneUp"/>Collection.deleteMany(tKeyFilter);
                return tResult.getDeletedCount();
            }
        </xsl:for-each>
    </xsl:template>

    <xsl:template mode="crudInsert" match="Message">
        /**
        * CRUD INSERT methods
        */
        public void insert<xsl:value-of select="functx:capitalize-first(@name)"/>( <xsl:value-of select="@name"/> p<xsl:value-of select="functx:capitalize-first(@name)"/> ) {
            Document tDoc = p<xsl:value-of select="functx:capitalize-first(@name)"/>.getMongoDocument();
            m<xsl:call-template name="getCollectionName"/>Collection.insertOne( tDoc );
            ObjectId _tId = tDoc.getObjectId("_id");
            if (_tId != null) {
                p<xsl:value-of select="functx:capitalize-first(@name)"/>.setMongoId( _tId.toString());
            }
        }

        public void insert<xsl:value-of select="functx:capitalize-first(@name)"/>( List&lt;<xsl:value-of select="@name"/>&gt; p<xsl:value-of select="functx:capitalize-first(@name)"/>List ) {
           List&lt;Document&gt; tList = p<xsl:value-of select="functx:capitalize-first(@name)"/>List.stream().map( <xsl:value-of select="functx:capitalize-first(@name)"/>::getMongoDocument).collect(Collectors.toList());
           m<xsl:call-template name="getCollectionName"/>Collection.insertMany( tList );
           for( int i = 0; i &lt; tList.size(); i++ ) {
             ObjectId _tId = tList.get(i).getObjectId("_id");
             if (_tId != null) {
                p<xsl:value-of select="functx:capitalize-first(@name)"/>List.get(i).setMongoId( _tId.toString());
             }
           }
        }

    </xsl:template>


    <xsl:template mode="crudUpdateInsert" match="Message">
        /**
        * CRUD UPDATE (INSERT) methods
        */
        public UpdateResult update<xsl:value-of select="functx:capitalize-first(@name)"/>ByMongoId( String pMongoObjectId, <xsl:value-of select="@name"/> p<xsl:value-of select="functx:capitalize-first(@name)"/> ) {
            Bson tFilter =  Filters.eq("_id", new ObjectId(pMongoObjectId));
            Document tDocSet = new Document("$set", p<xsl:value-of select="functx:capitalize-first(@name)"/>.getMongoDocument());
            UpdateResult tUpdSts = m<xsl:call-template name="getCollectionName"/>Collection.updateOne( tFilter, tDocSet, new UpdateOptions());
            return tUpdSts;
        }

        public UpdateResult update<xsl:value-of select="functx:capitalize-first(@name)"/>( <xsl:value-of select="@name"/> p<xsl:value-of select="functx:capitalize-first(@name)"/>, boolean pUpdateAllowInsert ) {
        UpdateOptions tOptions = new UpdateOptions().upsert(pUpdateAllowInsert);
        <xsl:call-template name="filtersMessage"/>


        Document tDocSet = new Document("$set", p<xsl:value-of select="functx:capitalize-first(@name)"/>.getMongoDocument());

        UpdateResult tUpdSts = m<xsl:call-template name="getCollectionName"/>Collection.updateOne( tKeyFilter, tDocSet, tOptions);
        return tUpdSts;
        }


        public UpdateResult update<xsl:value-of select="functx:capitalize-first(@name)"/>( <xsl:call-template name="crudParams"/>, <xsl:value-of select="@name"/> p<xsl:value-of select="functx:capitalize-first(@name)"/>, boolean pUpdateAllowInsert ) {
          UpdateOptions tOptions = new UpdateOptions().upsert(pUpdateAllowInsert);
          <xsl:call-template name="filtersParams"/>

           Document tDocSet = new Document("$set", p<xsl:value-of select="functx:capitalize-first(@name)"/>.getMongoDocument());

           UpdateResult tUpdSts = m<xsl:call-template name="getCollectionName"/>Collection.updateOne( tKeyFilter, tDocSet, tOptions);
           return tUpdSts;
        }

        public UpdateResult update<xsl:value-of select="functx:capitalize-first(@name)"/>( Bson pFilter, <xsl:value-of select="@name"/> p<xsl:value-of select="functx:capitalize-first(@name)"/>, boolean pUpdateAllowInsert ) {
           UpdateOptions tOptions = new UpdateOptions().upsert(pUpdateAllowInsert);
           Document tDocSet = new Document("$set", p<xsl:value-of select="functx:capitalize-first(@name)"/>.getMongoDocument());
           UpdateResult tUpdSts = m<xsl:call-template name="getCollectionName"/>Collection.updateOne( pFilter, tDocSet, tOptions);
           return tUpdSts;
        }
    </xsl:template>

    <xsl:template mode="crudFind" match="Message">
        /**
        * CRUD FIND methods
        */

        public List&lt;<xsl:value-of select='@name'/>&gt; find<xsl:value-of select="functx:capitalize-first(@name)"/>( Bson pFilter  ) {
         return find<xsl:value-of select="functx:capitalize-first(@name)"/>( pFilter, null );
        }

        public List&lt;<xsl:value-of select='@name'/>&gt; find<xsl:value-of select="functx:capitalize-first(@name)"/>( Bson pFilter, Bson pSortDoc  ) {

        FindIterable&lt;Document&gt; tDocuments = (pSortDoc == null) ? this.m<xsl:call-template name="getCollectionName"/>Collection.find( pFilter ) :
        this.m<xsl:call-template name="getCollectionName"/>Collection.find( pFilter ).sort( pSortDoc );


        if (tDocuments == null) {
        return null;
        }

        List&lt;<xsl:value-of select='@name'/>&gt; tResult = new ArrayList&lt;&gt;();
        MongoCursor&lt;Document&gt; tIter = tDocuments.iterator();
        while ( tIter.hasNext()) {
        Document tDoc = tIter.next();
        <xsl:value-of select='@name'/> t<xsl:value-of select="functx:capitalize-first(@name)"/> = new <xsl:value-of select='@name'/>();
        t<xsl:value-of select="functx:capitalize-first(@name)"/>.decodeMongoDocument( tDoc );
        tResult.add( t<xsl:value-of select="functx:capitalize-first(@name)"/> );
        }
        return tResult;
        }



        public List&lt;<xsl:value-of select='@name'/>&gt; findAll<xsl:value-of select="functx:capitalize-first(@name)"/>()
        {
           List&lt;<xsl:value-of select='@name'/>&gt; tResult = new ArrayList&lt;&gt;();

           FindIterable&lt;Document&gt; tDocuments  = this.m<xsl:call-template name="getCollectionName"/>Collection.find();
           MongoCursor&lt;Document&gt; tIter = tDocuments.iterator();
           while( tIter.hasNext()) {
               Document tDoc = tIter.next();
               <xsl:value-of select='@name'/> t<xsl:value-of select="functx:capitalize-first(@name)"/> = new <xsl:value-of select='@name'/>();
               t<xsl:value-of select="functx:capitalize-first(@name)"/>.decodeMongoDocument( tDoc );
               tResult.add(t<xsl:value-of select="functx:capitalize-first(@name)"/>);
            }
            return tResult;
        }

        public <xsl:value-of select='@name'/> find<xsl:value-of select="functx:capitalize-first(@name)"/>ByMongoId( String pMongoObjectId ) {
        Bson tFilter=  Filters.eq("_id", new ObjectId(pMongoObjectId));

        FindIterable&lt;Document&gt; tDocuments = this.m<xsl:call-template name="getCollectionName"/>Collection.find( tFilter );
        if (tDocuments == null) {
            return null;
        }

        List&lt;<xsl:value-of select='@name'/>&gt; tResult = new ArrayList&lt;&gt;();
        MongoCursor&lt;Document&gt; tIter = tDocuments.iterator();
        while ( tIter.hasNext()) {
        Document tDoc = tIter.next();
        <xsl:value-of select='@name'/> t<xsl:value-of select="functx:capitalize-first(@name)"/> = new <xsl:value-of select='@name'/>();
        t<xsl:value-of select="functx:capitalize-first(@name)"/>.decodeMongoDocument( tDoc );
        tResult.add( t<xsl:value-of select="functx:capitalize-first(@name)"/> );
        }
        return (tResult.size() &gt; 0) ? tResult.get(0) : null;
        }


        public List&lt;<xsl:value-of select='@name'/>&gt; find<xsl:value-of select="functx:capitalize-first(@name)"/>( <xsl:call-template name="crudParams"/> ) {
            <xsl:call-template name="filtersParams"/>

        FindIterable&lt;Document&gt; tDocuments = this.m<xsl:call-template name="getCollectionName"/>Collection.find( tKeyFilter );
        if (tDocuments == null) {
           return null;
        }

        List&lt;<xsl:value-of select='@name'/>&gt; tResult = new ArrayList&lt;&gt;();
        MongoCursor&lt;Document&gt; tIter = tDocuments.iterator();
        while ( tIter.hasNext()) {
           Document tDoc = tIter.next();
           <xsl:value-of select='@name'/> t<xsl:value-of select="functx:capitalize-first(@name)"/> = new <xsl:value-of select='@name'/>();
           t<xsl:value-of select="functx:capitalize-first(@name)"/>.decodeMongoDocument( tDoc );
           tResult.add( t<xsl:value-of select="functx:capitalize-first(@name)"/> );
        }
        return tResult;
        }

        <xsl:for-each select="Attribute[@dbKey]">
            public List&lt;<xsl:value-of select='../@name'/>&gt; find<xsl:value-of select="functx:capitalize-first(../@name)"/>By<xsl:value-of select="functx:capitalize-first(@name)"/>( <xsl:call-template name="crudParam"/> ) {
            List&lt;<xsl:value-of select='../@name'/>&gt; tResult = new ArrayList&lt;&gt;();
            <xsl:call-template name="filterSingle"/>

            FindIterable&lt;Document&gt; tDocuments  = this.m<xsl:call-template name="getCollectionNameOneUp"/>Collection.find( tKeyFilter );
            MongoCursor&lt;Document&gt; tIter = tDocuments.iterator();
            while( tIter.hasNext()) {
            Document tDoc = tIter.next();
            <xsl:value-of select='../@name'/> t<xsl:value-of select="functx:capitalize-first(../@name)"/> = new <xsl:value-of select='../@name'/>();
            t<xsl:value-of select="functx:capitalize-first(../@name)"/>.decodeMongoDocument( tDoc );
            tResult.add(t<xsl:value-of select="functx:capitalize-first(../@name)"/>);
            }
            return tResult;
            }
        </xsl:for-each>



    </xsl:template>

</xsl:stylesheet>