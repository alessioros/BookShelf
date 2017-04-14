package it.polimi.bookshelf.activities;

import android.app.Fragment;
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
        toolbar.setTitle("BookShelf");
        setSupportActionBar(toolbar);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        this.refreshHeader();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        Fragment fragment = HomeFragment.newInstance(false);
        getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
        getSupportActionBar().setTitle("Home");

        navigationView.setNavigationItemSelectedListener(this);
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

    public void refreshHeader(){
        PreferenceHandler pH = new PreferenceHandler(getApplicationContext());

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);

        TextView userInfo = (TextView) headerView.findViewById(R.id.textViewUserInfo);
        TextView booksCount = (TextView) headerView.findViewById(R.id.textViewBookCount);

        userInfo.setText(pH.getUser_name()+" "+pH.getUser_surname());
        booksCount.setText("0 books");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

            Fragment fragment = HomeFragment.newInstance(false);

            getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();

            getSupportActionBar().setTitle("Home");

        } else if (id == R.id.nav_library) {

            Fragment fragment = ShelfFragment.newInstance();

            getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();

            getSupportActionBar().setTitle("Library");

        } else if (id == R.id.nav_scanbooks) {

            Fragment fragment = HomeFragment.newInstance(true);

            getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();

            getSupportActionBar().setTitle("Home");

        } else if (id == R.id.nav_settings) {

            Fragment fragment = SettingsFragment.newInstance();

            getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();

            getSupportActionBar().setTitle("Settings");

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
