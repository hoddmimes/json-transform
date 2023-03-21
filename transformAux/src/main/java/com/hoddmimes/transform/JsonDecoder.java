package com.hoddmimes.transform;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Base64;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static java.lang.Enum.valueOf;

@SuppressWarnings({"WeakerAccess","unused"})
public class JsonDecoder
{
        private final JsonObject mDecoder;

    public JsonDecoder(JsonObject pJsonObject) {
        mDecoder = pJsonObject;
    }


    public JsonDecoder(String pJsonMsgString) {
        JsonParser tParser = new JsonParser();
        mDecoder = tParser.parse(pJsonMsgString).getAsJsonObject();
    }

    public JsonDecoder get( String pAttribute ) {
        if (mDecoder.get( pAttribute ) == null) {
            return null;
        }
        JsonObject tJsonObj = mDecoder.get( pAttribute ).getAsJsonObject();
        return new JsonDecoder( tJsonObj );
    }


    public LocalDate readDate(String pAttribute) {
        if (mDecoder.get(pAttribute) == null) {
            return null;
        }
        return DateUtils.stringToLocalDate( mDecoder.get(pAttribute).getAsString());
    }

    public LocalDateTime readDateTime(String pAttribute) {
        if (mDecoder.get(pAttribute) == null) {
            return null;
        }
        return DateUtils.stringToLocalDateTime( mDecoder.get(pAttribute).getAsString());
    }

    public Map<String,String> readMap(String pAttribute) {
        if (mDecoder.get(pAttribute) == null) {
            return null;
        }
        return MapUtils.readMap( mDecoder.get(pAttribute).getAsJsonObject());
    }

    public List<LocalDate> readDateArray(String pAttribute, String pListType) {
        if (mDecoder.get(pAttribute) == null) {
            return null;
        }
        JsonArray tArr = mDecoder.get(pAttribute).getAsJsonArray();
        List<LocalDate> tList =  ListFactory.getList( pListType );
        for (int i = 0; i < tArr.size(); i++) {
            tList.add( DateUtils.stringToLocalDate( tArr.get(i).getAsString()));
        }
        return tList;
    }

    public List<LocalDateTime> readDateTimeArray(String pAttribute, String pListType) {
        if (mDecoder.get(pAttribute) == null) {
            return null;
        }
        JsonArray tArr = mDecoder.get(pAttribute).getAsJsonArray();
        List<LocalDateTime> tList =  ListFactory.getList( pListType );
        for (int i = 0; i < tArr.size(); i++) {
            tList.add( DateUtils.stringToLocalDateTime( tArr.get(i).getAsString()));
        }
        return tList;
    }

    public List<Map<String,String>> readMapArray(String pAttribute, String pListType) {
        if (mDecoder.get(pAttribute) == null) {
            return null;
        }
        JsonArray tArr = mDecoder.get(pAttribute).getAsJsonArray();
        List<Map<String,String>>  tList =  ListFactory.getList( pListType );
        for (int i = 0; i < tArr.size(); i++) {
            tList.add( MapUtils.readMap( tArr.get(i).getAsJsonObject()));
        }
        return tList;
    }


    public Boolean readBoolean(String pAttribute) {
        if (mDecoder.get(pAttribute) == null) {
            return null;
        }
        return mDecoder.get(pAttribute).getAsBoolean();
    }

    public List<Boolean> readBooleanArray(String pAttribute, String pListType) {
        if (mDecoder.get(pAttribute) == null) {
            return null;
        }
        JsonArray tArr = mDecoder.get(pAttribute).getAsJsonArray();
        List<Boolean> tList =  ListFactory.getList( pListType );
        for (int i = 0; i < tArr.size(); i++) {
            tList.add(tArr.get(i).getAsBoolean());
        }
        return tList;
    }

    public Byte readByte(String pAttribute) {
        if (mDecoder.get(pAttribute) == null) {
            return null;
        }
        return mDecoder.get(pAttribute).getAsByte();
    }

