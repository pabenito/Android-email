package com.example.android_email.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.android_email.Adapters.ChatAdapter;
import com.example.android_email.DataBase.AppDataBase;
import com.example.android_email.DataBase.Entity.Chat;
import com.example.android_email.DataBase.Entity.Message;
import com.example.android_email.DataBase.Entity.User;
import com.example.android_email.databinding.ActivityChatBinding;

import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private ActivityChatBinding binding;
    private User user;
    private User contact;
    private List<Message> messages;
    private ChatAdapter chatAdapter;
    private AppDataBase db;
    private Chat chat;

    public ChatActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = AppDataBase.getInstance(getApplicationContext());
        user = SignInActivity.getSignedUser();
        chat = ContactsActivity.getSelectedChat();
        if (chat == null)
            startActivity(new Intent(this, ContactsActivity.class));
        contact = getTheOtherUserFromChat(chat, user);
        messages = db.messageDAO().getChatMessages(chat.id);

        binding.texName.setText(contact.username);

        setListeners();
        initAdapter();
    }

    private void initAdapter() {
        chatAdapter = new ChatAdapter(messages, user.username);
        binding.chatRecyclerView.setAdapter(chatAdapter);
        binding.chatRecyclerView.setVisibility(View.VISIBLE);
    }

    private void setListeners() {
        binding.imageBack.setOnClickListener(v -> startActivity(new Intent(this, ContactsActivity.class)));
        binding.layoutSend.setOnClickListener(v -> sendMessage());
    }

    private void sendMessage() {
        Message message = new Message();
        message.sender = user.username;
        message.receiver = contact.username;
        message.message = binding.inputMessage.getText().toString().trim();
        message.chatId = chat.id;

        long messageId = db.messageDAO().insert(message);
        messages.add(db.messageDAO().get(messageId));

        binding.inputMessage.getText().clear();

        chatAdapter.notifyItemInserted(messages.size()-1);
        binding.chatRecyclerView.smoothScrollToPosition(this.messages.size()-1);
    }

    private User getTheOtherUserFromChat(Chat chat, User user) {
        String theOtherUserUsername = chat.user1 == user.username ? chat.user1 : chat.user2;
        return db.userDao().get(theOtherUserUsername);
    }
}