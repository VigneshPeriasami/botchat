package com.github.vigneshperiasami.botchat.core;


public interface Dispatcher<T> {
  void dispatch(Payload<T> payload);
}
