package com.github.vigneshperiasami.botchat.ui;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.github.vigneshperiasami.botchat.R;
import com.github.vigneshperiasami.botchat.ui.model.ChatMessage;

import java.util.List;

public class ChatListAdapter extends BaseAdapter {
  private final Context context;
  private final List<ChatMessage> chatMessages;

  public ChatListAdapter(Context context, List<ChatMessage> chatMessages) {
    this.context = context;
    this.chatMessages = chatMessages;
  }

  @Override
  public int getCount() {
    return chatMessages.size();
  }

  @Override
  public ChatMessage getItem(int i) {
    return chatMessages.get(i);
  }

  @Override
  public long getItemId(int i) {
    return chatMessages.get(i).hashCode();
  }

  @Override
  public boolean hasStableIds() {
    return true;
  }

  @Override
  public int getViewTypeCount() {
    return 2;
  }

  @Override
  public int getItemViewType(int position) {
    return chatMessages.get(position).hasQuoteMessage() ? 0 : 1;
  }

  public View createView(ChatMessage chatMessage) {
    if (chatMessage.hasQuoteMessage()) {
      return View.inflate(context, R.layout.quote_chat_msg, null);
    }
    return View.inflate(context, R.layout.chat_msg_item, null);
  }

  @Override
  public View getView(int i, View view, ViewGroup viewGroup) {
    ChatMessage chatMessage = getItem(i);
    ViewHolder viewHolder;
    if (view == null) {
      view = createView(chatMessage);
      viewHolder = new ViewHolder(view);
      view.setTag(viewHolder);
    } else {
      viewHolder = (ViewHolder) view.getTag();
    }
    viewHolder.sender.setText(chatMessage.sender);
    viewHolder.message.setText(chatMessage.message);
    if (chatMessage.hasQuoteMessage()) {
      viewHolder.quoteMessage.setText(chatMessage.quote);
    }
    return view;
  }

  class ViewHolder {
    TextView sender;
    TextView message;
    TextView quoteMessage;

    ViewHolder(View view) {
      sender = (TextView) view.findViewById(R.id.txt_sender);
      message = (TextView) view.findViewById(R.id.txt_msg);
      View quote = view.findViewById(R.id.quote_text);
      if (quote != null) {
        quoteMessage = (TextView) quote;
      }
    }
  }
}
