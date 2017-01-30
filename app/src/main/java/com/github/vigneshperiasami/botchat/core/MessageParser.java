package com.github.vigneshperiasami.botchat.core;


import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MessageParser<T> {
  private final Scheduler<T>[] schedulers;
  private final PayloadMerger<T> payloadMerger;

  MessageParser(PayloadMerger<T> payloadMerger, Scheduler<T>... schedulers) {
    this.schedulers = schedulers;
    this.payloadMerger = payloadMerger;
  }

  private Dispatcher<T> createDispatcher(Callback<T> callback,
                                                    int expectedDispatchCalls) {
    return new DefaultDispatcher<>(callback, expectedDispatchCalls, payloadMerger);
  }

  public void parse(String phrase, Callback<T> callback) {
    Dispatcher<T> dispatcher = createDispatcher(callback, schedulers.length);
    for (Scheduler<T> scheduler : schedulers) {
      scheduler.extract(phrase, new SingleDispatch<>(dispatcher));
    }
  }

  static class SingleDispatch<T> implements Dispatcher<T> {
    private final Dispatcher<T> dispatcher;
    private boolean isDispatched = false;

    public SingleDispatch(Dispatcher<T> dispatcher) {
      this.dispatcher = dispatcher;
    }

    @Override
    public void dispatch(Payload<T> payload) {
      if (isDispatched) {
        throw new IllegalStateException("Cannot call more than once");
      }
      dispatcher.dispatch(payload);
      isDispatched = true;
    }
  }

  static class DefaultDispatcher<T> implements Dispatcher<T> {
    private final Callback<T> callback;
    private final AtomicInteger expectedDispatchCalls;
    private final List<Payload<T>> payloads;
    private final PayloadMerger<T> payloadMerger;

    DefaultDispatcher(Callback<T> callback, int expectedDispatchCalls,
                      PayloadMerger<T> payloadMerger) {
      this.callback = callback;
      this.expectedDispatchCalls = new AtomicInteger(expectedDispatchCalls);
      payloads = new ArrayList<>(expectedDispatchCalls);
      this.payloadMerger = payloadMerger;
    }

    private boolean isAllDispatchDone() {
      return expectedDispatchCalls.get() == 0;
    }

    private void collectPayload(Payload<T> payload) {
      payloads.add(payload);
      expectedDispatchCalls.decrementAndGet();
    }

    private T mergeAndGetPayloadResult() {
      return payloadMerger.merge(payloads);
    }

    @Override
    public void dispatch(Payload<T> payload) {
      collectPayload(payload);
      if (isAllDispatchDone()) {
        callback.onResult(mergeAndGetPayloadResult());
      }
    }
  }

  public static class Builder {
    private List<Scheduler<JsonElement>> schedulers = new LinkedList<>();

    public Builder add(Scheduler<JsonElement> scheduler) {
      schedulers.add(scheduler);
      return this;
    }

    @SuppressWarnings("unchecked")
    public MessageParser<JsonElement> build() {
      return new MessageParser<>(PayloadMerger.JSON_PAYLOAD_MERGER,
          schedulers.toArray(new Scheduler[schedulers.size()]));
    }
  }
}
