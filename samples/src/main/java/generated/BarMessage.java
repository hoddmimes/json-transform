
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
            public class BarMessage implements MessageInterface 
            {
                public static String NAME = "BarMessage";

            
                    private String mStrValue;
                    private Integer mIntValue;
                    private Boolean mBoolValue;
                    private Double mDoubleValue;
               public BarMessage()
               {
                
               }

               public BarMessage(String pJsonString ) {
                    
                    JsonDecoder tDecoder = new JsonDecoder( pJsonString );
                    this.decode( tDecoder );
               }
    
            public BarMessage setStrValue( String pStrValue ) {
            mStrValue = pStrValue;
            return this;
            }
            public Optional<String> getStrValue() {
              return  Optional.ofNullable(mStrValue);
            }
        
            public BarMessage setIntValue( Integer pIntValue ) {
            mIntValue = pIntValue;
            return this;
            }
            public Optional<Integer> getIntValue() {
              return  Optional.ofNullable(mIntValue);
            }
        
            public BarMessage setBoolValue( Boolean pBoolValue ) {
            mBoolValue = pBoolValue;
            return this;
            }
            public Optional<Boolean> getBoolValue() {
              return  Optional.ofNullable(mBoolValue);
            }
        
            public BarMessage setDoubleValue( Double pDoubleValue ) {
            mDoubleValue = pDoubleValue;
            return this;
            }
            public Optional<Double> getDoubleValue() {
              return  Optional.ofNullable(mDoubleValue);
            }
        
        public String getMessageName() {
        return "BarMessage";
        }
    

        public JsonObject toJson() {
            JsonEncoder tEncoder = new JsonEncoder();
            this.encode( tEncoder );
            return tEncoder.toJson();
        }

        
        public void encode( JsonEncoder pEncoder) {

        
            JsonEncoder tEncoder = pEncoder;
            //Encode Attribute: mStrValue Type: String List: false
            tEncoder.add( "strValue", mStrValue );
        
            //Encode Attribute: mIntValue Type: int List: false
            tEncoder.add( "intValue", mIntValue );
        
            //Encode Attribute: mBoolValue Type: boolean List: false
            tEncoder.add( "boolValue", mBoolValue );
        
            //Encode Attribute: mDoubleValue Type: double List: false
            tEncoder.add( "doubleValue", mDoubleValue );
        
        }

        
        public void decode( JsonDecoder pDecoder) {

        
            JsonDecoder tDecoder = pDecoder;
        
            //Decode Attribute: mStrValue Type:String List: false
            mStrValue = tDecoder.readString("strValue");
        
            //Decode Attribute: mIntValue Type:int List: false
            mIntValue = tDecoder.readInteger("intValue");
        
            //Decode Attribute: mBoolValue Type:boolean List: false
            mBoolValue = tDecoder.readBoolean("boolValue");
        
            //Decode Attribute: mDoubleValue Type:double List: false
            mDoubleValue = tDecoder.readDouble("doubleValue");
        

        }
    

        @Override
        public String toString() {
             Gson gsonPrinter = new GsonBuilder().setPrettyPrinting().create();
             return  gsonPrinter.toJson( this.toJson());
        }
    

        public static  Builder getBarMessageBuilder() {
            return new BarMessage.Builder();
        }


        public static class  Builder {
          private BarMessage mInstance;

          private Builder () {
            mInstance = new BarMessage();
          }

        
                        public Builder setStrValue( String pValue ) {
                        mInstance.setStrValue( pValue );
                        return this;
                    }
                
                        public Builder setIntValue( Integer pValue ) {
                        mInstance.setIntValue( pValue );
                        return this;
                    }
                
                        public Builder setBoolValue( Boolean pValue ) {
                        mInstance.setBoolValue( pValue );
                        return this;
                    }
                
                        public Builder setDoubleValue( Double pValue ) {
                        mInstance.setDoubleValue( pValue );
                        return this;
                    }
                

        public BarMessage build() {
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

    