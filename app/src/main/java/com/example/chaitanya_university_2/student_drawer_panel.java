package com.example.chaitanya_university_2;

import androidx.annotation.NonNull;
import org.jetbrains.annotations.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

public class student_drawer_panel extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    public static Intent intent;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_drawer_panel);

      //  TextView tv_d_name=findViewById(R.id.name);
       // tv_d_name.setText("Chaitanya");

        intent=new Intent(this,student_exams_results_activity.class);

        Toolbar toolbar=findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        DrawerLayout drawerLayout=findViewById(R.id.drawer_layout);
         navigationView=findViewById(R.id.nav_view);


        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_student_dashboard, R.id.nav_student_attendance, R.id.nav_student_timetable,R.id.nav_student_exams_results,R.id.nav_student_assignments,R.id.nav_student_library,R.id.nav_student_fees,R.id.nav_student_feedback,R.id.nav_student_profile_settings,R.id.nav_student_logout)
                .setDrawerLayout(drawerLayout)
                .build();
        NavController navController= Navigation.findNavController(this,R.id.nav_host_fragment);

        NavigationUI.setupActionBarWithNavController(this,navController,mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView,navController);

    }
/*
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();

        //Toast.makeText(getApplicationContext(),id,Toast.LENGTH_SHORT).show();
        /*
        if(id==R.id.nav_student_logout)
        {
            Toast.makeText(getApplicationContext(),"logout",Toast.LENGTH_SHORT).show();
        }*/
  //      return super.onOptionsItemSelected(item);
 //   }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController=Navigation.findNavController(this,R.id.nav_host_fragment);

        return NavigationUI.navigateUp(navController,mAppBarConfiguration)||super.onSupportNavigateUp();

    }

}
