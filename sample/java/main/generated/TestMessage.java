
            package generated;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.OptionalDouble;
import java.util.OptionalLong;
import java.io.IOException;




    import org.bson.BsonType;
    import org.bson.Document;
    import org.bson.conversions.Bson;
    import com.mongodb.BasicDBObject;
    import com.hoddmimes.jsontransform.MessageMongoInterface;
    import com.hoddmimes.jsontransform.MongoDecoder;
    import com.hoddmimes.jsontransform.MongoEncoder;


import com.hoddmimes.jsontransform.MessageInterface;
import com.hoddmimes.jsontransform.JsonDecoder;
import com.hoddmimes.jsontransform.JsonEncoder;
import com.google.gson.JsonObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

            

            @SuppressWarnings({"WeakerAccess","unused","unchecked"})
            public class TestMessage implements MessageInterface , MessageMongoInterface
            {
            
                    private List<String> mStringArray;
                private CG1 mConstValue;
                private List<CG2> mConstArray;
                    private Boolean mBoolValue;
                    private Character mCharValue;
                    private Byte mByteValue;
                    private Double mDoubleValue;
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
               public TestMessage() {}

               public TestMessage(String pJsonString ) {
                    JsonDecoder tDecoder = new JsonDecoder( pJsonString );
                    this.decode( tDecoder );
               }
    
            public TestMessage setStringArray(List<String> pStringArray ) {
            if ( pStringArray == null) {
            mStringArray = null;
            } else {
            mStringArray = new ArrayList<>( pStringArray.size() );
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
            mConstArray = new ArrayList<>(pConstArray.size());
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
            mIntArray = new ArrayList<>( pIntArray.size() );
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
            mBytesArrayValue = new ArrayList<>( pBytesArrayValue.size() );
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

            int tSize = pMsgArray.size();

            if ( mMsgArray == null)
            mMsgArray = new ArrayList<>( tSize + 1 );


            mMsgArray .addAll( pMsgArray );
            return this;
            }


            public TestMessage addMsgArray( List<TestSubMessage> pMsgArray ) {

            if ( mMsgArray == null)
            mMsgArray = new ArrayList<>();

            mMsgArray .addAll( pMsgArray );
            return this;
            }

            public TestMessage addMsgArray( TestSubMessage pMsgArray ) {

            if ( pMsgArray == null) {
            return this; // Not supporting null in vectors, well design issue
            }

            if ( mMsgArray == null) {
            mMsgArray = new ArrayList<>();
            }

            mMsgArray.add( pMsgArray );
            return this;
            }


            public Optional<List<TestSubMessage>> getMsgArray() {

            if (mMsgArray == null) {
                return  Optional.ofNullable(null);
            }

            List<TestSubMessage> tList = new ArrayList<>( mMsgArray.size() );
            tList.addAll( mMsgArray );
            return  Optional.ofNullable(tList);
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
            //Encode Attribute: mStringArray Type: String Array: true
            tEncoder.addStringArray("stringArray", mStringArray );
        
            //Encode Attribute: mConstValue Type:  Array: false
            tEncoder.add("constValue", mConstValue.toString());
        
            //Encode Attribute: mConstArray Type:  Array: true
            tEncoder.addConstantArray("constArray", mConstArray);
        
            //Encode Attribute: mBoolValue Type: boolean Array: false
            tEncoder.add( "boolValue", mBoolValue );
        
            //Encode Attribute: mCharValue Type: char Array: false
            tEncoder.add( "charValue", mCharValue );
        
            //Encode Attribute: mByteValue Type: byte Array: false
            tEncoder.add( "byteValue", mByteValue );
        
            //Encode Attribute: mDoubleValue Type: double Array: false
            tEncoder.add( "doubleValue", mDoubleValue );
        
            //Encode Attribute: mShortValue Type: short Array: false
            tEncoder.add( "shortValue", mShortValue );
        
            //Encode Attribute: mIntValue Type: int Array: false
            tEncoder.add( "intValue", mIntValue );
        
            //Encode Attribute: mIntArray Type: int Array: true
            tEncoder.addIntegerArray("intArray", mIntArray );
        
            //Encode Attribute: mLongValue Type: long Array: false
            tEncoder.add( "longValue", mLongValue );
        
            //Encode Attribute: mStrValue Type: String Array: false
            tEncoder.add( "strValue", mStrValue );
        
            //Encode Attribute: mBytesValue Type: byte[] Array: false
            tEncoder.add( "bytesValue", mBytesValue );
        
            //Encode Attribute: mBytesArrayValue Type: byte[] Array: true
            tEncoder.addByteVectorArray("bytesArrayValue", mBytesArrayValue );
        
            //Encode Attribute: mMsgValue Type: TestSubMessage Array: false
            tEncoder.add( "msgValue", mMsgValue );
        
            //Encode Attribute: mMsgArray Type: TestSubMessage Array: true
            tEncoder.addMessageArray("msgArray", mMsgArray );
        
            //Encode Attribute: mTimeString Type: String Array: false
            tEncoder.add( "timeString", mTimeString );
        
        }

        
        public void decode( JsonDecoder pDecoder) {

        
            JsonDecoder tDecoder = pDecoder.get("TestMessage");
        
            //Decode Attribute: mStringArray Type:String Array: true
            mStringArray = tDecoder.readStringArray("stringArray");
        
            //Decode Attribute: mConstValue Type: Array: false
            mConstValue = (CG1) tDecoder.readConstant("constValue", CG1.class );
        
            //Decode Attribute: mConstArray Type: Array: true
            mConstArray = (List<CG2>)tDecoder.readConstantArray("constArray", CG2.class );
        
            //Decode Attribute: mBoolValue Type:boolean Array: false
            mBoolValue = tDecoder.readBoolean("boolValue");
        
            //Decode Attribute: mCharValue Type:char Array: false
            mCharValue = tDecoder.readCharacter("charValue");
        
            //Decode Attribute: mByteValue Type:byte Array: false
            mByteValue = tDecoder.readByte("byteValue");
        
            //Decode Attribute: mDoubleValue Type:double Array: false
            mDoubleValue = tDecoder.readDouble("doubleValue");
        
            //Decode Attribute: mShortValue Type:short Array: false
            mShortValue = tDecoder.readShort("shortValue");
        
            //Decode Attribute: mIntValue Type:int Array: false
            mIntValue = tDecoder.readInteger("intValue");
        
            //Decode Attribute: mIntArray Type:int Array: true
            mIntArray = tDecoder.readIntegerArray("intArray");
        
            //Decode Attribute: mLongValue Type:long Array: false
            mLongValue = tDecoder.readLong("longValue");
        
            //Decode Attribute: mStrValue Type:String Array: false
            mStrValue = tDecoder.readString("strValue");
        
            //Decode Attribute: mBytesValue Type:byte[] Array: false
            mBytesValue = tDecoder.readByteVector("bytesValue");
        
            //Decode Attribute: mBytesArrayValue Type:byte[] Array: true
            mBytesArrayValue = tDecoder.readByteVectorArray("bytesArrayValue");
        
            //Decode Attribute: mMsgValue Type:TestSubMessage Array: false
            mMsgValue = (TestSubMessage) tDecoder.readMessage( "msgValue", TestSubMessage.class );
        
            //Decode Attribute: mMsgArray Type:TestSubMessage Array: true
            mMsgArray = (List<TestSubMessage>) tDecoder.readMessageArray( "msgArray", TestSubMessage.class );
        
            //Decode Attribute: mTimeString Type:String Array: false
            mTimeString = tDecoder.readString("timeString");
        

        }
    

        @Override
        public String toString() {
             Gson gsonPrinter = new GsonBuilder().setPrettyPrinting().create();
             return  gsonPrinter.toJson( this.toJson());
        }
    
        public Document getMongoDocument() {
            MongoEncoder tEncoder = new MongoEncoder();
        
                tEncoder.addStringArray( "stringArray", mStringArray );
                   tEncoder.add( "constValue", mConstValue ); 
                   tEncoder.addConstArray("constArray", mConstArray );
                tEncoder.add("boolValue",  mBoolValue );
                tEncoder.add("charValue",  mCharValue );
                tEncoder.add("byteValue",  mByteValue );
                tEncoder.add("doubleValue",  mDoubleValue );
                tEncoder.add("shortValue",  mShortValue );
                tEncoder.add("intValue",  mIntValue );
                tEncoder.addIntegerArray( "intArray", mIntArray );
                tEncoder.add("longValue",  mLongValue );
                tEncoder.add("strValue",  mStrValue );
                tEncoder.addByteVectorArray( "bytesArrayValue", mBytesArrayValue );
            tEncoder.add("msgValue",   mMsgValue );
            tEncoder.addMessageArray("msgArray",  mMsgArray );
                tEncoder.add("timeString",  mTimeString );
            return tEncoder.getDoc();
    }
    
        public void decodeMongoDocument( Document pDoc ) {
            Document tDoc = null;
            List<Document> tDocLst = null;

            MongoDecoder tDecoder = new MongoDecoder( pDoc );
        
           mStringArray = tDecoder.readStringArray("stringArray");
        
                      mConstValue = (CG1) tDecoder.readConstant("constValue", CG1.class); 
                      mConstArray = (List<CG2>)tDecoder.readConstArray("constArray", CG2.class); 
           mBoolValue = tDecoder.readBoolean("boolValue");
        
           mCharValue = tDecoder.readCharacter("charValue");
        
           mByteValue = tDecoder.readByte("byteValue");
        
           mDoubleValue = tDecoder.readDouble("doubleValue");
        
           mShortValue = tDecoder.readShort("shortValue");
        
           mIntValue = tDecoder.readInteger("intValue");
        
           mIntArray = tDecoder.readIntegerArray("intArray");
        
           mLongValue = tDecoder.readLong("longValue");
        
           mStrValue = tDecoder.readString("strValue");
        
           mBytesArrayValue = tDecoder.readByteVectorArray("bytesArrayValue");
        
            tDoc = (Document) tDecoder.readMessage("msgValue");
            if (tDoc == null) {
              mMsgValue = null;
            } else {
               mMsgValue = new TestSubMessage();
               mMsgValue.decodeMongoDocument( tDoc );
            } 
                tDocLst = (List<Document>) tDecoder.readMessageArray("msgArray");
                if (tDocLst == null) {
                   mMsgArray = null;
                } else {
                   mMsgArray = new ArrayList<>();
                   for( Document doc :  tDocLst ) {
                    TestSubMessage  m = new TestSubMessage();
                    m.decodeMongoDocument( doc );
                    mMsgArray.add(m);
                }
            } 
           mTimeString = tDecoder.readString("timeString");
        
        } // End decodeMongoDocument
    
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

    