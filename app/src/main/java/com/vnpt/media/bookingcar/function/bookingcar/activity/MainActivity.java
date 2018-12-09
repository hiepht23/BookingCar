package com.vnpt.media.bookingcar.function.bookingcar.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.transition.FragmentTransitionSupport;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.vnpt.media.bookingcar.R;
import com.vnpt.media.bookingcar.function.bookingcar.adapter.ExpandableListAdapter;
import com.vnpt.media.bookingcar.function.bookingcar.adapter.MyPagerAdapter;
import com.vnpt.media.bookingcar.function.bookingcar.fragment.CarListFragment;
import com.vnpt.media.bookingcar.function.bookingcar.fragment.EmployeeListFragment;
import com.vnpt.media.bookingcar.function.bookingcar.item.MenuModel;
import com.vnpt.media.bookingcar.function.login.activity.LoginActivity;
import com.vnpt.media.bookingcar.utils.NotificationUtils;
import com.vnpt.media.bookingcar.utils.PopupUnit;
import com.vnpt.media.bookingcar.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import customfonts.MyTextView_Roboto_Regular;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static String TAG=MainActivity.class.getSimpleName();

    private NotificationUtils notificationUtils;
    private Activity mActivity;


    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;


    private ViewPager viewPager;
    private MyPagerAdapter adapter;


    private MyTextView_Roboto_Regular listEmployee,listCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_icar);
        mActivity = this;

        // toolbar+navigationbar
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                return false;
            }
        });
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(false);
//        actionBar.setTitle("");
        setToolbar();
//        initViewpager();
        listEmployee=findViewById(R.id.listEmployee);
        listEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG,"Click ");
//               new EmployeeListFragment();
                drawer.closeDrawers();
                Fragment fragment=new EmployeeListFragment();
                FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.mainFrame,fragment);
                ft.commit();
            }
        });
        listCar=findViewById(R.id.listCar);
        listCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG,"Click car");
//               new EmployeeListFragment();
                drawer.closeDrawers();
                Fragment fragment=new CarListFragment();
                FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.mainFrame,fragment);
                ft.commit();
            }
        });
    }

    private void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setTitle("");

        toolbar.findViewById(R.id.navigation_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("Click", "navi");

                if (drawer.isDrawerOpen(navigationView)) {
                    drawer.closeDrawer(navigationView);
                } else {
                    drawer.openDrawer(navigationView);

                    Log.e("abc", "abc");
                }
            }
        });

    }

//    public void initViewpager(){
//        viewPager=findViewById(R.id.view_pager);
//        adapter=new MyPagerAdapter(getSupportFragmentManager());
//        viewPager.setAdapter(adapter);
//    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(Gravity.LEFT); //OPEN Nav Drawer!
        } else {
            finish();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment=null;
        int id=menuItem.getItemId();
        switch (id){
            case R.id.listEmployee:
                Log.e(TAG,"Click employee");
                fragment=new EmployeeListFragment();
                FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.mainFrame,fragment);
                ft.commit();
        }

        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
