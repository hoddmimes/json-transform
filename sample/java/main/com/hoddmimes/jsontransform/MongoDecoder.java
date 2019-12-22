package com.hoddmimes.jsontransform;

import org.bson.Document;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
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

    public Date readDate( String pAttribute ) {
        String x = mDoc.getString( pAttribute );
        if (x == null) {
            return null;
        }
        return DateUtils.stringToDate( x );
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



    public List<Boolean> readBooleanArray( String pAttribute, String pListType) {
        return (List<Boolean>) ListFactory.convertList( (List<Boolean>) mDoc.get( pAttribute ), pListType);
    }

    public List<byte[]> readByteArray( String pAttribute, String pListType) {
        List<String> tStrLst = (List<String>) mDoc.get( pAttribute );
        return stringsToByteArrays( tStrLst, pListType );
    }

    public List<Date> readDateArray( String pAttribute, String pListType) {
        List<String> tStrLst = (List<String>) mDoc.get( pAttribute );
        return stringsToDateArray( tStrLst, pListType );
    }

    public List<Short> readShortArray( String pAttribute, String pListType) {
        List<Integer> tList = (List<Integer>) mDoc.get( pAttribute );
        if (tList == null) {
            return null;
        }
        List<Short> tRetLst  = tList.stream().map( Integer::shortValue).collect(Collectors.toList());
        return ListFactory.convertList(tRetLst, pListType);
    }

    public List<Integer> readIntegerArray( String pAttribute, String pListType) {
        return (List<Integer>) ListFactory.convertList((List<Integer>) mDoc.get( pAttribute ), pListType );
    }

    public List<Long> readLongArray( String pAttribute, String pListType) {
        return (List<Long>) ListFactory.convertList((List<Long>) mDoc.get( pAttribute ), pListType );
    }

    public List<Double> readDoubleArray( String pAttribute, String pListType) {
        return (List<Double>) ListFactory.convertList((List<Double>)mDoc.get( pAttribute ), pListType);
    }

    public List<Character> readCharacterArray( String pAttribute, String pListType) {
        List<String> tList = (List<String>) mDoc.get( pAttribute );
        if (tList == null) {
            return null;
        }
        List<Character> tRetLst  = tList.stream().map( s ->{ return s.charAt(0);}).collect(Collectors.toList());
        return ListFactory.convertList(tRetLst, pListType);
    }

    public List<String> readStringArray( String pAttribute, String pListType) {
        return (List<String>) ListFactory.convertList((List<String>)mDoc.get( pAttribute ), pListType);
    }

    public List<byte[]> readByteVectorArray( String pAttribute, String pListType) {
        List<String> tStrLst =  (List<String>) mDoc.get( pAttribute );
        return stringsToByteArrays( tStrLst, pListType );
    }

    public List<?>  readConstArray( String pAttribute, String pListType, Class<? extends Enum> pEnumType) {
        List<String> tStrLst =  (List<String>) mDoc.get( pAttribute );
        return stringsToConsts( pEnumType, pListType, tStrLst );
    }


    public List<Document> readMessageArray( String pAttribute, String pListType ) {
        return ListFactory.convertList((List<Document>) mDoc.get( pAttribute ), pListType );
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

    private List<Date> stringsToDateArray( List<String> pDateStrArray, String pListType ) {
        if (pDateStrArray == null) {
            return null;
        }
        List<Date> tDateArr = new ArrayList<>();
        for( String tStr: pDateStrArray ) {
            tDateArr.add( DateUtils.stringToDate( tStr ));
        }
        return ListFactory.convertList(tDateArr, pListType);
    }

    private List<byte[]> stringsToByteArrays(List<String> pStrLst, String pListType ) {
        if (pStrLst == null) {
            return null;
        }
        List<byte[]> tByteArr = new ArrayList<>();
        for( String tStr: pStrLst ) {
            tByteArr.add( Base64.getDecoder().decode( tStr ));
        }
        return ListFactory.convertList(tByteArr, pListType);
    }

    public Enum<?> stringToConst(  Class<? extends Enum> pEnumType, String pConstStr ) {
        if (pConstStr == null) {
            return null;
        }
        return Enum.valueOf(pEnumType, pConstStr);
    }

    private List<?> stringsToConsts(  Class<? extends Enum> pEnumType, String pListType, List<String> pConstStrings ) {
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
