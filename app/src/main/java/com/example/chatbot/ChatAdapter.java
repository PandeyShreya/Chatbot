package com.example.chatbot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MsgModal> messages;
    private Context context;


    public ChatAdapter(List<MsgModal> messages, Context context) {
        this.messages = messages;
        this.context=context;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType){
            case 0:
                view= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_msg_item,parent,false);
                return new UserViewHolder(view);
            case 1:
                view= LayoutInflater.from(parent.getContext()).inflate(R.layout.bot_msg_item,parent,false);
                return new BotViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MsgModal message = messages.get(position);
        if (message.isUserMessage()) {
            ((UserViewHolder) holder).userTV.setText(message.getContent());
        } else {
            ((BotViewHolder) holder).botTV.setText(message.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public int getItemViewType(int position) {
        if (messages.get(position).isUserMessage()) {
            return 0; // User message type
        } else {
            return 1; // Bot message type
        }
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder{
        TextView userTV;
        public UserViewHolder(@NonNull View itemView){
            super(itemView);
            userTV = itemView.findViewById(R.id.idUser);

        }
    }

    public static class BotViewHolder extends RecyclerView.ViewHolder{
        TextView botTV;
        public BotViewHolder(@NonNull View itemView){
            super(itemView);
            botTV = itemView.findViewById(R.id.idBot);

        }
    }
}
