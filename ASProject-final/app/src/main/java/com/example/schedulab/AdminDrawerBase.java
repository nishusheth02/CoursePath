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

import com.google.android.material.navigation.NavigationView;

public class AdminDrawerBase extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    @Override
    public void setContentView(View view) {
        drawerLayout = (DrawerLayout)getLayoutInflater().inflate((R.layout.activity_admin_drawer_base), null);
        FrameLayout container = drawerLayout.findViewById(R.id.activityContainer);
        container.addView(view);
        super.setContentView(drawerLayout);

        Toolbar toolbar = drawerLayout.findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        NavigationView navigationView = drawerLayout.findViewById(R.id.nav_admin_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.menu_Open,R.string.close_menu);
        toggle.syncState();

    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_admin_profile:
                Log.i("Admin_menu_drawer_tag", "Profile clicked");
                startActivity(new Intent(AdminDrawerBase.this, com.example.schedulab.AdminDefault.class));
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_admin_all:
                Log.i("Admin_menu_drawer_tag", "All courses clicked");
                startActivity(new Intent(AdminDrawerBase.this, com.example.schedulab.AllCourses.class));
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_add:
                Log.i("Admin_menu_drawer_tag", "Add clicked");
                startActivity(new Intent(AdminDrawerBase.this, AdminAdd.class));
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_edit:
                Log.i("Admin_menu_drawer_tag", "Edit clicked");
                startActivity(new Intent(AdminDrawerBase.this, com.example.schedulab.AdminEdit.class));
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_delete:
                Log.i("Admin_menu_drawer_tag", "Delete clicked");
                startActivity(new Intent(AdminDrawerBase.this, AdminDelete.class));
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_logout:
                Log.i("Admin_menu_drawer_tag", "Logout clicked");
                startActivity(new Intent(AdminDrawerBase.this, com.example.schedulab.AdminLogin.class));
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
        }

        return true;
    }

    protected void allocateActivityTitle(String titleString){
        if (getSupportActionBar()!=null)
            getSupportActionBar().setTitle(titleString);

    }
    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_drawer_base);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.admin_drawer_layout);
        navigationView = findViewById(R.id.nav_admin_view);
        //
        navigationView.bringToFront();
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.menu_Open, R.string.close_menu);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_admin_all:
                        Log.i("Admin_menu_drawer_tag", "All courses clicked");
                        startActivity(new Intent(AdminDrawerBase.this, AllCourses.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.nav_add:
                        Log.i("Admin_menu_drawer_tag", "Add clicked");
                        startActivity(new Intent(AdminDrawerBase.this, AdminAdd.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.nav_edit:
                        Log.i("Admin_menu_drawer_tag", "Edit clicked");
                        startActivity(new Intent(AdminDrawerBase.this, AdminEdit.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.nav_delete:
                        Log.i("Admin_menu_drawer_tag", "Delete clicked");
                        startActivity(new Intent(AdminDrawerBase.this, AdminDelete.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.nav_logout:
                        Log.i("Admin_menu_drawer_tag", "Logout clicked");
                        startActivity(new Intent(AdminDrawerBase.this, AdminLogin.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }

                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (getSupportActionBar() != null) {
            androidx.appcompat.widget.Toolbar tbar = findViewById(R.id.toolbar);
            setSupportActionBar(tbar);
            actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.menu_Open, R.string.close_menu);

            actionBarDrawerToggle.syncState();
        }
        if (actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

     */
}