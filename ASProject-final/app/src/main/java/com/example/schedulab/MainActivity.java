package com.example.schedulab;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import android.os.Handler;
import android.telephony.SmsManager;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schedulab.databinding.ActivityMainBinding;
import com.example.schedulab.refactoring.Contract;
import com.example.schedulab.refactoring.Model;
import com.example.schedulab.refactoring.Presenter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

       private EditText editTextEmail, edittextPassword;
    private Button signIn;
    private TextView textView;

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        signIn = (Button) findViewById(R.id.signIn_a);
        signIn.setOnClickListener(this);

        textView = (TextView) findViewById(R.id.textView);
        textView.setOnClickListener(this);

        editTextEmail = (EditText) findViewById(R.id.email_a);
        edittextPassword = (EditText) findViewById(R.id.password_a);
        progressBar = findViewById(R.id.progressbar_a);

        mAuth = FirebaseAuth.getInstance();


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signIn_a:
                userLogin();
                break;
            case R.id.textView:
                startActivity(new Intent(this, LoginType.class));
                break;
        }
    }

    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = edittextPassword.getText().toString().trim();


        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please enter a valid email");
            editTextEmail.requestFocus();
        }

        if (password.isEmpty()) {
            edittextPassword.setError("Password is required");
            edittextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("AllUsers"); 
                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            Map<String, Object> studentMap = (HashMap<String, Object>) dataSnapshot.child(currentuser).getValue();
                            String type = (String) studentMap.get("type");
                            if (type.equals("Student")) {
                                startActivity(new Intent(MainActivity.this, StudentDefault.class));
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
                else {
                    Toast.makeText(AdminLogin.this, "Failed to login, please check your credentials", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);


                }
            }
        });

    }
}