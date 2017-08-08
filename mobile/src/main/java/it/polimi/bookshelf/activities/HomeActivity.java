package it.polimi.bookshelf.activities;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import it.polimi.bookshelf.R;
import it.polimi.bookshelf.data.PreferenceHandler;
import it.polimi.bookshelf.fragments.AboutFragment;
import it.polimi.bookshelf.fragments.HomeFragment;
import it.polimi.bookshelf.fragments.SettingsFragment;
import it.polimi.bookshelf.fragments.ShelfFragment;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        this.refreshHeader();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        Bundle extras = getIntent().getExtras();

        if (extras != null) {

            String F_TO_LOAD = extras.getString("FRAGMENT_TO_LOAD");

            if (F_TO_LOAD != null) {
                switch (F_TO_LOAD) {
                    case "SHELF":
                        Fragment fragment = ShelfFragment.newInstance();
                        getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
                        getSupportActionBar().setTitle("Shelves");
                }
            }
        } else {

            Fragment fragment = HomeFragment.newInstance(false);
            getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
            getSupportActionBar().setTitle("");
        }

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            Fragment fragment = HomeFragment.newInstance(false);
            getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
            getSupportActionBar().setTitle("");
        }
    }

    public void refreshHeader() {
        PreferenceHandler pH = new PreferenceHandler(getApplicationContext());

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);

        TextView userInfo = (TextView) headerView.findViewById(R.id.textViewUserInfo);
        TextView booksCount = (TextView) headerView.findViewById(R.id.textViewBookCount);

        userInfo.setText(pH.getUser_name() + " " + pH.getUser_surname());
        booksCount.setText(pH.getUser_book_count() + " books");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {

            Fragment fragment = SettingsFragment.newInstance();

            getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();

            getSupportActionBar().setTitle("Settings");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_home) {

            Fragment fragment = HomeFragment.newInstance(false);
            getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
            getSupportActionBar().setTitle("");

        } else if (id == R.id.nav_library) {

            Fragment fragment = ShelfFragment.newInstance();
            getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("shelves").commit();
            getSupportActionBar().setTitle("Shelves");

        } else if (id == R.id.nav_scanbooks) {

            Fragment fragment = HomeFragment.newInstance(true);
            getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
            getSupportActionBar().setTitle("");

        } else if (id == R.id.nav_settings) {

            Fragment fragment = SettingsFragment.newInstance();
            getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
            getSupportActionBar().setTitle("Settings");

        } else if (id == R.id.nav_about) {

            Fragment fragment = AboutFragment.newInstance();
            getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
            getSupportActionBar().setTitle("About");

        } else if (id == R.id.nav_logout) {

            PreferenceHandler pH = new PreferenceHandler(HomeActivity.this);
            pH.deleteUser();
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
