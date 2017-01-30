package com.github.vigneshperiasami.botchat.ui;


import android.os.AsyncTask;

import com.github.vigneshperiasami.botchat.core.LinkExtractor;
import com.github.vigneshperiasami.botchat.core.MentionExtractor;
import com.github.vigneshperiasami.botchat.core.MessageParser;
import com.github.vigneshperiasami.botchat.core.scheduler.ExecScheduler;
import com.github.vigneshperiasami.botchat.core.scheduler.SyncScheduler;
import com.github.vigneshperiasami.botchat.ui.model.ChatMessage;
import com.google.gson.JsonElement;

import java.util.concurrent.Executor;

public class BotChatService {
  private static final String BOT_USERNAME = "BOT";
  private MessageParser<JsonElement> messageParser;
  private ReplyListener replyListener;

  BotChatService(MessageParser<JsonElement> messageParser, ReplyListener replyListener) {
    this.messageParser = messageParser;
    this.replyListener = replyListener;
  }

  public static BotChatService create(ReplyListener replyListener) {
    Executor executor = AsyncTask.THREAD_POOL_EXECUTOR;
    MessageParser<JsonElement> messageParser = new MessageParser.Builder()
        .add(new SyncScheduler<>(new MentionExtractor()))
        .add(new ExecScheduler<>(executor, new LinkExtractor())).build();
    return new BotChatService(messageParser, replyListener);
  }

  public synchronized void destroy() {
    replyListener = ReplyListener.NONE;
  }

  private synchronized void notify(ChatMessage chatMessage) {
    replyListener.onReply(chatMessage);
  }

  public void getReply(String message) {
    messageParser.parse(message, (json) -> {
      notify(new ChatMessage(BOT_USERNAME, json.toString(), message));
    });
  }

  public interface ReplyListener {
    void onReply(ChatMessage message);
    ReplyListener NONE = new ReplyListener() {
      @Override
      public void onReply(ChatMessage message) {

      }
    };
  }
}
