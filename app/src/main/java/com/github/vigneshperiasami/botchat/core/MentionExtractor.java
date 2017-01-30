package com.github.vigneshperiasami.botchat.core;


import com.google.gson.JsonElement;

import java.util.regex.Pattern;

import static com.github.vigneshperiasami.botchat.core.Helper.findMatch;
import static com.github.vigneshperiasami.botchat.core.Helper.jsonEntry;

public class MentionExtractor implements PureExtractor<JsonElement> {
  final static String PAYLOAD_KEY = "mentions";
  final static Pattern pattern = Pattern.compile("\\B(@\\w+)");

  private static String[] trim(String[] mentions, int beginIndex) {
    String[] stripped = new String[mentions.length];
    for (int i = 0; i < mentions.length; i++) {
      stripped[i] = mentions[i].substring(beginIndex);
    }
    return stripped;
  }

  @Override
  public Payload<JsonElement> parse(String phrase) {
    String[] mentions = trim(findMatch(phrase, pattern), 1);
    return new Payload<>(PAYLOAD_KEY, jsonEntry(mentions));
  }
}
