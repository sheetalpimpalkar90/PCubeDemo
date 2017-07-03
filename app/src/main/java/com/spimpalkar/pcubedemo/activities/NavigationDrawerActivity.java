package com.spimpalkar.pcubedemo.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.spimpalkar.pcubedemo.R;
import com.spimpalkar.pcubedemo.adapters.ViewPagerAdapter;
import com.spimpalkar.pcubedemo.fragments.PopularDealsFragment;
import com.spimpalkar.pcubedemo.fragments.TopDealsFragment;
import com.spimpalkar.pcubedemo.helpers.Constants;
import com.spimpalkar.pcubedemo.helpers.SPDSingleton;
import com.spimpalkar.pcubedemo.helpers.SqliteDBHandler;
import com.spimpalkar.pcubedemo.models.FBUserInfo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class NavigationDrawerActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    FBUserInfo fbUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);

        /*Setup toolbar*/
        setToolbar();

        /*set up navigation drawer*/
        setDrawer();

        /*set up viewpager*/
        setViewPager();
    }

    private void setViewPager() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new TopDealsFragment(), getResources().getString(R.string.top_deals));
        adapter.addFragment(new PopularDealsFragment(), getResources().getString(R.string.popular_deals));
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    /*Method to setup the toolbar*/
    private void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    /*Method to set up navigation drawe*/
    private void setDrawer() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        ImageView profileImageView = (ImageView) header.findViewById(R.id.profile_imageViewID);
        TextView nameTextView = (TextView) header.findViewById(R.id.name_textViewID);
        Picasso.with(this)
                .load(SPDSingleton.getInstance().getStringFromSp(Constants.profilePicSP, this))
                .placeholder(R.drawable.bg_image) //this is optional the image to display while the url image is downloading
                .into(profileImageView);
        nameTextView.setText(SPDSingleton.getInstance().getStringFromSp(Constants.userNameSP, this));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.nav_logout) {
            logoutPopup();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logoutPopup() {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        /*YES button clicked*/
                        if(isInternetConnectionAvailable()){
                            logoutFromFB();
                        }else{
                            SPDSingleton.getInstance().showShortToast(getResources().getString(R.string.no_internet_connection),
                                    NavigationDrawerActivity.this);
                        }

                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        /*No button clicked*/
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    /*Logout fromm from facebook and clear tables from database*/
    private void logoutFromFB(){
        FacebookSdk.sdkInitialize(getApplicationContext());
        LoginManager.getInstance().logOut();
        AccessToken.setCurrentAccessToken(null);

        /*Clear preferences and delete data from database*/
        Constants.isAutoLogin = "false";
        SPDSingleton.getInstance().clearDataFromSp(NavigationDrawerActivity.this);
        SqliteDBHandler.getSqliteInstance(NavigationDrawerActivity.this).deleteTopDealTable();
        SqliteDBHandler.getSqliteInstance(NavigationDrawerActivity.this).deletePopularDealTable();
        finish();
    }

}