    public List<Byte> readByteArray(String pAttribute, String pListType) {
        if (mDecoder.get(pAttribute) == null) {
            return null;
        }
        JsonArray tArr = mDecoder.get(pAttribute).getAsJsonArray();
        List<Byte> tList =  ListFactory.getList( pListType );
        for (int i = 0; i < tArr.size(); i++) {
            tList.add(tArr.get(i).getAsByte());
        }
        return tList;
    }

    public Short readShort(String pAttribute) {
        if (mDecoder.get(pAttribute) == null) {
            return null;
        }
        return mDecoder.get(pAttribute).getAsShort();
    }

    public List<Short> readShortArray(String pAttribute, String pListType) {
        if (mDecoder.get(pAttribute) == null) {
            return null;
        }
        JsonArray tArr = mDecoder.get(pAttribute).getAsJsonArray();
        List<Short> tList = ListFactory.getList( pListType );
        for (int i = 0; i < tArr.size(); i++) {
            tList.add(tArr.get(i).getAsShort());
        }
        return tList;
    }

    public Integer readInteger(String pAttribute) {
        if (mDecoder.get(pAttribute) == null) {
            return null;
        }
        return mDecoder.get(pAttribute).getAsInt();
    }

    public List<Integer> readIntegerArray(String pAttribute, String pListType) {
        if (mDecoder.get(pAttribute) == null) {
            return null;
        }
        JsonArray tArr = mDecoder.get(pAttribute).getAsJsonArray();
        List<Integer> tList = ListFactory.getList( pListType );
        for (int i = 0; i < tArr.size(); i++) {
            tList.add(tArr.get(i).getAsInt());
        }
        return tList;
    }

    public Character readCharacter(String pAttribute) {
        if (mDecoder.get(pAttribute) == null) {
            return null;
        }
        return mDecoder.get(pAttribute).getAsCharacter();
    }

    public List<Character> readCharacterArray(String pAttribute, String pListType) {
        if (mDecoder.get(pAttribute) == null) {
            return null;
        }
        JsonArray tArr = mDecoder.get(pAttribute).getAsJsonArray();
        List<Character> tList =  ListFactory.getList( pListType );
        for (int i = 0; i < tArr.size(); i++) {
            tList.add(tArr.get(i).getAsCharacter());
        }
        return tList;
    }

    public Long readLong(String pAttribute) {
        if (mDecoder.get(pAttribute) == null) {
            return null;
        }
        return mDecoder.get(pAttribute).getAsLong();
    }

    public List<Long> readLongArray(String pAttribute, String pListType) {
        if (mDecoder.get(pAttribute) == null) {
            return null;
        }
        JsonArray tArr = mDecoder.get(pAttribute).getAsJsonArray();
        List<Long> tList = ListFactory.getList( pListType );
        for (int i = 0; i < tArr.size(); i++) {
            tList.add(tArr.get(i).getAsLong());
        }
        return tList;
    }

    public Double readDouble(String pAttribute) {
        if (mDecoder.get(pAttribute) == null) {
            return null;
        }
        return mDecoder.get(pAttribute).getAsDouble();
    }

    public List<Double> readDoubleArray(String pAttribute, String pListType) {
        if (mDecoder.get(pAttribute) == null) {
            return null;
        }
        JsonArray tArr = mDecoder.get(pAttribute).getAsJsonArray();
        List<Double> tList = ListFactory.getList( pListType );
        for (int i = 0; i < tArr.size(); i++) {
            tList.add(tArr.get(i).getAsDouble());
        }
        return tList;
    }

    public String readString(String pAttribute) {
        if (mDecoder.get(pAttribute) == null) {
            return null;
        }
        return mDecoder.get(pAttribute).getAsString();
    }

    public List<String> readStringArray(String pAttribute, String pListType ) {
        if (mDecoder.get(pAttribute) == null) {
            return null;
        }
        JsonArray tArr = mDecoder.get(pAttribute).getAsJsonArray();
        List<String> tList = ListFactory.getList( pListType );
        for (int i = 0; i < tArr.size(); i++) {
            tList.add(tArr.get(i).getAsString());
        }
        return tList;
    }

