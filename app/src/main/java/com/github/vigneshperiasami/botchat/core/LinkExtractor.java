package com.github.vigneshperiasami.botchat.core;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LinkExtractor implements PureExtractor<JsonElement> {
  static final String REGEX = "(https?:\\/\\/|www\\.)" +
      "[-a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)";
  static final String TITLE_REGEX = "(<title>).*(<\\/title>)";
  static final Pattern PATTERN = Pattern.compile(REGEX);
  private final UrlReader urlReader;

  LinkExtractor(UrlReader urlReader) {
    this.urlReader = urlReader;
  }

  public LinkExtractor() {
    this(new DefaultUrlReader());
  }

  private JsonArray findMetaInfo(String[] urls) {
    JsonArray jsonArray = new JsonArray();

    for (String url : urls) {
      jsonArray.add(urlReader.read(url));
    }
    return jsonArray;
  }

  @Override
  public Payload<JsonElement> parse(String phrase) {
    String[] urls = Helper.findMatch(phrase, PATTERN);
    return new Payload<>("links", findMetaInfo(urls));
  }

  interface UrlReader {
    JsonElement read(String url);
  }

  static class DefaultUrlReader implements UrlReader {
    static final Pattern TITLE_PATTERN = Pattern.compile(TITLE_REGEX);
    final OkHttpClient client = new OkHttpClient();

    private static String findTitle(String body) {
      Matcher matcher = TITLE_PATTERN.matcher(body);
      if (matcher.find()) {
        return matcher.group().replace("<title>", "").replace("</title>", "");
      }
      return body;
    }

    private static JsonElement create(String url, String title) {
      JsonObject jsonObject = new JsonObject();
      jsonObject.addProperty("url", url);
      jsonObject.addProperty("title", title);
      return jsonObject;
    }

    @Override
    public JsonElement read(String url) {
      try {
        Response response = client.newCall(new Request.Builder().url(url).build()).execute();
        String title = findTitle(response.body().string());
        return create(url, title);
      } catch (IOException e) {
        return create(url, "");
      }
    }
  }
}
