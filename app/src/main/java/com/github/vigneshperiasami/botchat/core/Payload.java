package com.github.vigneshperiasami.botchat.core;


public class Payload<T> {
  private final String key;
  private final T value;

  public Payload(String key, T value) {
    this.key = key;
    this.value = value;
  }

  public String getKey() {
    return key;
  }

  public T getValue() {
    return value;
  }
}
