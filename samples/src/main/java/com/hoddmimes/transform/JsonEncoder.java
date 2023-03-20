package com.hoddmimes.transform;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.bson.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"WeakerAccess","unused"})
public class JsonEncoder
{
    JsonObject mEncoder;

    public JsonEncoder() {
        mEncoder = new JsonObject();
    }

    public JsonObject toJson() {
        return mEncoder;
    }
    public void add(String pAttribute, JsonElement pValue) {
        mEncoder.add( pAttribute, pValue );
    }




    public void add( String pAttribute, String pValue ) {
        if (pValue == null) {
            return;
        }
        mEncoder.addProperty(pAttribute, pValue);
    }

    public void add( String pAttribute, LocalDate pValue ) {
        if (pValue == null) {
            return;
        }

        mEncoder.addProperty(pAttribute, DateUtils.localDateToString( pValue ));
    }

    public void add( String pAttribute, LocalDateTime pValue ) {
        if (pValue == null) {
            return;
        }

        mEncoder.addProperty(pAttribute, DateUtils.localDateTimeToString( pValue ));
    }

    public void add( String pAttribute, Map<String,String> pValue ) {
        if (pValue == null) {
            return;
        }
        mEncoder.add(pAttribute,  MapUtils.encodeMap(pValue));
    }

    public void add( String pAttribute, Boolean pValue ) {
        if (pValue == null) {
            return;
        }
        mEncoder.addProperty(pAttribute, pValue);
    }

    public void add( String pAttribute, Character pValue ) {
        if (pValue == null) {
            return;
        }
        mEncoder.addProperty(pAttribute, pValue);
    }

    public void add( String pAttribute, Byte pValue ) {
        if (pValue == null) {
            return;
        }
        mEncoder.addProperty(pAttribute, pValue);
    }

    public void add( String pAttribute, Short pValue ) {
        if (pValue == null) {
            return;
        }
        mEncoder.addProperty(pAttribute, pValue);
    }

    public void add( String pAttribute, Integer pValue ) {
        if (pValue == null) {
            return;
        }
        mEncoder.addProperty(pAttribute, pValue);
    }

    public void add( String pAttribute, Long pValue ) {
        if (pValue == null) {
            return;
        }
        mEncoder.addProperty(pAttribute, pValue);
    }

    public void add( String pAttribute, Double pValue ) {
        if (pValue == null) {
            return;
        }
        mEncoder.addProperty(pAttribute, pValue);
    }

    public void add( String pAttribute, byte[] pValue ) {
        if (pValue == null) {
            return;
        }
        String s = b64ToString( pValue );
        mEncoder.addProperty(pAttribute, s);
    }
    public void add( String pAttribute, MessageInterface pValue ) {
        if (pValue == null) {
            return;
        }
        JsonEncoder tEnc = new JsonEncoder();
        pValue.encode( tEnc );
        mEncoder.add(pAttribute, tEnc.toJson() );
    }

    public void addDateArray(String pAttribute, List<LocalDate> pValue) {
        if (pValue == null) {
            return;
        }
        JsonArray tArr = new JsonArray();
        for (LocalDate d : pValue) {
            tArr.add(DateUtils.localDateToString(d));
        }
        mEncoder.add(pAttribute, tArr);
    }

    public void addMapArray(String pAttribute, List<Map<String,String>> pValue) {
        if (pValue == null) {
            return;
        }
        JsonArray tArr = new JsonArray();
        for (Map<String,String> m : pValue) {
            tArr.add( MapUtils.encodeMap(m));
        }
        mEncoder.add(pAttribute, tArr);
    }

    public void addDateTimeArray(  String pAttribute, List<LocalDateTime> pValue ) {
        if (pValue == null) {
            return;
        }
        JsonArray tArr = new JsonArray();
        for (LocalDateTime d : pValue) {
            tArr.add( DateUtils.localDateTimeToString( d ));
        }
        mEncoder.add( pAttribute, tArr);
    }
    public void addByteArray(  String pAttribute, List<Byte> pValue ) {
        if (pValue == null) {
            return;
        }
        JsonArray tArr = new JsonArray();
        for (Byte v : pValue) {
            tArr.add( v );
        }
        mEncoder.add( pAttribute, tArr);
    }

    public void addStringArray(  String pAttribute, List<String> pValue ) {
        if (pValue == null) {
            return;
        }
        JsonArray tArr = new JsonArray();
        for (String v : pValue) {
            tArr.add( v );
        }
        mEncoder.add( pAttribute, tArr);
    }