    public byte[] readByteVector(String pAttribute) {
        if (mDecoder.get(pAttribute) == null) {
            return null;
        }
        String b64Str = mDecoder.get(pAttribute).getAsString();
        return Base64.getDecoder().decode(b64Str);
    }

    public List<byte[]> readByteVectorArray(String pAttribute, String pListType) {
        if (mDecoder.get(pAttribute) == null) {
            return null;
        }
        JsonArray tArr = mDecoder.get(pAttribute).getAsJsonArray();
        List<byte[]> tList =  ListFactory.getList( pListType );
        for (int i = 0; i < tArr.size(); i++) {
            String b64Str = tArr.get(i).getAsString();
            tList.add( Base64.getDecoder().decode(b64Str));
        }
        return tList;
    }

    public MessageInterface readMessage( String pAttribute, Class pClass ) {
        if (mDecoder.get(pAttribute) == null) {
            return null;
        }
        MessageInterface mi;
        try{
            mi = (MessageInterface) pClass.newInstance();
        }
        catch( Exception e) {
            throw new RuntimeException( e );
        }
        JsonObject tJsonObj = mDecoder.get( pAttribute ).getAsJsonObject();
        JsonDecoder tDecoder = new JsonDecoder( tJsonObj );
        mi.decode( tDecoder );
        return mi;
    }

    public List<? extends MessageInterface> readMessageArray( String pAttribute, String pListType, Class pClass ) {
        if (mDecoder.get(pAttribute) == null) {
            return null;
        }

        JsonArray tArr = mDecoder.get(pAttribute).getAsJsonArray();
        List<MessageInterface> tList = ListFactory.getList( pListType );
        MessageInterface mi;

        for (int i = 0; i < tArr.size(); i++) {
            try {
                mi = (MessageInterface) pClass.newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            JsonObject tJsonObj = tArr.get(i).getAsJsonObject();
            mi.decode(new JsonDecoder(tJsonObj));
            tList.add( mi );
        }
        return tList;
    }


    public Enum readConstant( String pAttribute, Class pEnumClass ) {
        if (mDecoder.get(pAttribute) == null) {
            return null;
        }
        String tEnumValueStr = mDecoder.get(pAttribute).getAsString();
        return  valueOf(pEnumClass, tEnumValueStr);
    }

    public List<? extends Enum> readConstantArray( String pAttribute, String pListType, Class pEnumClass ) {
        if (mDecoder.get(pAttribute) == null) {
            return null;
        }
        JsonArray tArr = mDecoder.get(pAttribute).getAsJsonArray();
        List<Enum> tList = ListFactory.getEnumList( pListType );
        MessageInterface mi = null;

        for (int i = 0; i < tArr.size(); i++) {
            String tEnumValueStr = tArr.get(i).getAsString();
            Enum tEnum = valueOf(pEnumClass, tEnumValueStr);
            tList.add( tEnum );
        }
        return tList;
    }


    public static byte[] stringToByteArray( String pStr ) {
        if (pStr == null) {
            return null;
        }
        return Base64.getDecoder().decode( pStr );
    }

    public static List<byte[]> stringsToByteArrays( List<String> pStrLst, String pListType ) {
        if (pStrLst == null) {
            return null;
        }
        List<byte[]> tByteArr = ListFactory.getList( pListType );
        for( String tStr: pStrLst ) {
            tByteArr.add( Base64.getDecoder().decode( tStr ));
        }
        return tByteArr;
    }

    public static Enum<?> stringToConst(  Class<? extends Enum> pEnumType, String pConstStr ) {
            if (pConstStr == null) {
                return null;
            }
            return Enum.valueOf(pEnumType, pConstStr);
    }

    public static List<?> stringsToConsts(  Class<? extends Enum> pEnumType, String pListType,  List<String> pConstStrings ) {
        if (pConstStrings == null) {
            return null;
        }
        List<Enum<?>> tConstLst = new ArrayList<>();
        for( String pStr : pConstStrings) {
            tConstLst.add(stringToConst(pEnumType, pStr));
        }
        return ListFactory.converEnumtList(tConstLst, pListType);
    }

}
