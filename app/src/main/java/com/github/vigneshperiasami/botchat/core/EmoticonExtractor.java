package com.github.vigneshperiasami.botchat.core;


import com.google.gson.JsonElement;

import java.util.regex.Pattern;

import static com.github.vigneshperiasami.botchat.core.Helper.findMatch;
import static com.github.vigneshperiasami.botchat.core.Helper.jsonEntry;

public class EmoticonExtractor implements PureExtractor<JsonElement> {
  private static final Pattern PATTERN = Pattern.compile("\\(.*\\)");
  private static final String PAYLOAD_KEY = "emoticons";

  private static String[] trim(String[] elems) {
    String[] result = new String[elems.length];
    for (int i = 0; i < elems.length; i++) {
      result[i] = elems[i].replace("(", "").replace(")", "");
    }
    return result;
  }

  @Override
  public Payload<JsonElement> parse(String phrase) {
    String[] emoticons = trim(findMatch(phrase, PATTERN));
    return new Payload<>(PAYLOAD_KEY, jsonEntry(emoticons));
  }
}
