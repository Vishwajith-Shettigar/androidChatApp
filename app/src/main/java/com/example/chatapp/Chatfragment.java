package com.example.chatapp;

import static android.widget.LinearLayout.VERTICAL;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

public class Chatfragment extends Fragment {

    private FirebaseFirestore firebaseFirestore;
private FirebaseAuth firebaseAuth;
private FirestoreRecyclerAdapter<Firebasemodel,Noteviewholder> chatAdapter;
    FirestoreRecyclerOptions<Firebasemodel>allusername;
RecyclerView recyclerView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.chatfragment,container,false);
Log.e("*","lolol=-0=--");

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        recyclerView=view.findViewById(R.id.recyclerviewchatfragment);
        Query query=firebaseFirestore.collection("users").whereNotEqualTo("UID",firebaseAuth.getUid());
       allusername=new FirestoreRecyclerOptions.Builder<Firebasemodel>().setQuery(query,Firebasemodel.class).build();

        chatAdapter=new FirestoreRecyclerAdapter<Firebasemodel, Noteviewholder>(allusername) {
            @Override
            protected void onBindViewHolder(@NonNull Noteviewholder holder, int position, @NonNull Firebasemodel model) {

       holder.textviewuername.setText(model.getName());
       String uri= model.getImage()+" ";
       Log.e("*","name"+model.getName());
                Picasso.get().load(uri).into(holder.imageView);



                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent=new Intent(getActivity(),Specificchat.class);
                        intent.putExtra("UID",model.getUID());
                        intent.putExtra("name",model.getName());
                        intent.putExtra("image",model.getImage());
                        intent.putExtra("status",model.getStatus());
                        startActivity(intent);


                        Toast.makeText(getActivity(), "clicked", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @NonNull
            @Override
            public Noteviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.chatview,parent,false);
                return  new Noteviewholder(view);

            }
        };

        recyclerView.setHasFixedSize(true);
LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
recyclerView.setLayoutManager(linearLayoutManager);

recyclerView.setAdapter(chatAdapter);


return  view;

    }


    public  class  Noteviewholder extends  RecyclerView.ViewHolder{


        private TextView textviewuername,textviewchat;
        private ImageView imageView;


        public Noteviewholder(@NonNull View itemView) {
            super(itemView);

            textviewuername=itemView.findViewById(R.id.nameofuser);
            textviewchat=itemView.findViewById(R.id.userchat);
            imageView=itemView.findViewById(R.id.userdp);


        }


    }

    @Override
    public void onStart() {
        super.onStart();

        Log.e("*","hot back 999999999910000099");

        chatAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(chatAdapter!=null)
            chatAdapter.stopListening();
    }
}
