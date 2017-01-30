package com.github.vigneshperiasami.botchat.core;


import com.google.gson.JsonElement;

import org.junit.Test;

import static com.github.vigneshperiasami.botchat.core.Helper.jsonEntry;
import static com.google.common.truth.Truth.assertThat;

public class MentionExtractorTest {

  @Test
  public void extractMentionsFromPhrase() {
    PureExtractor<JsonElement> extractor = new MentionExtractor();
    Payload<JsonElement> payload = extractor.parse("Hello @chris !!!");
    assertThat(payload.getKey()).isEqualTo("mentions");
    assertThat(payload.getValue()).isEqualTo(jsonEntry(new String[] { "chris" }));
  }
}
