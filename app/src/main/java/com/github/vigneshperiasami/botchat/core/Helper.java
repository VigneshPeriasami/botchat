package com.github.vigneshperiasami.botchat.core;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Helper {

  public static String[] findMatch(String message, Pattern pattern) {
    Matcher matcher = pattern.matcher(message);
    List<String> result = new LinkedList<>();
    while (matcher.find()) {
      result.add(matcher.group());
    }
    return result.toArray(new String[result.size()]);
  }

  public static JsonElement jsonEntry(String[] values) {
    JsonArray jsonArray = new JsonArray();
    for (String val : values) {
      jsonArray.add(val);
    }
    return jsonArray;
  }
}
