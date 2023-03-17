package com.example.chatapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class MessagesAdapter extends RecyclerView.Adapter {


    Context context;
    ArrayList<Messages>messagesArrayList;

    int SENDERITEM=1;
    int RECEIVERITEM=2;


    public MessagesAdapter(Context context, ArrayList<Messages> messagesArrayList) {
        this.context = context;
        this.messagesArrayList = messagesArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType==SENDERITEM)
        {
View view = LayoutInflater.from(context).inflate(R.layout.senderchatlayout,parent,false);




            return new SenderViewholder(view);

        }
        else{
            View view = LayoutInflater.from(context).inflate(R.layout.receiverchatlayout,parent,false);




            return new ReceiverViewholder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        Messages messages=messagesArrayList.get(position);
        if(holder.getClass()==SenderViewholder.class)
        {
              ((SenderViewholder) holder).message.setText(messages.getMessage());
              ((SenderViewholder) holder).time.setText(messages.getCurrenttime());

        }
        else{

            ((ReceiverViewholder) holder).message.setText(messages.getMessage());
            ((ReceiverViewholder) holder).time.setText(messages.getCurrenttime());
        }
    }


    @Override
    public int getItemViewType(int position) {
        Messages messages=messagesArrayList.get(position);


      if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(messages.getSenderUID()))
      {
        return SENDERITEM;


      }
      else{

          return  RECEIVERITEM;
      }
    }

    @Override
    public int getItemCount() {
        return messagesArrayList.size();
    }



    class SenderViewholder extends  RecyclerView.ViewHolder{


        TextView message,time;

        public SenderViewholder(@NonNull View itemView) {
            super(itemView);
            message=itemView.findViewById(R.id.sendermessage);
            time=itemView.findViewById(R.id.timesendrmessage);

        }
    }

    class ReceiverViewholder extends  RecyclerView.ViewHolder{


        TextView message,time;

        public ReceiverViewholder(@NonNull View itemView) {
            super(itemView);
            message=itemView.findViewById(R.id.sendermessage);
            time=itemView.findViewById(R.id.timesendrmessage);

        }
    }
}
