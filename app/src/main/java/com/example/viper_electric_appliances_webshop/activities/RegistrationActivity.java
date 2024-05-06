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

public class RegistrationActivity extends AppCompatActivity {

    EditText name,email,password, passwordAgain;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser() != null){
            startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
            finish();
        }
        
        name = findViewById(R.id.nevInputReg);
        email = findViewById(R.id.emailInputReg);
        password = findViewById(R.id.jelszoInputReg);
        passwordAgain = findViewById(R.id.jelszoInput2Reg);

        ImageView brandIcon = findViewById(R.id.brand_ic);
        Animation rotationAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate_anim);
        brandIcon.startAnimation(rotationAnimation);
    }

    public void signUp(View view){

        String userName = name.getText().toString();
        String userEmail = email.getText().toString();
        String userPass = password.getText().toString();
        String userPassAgain = passwordAgain.getText().toString();

        if(TextUtils.isEmpty(userName)){ Toast.makeText(this, "Add meg a neved!", Toast.LENGTH_LONG).show(); return; }
        if(TextUtils.isEmpty(userEmail)){ Toast.makeText(this, "Add meg az email címed!", Toast.LENGTH_LONG).show(); return; }
        if(TextUtils.isEmpty(userPass)){ Toast.makeText(this, "Add meg a jelszavad!", Toast.LENGTH_LONG).show(); return; }
        if(TextUtils.isEmpty(userPassAgain)){ Toast.makeText(this, "Add meg a jelszavad újra!", Toast.LENGTH_LONG).show(); return; }
        if(userPass.length() < 8) { Toast.makeText(this, "A jelszavad túl rövid(minimum 8 karakter)!", Toast.LENGTH_LONG).show(); return; }
        if(!userPass.equals(userPassAgain)){ Toast.makeText(this, "A megadott két jelszó nem egyezik!", Toast.LENGTH_LONG).show(); return; }

        auth.createUserWithEmailAndPassword(userEmail, userPass).addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegistrationActivity.this, "Sikeres regisztráció!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                } else { Toast.makeText(RegistrationActivity.this, "Sikertelen regisztráció! " + task.getException(), Toast.LENGTH_SHORT).show(); }
            }
        });

        //------------
    }

    public void signIn(View view){
        startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));

    }

}
