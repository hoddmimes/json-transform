
            package generated;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;
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
    import org.bson.types.ObjectId;
    import com.hoddmimes.jsontransform.MessageMongoInterface;
    import com.hoddmimes.jsontransform.MongoDecoder;
    import com.hoddmimes.jsontransform.MongoEncoder;


import com.hoddmimes.jsontransform.MessageInterface;
import com.hoddmimes.jsontransform.JsonDecoder;
import com.hoddmimes.jsontransform.JsonEncoder;
import com.hoddmimes.jsontransform.ListFactory;
import com.google.gson.JsonObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

            

            @SuppressWarnings({"WeakerAccess","unused","unchecked"})
            public class TestSubMessage implements MessageInterface , MessageMongoInterface
            {
            
                    private Integer mIntValue;
                    private String mStrValue;
                    private List<String> mStringArray;
                    private List<Integer> mIntArray;
               public TestSubMessage()
               {
                
               }

               public TestSubMessage(String pJsonString ) {
                    
                    JsonDecoder tDecoder = new JsonDecoder( pJsonString );
                    this.decode( tDecoder );
               }
    
            public TestSubMessage setIntValue( Integer pIntValue ) {
            mIntValue = pIntValue;
            return this;
            }
            public Optional<Integer> getIntValue() {
              return  Optional.ofNullable(mIntValue);
            }
        
            public TestSubMessage setStrValue( String pStrValue ) {
            mStrValue = pStrValue;
            return this;
            }
            public Optional<String> getStrValue() {
              return  Optional.ofNullable(mStrValue);
            }
        
            public TestSubMessage setStringArray(List<String> pStringArray ) {
            if ( pStringArray == null) {
            mStringArray = null;
            } else {
            mStringArray = ListFactory.getList("array");
            mStringArray.addAll( pStringArray );
            }
            return this;
            }

            public Optional<List<String>> getStringArray()
            {
            return  Optional.ofNullable(mStringArray);
            }
        
            public TestSubMessage setIntArray(List<Integer> pIntArray ) {
            if ( pIntArray == null) {
            mIntArray = null;
            } else {
            mIntArray = ListFactory.getList("array");
            mIntArray.addAll( pIntArray );
            }
            return this;
            }

            public Optional<List<Integer>> getIntArray()
            {
            return  Optional.ofNullable(mIntArray);
            }
        

        public String getMessageName() {
        return "TestSubMessage";
        }
    

        public JsonObject toJson() {
            JsonEncoder tEncoder = new JsonEncoder();
            this.encode( tEncoder );
            return tEncoder.toJson();
        }

        
        public void encode( JsonEncoder pEncoder) {

        
            JsonEncoder tEncoder = pEncoder;
            //Encode Attribute: mIntValue Type: int List: false
            tEncoder.add( "intValue", mIntValue );
        
            //Encode Attribute: mStrValue Type: String List: false
            tEncoder.add( "strValue", mStrValue );
        
            //Encode Attribute: mStringArray Type: String List: true
            tEncoder.addStringArray("stringArray", mStringArray );
        
            //Encode Attribute: mIntArray Type: int List: true
            tEncoder.addIntegerArray("intArray", mIntArray );
        
        }

        
        public void decode( JsonDecoder pDecoder) {

        
            JsonDecoder tDecoder = pDecoder;
        
            //Decode Attribute: mIntValue Type:int List: false
            mIntValue = tDecoder.readInteger("intValue");
        
            //Decode Attribute: mStrValue Type:String List: false
            mStrValue = tDecoder.readString("strValue");
        
            //Decode Attribute: mStringArray Type:String List: true
            mStringArray = tDecoder.readStringArray("stringArray", "array");
        
            //Decode Attribute: mIntArray Type:int List: true
            mIntArray = tDecoder.readIntegerArray("intArray", "array");
        

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
        
                pEncoder.add("intValue",  mIntValue );
                pEncoder.add("strValue",  mStrValue );
                pEncoder.addStringArray( "stringArray", mStringArray );
                pEncoder.addIntegerArray( "intArray", mIntArray );
    }
    
        public void decodeMongoDocument( Document pDoc ) {

            Document tDoc = null;
            List<Document> tDocLst = null;
            MongoDecoder tDecoder = new MongoDecoder( pDoc );

            
           mIntValue = tDecoder.readInteger("intValue");
        
           mStrValue = tDecoder.readString("strValue");
        
           mStringArray = tDecoder.readStringArray("stringArray", "array");
        
           mIntArray = tDecoder.readIntegerArray("intArray", "array");
        
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

    