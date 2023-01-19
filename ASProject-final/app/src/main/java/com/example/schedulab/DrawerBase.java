package com.example.schedulab;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.schedulab.StudentDefault;
import com.google.android.material.navigation.NavigationView;

public class DrawerBase extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout ;
    @Override
    public void setContentView(View view) {
        drawerLayout = (DrawerLayout)getLayoutInflater().inflate(R.layout.activity_drawer_base,null);
        FrameLayout container = drawerLayout.findViewById(R.id.activityContainer);
        container.addView(view);
        super.setContentView(drawerLayout);

        Toolbar toolbar = drawerLayout.findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        NavigationView navigationView = drawerLayout.findViewById(R.id.nav_student_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.menu_Open,R.string.close_menu);
        toggle.syncState();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.nav_profile:
                Log.i("Menu_drawer_tag", "Profile clicked");
                startActivity(new Intent(DrawerBase.this, StudentDefault.class));
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_courses:
                Log.i("Menu_drawer_tag", "Courses clicked");
                startActivity(new Intent(DrawerBase.this, showData.class));
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_add:
                Log.i("Menu_drawer_tag", "Add clicked");
                startActivity(new Intent(DrawerBase.this, AddCourse.class));
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_req:
                Log.i("Menu_drawer_tag", "Request clicked");
                startActivity(new Intent(DrawerBase.this, RequestTimeline.class));
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_logout:
                Log.i("Menu_drawer_tag", "Logout clicked");
                startActivity(new Intent(DrawerBase.this, MainActivity.class));
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
        }

        return true;
    }

    protected void allocateActivityTitle(String titleString){
        if (getSupportActionBar()!=null)
            getSupportActionBar().setTitle(titleString);

    }
}