package com.github.vigneshperiasami.botchat.core;


public interface PureExtractor<T> {
  Payload<T> parse(String phrase);
}
