package com.github.vigneshperiasami.botchat.core.scheduler;


import com.github.vigneshperiasami.botchat.core.Dispatcher;
import com.github.vigneshperiasami.botchat.core.PureExtractor;
import com.github.vigneshperiasami.botchat.core.Scheduler;

import java.util.concurrent.Executor;

public class ExecScheduler<T> implements Scheduler<T> {
  private final Executor executor;
  private final PureExtractor<T> extractor;

  public ExecScheduler(Executor executor, PureExtractor<T> extractor) {
    this.executor = executor;
    this.extractor = extractor;
  }

  @Override
  public void extract(String phrase, Dispatcher<T> dispatcher) {
    executor.execute(() -> {
      dispatcher.dispatch(extractor.parse(phrase));
    });
  }
}
