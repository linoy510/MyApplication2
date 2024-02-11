package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginPage extends AppCompatActivity {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        if(mAuth.getCurrentUser() != null)
        {
            moveToNextActivity();
        }
    }

    public void moveToNextActivity()
    {
        Intent intent = new Intent(loginPage.this, MainAtv1.class);
        startActivity(intent);
    }

    public void Register(View view)
    {
        EditText etEmail = findViewById(R.id.editTextTextEmailAddress);
        EditText etPassword = findViewById(R.id.editTextTextPassword);
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        loginClass l = new loginClass();
        l.Register(etEmail, etPassword, email, password, this);
        //mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

        }
    }
