package com.vartyr.littlebirdblock.models;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class DailyGratitudeObject {

    String text;
    String date_added;
    String date_last_modified;

    // default constructor
    public DailyGratitudeObject(String t){
        text = t;
        date_added = util_getCurrentTimeStampAsString();
        date_last_modified = util_getCurrentTimeStampAsString();
    }

    public Map<String, Object> CreateAndReturnObjectMapFromObject(){
        Map<String, Object> gratObj = new HashMap<>();
        gratObj.put("text", text);
        gratObj.put("date_added", date_added);
        gratObj.put("date_last_modified", date_last_modified);
        gratObj.put("user_id", "rhyEHzVbVSXbnxAnsdrZ3CVqseJ3"); // TODO: hardcoded user_id, change later
        return gratObj;
    }

    // TODO: Move all util methods into their own class
    private static String util_getCurrentTimeStampAsString(){
        return String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
    }

}
