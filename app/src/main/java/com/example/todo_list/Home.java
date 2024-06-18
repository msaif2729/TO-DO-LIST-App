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

        if (savedInstanceState==null)
        {
            nav.setCheckedItem(R.id.home);
        }

        topbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.open();
            }
        });

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                item.setChecked(true);

               if(item.getItemId()==R.id.home)
               {
                   Toast.makeText(Home.this, "Home", Toast.LENGTH_SHORT).show();
               }
               else if(item.getItemId()==R.id.fav)
               {
                   Toast.makeText(Home.this, "Favourite", Toast.LENGTH_SHORT).show();
               } else if (item.getItemId()==R.id.add) {
                   Toast.makeText(Home.this, "Add Task", Toast.LENGTH_SHORT).show();
               }
               else if(item.getItemId()==R.id.logout)
               {
                   Toast.makeText(Home.this, "Logout", Toast.LENGTH_SHORT).show();
               }

                return true;
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