package com.github.vigneshperiasami.botchat.core;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static com.google.common.truth.Truth.assertThat;

public class LinkExtractorTest {

  private static JsonElement create(String url, String title) {
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("url", url);
    jsonObject.addProperty("title", title);
    return jsonObject;
  }

  @Test
  public void extractLinkAndTitle() throws IOException {
    String url = "https://www.google.co.in";
    String title = "Google";
    LinkExtractor.UrlReader urlReader = Mockito.mock(LinkExtractor.UrlReader.class);
    Mockito.when(urlReader.read(url)).thenReturn(create(url, title));

    LinkExtractor linkExtractor = new LinkExtractor(urlReader);
    Payload<JsonElement> payload = linkExtractor.parse("Checkout this link " + url);

    assertThat(payload.getValue().getAsJsonArray().size()).isEqualTo(1);
    assertThat(payload.getValue().getAsJsonArray()
        .get(0).getAsJsonObject().get("title").getAsString()).isEqualTo(title);
  }
}
