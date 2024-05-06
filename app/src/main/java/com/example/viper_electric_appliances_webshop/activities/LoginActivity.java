package com.example.viper_electric_appliances_webshop.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.viper_electric_appliances_webshop.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText email,password;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        email = findViewById(R.id.emailInputLogin);
        password = findViewById(R.id.jelszoInputLogin);

        ImageView brandIcon = findViewById(R.id.brand_ic);
        Animation rotationAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate_anim);
        brandIcon.startAnimation(rotationAnimation);
    }

    public void signIn2(View view){

        String userEmail = email.getText().toString();
        String userPass = password.getText().toString();

        if(TextUtils.isEmpty(userEmail)){ Toast.makeText(this, "Add meg az email címed!", Toast.LENGTH_LONG).show(); return; }
        if(TextUtils.isEmpty(userPass)){ Toast.makeText(this, "Add meg a jelszavad!", Toast.LENGTH_LONG).show(); return; }

        auth.signInWithEmailAndPassword(userEmail, userPass).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                } else {
                    Toast.makeText(LoginActivity.this, "Hiba!" + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        //-------------------------
    }

    public void signUp2(View view){
        startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
    }

}