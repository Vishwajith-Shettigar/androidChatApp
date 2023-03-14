package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

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
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Setprofileactivity extends AppCompatActivity {

    private CardView cardViewsetimage;
    private ImageView setimage;
    private EditText setusername;

    private  android.widget.Button saveprofilebtn;

    private ProgressBar setprofileprogressbar;

    private FirebaseAuth firebaseAuth;
    private FirebaseStorage firebaseStorage;

    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;

private  String name;

private  String imageAccessToken;
    private  static  int PICK_IMAGE=123;
    private Uri imagepath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setprofileactivity);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseStorage=FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference();
        firebaseFirestore=FirebaseFirestore.getInstance();

        cardViewsetimage=findViewById(R.id.cardViewsetimage);
        setimage=findViewById(R.id.setimage);
        setusername=findViewById(R.id.setusername);
        saveprofilebtn=findViewById(R.id.saveprofilebtn);
        setprofileprogressbar=findViewById(R.id.setprofileprogressbar);


try {
    storageReference = firebaseStorage.getReference();

    storageReference.child("images").child(firebaseAuth.getUid()).child("profilepic")
            .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    imageAccessToken = uri.toString();
                    Picasso.get().load(uri).into(setimage);
                }
            });
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());
    databaseReference.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {

            UserProfile userProfile = snapshot.getValue(UserProfile.class);
            if(userProfile!=null)
                 setusername.setText(userProfile.getUsername());

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });
}
catch(Exception e)
{


}

        cardViewsetimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);

                startActivityForResult(intent,PICK_IMAGE);;


            }
        });




        saveprofilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name=setusername.getText().toString();
                if(name.isEmpty())
                {

                    Toast.makeText(Setprofileactivity.this, "Enter name", Toast.LENGTH_SHORT).show();
                }else{


                    setprofileprogressbar.setVisibility(View.VISIBLE);
                    saveUserData();
                    setprofileprogressbar.setVisibility(View.INVISIBLE);
                    startActivity(new Intent(Setprofileactivity.this,Chatactivity.class));
                    finish();

                }



            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        // if user selected a image fromm gallary
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK)
        {

            imagepath=data.getData();
            setimage.setImageURI(imagepath);
        }


        super.onActivityResult(requestCode, resultCode, data);


    }

    private void saveUserData(){

        setdataToRealtimedatabase();
    }

    private  void setdataToRealtimedatabase()
    {
// realtime database
        name=setusername.getText().toString().trim();
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();


        DatabaseReference databaseReference=firebaseDatabase.getReference(firebaseAuth.getUid());

        UserProfile  userProfile=new UserProfile(name,firebaseAuth.getUid());
        databaseReference.setValue(userProfile);
        Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show();


        sendImagetoStorage();


    }

    private  void sendImagetoStorage()
    {

        StorageReference iamgeRef=storageReference.child("images").child(firebaseAuth.getUid()).child("profilepic");



        // image compression

        Bitmap bitmap=null;

        try{

            bitmap=MediaStore.Images.Media.getBitmap(getContentResolver(),imagepath);



        }
         catch (IOException e) {

        }


        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,25,byteArrayOutputStream);



        byte[] data=byteArrayOutputStream.toByteArray();

        // save in storage
        UploadTask uploadTask=iamgeRef.putBytes(data);

      uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
          @Override
          public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
              iamgeRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                  @Override
                  public void onSuccess(Uri uri) {
                      imageAccessToken=uri.toString();
                      sendDatatocloudFirestore();
                  }
              });
          }
      });



    }

    private  void  sendDatatocloudFirestore()

    {

        DocumentReference documentReference=firebaseFirestore.collection("users").document(firebaseAuth.getUid());

        Map<String,Object> userdata=new HashMap<>();

        userdata.put("name",name);
        userdata.put("image",imageAccessToken);
        userdata.put("UID",firebaseAuth.getUid());
        userdata.put("status","online");

        documentReference.set(userdata).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {


            }
        });




    }
}