package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {



    EditText phoneno;
    android.widget.Button phonesubmit;
    CountryCodePicker ccp;
    String countycode;
    String phonenumber;

    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mcallback;
    String codesent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ccp=findViewById(R.id.ccp);
        phoneno=findViewById(R.id.phoneno);
        phonesubmit=findViewById(R.id.phonesubmit);
        progressBar=findViewById(R.id.progressbar);

        firebaseAuth=FirebaseAuth.getInstance();
        countycode=ccp.getSelectedCountryCodeWithPlus();

        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                countycode=ccp.getSelectedCountryCodeWithPlus();
            }
        });

        phonesubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String number;
                number=phoneno.getText().toString();
                if(number.isEmpty())
                {

                    Toast.makeText(MainActivity.this, "please enter phone number", Toast.LENGTH_SHORT).show();

                }
                else if(number.length()!=10){
                    Toast.makeText(MainActivity.this, "please enter correct phone number", Toast.LENGTH_SHORT).show();

                }else{

                    progressBar.setVisibility(View.VISIBLE);
                    phonenumber=countycode+number;
                    Log.e("*",phonenumber);
                    PhoneAuthOptions phoneAuthOptions= PhoneAuthOptions.newBuilder(firebaseAuth)
                            .setPhoneNumber(phonenumber)
                            .setTimeout(60L, TimeUnit.SECONDS)
                            .setActivity(MainActivity.this)
                            .setCallbacks(mcallback)
                            .build();

                    PhoneAuthProvider.verifyPhoneNumber(phoneAuthOptions);

                    Log.e("*","heererer");




                }
            }


        });



        mcallback=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.e("*","failed");
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

             // this is callback when phone no is correct and otp is sent

                super.onCodeSent(s, forceResendingToken);
                Toast.makeText(MainActivity.this, "otp sent", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
                codesent=s; // storing the code sent to the user so that we can compare it when user enters in otp activity

                Log.e("*","ppppppppppppppppppppp");
                Log.e("*",codesent);
                startActivity(new Intent(MainActivity.this,OtpActivity.class)

                        .putExtra("otp",codesent)
                );


            }
        };




    }


    @Override
    protected void onStart() {
        super.onStart();
        if(firebaseAuth.getInstance().getCurrentUser()!=null)
        {

            Intent intent=new Intent(MainActivity.this,Chatactivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}