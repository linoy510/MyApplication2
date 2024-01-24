package com.example.myapplication;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginClass
{
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public loginClass(){}

    public void Register(EditText etEmail, EditText etPassword, String email, String password, loginPage p)
    {

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if(task.isSuccessful())
                {
                    Toast.makeText( p, "register success!", Toast.LENGTH_SHORT).show();
                    p.moveToNextActivity();
                }
                else
                {
                    String failureR = task.getException().toString();
                    Toast.makeText(p, "register fail!" + failureR, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
