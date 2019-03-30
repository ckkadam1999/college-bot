package com.webhostapp.collegebot;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {
    NavigationView nv;
    DrawerLayout layout;
    FragmentTransaction fragmentTransaction;
    Fragment chatBotFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final Session session = new Session(this);
        nv = findViewById(R.id.nv);
        layout = findViewById(R.id.layout);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_hamburger);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        chatBotFragment = new ChatBotFragment();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.drawer, chatBotFragment).commit();

        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                switch (menuItem.getTitle().toString()){
                    case "Chat Bot":
                        fragmentTransaction.replace(R.id.drawer, chatBotFragment).commit();
                        break;
                    case "Logout":
                        session.setLogin(false);
                        Toast.makeText(getApplicationContext(),"Successfuly logged out",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                }
                if(layout.isDrawerOpen(nv))
                    layout.closeDrawer(nv);
                return true;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (layout.isDrawerOpen(nv))
            layout.closeDrawer(nv);
        else
            layout.openDrawer(nv);
        return true;
    }
}
