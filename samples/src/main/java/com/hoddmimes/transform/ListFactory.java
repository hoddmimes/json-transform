package com.hoddmimes.transform;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class ListFactory
{

    public static <T> List<T> getList(String pListType ) {
        if (pListType == null) {
            return new ArrayList();
        }
        if (pListType.compareTo("array") == 0) {
            return new ArrayList();
        } else if (pListType.compareTo("linked") == 0) {
            return new LinkedList();
        } else if (pListType.compareTo("stack") == 0) {
            return new Stack();
        }
        return new ArrayList();

    }

    public static List<Enum> getEnumList( String pListType ) {
        if (pListType == null) {
            return new ArrayList();
        }
        if (pListType.compareTo("array") == 0) {
            return new ArrayList();
        } else if (pListType.compareTo("linked") == 0) {
            return new LinkedList();
        } else if (pListType.compareTo("stack") == 0) {
            return new Stack();
        }
        return new ArrayList();
    }

     public static <T> List<T> convertList( List<T> pList, String pListType ) {
        if (pList == null) {
            return null;
        }
        List<T> tList;

        if (pListType == null) {
            tList = new ArrayList();
        }
        if (pListType.compareTo("array") == 0) {
            tList = new ArrayList();
        } else if (pListType.compareTo("linked") == 0) {
            tList =new LinkedList();
        } else if (pListType.compareTo("stack") == 0) {
            tList = new Stack();
        } else {
            tList = new ArrayList();
        }
        tList.addAll( pList );
        return tList;
    }

     public static List<Enum> converEnumtList( List<Enum<?>> pList, String pListType ) {
        if (pList == null) {
            return null;
        }
        List<Enum> tList;

        if (pListType == null) {
            tList = new ArrayList();
        }
        if (pListType.compareTo("array") == 0) {
            tList = new ArrayList();
        } else if (pListType.compareTo("linked") == 0) {
            tList =new LinkedList();
        } else if (pListType.compareTo("stack") == 0) {
            tList = new Stack();
        } else {
            tList = new ArrayList();
        }
        tList.addAll( pList );
        return tList;
    }


}
