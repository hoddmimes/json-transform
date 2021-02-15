package com.hoddmimes.jsontransform;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MapUtils
{

    public static JsonObject encodeMap(Map<String,String> pMap ) {
        JsonObject jObject = new JsonObject();
        for( Map.Entry me : pMap.entrySet()) {
                jObject.addProperty(me.getKey().toString(), me.getValue().toString());
        }
        return jObject;
    }

    public static Map<String,String> readMap( JsonObject pJsonMapObject  ) {
        Map<String, String> m = new HashMap<>();
        Iterator<String> tKeyItr = pJsonMapObject.keySet().iterator();
        while (tKeyItr.hasNext()) {
            String tKey = tKeyItr.next();
            m.put(tKey, pJsonMapObject.get(tKey).getAsString());
        }
        return m;
    }
}
