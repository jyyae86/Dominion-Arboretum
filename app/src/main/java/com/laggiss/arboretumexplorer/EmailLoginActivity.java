package com.laggiss.arboretumexplorer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class EmailLoginActivity extends AppCompatActivity {

    //UI attributes
    EditText email;
    EditText password;
    Button signUp;
    Button login;
    ProgressDialog progressDialog;

    //Firebase
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_login);

        mAuth = FirebaseAuth.getInstance();

//        if(mAuth != null){
//            Log.e("reached auth","tst");
//            finish();
//            startActivity((new Intent(getApplicationContext(), MainActivity.class)));
//        }

        email = (EditText)findViewById(R.id.editTextEmail);
        password = (EditText)findViewById(R.id.editTextPassword);
        signUp = (Button)findViewById(R.id.buttonSignUp);
        login = (Button)findViewById(R.id.buttonLogin);
        progressDialog = new ProgressDialog(this);
    }

    public void loginWithEmail(View v){
        String eml = email.getText().toString();
        String pswd = password.getText().toString();

        if(TextUtils.isEmpty(eml)){
            Toast.makeText(this,"Email cannot be empty",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(pswd)){
            Toast.makeText(this,"Password cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        //start progress dialog
        progressDialog.setMessage("loading, please wait");
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(eml, pswd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            finish();
                            startActivity((new Intent(getApplicationContext(), MainActivity.class)));
                        }else{

                        }
                    }
                });
    }

    public void createSignUpActivity(View v){
        //redirects to signup
        startActivity(new Intent(this,EmailSignUpActivity.class));
    }

    public void createMainActivityWithoutLogin(View v){
        //temp
        startActivity(new Intent(this,MainActivity.class));
    }

}
