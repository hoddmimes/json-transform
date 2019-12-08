
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
            public class Vehicle implements MessageInterface , MessageMongoInterface
            {
            
                private Type mType;
                    private String mLicensPlate;
                    private String mInsurensNumber;
                    private Integer mWheels;
               public Vehicle()
               {
                
               }

               public Vehicle(String pJsonString ) {
                    
                    JsonDecoder tDecoder = new JsonDecoder( pJsonString );
                    this.decode( tDecoder );
               }
    
            public Vehicle setType(Type pType) {
            mType = pType;
            return this;
            }

            public Optional<Type> getType() {
            return  Optional.ofNullable(mType);
            }
        
            public Vehicle setLicensPlate( String pLicensPlate ) {
            mLicensPlate = pLicensPlate;
            return this;
            }
            public Optional<String> getLicensPlate() {
              return  Optional.ofNullable(mLicensPlate);
            }
        
            public Vehicle setInsurensNumber( String pInsurensNumber ) {
            mInsurensNumber = pInsurensNumber;
            return this;
            }
            public Optional<String> getInsurensNumber() {
              return  Optional.ofNullable(mInsurensNumber);
            }
        
            public Vehicle setWheels( Integer pWheels ) {
            mWheels = pWheels;
            return this;
            }
            public Optional<Integer> getWheels() {
              return  Optional.ofNullable(mWheels);
            }
        

        public String getMessageName() {
        return "Vehicle";
        }
    

        public JsonObject toJson() {
            JsonEncoder tEncoder = new JsonEncoder();
            this.encode( tEncoder );
            return tEncoder.toJson();
        }

        
        public void encode( JsonEncoder pEncoder) {

        
            JsonEncoder tEncoder = pEncoder;
            //Encode Attribute: mType Type:  List: false
            tEncoder.add("type", mType.toString());
        
            //Encode Attribute: mLicensPlate Type: String List: false
            tEncoder.add( "licensPlate", mLicensPlate );
        
            //Encode Attribute: mInsurensNumber Type: String List: false
            tEncoder.add( "insurensNumber", mInsurensNumber );
        
            //Encode Attribute: mWheels Type: int List: false
            tEncoder.add( "wheels", mWheels );
        
        }

        
        public void decode( JsonDecoder pDecoder) {

        
            JsonDecoder tDecoder = pDecoder;
        
            //Decode Attribute: mType Type: List: false
            mType = (Type) tDecoder.readConstant("type", Type.class );
        
            //Decode Attribute: mLicensPlate Type:String List: false
            mLicensPlate = tDecoder.readString("licensPlate");
        
            //Decode Attribute: mInsurensNumber Type:String List: false
            mInsurensNumber = tDecoder.readString("insurensNumber");
        
            //Decode Attribute: mWheels Type:int List: false
            mWheels = tDecoder.readInteger("wheels");
        

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
        
                   pEncoder.add( "type", mType ); 
                pEncoder.add("licensPlate",  mLicensPlate );
                pEncoder.add("insurensNumber",  mInsurensNumber );
                pEncoder.add("wheels",  mWheels );
    }
    
        public void decodeMongoDocument( Document pDoc ) {

            Document tDoc = null;
            List<Document> tDocLst = null;
            MongoDecoder tDecoder = new MongoDecoder( pDoc );

            
                    mType = (Type) tDecoder.readConstant("type", Type.class); 
           mLicensPlate = tDecoder.readString("licensPlate");
        
           mInsurensNumber = tDecoder.readString("insurensNumber");
        
           mWheels = tDecoder.readInteger("wheels");
        
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

    