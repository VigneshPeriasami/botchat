package com.github.vigneshperiasami.botchat.ui.model;


public class ChatMessage {
  public static final String SELF_USER_NAME = "ME";

  public final String sender;
  public final String message;
  public final String quote;

  public ChatMessage(String sender, String message, String quote) {
    this.sender = sender;
    this.message = message;
    this.quote = quote;
  }

  public static ChatMessage selfMessage(String message) {
    return new ChatMessage(SELF_USER_NAME, message, null);
  }

  public static ChatMessage messageWithQuote(String sender, String message, String quote) {
    return new ChatMessage(sender, message, quote);
  }

  @Override
  public int hashCode() {
    return String.format("%s: %s", sender, message).hashCode();
  }

  public boolean hasQuoteMessage() {
    return null != quote;
  }

  public boolean isFromSelf() {
    return SELF_USER_NAME.equals(sender);
  }
}
