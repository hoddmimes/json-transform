package com.hoddmimes.transform;

import org.bson.Document;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MongoEncoder
{
    private Document mDoc;

    public MongoEncoder() {
        mDoc = new Document();
    }

    public Document getDoc() {
        return mDoc;
    }
    /**
     * ===========================================================================================
     *
     * Stardarnd Type Encoder Methods
     *
     * ==========================================================================================
     */


    public void add( String pAttribute, LocalDate pValue ) {
        if (pValue == null) {
            return;
        }
        mDoc.append( pAttribute, DateUtils.localDateToString(pValue));
    }

    public void add(String pAttribute, Map<String,String> pMap) {
        if (pMap == null) {
            return;
        }
        mDoc.append( pAttribute, javaMapToDocument( pMap ));
    }


    public void add( String pAttribute, LocalDateTime pValue ) {
        if (pValue == null) {
            return;
        }

        mDoc.append( pAttribute, DateUtils.localDateTimeToString(pValue));
    }

    public void add( String pAttribute, Boolean pValue ) {
        mDoc.append( pAttribute, pValue);
    }

    public void add( String pAttribute, Byte pValue ) {
        mDoc.append( pAttribute, pValue);
    }

    public void add( String pAttribute, Character pValue ) {
        mDoc.append( pAttribute, pValue);
    }

    public void add( String pAttribute, Short pValue ) {
        mDoc.append( pAttribute, pValue);
    }

    public void add( String pAttribute, Integer pValue ) {
        mDoc.append( pAttribute, pValue);
    }

    public void add( String pAttribute, Long pValue ) {
        mDoc.append( pAttribute, pValue);
    }

    public void add( String pAttribute, Double pValue ) {
        mDoc.append( pAttribute, pValue);
    }

    public void add( String pAttribute, String pValue ) {
        mDoc.append( pAttribute, pValue);
    }

    public void add( String pAttribute, byte[] pValue ) {
        mDoc.append( pAttribute, byteArrayToString( pValue ));
    }

    public void add( String pAttribute, Enum<?> pConst) {
        mDoc.append( pAttribute, constToString( pConst ));
    }

    public void add( String pAttribute, MessageMongoInterface pMsg) {
        mDoc.append( pAttribute, msgToMongoDocument( pMsg ));
    }

    /**
     * ===========================================================================================
     *
     * Stardarnd Type Array Encoder Methods
     *
     * ==========================================================================================
     */

    public void addBooleanArray( String pAttribute, List<Boolean> pList ) {
        mDoc.append( pAttribute, pList );
    }

    public void addByteArray( String pAttribute, byte[] pByteArray ) {
        mDoc.append( pAttribute, byteArrayToString( pByteArray ));
    }

    public void addShortArray( String pAttribute, List<Short> pList ) {
        mDoc.append( pAttribute, pList );
    }

    public void addIntegerArray( String pAttribute, List<Integer> pList ) {
        mDoc.append( pAttribute, pList );
    }

    public void addCharacterArray( String pAttribute, List<Character> pList ) {
        mDoc.append( pAttribute, pList );
    }



    public void addLongArray( String pAttribute, List<Long> pList ) {
        mDoc.append( pAttribute, pList );
    }

    public void addDoubleArray( String pAttribute, List<Double> pList ) {
        mDoc.append( pAttribute, pList );
    }

    public void addStringArray( String pAttribute, List<String> pList ) {
        mDoc.append( pAttribute, pList );
    }


    public void addByteVectorArray( String pAttribute, List<byte[]> pList ) {
        mDoc.append( pAttribute, byteArraysToStrings( pList));
    }

    public void addDateArray( String pAttribute, List<LocalDate> pList ) {
        if (pList == null) {
            return;
        }
        ArrayList<String> tDateLst = new ArrayList<>();
        pList.forEach( ld -> tDateLst.add( DateUtils.localDateToString( ld )));
        mDoc.append( pAttribute, tDateLst);
    }

    public void addDateTimeArray( String pAttribute, List<LocalDateTime> pList ) {
        if (pList == null) {
            return;
        }
        ArrayList<String> tDateLst = new ArrayList<>();
        pList.forEach( ldt -> tDateLst.add( DateUtils.localDateTimeToString( ldt )));
        mDoc.append( pAttribute, tDateLst);
    }

    public void addConstArray( String pAttribute, List<?> pConstArray ) {
        mDoc.append( pAttribute, constsToStrings(  pConstArray ));
    }

    public void addMapArray( String pAttribute, List<Map<String,String>> pMapList) {
        if (pMapList == null) {
            return;
        }
        List<Document> tMapLst = mapsToMongoDocuments( pMapList );
        mDoc.append( pAttribute,  tMapLst );
    }

    public void addMessageArray( String pAttribute, List<? extends MessageMongoInterface> pMsgArray ) {
        if (pMsgArray == null) {
            mDoc.append( pAttribute, null);
        }
        List<Document> tDocLst = pMsgArray.stream().map( m -> { return m.getMongoDocument(); }).collect(Collectors.toList());
        mDoc.append( pAttribute,  tDocLst );
    }
    /**
     * ==================================================================================
     *
     * Encoder Auxilliary Methods
     *
     * ===================================================================================
     */

    private String b64ToString( byte[] pValue) {
        if (pValue == null) {
            return null;
        }
        String s = Base64.getEncoder().encodeToString( pValue );
        return s;
    }

    private String byteArrayToString( byte[] pBytArr ) {
        if (pBytArr == null) {
            return null;
        }
        return Base64.getEncoder().encodeToString( pBytArr );
    }

    private List<String> byteArraysToStrings(List<byte[]> pBytArrs ) {
        if (pBytArrs == null) {
            return null;
        }
        List<String> tStrArr = new ArrayList<>();
        for( byte[] ba : pBytArrs ) {
            tStrArr.add( Base64.getEncoder().encodeToString( ba ));
        }
        return tStrArr;
    }


    private String constToString(Enum<?> pConst) {
        if (pConst == null) {
            return null;
        }
        return pConst.toString();
    }

    private List<String> constsToStrings(List<?> pConstArray) {
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

    private Document msgToMongoDocument( MessageMongoInterface pMsg )
    {
        if (pMsg == null) {
            return null;
        }
        return pMsg.getMongoDocument();
    }


    private List<Document> msgsToMongoDocuments( List<? extends MessageMongoInterface> pMsgs )
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

    private Document javaMapToDocument( Map<String,String> pMap) {
        Document tDoc = new Document();
        for (Map.Entry<String, String> tEntry : pMap.entrySet()) {
            tDoc.append(tEntry.getKey(), tEntry.getValue());
        }
        return tDoc;
    }


    private List<Document> mapsToMongoDocuments( List<? extends Map<String,String>> pMaps )
    {
        if (pMaps == null) {
            return null;
        }
        List<Document> tMapLst = new ArrayList<>();
        for( Map<String,String> tMap : pMaps) {
            if (!tMap.isEmpty()) {
                tMapLst.add(javaMapToDocument(tMap));
            }
        }
        return tMapLst;
    }
}
