package com.example.schedulab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class LoginType extends AppCompatActivity implements View.OnClickListener {

    private ImageView Admin, Student;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_type);

        Admin = (ImageView) findViewById(R.id.Admin_type);
        Admin.setOnClickListener(this);


        Student = (ImageView) findViewById(R.id.Student_type);
        Student.setOnClickListener(this);



    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Admin_type:
                startActivity(new Intent(this, AdminLogin.class));
                break;
            case R.id.Student_type:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }




}

