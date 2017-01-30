package com.github.vigneshperiasami.botchat.core;


public interface Scheduler<T> {
  void extract(String phrase, Dispatcher<T> dispatcher);
}
