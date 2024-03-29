
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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.OptionalDouble;
import java.util.OptionalLong;
import java.io.IOException;




    import org.bson.BsonType;
    import org.bson.Document;
    import org.bson.conversions.Bson;
    import com.mongodb.BasicDBObject;
    import org.bson.types.ObjectId;
    import com.hoddmimes.transform.MessageMongoInterface;
    import com.hoddmimes.transform.MongoDecoder;
    import com.hoddmimes.transform.MongoEncoder;


import com.hoddmimes.transform.MessageInterface;
import com.hoddmimes.transform.JsonDecoder;
import com.hoddmimes.transform.JsonEncoder;
import com.hoddmimes.transform.ListFactory;
import com.google.gson.JsonObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;



            

            @SuppressWarnings({"WeakerAccess","unused","unchecked"})
            public class TestMessage implements MessageInterface , MessageMongoInterface
            {
                public static String NAME = "TestMessage";

            
                private String mMongoId = null;
                    private List<String> mStringArray;
                private CG1 mConstValue;
                private List<CG2> mConstArray;
                    private Boolean mBoolValue;
                    private Character mCharValue;
                    private LocalDate mDateValue;
                    private Byte mByteValue;
                    private Double mDoubleValue;
                    private Map<String,String> mMapValue;
                    private Short mShortValue;
                    private Integer mIntValue;
                    private List<Integer> mIntArray;
                    private Long mLongValue;
                    private String mStrValue;
                    private byte[] mBytesValue;
                    private List<byte[]> mBytesArrayValue;
                    private TestSubMessage mMsgValue;
                    private List<TestSubMessage> mMsgArray;
                    private String mTimeString;
               public TestMessage()
               {
                
               }

               public TestMessage(String pJsonString ) {
                    
                    JsonDecoder tDecoder = new JsonDecoder( pJsonString );
                    this.decode( tDecoder );
               }
    
            public String getMongoId() {
            return this.mMongoId;
            }

            public void setMongoId( String pMongoId ) {
            this.mMongoId = pMongoId;
            }
        
            public TestMessage setStringArray(List<String> pStringArray ) {
            if ( pStringArray == null) {
            mStringArray = null;
            } else {
            mStringArray = ListFactory.getList("linked");
            mStringArray.addAll( pStringArray );
            }
            return this;
            }

            public Optional<List<String>> getStringArray()
            {
            return  Optional.ofNullable(mStringArray);
            }
        
            public TestMessage setConstValue(CG1 pConstValue) {
            mConstValue = pConstValue;
            return this;
            }

            public Optional<CG1> getConstValue() {
            return  Optional.ofNullable(mConstValue);
            }
        
            public Optional<List<CG2>> getConstArray() {
            return  Optional.ofNullable(mConstArray) ;
            }
            public TestMessage setConstArray(List<CG2>pConstArray) {
            if (pConstArray == null) {
            mConstArray = null;
            } else {
            mConstArray = ListFactory.getList("array");
            mConstArray.addAll( pConstArray );
            }
            return this;
            }
        
            public TestMessage setBoolValue( Boolean pBoolValue ) {
            mBoolValue = pBoolValue;
            return this;
            }
            public Optional<Boolean> getBoolValue() {
              return  Optional.ofNullable(mBoolValue);
            }
        
            public TestMessage setCharValue( Character pCharValue ) {
            mCharValue = pCharValue;
            return this;
            }
            public Optional<Character> getCharValue() {
              return  Optional.ofNullable(mCharValue);
            }
        
            public TestMessage setDateValue( LocalDate pDateValue ) {
            mDateValue = pDateValue;
            return this;
            }
            public Optional<LocalDate> getDateValue() {
              return  Optional.ofNullable(mDateValue);
            }
        
            public TestMessage setByteValue( Byte pByteValue ) {
            mByteValue = pByteValue;
            return this;
            }
            public Optional<Byte> getByteValue() {
              return  Optional.ofNullable(mByteValue);
            }
        
            public TestMessage setDoubleValue( Double pDoubleValue ) {
            mDoubleValue = pDoubleValue;
            return this;
            }
            public Optional<Double> getDoubleValue() {
              return  Optional.ofNullable(mDoubleValue);
            }
        
            public TestMessage setMapValue( Map<String,String> pMapValue ) {
            mMapValue = pMapValue;
            return this;
            }
            public Optional<Map<String,String>> getMapValue() {
              return  Optional.ofNullable(mMapValue);
            }
        
            public TestMessage setShortValue( Short pShortValue ) {
            mShortValue = pShortValue;
            return this;
            }
            public Optional<Short> getShortValue() {
              return  Optional.ofNullable(mShortValue);
            }
        
            public TestMessage setIntValue( Integer pIntValue ) {
            mIntValue = pIntValue;
            return this;
            }
            public Optional<Integer> getIntValue() {
              return  Optional.ofNullable(mIntValue);
            }
        
            public TestMessage setIntArray(List<Integer> pIntArray ) {
            if ( pIntArray == null) {
            mIntArray = null;
            } else {
            mIntArray = ListFactory.getList("stack");
            mIntArray.addAll( pIntArray );
            }
            return this;
            }

            public Optional<List<Integer>> getIntArray()
            {
            return  Optional.ofNullable(mIntArray);
            }
        
            public TestMessage setLongValue( Long pLongValue ) {
            mLongValue = pLongValue;
            return this;
            }
            public Optional<Long> getLongValue() {
              return  Optional.ofNullable(mLongValue);
            }
        
            public TestMessage setStrValue( String pStrValue ) {
            mStrValue = pStrValue;
            return this;
            }
            public Optional<String> getStrValue() {
              return  Optional.ofNullable(mStrValue);
            }
        
            public TestMessage setBytesValue( byte[] pBytesValue ) {
            mBytesValue = pBytesValue;
            return this;
            }
            public Optional<byte[]> getBytesValue() {
              return  Optional.ofNullable(mBytesValue);
            }
        
            public TestMessage setBytesArrayValue(List<byte[]> pBytesArrayValue ) {
            if ( pBytesArrayValue == null) {
            mBytesArrayValue = null;
            } else {
            mBytesArrayValue = ListFactory.getList("array");
            mBytesArrayValue.addAll( pBytesArrayValue );
            }
            return this;
            }

            public Optional<List<byte[]>> getBytesArrayValue()
            {
            return  Optional.ofNullable(mBytesArrayValue);
            }
        

            public Optional<TestSubMessage> getMsgValue() {
              return  Optional.ofNullable(mMsgValue);
            }

            public TestMessage setMsgValue(TestSubMessage pMsgValue) {
            mMsgValue = pMsgValue;
            return this;
            }

        
            public TestMessage setMsgArray( List<TestSubMessage> pMsgArray ) {
              if (pMsgArray == null) {
                mMsgArray = null;
                return this;
              }


            if ( mMsgArray == null)
            mMsgArray = ListFactory.getList("array");


            mMsgArray .addAll( pMsgArray );
            return this;
            }


            public TestMessage addMsgArray( List<TestSubMessage> pMsgArray ) {

            if ( mMsgArray == null)
            mMsgArray = ListFactory.getList("array");

            mMsgArray .addAll( pMsgArray );
            return this;
            }

            public TestMessage addMsgArray( TestSubMessage pMsgArray ) {

            if ( pMsgArray == null) {
            return this; // Not supporting null in vectors, well design issue
            }

            if ( mMsgArray == null) {
            mMsgArray = ListFactory.getList("array");
            }

            mMsgArray.add( pMsgArray );
            return this;
            }


            public Optional<List<TestSubMessage>> getMsgArray() {

            if (mMsgArray == null) {
                return  Optional.ofNullable(null);
            }

             //List<TestSubMessage> tList = ListFactory.getList("array");
             //tList.addAll( mMsgArray );
             // return  Optional.ofNullable(tList);
             return Optional.ofNullable(mMsgArray);
            }

        
            public TestMessage setTimeString( String pTimeString ) {
            mTimeString = pTimeString;
            return this;
            }
            public Optional<String> getTimeString() {
              return  Optional.ofNullable(mTimeString);
            }
        
        public String getMessageName() {
        return "TestMessage";
        }
    

        public JsonObject toJson() {
            JsonEncoder tEncoder = new JsonEncoder();
            this.encode( tEncoder );
            return tEncoder.toJson();
        }

        
        public void encode( JsonEncoder pEncoder) {

        
            JsonEncoder tEncoder = new JsonEncoder();
            pEncoder.add("TestMessage", tEncoder.toJson() );
            //Encode Attribute: mStringArray Type: String List: true
            tEncoder.addStringArray("stringArray", mStringArray );
        
            //Encode Attribute: mConstValue Type:  List: false
            tEncoder.add("constValue", mConstValue.toString());
        
            //Encode Attribute: mConstArray Type:  List: true
            tEncoder.addConstantArray("constArray", mConstArray);
        
            //Encode Attribute: mBoolValue Type: boolean List: false
            tEncoder.add( "boolValue", mBoolValue );
        
            //Encode Attribute: mCharValue Type: char List: false
            tEncoder.add( "charValue", mCharValue );
        
            //Encode Attribute: mDateValue Type: LocalDate List: false
            tEncoder.add( "dateValue", mDateValue );
        
            //Encode Attribute: mByteValue Type: byte List: false
            tEncoder.add( "byteValue", mByteValue );
        
            //Encode Attribute: mDoubleValue Type: double List: false
            tEncoder.add( "doubleValue", mDoubleValue );
        
            //Encode Attribute: mMapValue Type: Map List: false
            tEncoder.add( "mapValue", mMapValue );
        
            //Encode Attribute: mShortValue Type: short List: false
            tEncoder.add( "shortValue", mShortValue );
        
            //Encode Attribute: mIntValue Type: int List: false
            tEncoder.add( "intValue", mIntValue );
        
            //Encode Attribute: mIntArray Type: int List: true
            tEncoder.addIntegerArray("intArray", mIntArray );
        
            //Encode Attribute: mLongValue Type: long List: false
            tEncoder.add( "longValue", mLongValue );
        
            //Encode Attribute: mStrValue Type: String List: false
            tEncoder.add( "strValue", mStrValue );
        
            //Encode Attribute: mBytesValue Type: byte[] List: false
            tEncoder.add( "bytesValue", mBytesValue );
        
            //Encode Attribute: mBytesArrayValue Type: byte[] List: true
            tEncoder.addByteVectorArray("bytesArrayValue", mBytesArrayValue );
        
            //Encode Attribute: mMsgValue Type: TestSubMessage List: false
            tEncoder.add( "msgValue", mMsgValue );
        
            //Encode Attribute: mMsgArray Type: TestSubMessage List: true
            tEncoder.addMessageArray("msgArray", mMsgArray );
        
            //Encode Attribute: mTimeString Type: String List: false
            tEncoder.add( "timeString", mTimeString );
        
        }

        
        public void decode( JsonDecoder pDecoder) {

        
            JsonDecoder tDecoder = pDecoder.get("TestMessage");
        
            //Decode Attribute: mStringArray Type:String List: true
            mStringArray = tDecoder.readStringArray("stringArray", "linked");
        
            //Decode Attribute: mConstValue Type: List: false
            mConstValue = (CG1) tDecoder.readConstant("constValue", CG1.class );
        
            //Decode Attribute: mConstArray Type: List: true
            mConstArray = (List<CG2>)tDecoder.readConstantArray("constArray", "constArray", CG2.class );
        
            //Decode Attribute: mBoolValue Type:boolean List: false
            mBoolValue = tDecoder.readBoolean("boolValue");
        
            //Decode Attribute: mCharValue Type:char List: false
            mCharValue = tDecoder.readCharacter("charValue");
        
            //Decode Attribute: mDateValue Type:LocalDate List: false
            mDateValue = tDecoder.readDate("dateValue");
        
            //Decode Attribute: mByteValue Type:byte List: false
            mByteValue = tDecoder.readByte("byteValue");
        
            //Decode Attribute: mDoubleValue Type:double List: false
            mDoubleValue = tDecoder.readDouble("doubleValue");
        
            //Decode Attribute: mMapValue Type:Map List: false
            mMapValue = tDecoder.readMap("mapValue");
        
            //Decode Attribute: mShortValue Type:short List: false
            mShortValue = tDecoder.readShort("shortValue");
        
            //Decode Attribute: mIntValue Type:int List: false
            mIntValue = tDecoder.readInteger("intValue");
        
            //Decode Attribute: mIntArray Type:int List: true
            mIntArray = tDecoder.readIntegerArray("intArray", "stack");
        
            //Decode Attribute: mLongValue Type:long List: false
            mLongValue = tDecoder.readLong("longValue");
        
            //Decode Attribute: mStrValue Type:String List: false
            mStrValue = tDecoder.readString("strValue");
        
            //Decode Attribute: mBytesValue Type:byte[] List: false
            mBytesValue = tDecoder.readByteVector("bytesValue");
        
            //Decode Attribute: mBytesArrayValue Type:byte[] List: true
            mBytesArrayValue = tDecoder.readByteVectorArray("bytesArrayValue", "array");
        
            //Decode Attribute: mMsgValue Type:TestSubMessage List: false
            mMsgValue = (TestSubMessage) tDecoder.readMessage( "msgValue", TestSubMessage.class );
        
            //Decode Attribute: mMsgArray Type:TestSubMessage List: true
            mMsgArray = (List<TestSubMessage>) tDecoder.readMessageArray( "msgArray", "array", TestSubMessage.class );
        
            //Decode Attribute: mTimeString Type:String List: false
            mTimeString = tDecoder.readString("timeString");
        

        }
    

        @Override
        public String toString() {
             Gson gsonPrinter = new GsonBuilder().setPrettyPrinting().create();
             return  gsonPrinter.toJson( this.toJson());
        }
    
        public Document getMongoDocument() {
            MongoEncoder tEncoder = new MongoEncoder();
            
            mongoEncode( tEncoder );
            return tEncoder.getDoc();
        }

     protected void mongoEncode(  MongoEncoder pEncoder ) {
        
                pEncoder.addStringArray( "stringArray", mStringArray );
                   pEncoder.add( "constValue", mConstValue ); 
                   pEncoder.addConstArray("constArray", mConstArray );
                pEncoder.add("boolValue",  mBoolValue );
                pEncoder.add("charValue",  mCharValue );
                pEncoder.add("dateValue",  mDateValue );
                pEncoder.add("byteValue",  mByteValue );
                pEncoder.add("doubleValue",  mDoubleValue );
                pEncoder.add("mapValue",  mMapValue );
                pEncoder.add("shortValue",  mShortValue );
                pEncoder.add("intValue",  mIntValue );
                pEncoder.addIntegerArray( "intArray", mIntArray );
                pEncoder.add("longValue",  mLongValue );
                pEncoder.add("strValue",  mStrValue );
                pEncoder.addByteVectorArray( "bytesArrayValue", mBytesArrayValue );
            pEncoder.add("msgValue",   mMsgValue );
            pEncoder.addMessageArray("msgArray",  mMsgArray );
                pEncoder.add("timeString",  mTimeString );
    }
    
        public void decodeMongoDocument( Document pDoc ) {

            Document tDoc = null;
            List<Document> tDocLst = null;
            MongoDecoder tDecoder = new MongoDecoder( pDoc );

            
            ObjectId _tId = pDoc.get("_id", ObjectId.class);
            this.mMongoId = _tId.toString();
            
           mStringArray = tDecoder.readStringArray("stringArray", "linked");
        
                    mConstValue = (CG1) tDecoder.readConstant("constValue", CG1.class); 
                    mConstArray = (List<CG2>)tDecoder.readConstArray("constArray", "array", CG2.class); 
           mBoolValue = tDecoder.readBoolean("boolValue");
        
           mCharValue = tDecoder.readCharacter("charValue");
        
           mDateValue = tDecoder.readDate("dateValue");
        
           mByteValue = tDecoder.readByte("byteValue");
        
           mDoubleValue = tDecoder.readDouble("doubleValue");
        
           mMapValue = tDecoder.readMap("mapValue");
        
           mShortValue = tDecoder.readShort("shortValue");
        
           mIntValue = tDecoder.readInteger("intValue");
        
           mIntArray = tDecoder.readIntegerArray("intArray", "stack");
        
           mLongValue = tDecoder.readLong("longValue");
        
           mStrValue = tDecoder.readString("strValue");
        
           mBytesArrayValue = tDecoder.readByteVectorArray("bytesArrayValue", "array");
        
            tDoc = (Document) tDecoder.readMessage("msgValue");
            if (tDoc == null) {
              mMsgValue = null;
            } else {
               mMsgValue = new TestSubMessage();
               mMsgValue.decodeMongoDocument( tDoc );
            } 
                tDocLst = (List<Document>) tDecoder.readMessageArray("msgArray", "array" );
                if (tDocLst == null) {
                   mMsgArray = null;
                } else {
                   mMsgArray = ListFactory.getList("array");
                   for( Document doc :  tDocLst ) {
                    TestSubMessage  m = new TestSubMessage();
                    m.decodeMongoDocument( doc );
                    mMsgArray.add(m);
                }
            } 
           mTimeString = tDecoder.readString("timeString");
        
        } // End decodeMongoDocument
    

        public static  Builder getTestMessageBuilder() {
            return new TestMessage.Builder();
        }


        public static class  Builder {
          private TestMessage mInstance;

          private Builder () {
            mInstance = new TestMessage();
          }

        
                        public Builder setStringArray( List<String> pValue ) {
                        mInstance.setStringArray( pValue );
                        return this;
                    }
                
                public Builder setConstValue( CG1 pValue ) {
                    mInstance.setConstValue( pValue );
                    return this;
                }
            
                public Builder setConstArray( List<CG2> pValue ) {
                    mInstance.setConstArray( pValue );
                    return this;
                }
            
                        public Builder setBoolValue( Boolean pValue ) {
                        mInstance.setBoolValue( pValue );
                        return this;
                    }
                
                        public Builder setCharValue( Character pValue ) {
                        mInstance.setCharValue( pValue );
                        return this;
                    }
                
                        public Builder setDateValue( LocalDate pValue ) {
                        mInstance.setDateValue( pValue );
                        return this;
                    }
                
                        public Builder setByteValue( Byte pValue ) {
                        mInstance.setByteValue( pValue );
                        return this;
                    }
                
                        public Builder setDoubleValue( Double pValue ) {
                        mInstance.setDoubleValue( pValue );
                        return this;
                    }
                
                        public Builder setMapValue( Map<String,String> pValue ) {
                        mInstance.setMapValue( pValue );
                        return this;
                    }
                
                        public Builder setShortValue( Short pValue ) {
                        mInstance.setShortValue( pValue );
                        return this;
                    }
                
                        public Builder setIntValue( Integer pValue ) {
                        mInstance.setIntValue( pValue );
                        return this;
                    }
                
                        public Builder setIntArray( List<Integer> pValue ) {
                        mInstance.setIntArray( pValue );
                        return this;
                    }
                
                        public Builder setLongValue( Long pValue ) {
                        mInstance.setLongValue( pValue );
                        return this;
                    }
                
                        public Builder setStrValue( String pValue ) {
                        mInstance.setStrValue( pValue );
                        return this;
                    }
                
                        public Builder setBytesValue( byte[] pValue ) {
                        mInstance.setBytesValue( pValue );
                        return this;
                    }
                
                        public Builder setBytesArrayValue( List<byte[]> pValue ) {
                        mInstance.setBytesArrayValue( pValue );
                        return this;
                    }
                
                    public Builder setMsgValue( TestSubMessage pValue )  {
                        mInstance.setMsgValue( pValue );
                        return this;
                    }
                
                    public Builder setMsgArray( List<TestSubMessage> pValue )  {
                        mInstance.setMsgArray( pValue );
                        return this;
                    }
                
                        public Builder setTimeString( String pValue ) {
                        mInstance.setTimeString( pValue );
                        return this;
                    }
                

        public TestMessage build() {
            return mInstance;
        }

        }
    
            }
            
        /**
            Possible native attributes
            o "boolean" mapped to JSON "Boolean"
            o "byte" mapped to JSON "Integer"
            o "char" mapped to JSON "Integer"
            o "short" mapped to JSON "Integer"
            o "int" mapped to JSON "Integer"
            o "long" mapped to JSON "Integer"
            o "double" mapped to JSON "Numeric"
            o "String" mapped to JSON "String"
            o "byte[]" mapped to JSON "String" (Base64 string)


            An attribute could also be an "constant" i.e. having the property "constantGroup", should then refer to an defined /Constang/Group
             conastants are mapped to JSON strings,


            If the type is not any of the types below it will be refer to an other structure / object

        **/

    