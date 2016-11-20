package com.stackdroid.ui;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.stackdroid.R;
import com.stackdroid.ui.bookmark.BookmarkFragment;
import com.stackdroid.ui.home.HomeFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.dashboard_toolbar)
    Toolbar dashboardToolbar;
    @Bind(R.id.appbar)
    AppBarLayout appbar;
    @Bind(R.id.fragmentContainer)
    FrameLayout fragmentContainer;
    @Bind(R.id.coordinator)
    CoordinatorLayout coordinator;
    @Bind(R.id.navigation_view)
    NavigationView navigationView;
    @Bind(R.id.drawer)
    DrawerLayout drawerLayout;
    @Bind(R.id.fab)
    FloatingActionButton fab;

    private Fragment fragment;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        setupToolBar();
        setupNavDrawer();
        fragment = new HomeFragment();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment, "home");
        fragmentTransaction.commit();
        fab.setOnClickListener(o -> callFragmentSort());


    }

    private void callFragmentSort() {
        HomeFragment homeFragment = ((HomeFragment) getSupportFragmentManager().findFragmentByTag("home"));
        if (homeFragment != null)
            homeFragment.changeSort();
    }

    private void setupToolBar() {
        setSupportActionBar(dashboardToolbar);
        getSupportActionBar().setTitle("Home");

    }

    private void setupNavDrawer() {
        navigationView.setNavigationItemSelectedListener(item -> {
            drawerLayout.closeDrawers();

            switch (item.getItemId()) {
                case R.id.home:
                    fragment = new HomeFragment();
                    getSupportActionBar().setTitle("Home");
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainer, fragment, "home");
                    fragmentTransaction.commit();
                    appbar.setExpanded(true, true);
                    fab.setVisibility(View.VISIBLE);
                    return true;
                case R.id.likedQns:
                    getSupportActionBar().setTitle("Bookmarked Questions");
                    fragment = new BookmarkFragment();
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainer, fragment, "liked");
                    fragmentTransaction.commit();
                    appbar.setExpanded(true, true);
                    fab.setVisibility(View.GONE);

                    return true;


                default:
                    return true;
            }
        });

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, dashboardToolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }
}
