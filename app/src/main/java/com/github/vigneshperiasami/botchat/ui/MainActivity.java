package com.github.vigneshperiasami.botchat.ui;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.github.vigneshperiasami.botchat.R;
import com.github.vigneshperiasami.botchat.ui.model.ChatMessage;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BotChatService.ReplyListener {
  private ListView lstChatMessages;
  private List<ChatMessage> chatMessages;
  private EditText edtChatText;
  private ChatListAdapter chatListAdapter;

  private BotChatService botChatService;

  private void hideKeypad() {
    View view = this.getCurrentFocus();
    if (view != null) {
      InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
      imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main_activity);
    chatMessages = new LinkedList<>();
    botChatService = BotChatService.create(this);
    initViews();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
  }

  private void pushMessage(ChatMessage chatMessage) {
    chatMessages.add(chatMessage);
    chatListAdapter.notifyDataSetChanged();
    lstChatMessages.setSelection(chatListAdapter.getCount() - 1);
  }

  private void initViews() {
    lstChatMessages = (ListView) findViewById(R.id.lst_chat_messages);
    chatListAdapter = new ChatListAdapter(this, chatMessages);
    lstChatMessages.setAdapter(chatListAdapter);

    edtChatText = (EditText) findViewById(R.id.edt_chat_message);
    Button btnSend = (Button) findViewById(R.id.btn_send);
    btnSend.setOnClickListener((v) -> {
      ChatMessage chatMessage = ChatMessage.selfMessage(edtChatText.getText().toString());
      edtChatText.setText("");
      hideKeypad();
      edtChatText.clearFocus();
      pushMessage(chatMessage);
      botChatService.getReply(chatMessage.message);
    });
  }

  @Override
  public void onReply(ChatMessage message) {
    runOnUiThread(() -> pushMessage(message));
  }
}
