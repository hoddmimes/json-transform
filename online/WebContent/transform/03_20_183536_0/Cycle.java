
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





import com.hoddmimes.transform.MessageInterface;
import com.hoddmimes.transform.JsonDecoder;
import com.hoddmimes.transform.JsonEncoder;
import com.hoddmimes.transform.ListFactory;
import com.google.gson.JsonObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;



            

            @SuppressWarnings({"WeakerAccess","unused","unchecked"})
            public class Cycle implements MessageInterface 
            {
                public static String NAME = "Cycle";

            
                    private String mModel;
                    private String mManufacture;
                    private Integer mWeight;
               public Cycle()
               {
                
               }

               public Cycle(String pJsonString ) {
                    
                    JsonDecoder tDecoder = new JsonDecoder( pJsonString );
                    this.decode( tDecoder );
               }
    
            public Cycle setModel( String pModel ) {
            mModel = pModel;
            return this;
            }
            public Optional<String> getModel() {
              return  Optional.ofNullable(mModel);
            }
        
            public Cycle setManufacture( String pManufacture ) {
            mManufacture = pManufacture;
            return this;
            }
            public Optional<String> getManufacture() {
              return  Optional.ofNullable(mManufacture);
            }
        
            public Cycle setWeight( Integer pWeight ) {
            mWeight = pWeight;
            return this;
            }
            public Optional<Integer> getWeight() {
              return  Optional.ofNullable(mWeight);
            }
        
        public String getMessageName() {
        return "Cycle";
        }
    

        public JsonObject toJson() {
            JsonEncoder tEncoder = new JsonEncoder();
            this.encode( tEncoder );
            return tEncoder.toJson();
        }

        
        public void encode( JsonEncoder pEncoder) {

        
            JsonEncoder tEncoder = pEncoder;
            //Encode Attribute: mModel Type: String List: false
            tEncoder.add( "model", mModel );
        
            //Encode Attribute: mManufacture Type: String List: false
            tEncoder.add( "manufacture", mManufacture );
        
            //Encode Attribute: mWeight Type: int List: false
            tEncoder.add( "weight", mWeight );
        
        }

        
        public void decode( JsonDecoder pDecoder) {

        
            JsonDecoder tDecoder = pDecoder;
        
            //Decode Attribute: mModel Type:String List: false
            mModel = tDecoder.readString("model");
        
            //Decode Attribute: mManufacture Type:String List: false
            mManufacture = tDecoder.readString("manufacture");
        
            //Decode Attribute: mWeight Type:int List: false
            mWeight = tDecoder.readInteger("weight");
        

        }
    

        @Override
        public String toString() {
             Gson gsonPrinter = new GsonBuilder().setPrettyPrinting().create();
             return  gsonPrinter.toJson( this.toJson());
        }
    

        public static  Builder getCycleBuilder() {
            return new Cycle.Builder();
        }


        public static class  Builder {
          private Cycle mInstance;

          private Builder () {
            mInstance = new Cycle();
          }

        
                        public Builder setModel( String pValue ) {
                        mInstance.setModel( pValue );
                        return this;
                    }
                
                        public Builder setManufacture( String pValue ) {
                        mInstance.setManufacture( pValue );
                        return this;
                    }
                
                        public Builder setWeight( Integer pValue ) {
                        mInstance.setWeight( pValue );
                        return this;
                    }
                

        public Cycle build() {
            return mInstance;
        }

        }
    
            }
            