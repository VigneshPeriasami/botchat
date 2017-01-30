package com.github.vigneshperiasami.botchat.core;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.List;

public interface PayloadMerger<T> {
  T merge(List<Payload<T>> payloadList);

  PayloadMerger<JsonElement> JSON_PAYLOAD_MERGER = new PayloadMerger<JsonElement>() {
    @Override
    public JsonElement merge(List<Payload<JsonElement>> payloads) {
      JsonObject jsonObject = new JsonObject();
      for (Payload<JsonElement> payload : payloads) {
        jsonObject.add(payload.getKey(), payload.getValue());
      }
      return jsonObject;
    }
  };
}
