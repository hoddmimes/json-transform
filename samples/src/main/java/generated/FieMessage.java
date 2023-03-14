
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
            public class FieMessage implements MessageInterface 
            {
                public static String NAME = "FieMessage";

            
                    private String mName;
                    private String mValue;
               public FieMessage()
               {
                
               }

               public FieMessage(String pJsonString ) {
                    
                    JsonDecoder tDecoder = new JsonDecoder( pJsonString );
                    this.decode( tDecoder );
               }
    
            public FieMessage setName( String pName ) {
            mName = pName;
            return this;
            }
            public Optional<String> getName() {
              return  Optional.ofNullable(mName);
            }
        
            public FieMessage setValue( String pValue ) {
            mValue = pValue;
            return this;
            }
            public Optional<String> getValue() {
              return  Optional.ofNullable(mValue);
            }
        
        public String getMessageName() {
        return "FieMessage";
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
        
            //Encode Attribute: mValue Type: String List: false
            tEncoder.add( "value", mValue );
        
        }

        
        public void decode( JsonDecoder pDecoder) {

        
            JsonDecoder tDecoder = pDecoder;
        
            //Decode Attribute: mName Type:String List: false
            mName = tDecoder.readString("name");
        
            //Decode Attribute: mValue Type:String List: false
            mValue = tDecoder.readString("value");
        

        }
    

        @Override
        public String toString() {
             Gson gsonPrinter = new GsonBuilder().setPrettyPrinting().create();
             return  gsonPrinter.toJson( this.toJson());
        }
    

        public static  Builder getFieMessageBuilder() {
            return new FieMessage.Builder();
        }


        public static class  Builder {
          private FieMessage mInstance;

          private Builder () {
            mInstance = new FieMessage();
          }

        
                        public Builder setName( String pValue ) {
                        mInstance.setName( pValue );
                        return this;
                    }
                
                        public Builder setValue( String pValue ) {
                        mInstance.setValue( pValue );
                        return this;
                    }
                

        public FieMessage build() {
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

    