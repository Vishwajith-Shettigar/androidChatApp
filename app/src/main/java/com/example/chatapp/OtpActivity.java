package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class OtpActivity extends AppCompatActivity {

    EditText editText;
    android.widget.Button submit;
TextView changenumber;

FirebaseAuth firebaseAuth;
ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        changenumber=findViewById(R.id.changeno);
        editText=findViewById(R.id.otptext);
        submit=findViewById(R.id.otpsubmit);

        progressBar=findViewById(R.id.otpprogressbar);

        firebaseAuth=FirebaseAuth.getInstance();



        changenumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(OtpActivity.this,MainActivity.class));
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String enteredotp = editText.getText().toString();
                if (enteredotp.isEmpty())
                {


                }else{


                    progressBar.setVisibility(View.VISIBLE);
                    String codereceived=getIntent().getStringExtra("otp");
                    PhoneAuthCredential phoneAuthCredential= PhoneAuthProvider.getCredential(codereceived,enteredotp);
                   signinwithauthcredential(phoneAuthCredential);

                }

            }
        });


    }

    private  void signinwithauthcredential(PhoneAuthCredential phoneAuthCredential)
    {

        firebaseAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
if(task.isSuccessful())

{

    progressBar.setVisibility(View.INVISIBLE);

    startActivity(new Intent(OtpActivity.this,Setprofileactivity.class));
finish();


}
else{


    if(task.getException() instanceof FirebaseAuthInvalidCredentialsException)
    {

        progressBar.setVisibility(View.INVISIBLE);
        Toast.makeText(OtpActivity.this, "Invalid otp", Toast.LENGTH_SHORT).show();
    }
}
            }
        });
    }
}