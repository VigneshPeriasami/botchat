package com.github.vigneshperiasami.botchat.core;


import com.google.gson.JsonElement;

import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class EmoticonExtractorTest {

  @Test
  public void extractEmoticonText() {
    EmoticonExtractor emoticonExtractor = new EmoticonExtractor();
    Payload<JsonElement> payload = emoticonExtractor.parse("Hello (smily) !!!");

    assertThat(payload.getKey()).isEqualTo("emoticons");
    assertThat(payload.getValue().toString()).isEqualTo("[\"smily\"]");
  }
}