    public void addBooleanArray(  String pAttribute, List<Boolean> pValue ) {
        if (pValue == null) {
            return;
        }
        JsonArray tArr = new JsonArray();
        for (Boolean v : pValue) {
            tArr.add( v );
        }
        mEncoder.add( pAttribute, tArr);
    }
    public void addCharacterArray(  String pAttribute, List<Character> pValue ) {
        if (pValue == null) {
            return;
        }
        JsonArray tArr = new JsonArray();
        for (Character v : pValue) {
            tArr.add( v );
        }
        mEncoder.add( pAttribute, tArr);
    }

    public void addShortArray(  String pAttribute, List<Short> pValue ) {
        if (pValue == null) {
            return;
        }
        JsonArray tArr = new JsonArray();
        for (Short v : pValue) {
            tArr.add( v );
        }
        mEncoder.add( pAttribute, tArr);
    }

    public void addIntegerArray(  String pAttribute, List<Integer> pValue ) {
        if (pValue == null) {
            return;
        }
        JsonArray tArr = new JsonArray();
        for (Integer v : pValue) {
            tArr.add( v );
        }
        mEncoder.add( pAttribute, tArr);
    }

    public void addLongArray(  String pAttribute, List<Long> pValue ) {
        if (pValue == null) {
            return;
        }
        JsonArray tArr = new JsonArray();
        for (Long v : pValue) {
            tArr.add( v );
        }
        mEncoder.add( pAttribute, tArr);
    }

    public void addDoubleArray(  String pAttribute, List<Double> pValue ) {
        if (pValue == null) {
            return;
        }
        JsonArray tArr = new JsonArray();
        for (Double v : pValue) {
            tArr.add(  v );
        }
        mEncoder.add( pAttribute, tArr);
    }

    public void addByteVectorArray(  String pAttribute, List<byte[]> pValue ) {
        if (pValue == null) {
            return;
        }
        JsonArray tArr = new JsonArray();
        for (byte[] v : pValue) {
            String s = b64ToString( v );
            tArr.add( s );
        }
        mEncoder.add( pAttribute, tArr);
    }

    public void addMessageArray( String pAttribute, List<? extends MessageInterface> pValue) {
        if (pValue == null) {
            return;
        }
        JsonArray tArr = new JsonArray();
        for (MessageInterface m : pValue) {
            JsonEncoder tEnc = new JsonEncoder();
            m.encode( tEnc );
            tArr.add( tEnc.toJson());
        }
        mEncoder.add( pAttribute, tArr);
    }

    public void addConstantArray( String pAttribute, List<? extends Enum> pValue) {
        if (pValue == null) {
            return;
        }
        JsonArray tArr = new JsonArray();
        for (Enum e : pValue) {
            tArr.add( e.toString());
        }
        mEncoder.add( pAttribute, tArr);
    }

    private String b64ToString( byte[] pValue) {
        if (pValue == null) {
            return null;
        }
        String s = Base64.getEncoder().encodeToString( pValue );
        return s;
    }

    public static String byteArrayToString( byte[] pBytArr ) {
        if (pBytArr == null) {
            return null;
        }
        return Base64.getEncoder().encodeToString( pBytArr );
    }

    public static List<String> byteArraysToStrings( List<byte[]> pBytArrs ) {
        if (pBytArrs == null) {
            return null;
        }
        List<String> tStrArr = new ArrayList<>();
        for( byte[] ba : pBytArrs ) {
            tStrArr.add( Base64.getEncoder().encodeToString( ba ));
        }
        return tStrArr;
    }

    public static String constToString(Enum<?> pConst) {
        if (pConst == null) {
            return null;
        }
        return pConst.toString();
    }

    public static List<String> constsToStrings(List<?> pConstArray) {
        if (pConstArray == null) {
            return null;
        }
        List<String> tStrLst = new ArrayList<>();
        for( Object obj : pConstArray) {
            Enum tEnum = (Enum) obj;
            tStrLst.add( constToString( tEnum ));
        }
        return tStrLst;
    }

    public static Document msgToMongoDocument( MessageMongoInterface pMsg )
    {
        if (pMsg == null) {
            return null;
        }
        return pMsg.getMongoDocument();
    }

    public static List<Document> msgsToMongoDocuments( List<? extends MessageMongoInterface> pMsgs )
    {
        if (pMsgs == null) {
            return null;
        }
        List<Document> tDocLst = new ArrayList<>();
        for( MessageMongoInterface msg : pMsgs) {
            tDocLst.add( msg.getMongoDocument());
        }
        return tDocLst;
    }
}
