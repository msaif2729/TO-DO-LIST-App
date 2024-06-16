package com.example.todo_list;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class Home extends AppCompatActivity {

    private MaterialToolbar topbar;
    private DrawerLayout drawerLayout;
    private NavigationView nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        topbar = (MaterialToolbar) findViewById(R.id.topbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        nav = (NavigationView) findViewById(R.id.nav);

        topbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Home.this, "Navigation Clicked", Toast.LENGTH_SHORT).show();
                drawerLayout.open();
            }
        });

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                drawerLayout.close();
                return false;
            }
        });

        topbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Toast.makeText(Home.this, "Account Logo Clicked", Toast.LENGTH_SHORT).show();
                
                return false;
            }
        });



    }
}