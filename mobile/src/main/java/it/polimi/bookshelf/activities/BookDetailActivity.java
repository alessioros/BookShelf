package it.polimi.bookshelf.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import it.polimi.bookshelf.R;
import it.polimi.bookshelf.data.DataHandler;
import it.polimi.bookshelf.data.DatabaseHandler;
import it.polimi.bookshelf.model.Book;
import it.polimi.bookshelf.model.Shelf;

public class BookDetailActivity extends AppCompatActivity {

    private Book book;
    private static int REDIRECT_TIME_OUT = 500;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private DatabaseHandler dbH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initActivityTransitions();
        setContentView(R.layout.activity_book_detail);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, android.R.color.transparent));

        book = new Book();
        Intent i = getIntent();

        final ImageView bookImage = (ImageView) findViewById(R.id.book_image);
        TextView bookTitle = (TextView) findViewById(R.id.book_title);
        TextView bookAuthor = (TextView) findViewById(R.id.book_author);
        TextView bookPageCount = (TextView) findViewById(R.id.book_pagecount);
        TextView bookDescription = (TextView) findViewById(R.id.book_description);
        TextView bookPublisher = (TextView) findViewById(R.id.book_publisher);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        View ruler = findViewById(R.id.first_ruler);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            book = i.getParcelableExtra("book");
            book.setShelfID(i.getStringExtra("shelf_name"));

            try {
                if (book.getDescription().length() > 500) {

                    bookDescription.setText(book.getDescription().substring(0, 500) + "..");

                } else if (!book.getDescription().equals("")) {

                    bookDescription.setText(book.getDescription());
                }

            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            bookAuthor.setText(book.getAuthor());

            try{
                if (book.getPageCount() != 0) {
                     bookPageCount.setText(book.getPageCount() + " " + getResources().getString(R.string.book_pages));
                }
            }catch (Exception e ) {
                e.printStackTrace();
            }
            
            bookTitle.setText(book.getTitle());

            if (book.getTitle().length() > 30) {

                collapsingToolbarLayout.setTitle(book.getTitle().substring(0, 29) + "..");

            } else {

                collapsingToolbarLayout.setTitle(book.getTitle());
            }

            try {
                if (!book.getPublisher().equals("") && book.getPublishedDate() != null) {

                    bookPublisher.setText(book.getPublisher() + " - " + book.getPublishedDate());

                } else if (!book.getPublisher().equals("")) {

                    bookPublisher.setText(book.getPublisher());

                } else if (!book.getPublishedDate().toString().equals("")) {

                    bookPublisher.setText(book.getPublishedDate().toString());
                }

            } catch (NullPointerException e) {
                e.printStackTrace();
            }


        } else {

            ruler.setVisibility(View.GONE);
        }

        Picasso.with(this).load(book.getImgUrl()).into(bookImage, new Callback() {
            @Override
            public void onSuccess() {
                Bitmap bitmap = ((BitmapDrawable) bookImage.getDrawable()).getBitmap();
                Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                    public void onGenerated(Palette palette) {
                        applyPalette(palette);
                    }
                });
            }

            @Override
            public void onError() {

            }
        });


        if (i.getStringExtra("button").equals("delete")) {

            fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_delete));
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    deleteBook();
                }
            });
            Button deleteButton = (Button) findViewById(R.id.button_book_detail);
            deleteButton.setText(R.string.del_book);
            deleteButton.setVisibility(Button.VISIBLE);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    deleteBook();
                }
            });
        } else if (i.getStringExtra("button").equals("add")) {

            fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_add));
            Button addButton = (Button) findViewById(R.id.button_book_detail);
            addButton.setText(R.string.add_book);
            addButton.setVisibility(Button.VISIBLE);
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    addBook();
                }
            });

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    addBook();
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initActivityTransitions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide transition = new Slide();
            transition.excludeTarget(android.R.id.statusBarBackground, true);
            getWindow().setEnterTransition(transition);
            getWindow().setReturnTransition(transition);
        }
    }

    private void applyPalette(Palette palette) {

        collapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(ContextCompat.getColor(this, R.color.colorPrimary)));
        collapsingToolbarLayout.setStatusBarScrimColor(palette.getDarkMutedColor(ContextCompat.getColor(this, R.color.colorPrimaryDark)));
        updateBackground((FloatingActionButton) findViewById(R.id.fab), palette);
        supportStartPostponedEnterTransition();

        updateButtonBackground((Button) findViewById(R.id.button_book_detail), palette);
    }

    private void updateBackground(FloatingActionButton fab, Palette palette) {
        int lightVibrantColor = palette.getLightVibrantColor(ContextCompat.getColor(this, R.color.white));
        int vibrantColor = palette.getVibrantColor(ContextCompat.getColor(this, R.color.colorAccent));

        fab.setRippleColor(lightVibrantColor);
        fab.setBackgroundTintList(ColorStateList.valueOf(vibrantColor));
    }

    private void updateButtonBackground(Button button, Palette palette) {
        int lightVibrantColor = palette.getLightVibrantColor(ContextCompat.getColor(this, R.color.white));
        int vibrantColor = palette.getVibrantColor(ContextCompat.getColor(this, R.color.colorAccent));

        button.setBackgroundColor(lightVibrantColor);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            button.setBackgroundTintList(ColorStateList.valueOf(vibrantColor));
        }
    }

    private void deleteBook() {

        AlertDialog.Builder builder = new AlertDialog.Builder(BookDetailActivity.this);

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                DatabaseHandler dbH = new DataHandler(BookDetailActivity.this).getDatabaseHandler();

                try {

                    dbH.deleteBook(book.getISBN());
                    Toast.makeText(BookDetailActivity.this, getResources().getString(R.string.success_delete), Toast.LENGTH_SHORT).show();
                    // redirects to library after 0.5 seconds, allowing library to display the new book
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {

                            finish();
                        }
                    }, REDIRECT_TIME_OUT);

                } catch (Exception e) {

                    Toast.makeText(BookDetailActivity.this, getResources().getString(R.string.error_delete), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        builder.setMessage(R.string.delete_confirm)
                .setTitle(R.string.del_book);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void addBook() {

        try {
            dbH = new DataHandler(BookDetailActivity.this).getDatabaseHandler();

            List<Book> books = dbH.getBookList();

            for(Book book2 : books){
                if (book2.getISBN().equals(book.getISBN())){
                    Toast.makeText(BookDetailActivity.this, "you already added this book", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            if (book.getShelfID().equals("none")) {

                AlertDialog.Builder builder = new AlertDialog.Builder(BookDetailActivity.this);
                builder.setTitle("Select the shelf:");

                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(BookDetailActivity.this, android.R.layout.select_dialog_singlechoice);

                List<Shelf> shelves = dbH.getShelfList();

                for (Shelf shelf : shelves) {
                    arrayAdapter.add(shelf.getName());
                }

                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        book.setShelfID(arrayAdapter.getItem(which));
                        dbH.insertBook(book);
                        redirectToLibrary();
                    }
                });
                builder.show();

            } else {

                if (dbH.queryBook(book.getISBN()).getISBN() != null) {
                    Toast.makeText(BookDetailActivity.this, getResources().getString(R.string.book_alr_added), Toast.LENGTH_SHORT).show();
                } else {
                    dbH.insertBook(book);

                    Toast.makeText(BookDetailActivity.this, getResources().getString(R.string.success_add), Toast.LENGTH_SHORT).show();
                }
                redirectToLibrary();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(BookDetailActivity.this, getResources().getString(R.string.error_add), Toast.LENGTH_SHORT).show();

        }
    }

    private void redirectToLibrary() {

        // redirects to library after 0.5 seconds, allowing library to display the new book
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                finish();
                Intent i = new Intent(BookDetailActivity.this, HomeActivity.class);
                i.putExtra("FRAGMENT_TO_LOAD", "SHELF");
                startActivity(i);
            }
        }, REDIRECT_TIME_OUT);
    }
}