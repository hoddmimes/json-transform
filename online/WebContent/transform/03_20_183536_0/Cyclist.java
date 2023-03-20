
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
            public class Cyclist implements MessageInterface 
            {
                public static String NAME = "Cyclist";

            
                    private String mName;
                    private Integer mAge;
                    private String mTeam;
                    private Boolean mProfessional;
                    private Cycle mCycle;
                    private List<Result> mResults;
               public Cyclist()
               {
                
               }

               public Cyclist(String pJsonString ) {
                    
                    JsonDecoder tDecoder = new JsonDecoder( pJsonString );
                    this.decode( tDecoder );
               }
    
            public Cyclist setName( String pName ) {
            mName = pName;
            return this;
            }
            public Optional<String> getName() {
              return  Optional.ofNullable(mName);
            }
        
            public Cyclist setAge( Integer pAge ) {
            mAge = pAge;
            return this;
            }
            public Optional<Integer> getAge() {
              return  Optional.ofNullable(mAge);
            }
        
            public Cyclist setTeam( String pTeam ) {
            mTeam = pTeam;
            return this;
            }
            public Optional<String> getTeam() {
              return  Optional.ofNullable(mTeam);
            }
        
            public Cyclist setProfessional( Boolean pProfessional ) {
            mProfessional = pProfessional;
            return this;
            }
            public Optional<Boolean> getProfessional() {
              return  Optional.ofNullable(mProfessional);
            }
        

            public Optional<Cycle> getCycle() {
              return  Optional.ofNullable(mCycle);
            }

            public Cyclist setCycle(Cycle pCycle) {
            mCycle = pCycle;
            return this;
            }

        
            public Cyclist setResults( List<Result> pResults ) {
              if (pResults == null) {
                mResults = null;
                return this;
              }


            if ( mResults == null)
            mResults = ListFactory.getList("array");


            mResults .addAll( pResults );
            return this;
            }


            public Cyclist addResults( List<Result> pResults ) {

            if ( mResults == null)
            mResults = ListFactory.getList("array");

            mResults .addAll( pResults );
            return this;
            }

            public Cyclist addResults( Result pResults ) {

            if ( pResults == null) {
            return this; // Not supporting null in vectors, well design issue
            }

            if ( mResults == null) {
            mResults = ListFactory.getList("array");
            }

            mResults.add( pResults );
            return this;
            }


            public Optional<List<Result>> getResults() {

            if (mResults == null) {
                return  Optional.ofNullable(null);
            }

             //List<Result> tList = ListFactory.getList("array");
             //tList.addAll( mResults );
             // return  Optional.ofNullable(tList);
             return Optional.ofNullable(mResults);
            }

        
        public String getMessageName() {
        return "Cyclist";
        }
    

        public JsonObject toJson() {
            JsonEncoder tEncoder = new JsonEncoder();
            this.encode( tEncoder );
            return tEncoder.toJson();
        }

        
        public void encode( JsonEncoder pEncoder) {

        
            JsonEncoder tEncoder = pEncoder;
            //Encode Attribute: mName Type: String List: false
            tEncoder.add( "name", mName );
        
            //Encode Attribute: mAge Type: int List: false
            tEncoder.add( "age", mAge );
        
            //Encode Attribute: mTeam Type: String List: false
            tEncoder.add( "team", mTeam );
        
            //Encode Attribute: mProfessional Type: boolean List: false
            tEncoder.add( "professional", mProfessional );
        
            //Encode Attribute: mCycle Type: Cycle List: false
            tEncoder.add( "cycle", mCycle );
        
            //Encode Attribute: mResults Type: Result List: true
            tEncoder.addMessageArray("results", mResults );
        
        }

        
        public void decode( JsonDecoder pDecoder) {

        
            JsonDecoder tDecoder = pDecoder;
        
            //Decode Attribute: mName Type:String List: false
            mName = tDecoder.readString("name");
        
            //Decode Attribute: mAge Type:int List: false
            mAge = tDecoder.readInteger("age");
        
            //Decode Attribute: mTeam Type:String List: false
            mTeam = tDecoder.readString("team");
        
            //Decode Attribute: mProfessional Type:boolean List: false
            mProfessional = tDecoder.readBoolean("professional");
        
            //Decode Attribute: mCycle Type:Cycle List: false
            mCycle = (Cycle) tDecoder.readMessage( "cycle", Cycle.class );
        
            //Decode Attribute: mResults Type:Result List: true
            mResults = (List<Result>) tDecoder.readMessageArray( "results", "array", Result.class );
        

        }
    

        @Override
        public String toString() {
             Gson gsonPrinter = new GsonBuilder().setPrettyPrinting().create();
             return  gsonPrinter.toJson( this.toJson());
        }
    

        public static  Builder getCyclistBuilder() {
            return new Cyclist.Builder();
        }


        public static class  Builder {
          private Cyclist mInstance;

          private Builder () {
            mInstance = new Cyclist();
          }

        
                        public Builder setName( String pValue ) {
                        mInstance.setName( pValue );
                        return this;
                    }
                
                        public Builder setAge( Integer pValue ) {
                        mInstance.setAge( pValue );
                        return this;
                    }
                
                        public Builder setTeam( String pValue ) {
                        mInstance.setTeam( pValue );
                        return this;
                    }
                
                        public Builder setProfessional( Boolean pValue ) {
                        mInstance.setProfessional( pValue );
                        return this;
                    }
                
                    public Builder setCycle( Cycle pValue )  {
                        mInstance.setCycle( pValue );
                        return this;
                    }
                
                    public Builder setResults( List<Result> pValue )  {
                        mInstance.setResults( pValue );
                        return this;
                    }
                

        public Cyclist build() {
            return mInstance;
        }

        }
    
            }
            