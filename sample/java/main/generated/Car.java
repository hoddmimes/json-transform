
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
            public class Car extends Vehicle implements MessageInterface , MessageMongoInterface
            {
            
                private String mMongoId = null;
                    private String mColor;
                    private Boolean mAirbags;
                    private Integer mSeats;
                    private String mManufactor;
                    private Integer mProductionYear;
               public Car()
               {
                super();
               }

               public Car(String pJsonString ) {
                    super( pJsonString );
                    JsonDecoder tDecoder = new JsonDecoder( pJsonString );
                    this.decode( tDecoder );
               }
    
            public String getMongoId() {
            return this.mMongoId;
            }

            public void setMongoId( String pMongoId ) {
            this.mMongoId = pMongoId;
            }
        
            public Car setColor( String pColor ) {
            mColor = pColor;
            return this;
            }
            public Optional<String> getColor() {
              return  Optional.ofNullable(mColor);
            }
        
            public Car setAirbags( Boolean pAirbags ) {
            mAirbags = pAirbags;
            return this;
            }
            public Optional<Boolean> getAirbags() {
              return  Optional.ofNullable(mAirbags);
            }
        
            public Car setSeats( Integer pSeats ) {
            mSeats = pSeats;
            return this;
            }
            public Optional<Integer> getSeats() {
              return  Optional.ofNullable(mSeats);
            }
        
            public Car setManufactor( String pManufactor ) {
            mManufactor = pManufactor;
            return this;
            }
            public Optional<String> getManufactor() {
              return  Optional.ofNullable(mManufactor);
            }
        
            public Car setProductionYear( Integer pProductionYear ) {
            mProductionYear = pProductionYear;
            return this;
            }
            public Optional<Integer> getProductionYear() {
              return  Optional.ofNullable(mProductionYear);
            }
        

        public String getMessageName() {
        return "Car";
        }
    

        public JsonObject toJson() {
            JsonEncoder tEncoder = new JsonEncoder();
            this.encode( tEncoder );
            return tEncoder.toJson();
        }

        
        public void encode( JsonEncoder pEncoder) {

        
            JsonEncoder tEncoder = new JsonEncoder();
            pEncoder.add("Car", tEncoder.toJson() );super.encode( tEncoder );
            //Encode Attribute: mColor Type: String List: false
            tEncoder.add( "color", mColor );
        
            //Encode Attribute: mAirbags Type: boolean List: false
            tEncoder.add( "airbags", mAirbags );
        
            //Encode Attribute: mSeats Type: int List: false
            tEncoder.add( "seats", mSeats );
        
            //Encode Attribute: mManufactor Type: String List: false
            tEncoder.add( "manufactor", mManufactor );
        
            //Encode Attribute: mProductionYear Type: int List: false
            tEncoder.add( "productionYear", mProductionYear );
        
        }

        
        public void decode( JsonDecoder pDecoder) {

        
            JsonDecoder tDecoder = pDecoder.get("Car");
        super.decode( tDecoder );
            //Decode Attribute: mColor Type:String List: false
            mColor = tDecoder.readString("color");
        
            //Decode Attribute: mAirbags Type:boolean List: false
            mAirbags = tDecoder.readBoolean("airbags");
        
            //Decode Attribute: mSeats Type:int List: false
            mSeats = tDecoder.readInteger("seats");
        
            //Decode Attribute: mManufactor Type:String List: false
            mManufactor = tDecoder.readString("manufactor");
        
            //Decode Attribute: mProductionYear Type:int List: false
            mProductionYear = tDecoder.readInteger("productionYear");
        

        }
    

        @Override
        public String toString() {
             Gson gsonPrinter = new GsonBuilder().setPrettyPrinting().create();
             return  gsonPrinter.toJson( this.toJson());
        }
    
        public Document getMongoDocument() {
            MongoEncoder tEncoder = new MongoEncoder();
            super.mongoEncode( tEncoder );
            mongoEncode( tEncoder );
            return tEncoder.getDoc();
        }

     protected void mongoEncode(  MongoEncoder pEncoder ) {
        
                pEncoder.add("color",  mColor );
                pEncoder.add("airbags",  mAirbags );
                pEncoder.add("seats",  mSeats );
                pEncoder.add("manufactor",  mManufactor );
                pEncoder.add("productionYear",  mProductionYear );
    }
    
        public void decodeMongoDocument( Document pDoc ) {

            Document tDoc = null;
            List<Document> tDocLst = null;
            MongoDecoder tDecoder = new MongoDecoder( pDoc );

            super.decodeMongoDocument( pDoc );
            ObjectId _tId = pDoc.get("_id", ObjectId.class);
            this.mMongoId = _tId.toString();
            
           mColor = tDecoder.readString("color");
        
           mAirbags = tDecoder.readBoolean("airbags");
        
           mSeats = tDecoder.readInteger("seats");
        
           mManufactor = tDecoder.readString("manufactor");
        
           mProductionYear = tDecoder.readInteger("productionYear");
        
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

    