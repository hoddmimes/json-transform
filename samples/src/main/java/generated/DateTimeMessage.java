
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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.hoddmimes.jsontransform.*;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@SuppressWarnings({"WeakerAccess", "unused", "unchecked"})
public class DateTimeMessage implements MessageInterface, MessageMongoInterface {
    public static String NAME = "DateTimeMessage";


    private String mMongoId = null;
    private LocalDate mDate;
    private LocalDateTime mDateTime;
    private Integer mValue;

    public DateTimeMessage() {

    }

    public DateTimeMessage(String pJsonString) {

        JsonDecoder tDecoder = new JsonDecoder(pJsonString);
        this.decode(tDecoder);
    }

    public static Builder getDateTimeMessageBuilder() {
        return new DateTimeMessage.Builder();
    }

    public String getMongoId() {
        return this.mMongoId;
    }

    public void setMongoId(String pMongoId) {
        this.mMongoId = pMongoId;
    }

    public Optional<LocalDate> getDate() {
        return Optional.ofNullable(mDate);
    }

    public DateTimeMessage setDate(LocalDate pDate) {
        mDate = pDate;
        return this;
    }

    public Optional<LocalDateTime> getDateTime() {
        return Optional.ofNullable(mDateTime);
    }

    public DateTimeMessage setDateTime(LocalDateTime pDateTime) {
        mDateTime = pDateTime;
        return this;
    }

    public Optional<Integer> getValue() {
        return Optional.ofNullable(mValue);
    }

    public DateTimeMessage setValue(Integer pValue) {
        mValue = pValue;
        return this;
    }

    public String getMessageName() {
        return "DateTimeMessage";
    }

    public JsonObject toJson() {
        JsonEncoder tEncoder = new JsonEncoder();
        this.encode(tEncoder);
        return tEncoder.toJson();
    }

    public void encode(JsonEncoder pEncoder) {


        JsonEncoder tEncoder = new JsonEncoder();
        pEncoder.add("DateTimeMessage", tEncoder.toJson());
        //Encode Attribute: mDate Type: LocalDate List: false
        tEncoder.add("date", mDate);

        //Encode Attribute: mDateTime Type: LocalDateTime List: false
        tEncoder.add("dateTime", mDateTime);

        //Encode Attribute: mValue Type: int List: false
        tEncoder.add("value", mValue);

    }

    public void decode(JsonDecoder pDecoder) {


        JsonDecoder tDecoder = pDecoder.get("DateTimeMessage");

        //Decode Attribute: mDate Type:LocalDate List: false
        mDate = tDecoder.readDate("date");

        //Decode Attribute: mDateTime Type:LocalDateTime List: false
        mDateTime = tDecoder.readDateTime("dateTime");

        //Decode Attribute: mValue Type:int List: false
        mValue = tDecoder.readInteger("value");


    }

    @Override
    public String toString() {
        Gson gsonPrinter = new GsonBuilder().setPrettyPrinting().create();
        return gsonPrinter.toJson(this.toJson());
    }

    public Document getMongoDocument() {
        MongoEncoder tEncoder = new MongoEncoder();

        mongoEncode(tEncoder);
        return tEncoder.getDoc();
    }

    protected void mongoEncode(MongoEncoder pEncoder) {

        pEncoder.add("date", mDate);
        pEncoder.add("dateTime", mDateTime);
        pEncoder.add("value", mValue);
    }

    public void decodeMongoDocument(Document pDoc) {

        Document tDoc = null;
        List<Document> tDocLst = null;
        MongoDecoder tDecoder = new MongoDecoder(pDoc);


        ObjectId _tId = pDoc.get("_id", ObjectId.class);
        this.mMongoId = _tId.toString();

        mDate = tDecoder.readDate("date");

        mDateTime = tDecoder.readDateTime("dateTime");

        mValue = tDecoder.readInteger("value");

    } // End decodeMongoDocument

    public static class Builder {
        private final DateTimeMessage mInstance;

        private Builder() {
            mInstance = new DateTimeMessage();
        }


        public Builder setDate(LocalDate pValue) {
            mInstance.setDate(pValue);
            return this;
        }

        public Builder setDateTime(LocalDateTime pValue) {
            mInstance.setDateTime(pValue);
            return this;
        }

        public Builder setValue(Integer pValue) {
            mInstance.setValue(pValue);
            return this;
        }


        public DateTimeMessage build() {
            return mInstance;
        }

    }

}

/**
 * Possible native attributes
 * o "boolean" mapped to JSON "Boolean"
 * o "byte" mapped to JSON "Integer"
 * o "char" mapped to JSON "Integer"
 * o "short" mapped to JSON "Integer"
 * o "int" mapped to JSON "Integer"
 * o "long" mapped to JSON "Integer"
 * o "double" mapped to JSON "Numeric"
 * o "String" mapped to JSON "String"
 * o "byte[]" mapped to JSON "String" (Base64 string)
 * <p>
 * <p>
 * An attribute could also be an "constant" i.e. having the property "constantGroup", should then refer to an defined /Constang/Group
 * conastants are mapped to JSON strings,
 * <p>
 * <p>
 * If the type is not any of the types below it will be refer to an other structure / object
 **/

    