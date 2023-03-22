package com.board.board.service.util;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

@Service
public class Utils {

    /* 해시태그 파싱 */
    public String hashtagParse(String tags) {
        StringBuilder sb = new StringBuilder();
        try {
            JSONParser parser = new JSONParser();
            JSONArray json = (JSONArray) parser.parse(tags);
            json.forEach(item -> {
                JSONObject jsonObject = (JSONObject) JSONValue.parse(item.toString());
                sb.append(" #").append(jsonObject.get("value").toString());
            });
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }

    /* 해시태그 분리 */
    public String hashtagSeparate(String tag) {
        String[] tags = tag.split(" #");
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < tags.length; i++) {
            sb.append(tags[i]).append(",");
        }
        return sb.toString();
    }

    /* String Null 체크 */
    public boolean isStringEmptyOrNull(String str) {
        return str == null || str.isEmpty();
    }
}
