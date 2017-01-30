package com.github.vigneshperiasami.botchat.core.scheduler;


import com.github.vigneshperiasami.botchat.core.Dispatcher;
import com.github.vigneshperiasami.botchat.core.PureExtractor;
import com.github.vigneshperiasami.botchat.core.Scheduler;
import com.google.gson.JsonElement;

public class SyncScheduler<T> implements Scheduler<T> {
  private final PureExtractor<T> pureExtractor;

  public SyncScheduler(PureExtractor<T> pureExtractor) {
    this.pureExtractor = pureExtractor;
  }

  @Override
  public void extract(String phrase, Dispatcher<T> dispatcher) {
    dispatcher.dispatch(pureExtractor.parse(phrase));
  }
}
