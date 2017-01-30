package com.github.vigneshperiasami.botchat;


import com.google.gson.JsonElement;

import org.json.JSONException;
import org.junit.Test;

import java.io.IOException;
import java.util.regex.Pattern;

import static com.github.vigneshperiasami.botchat.core.Helper.findMatch;
import static com.github.vigneshperiasami.botchat.core.Helper.jsonEntry;
import static com.google.common.truth.Truth.assertThat;

public class HelperTest {

  @Test
  public void makeJsonArray() throws IOException, JSONException {
    JsonElement jsonObject = jsonEntry(new String[] { "chris" });
    assertThat(jsonObject.toString()).isEqualTo("[\"chris\"]");
  }

  @Test
  public void findAllMatch() {
    String[] result = findMatch("Hello @chris not@this", Pattern.compile("\\B@\\w+"));
    assertThat(result).asList().containsExactly("@chris");
  }
}
