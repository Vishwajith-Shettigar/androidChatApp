package com.example.chatapp;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class Showprofile extends AppCompatActivity {

  private   EditText showusername;
    private FirebaseAuth firebaseAuth;
    private ImageView showimage;
    private  FirebaseDatabase firebaseDatabase;
    private TextView editprofile;
    private FirebaseFirestore firebaseFirestore;

    private FirebaseStorage firebaseStorage;

    private  StorageReference storageReference;

    private String ImageAccessToken;

    Toolbar toolbar;
    ImageButton backbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showprofile);

        showusername=findViewById(R.id.showusername);
        showimage=findViewById(R.id.showimage);
        backbtn=findViewById(R.id.showprofilebackbtn);

toolbar=findViewById(R.id.toolbarshowprofile);


        firebaseFirestore =FirebaseFirestore.getInstance();

        firebaseDatabase=FirebaseDatabase.getInstance();

        firebaseAuth=FirebaseAuth.getInstance();

firebaseStorage=FirebaseStorage.getInstance();

        editprofile=findViewById(R.id.editprofile);

setSupportActionBar(toolbar);
backbtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

//        startActivity(new Intent(Showprofile.this,Chatactivity.class));

        finish();
    }
});


storageReference=firebaseStorage.getReference();

        storageReference.child("images").child(firebaseAuth.getUid()).child("profilepic")
                .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        ImageAccessToken=uri.toString();
                        Picasso.get().load(uri).into(showimage);
                    }
                });


        DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());
       databaseReference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               UserProfile userProfile= snapshot.getValue(UserProfile.class);
               showusername.setText(userProfile.getUsername());

           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });

       editprofile.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(Showprofile.this,Setprofileactivity.class));
finish();
           }
       });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        startActivity(new Intent(Showprofile.this,Chatactivity.class));

        finish();
    }


    @Override
    protected void onStart() {
        super.onStart();

        DocumentReference documentReference=firebaseFirestore.collection("users").document(firebaseAuth.getUid());
        documentReference.update("status","online");

    }

    @Override
    protected void onStop() {
        super.onStop();

        DocumentReference documentReference=firebaseFirestore.collection("users").document(firebaseAuth.getUid());
        documentReference.update("status","offline");
    }
}