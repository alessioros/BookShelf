package it.polimi.bookshelf.activities;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import it.polimi.bookshelf.R;
import it.polimi.bookshelf.fragments.BookListFragment;

public class ShelfActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelf);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Fragment fragment = BookListFragment.newInstance();
        getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            getSupportActionBar().setTitle(extras.getString("shelf_name"));

        }
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void onBackPressed() {

        Intent i = new Intent(ShelfActivity.this, HomeActivity.class);
        i.putExtra("FRAGMENT_TO_LOAD","SHELF");
        startActivity(i);
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {

            Intent i = new Intent(ShelfActivity.this, HomeActivity.class);
            i.putExtra("FRAGMENT_TO_LOAD","SHELF");
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
