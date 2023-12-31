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

    private void moveToNextActivity()
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

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if(task.isSuccessful())
                {
                    Toast.makeText(loginPage.this, "register success!", Toast.LENGTH_SHORT).show();
                    loginPage.this.moveToNextActivity();
                }
                else
                {
                    String failureR = task.getException().toString();
                    Toast.makeText(loginPage.this, "register fail!" + failureR, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}