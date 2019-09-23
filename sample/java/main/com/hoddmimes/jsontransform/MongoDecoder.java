package com.hoddmimes.jsontransform;

import org.bson.Document;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

public class MongoDecoder
{
    private Document mDoc;

    public MongoDecoder( Document pDoc ) {
        mDoc = pDoc;
    }
    /**
     * ============================================================================================================
     *
     * Standar Type Decoder Methods
     *
     * ===========================================================================================================
     */

    public Boolean readBoolean(String pAttribute ) {
        return mDoc.getBoolean( pAttribute );
    }

    public Byte readByte( String pAttribute ) {
        Integer x = mDoc.getInteger( pAttribute );
        if (x == null) {
            return null;
        }
        return x.byteValue();
    }

    public Short readShort( String pAttribute ) {
        Integer x = mDoc.getInteger( pAttribute );
        if (x == null) {
            return null;
        }
        return x.shortValue();
    }

    public Integer readInteger( String pAttribute ) {
        return mDoc.getInteger( pAttribute );
    }

    public Long readLong( String pAttribute ) {
        return mDoc.getLong( pAttribute );
    }

    public Double readDouble( String pAttribute ) {
        return mDoc.getDouble( pAttribute );
    }

    public String readString( String pAttribute ) {
        return mDoc.getString( pAttribute );
    }

    public Character readCharacter( String pAttribute ) {
        String tStr = mDoc.getString( pAttribute );
        if (tStr == null) {
            return null;
        }
        return tStr.charAt(0);
    }

    public byte[] readByteVector( String pAttribute ) {
        String tStr = mDoc.getString( pAttribute );
        return stringToByteArray( tStr );
    }

    public Enum<?> readConstant(String pAttribute, Class<? extends Enum> pEnumType) {
        String tStr = mDoc.getString( pAttribute );
        return stringToConst( pEnumType, tStr);
    }

    public Document readMessage(String pAttribute ) {
        return (Document) mDoc.get( pAttribute );
    }


    /**
     * ==========================================================================================
     *
     * Standarad Array Methods
     *
     * ==========================================================================================
     */
    public List<Boolean> readBooleanArray( String pAttribute) {
        return (List<Boolean>) mDoc.get( pAttribute );
    }

    public List<byte[]> readByteArray( String pAttribute) {
        List<String> tStrLst = (List<String>) mDoc.get( pAttribute );
        return stringsToByteArrays( tStrLst );
    }

    public List<Short> readShortArray( String pAttribute) {
        List<Integer> tList = (List<Integer>) mDoc.get( pAttribute );
        if (tList == null) {
            return null;
        }
        List<Short> tRetLst  = tList.stream().map( Integer::shortValue).collect(Collectors.toList());
        return tRetLst;
    }

    public List<Integer> readIntegerArray( String pAttribute) {
        return (List<Integer>) mDoc.get( pAttribute );
    }

    public List<Long> readLongArray( String pAttribute) {
        return (List<Long>) mDoc.get( pAttribute );
    }

    public List<Double> readDoubleArray( String pAttribute) {
        return (List<Double>) mDoc.get( pAttribute );
    }

    public List<Character> readCharacterArray( String pAttribute) {
        List<String> tList = (List<String>) mDoc.get( pAttribute );
        if (tList == null) {
            return null;
        }
        List<Character> tRetLst  = tList.stream().map( s ->{ return s.charAt(0);}).collect(Collectors.toList());
        return tRetLst;
    }

    public List<String> readStringArray( String pAttribute) {
        return (List<String>) mDoc.get( pAttribute );
    }

    public List<byte[]> readByteVectorArray( String pAttribute) {
        List<String> tStrLst =  (List<String>) mDoc.get( pAttribute );
        return stringsToByteArrays( tStrLst );
    }

    public List<?>  readConstArray( String pAttribute, Class<? extends Enum> pEnumType) {
        List<String> tStrLst =  (List<String>) mDoc.get( pAttribute );
        return stringsToConsts( pEnumType, tStrLst );
    }


    public List<Document> readMessageArray( String pAttribute ) {
        return (List<Document>) mDoc.get( pAttribute );
    }


    /**
     * ==========================================================================================
     *
     * Auxiliary Methods
     *
     * ==========================================================================================
     */

    private byte[] stringToByteArray( String pStr ) {
        if (pStr == null) {
            return null;
        }
        return Base64.getDecoder().decode( pStr );
    }

    private List<byte[]> stringsToByteArrays(List<String> pStrLst ) {
        if (pStrLst == null) {
            return null;
        }
        List<byte[]> tByteArr = new ArrayList<>();
        for( String tStr: pStrLst ) {
            tByteArr.add( Base64.getDecoder().decode( tStr ));
        }
        return tByteArr;
    }

    public Enum<?> stringToConst(  Class<? extends Enum> pEnumType, String pConstStr ) {
        if (pConstStr == null) {
            return null;
        }
        return Enum.valueOf(pEnumType, pConstStr);
    }

    private List<?> stringsToConsts(  Class<? extends Enum> pEnumType, List<String> pConstStrings ) {
        if (pConstStrings == null) {
            return null;
        }
        List<Enum<?>> tConstLst = new ArrayList<>();
        for( String pStr : pConstStrings) {
            tConstLst.add(stringToConst(pEnumType, pStr));
        }
        return tConstLst;
    }



}
