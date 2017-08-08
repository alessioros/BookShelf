package it.polimi.bookshelf.activities;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

        Bundle bundle = new Bundle();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            getSupportActionBar().setTitle(extras.getString("shelf_name"));
            bundle.putString("shelf_name", extras.getString("shelf_name"));
        }
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Fragment fragment = BookListFragment.newInstance();
        fragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();

    }

    @Override
    public void onBackPressed() {

        Intent i = new Intent(ShelfActivity.this, HomeActivity.class);
        i.putExtra("FRAGMENT_TO_LOAD", "SHELF");
        startActivity(i);
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {

            Intent i = new Intent(ShelfActivity.this, HomeActivity.class);
            i.putExtra("FRAGMENT_TO_LOAD", "SHELF");
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
