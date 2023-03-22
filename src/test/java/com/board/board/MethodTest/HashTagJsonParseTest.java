package com.board.board.MethodTest;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

public class HashTagJsonParseTest {

    @DisplayName("Json 해시 태그 파싱")
    @Test
    public void JSON_포맷_해시태그_파싱() {
        String tags = "[{\"value\":\"BackEnd\"},{\"value\":\"FrontEnd\"},{\"value\":\"JAVA\"},{\"value\":\"C#\"}]";

        StringBuilder sb = new StringBuilder();

        /* 해시태그 저장 */
        if(!tags.isEmpty()) {
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
        }

        System.out.println(sb);
    }

    @DisplayName("해시태그 쉼표로 분리")
    @Test
    public void 해시태그_분리() {
        String tag = " #1 #2 #3";
        String[] tags = tag.split(" #");
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < tags.length; i++) {
            sb.append(tags[i]).append(",");
        }
        System.out.println(sb);
    }

}
