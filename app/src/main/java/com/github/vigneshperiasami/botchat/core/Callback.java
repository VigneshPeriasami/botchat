package com.github.vigneshperiasami.botchat.core;


public interface Callback<T> {
  void onResult(T result);
}
