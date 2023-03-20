
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
            public class Result implements MessageInterface 
            {
                public static String NAME = "Result";

            
                    private String mRace;
                    private Double mUciRanking;
                    private Integer mPlace;
                    private LocalDate mDate;
               public Result()
               {
                
               }

               public Result(String pJsonString ) {
                    
                    JsonDecoder tDecoder = new JsonDecoder( pJsonString );
                    this.decode( tDecoder );
               }
    
            public Result setRace( String pRace ) {
            mRace = pRace;
            return this;
            }
            public Optional<String> getRace() {
              return  Optional.ofNullable(mRace);
            }
        
            public Result setUciRanking( Double pUciRanking ) {
            mUciRanking = pUciRanking;
            return this;
            }
            public Optional<Double> getUciRanking() {
              return  Optional.ofNullable(mUciRanking);
            }
        
            public Result setPlace( Integer pPlace ) {
            mPlace = pPlace;
            return this;
            }
            public Optional<Integer> getPlace() {
              return  Optional.ofNullable(mPlace);
            }
        
            public Result setDate( LocalDate pDate ) {
            mDate = pDate;
            return this;
            }
            public Optional<LocalDate> getDate() {
              return  Optional.ofNullable(mDate);
            }
        
        public String getMessageName() {
        return "Result";
        }
    

        public JsonObject toJson() {
            JsonEncoder tEncoder = new JsonEncoder();
            this.encode( tEncoder );
            return tEncoder.toJson();
        }

        
        public void encode( JsonEncoder pEncoder) {

        
            JsonEncoder tEncoder = pEncoder;
            //Encode Attribute: mRace Type: String List: false
            tEncoder.add( "race", mRace );
        
            //Encode Attribute: mUciRanking Type: double List: false
            tEncoder.add( "uciRanking", mUciRanking );
        
            //Encode Attribute: mPlace Type: int List: false
            tEncoder.add( "place", mPlace );
        
            //Encode Attribute: mDate Type: LocalDate List: false
            tEncoder.add( "date", mDate );
        
        }

        
        public void decode( JsonDecoder pDecoder) {

        
            JsonDecoder tDecoder = pDecoder;
        
            //Decode Attribute: mRace Type:String List: false
            mRace = tDecoder.readString("race");
        
            //Decode Attribute: mUciRanking Type:double List: false
            mUciRanking = tDecoder.readDouble("uciRanking");
        
            //Decode Attribute: mPlace Type:int List: false
            mPlace = tDecoder.readInteger("place");
        
            //Decode Attribute: mDate Type:LocalDate List: false
            mDate = tDecoder.readDate("date");
        

        }
    

        @Override
        public String toString() {
             Gson gsonPrinter = new GsonBuilder().setPrettyPrinting().create();
             return  gsonPrinter.toJson( this.toJson());
        }
    

        public static  Builder getResultBuilder() {
            return new Result.Builder();
        }


        public static class  Builder {
          private Result mInstance;

          private Builder () {
            mInstance = new Result();
          }

        
                        public Builder setRace( String pValue ) {
                        mInstance.setRace( pValue );
                        return this;
                    }
                
                        public Builder setUciRanking( Double pValue ) {
                        mInstance.setUciRanking( pValue );
                        return this;
                    }
                
                        public Builder setPlace( Integer pValue ) {
                        mInstance.setPlace( pValue );
                        return this;
                    }
                
                        public Builder setDate( LocalDate pValue ) {
                        mInstance.setDate( pValue );
                        return this;
                    }
                

        public Result build() {
            return mInstance;
        }

        }
    
            }
            