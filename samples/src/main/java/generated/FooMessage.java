
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
            public class FooMessage implements MessageInterface 
            {
                public static String NAME = "FooMessage";

            
                    private Map<String,String> mMapValue;
                    private List<Map<String,String>> mMapArray;
                    private Integer mIntValue;
                    private String mStrValue;
                    private List<BarMessage> mBarArray;
                    private List<FieMessage> mFieArray;
               public FooMessage()
               {
                
               }

               public FooMessage(String pJsonString ) {
                    
                    JsonDecoder tDecoder = new JsonDecoder( pJsonString );
                    this.decode( tDecoder );
               }
    
            public FooMessage setMapValue( Map<String,String> pMapValue ) {
            mMapValue = pMapValue;
            return this;
            }
            public Optional<Map<String,String>> getMapValue() {
              return  Optional.ofNullable(mMapValue);
            }
        
            public FooMessage setMapArray(List<Map<String,String>> pMapArray ) {
            if ( pMapArray == null) {
            mMapArray = null;
            } else {
            mMapArray = ListFactory.getList("linked");
            mMapArray.addAll( pMapArray );
            }
            return this;
            }

            public Optional<List<Map<String,String>>> getMapArray()
            {
            return  Optional.ofNullable(mMapArray);
            }
        
            public FooMessage setIntValue( Integer pIntValue ) {
            mIntValue = pIntValue;
            return this;
            }
            public Optional<Integer> getIntValue() {
              return  Optional.ofNullable(mIntValue);
            }
        
            public FooMessage setStrValue( String pStrValue ) {
            mStrValue = pStrValue;
            return this;
            }
            public Optional<String> getStrValue() {
              return  Optional.ofNullable(mStrValue);
            }
        
            public FooMessage setBarArray( List<BarMessage> pBarArray ) {
              if (pBarArray == null) {
                mBarArray = null;
                return this;
              }


            if ( mBarArray == null)
            mBarArray = ListFactory.getList("array");


            mBarArray .addAll( pBarArray );
            return this;
            }


            public FooMessage addBarArray( List<BarMessage> pBarArray ) {

            if ( mBarArray == null)
            mBarArray = ListFactory.getList("array");

            mBarArray .addAll( pBarArray );
            return this;
            }

            public FooMessage addBarArray( BarMessage pBarArray ) {

            if ( pBarArray == null) {
            return this; // Not supporting null in vectors, well design issue
            }

            if ( mBarArray == null) {
            mBarArray = ListFactory.getList("array");
            }

            mBarArray.add( pBarArray );
            return this;
            }


            public Optional<List<BarMessage>> getBarArray() {

            if (mBarArray == null) {
                return  Optional.ofNullable(null);
            }

             //List<BarMessage> tList = ListFactory.getList("array");
             //tList.addAll( mBarArray );
             // return  Optional.ofNullable(tList);
             return Optional.ofNullable(mBarArray);
            }

        
            public FooMessage setFieArray( List<FieMessage> pFieArray ) {
              if (pFieArray == null) {
                mFieArray = null;
                return this;
              }


            if ( mFieArray == null)
            mFieArray = ListFactory.getList("array");


            mFieArray .addAll( pFieArray );
            return this;
            }


            public FooMessage addFieArray( List<FieMessage> pFieArray ) {

            if ( mFieArray == null)
            mFieArray = ListFactory.getList("array");

            mFieArray .addAll( pFieArray );
            return this;
            }

            public FooMessage addFieArray( FieMessage pFieArray ) {

            if ( pFieArray == null) {
            return this; // Not supporting null in vectors, well design issue
            }

            if ( mFieArray == null) {
            mFieArray = ListFactory.getList("array");
            }

            mFieArray.add( pFieArray );
            return this;
            }


            public Optional<List<FieMessage>> getFieArray() {

            if (mFieArray == null) {
                return  Optional.ofNullable(null);
            }

             //List<FieMessage> tList = ListFactory.getList("array");
             //tList.addAll( mFieArray );
             // return  Optional.ofNullable(tList);
             return Optional.ofNullable(mFieArray);
            }

        
        public String getMessageName() {
        return "FooMessage";
        }
    

        public JsonObject toJson() {
            JsonEncoder tEncoder = new JsonEncoder();
            this.encode( tEncoder );
            return tEncoder.toJson();
        }

        
        public void encode( JsonEncoder pEncoder) {

        
            JsonEncoder tEncoder = pEncoder;
            //Encode Attribute: mMapValue Type: Map List: false
            tEncoder.add( "mapValue", mMapValue );
        
            //Encode Attribute: mMapArray Type: Map List: true
            tEncoder.addMapArray("mapArray", mMapArray );
        
            //Encode Attribute: mIntValue Type: int List: false
            tEncoder.add( "intValue", mIntValue );
        
            //Encode Attribute: mStrValue Type: String List: false
            tEncoder.add( "strValue", mStrValue );
        
            //Encode Attribute: mBarArray Type: BarMessage List: true
            tEncoder.addMessageArray("barArray", mBarArray );
        
            //Encode Attribute: mFieArray Type: FieMessage List: true
            tEncoder.addMessageArray("fieArray", mFieArray );
        
        }

        
        public void decode( JsonDecoder pDecoder) {

        
            JsonDecoder tDecoder = pDecoder;
        
            //Decode Attribute: mMapValue Type:Map List: false
            mMapValue = tDecoder.readMap("mapValue");
        
            //Decode Attribute: mMapArray Type:Map List: true
            mMapArray = tDecoder.readMapArray("mapArray", "linked");
        
            //Decode Attribute: mIntValue Type:int List: false
            mIntValue = tDecoder.readInteger("intValue");
        
            //Decode Attribute: mStrValue Type:String List: false
            mStrValue = tDecoder.readString("strValue");
        
            //Decode Attribute: mBarArray Type:BarMessage List: true
            mBarArray = (List<BarMessage>) tDecoder.readMessageArray( "barArray", "array", BarMessage.class );
        
            //Decode Attribute: mFieArray Type:FieMessage List: true
            mFieArray = (List<FieMessage>) tDecoder.readMessageArray( "fieArray", "array", FieMessage.class );
        

        }
    

        @Override
        public String toString() {
             Gson gsonPrinter = new GsonBuilder().setPrettyPrinting().create();
             return  gsonPrinter.toJson( this.toJson());
        }
    

        public static  Builder getFooMessageBuilder() {
            return new FooMessage.Builder();
        }


        public static class  Builder {
          private FooMessage mInstance;

          private Builder () {
            mInstance = new FooMessage();
          }

        
                        public Builder setMapValue( Map<String,String> pValue ) {
                        mInstance.setMapValue( pValue );
                        return this;
                    }
                
                        public Builder setMapArray( List<Map<String,String>> pValue ) {
                        mInstance.setMapArray( pValue );
                        return this;
                    }
                
                        public Builder setIntValue( Integer pValue ) {
                        mInstance.setIntValue( pValue );
                        return this;
                    }
                
                        public Builder setStrValue( String pValue ) {
                        mInstance.setStrValue( pValue );
                        return this;
                    }
                
                    public Builder setBarArray( List<BarMessage> pValue )  {
                        mInstance.setBarArray( pValue );
                        return this;
                    }
                
                    public Builder setFieArray( List<FieMessage> pValue )  {
                        mInstance.setFieArray( pValue );
                        return this;
                    }
                

        public FooMessage build() {
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

    