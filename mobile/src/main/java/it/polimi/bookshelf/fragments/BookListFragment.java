package it.polimi.bookshelf.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONObject;

import java.util.ArrayList;

import it.polimi.bookshelf.R;
import it.polimi.bookshelf.activities.BookDetailActivity;
import it.polimi.bookshelf.activities.VerticalOrientationCA;
import it.polimi.bookshelf.adapters.MyBookRecyclerViewAdapter;
import it.polimi.bookshelf.data.DataHandler;
import it.polimi.bookshelf.model.Book;
import it.polimi.bookshelf.utilities.AmazonFinder;
import it.polimi.bookshelf.utilities.GoogleBooksFinder;
import it.polimi.bookshelf.utilities.MergeBookSources;

@SuppressWarnings("FieldCanBeLocal")
public class BookListFragment extends Fragment {

    private ArrayList<Book> mBooks;
    private Book book, amazonBook, googleBook;
    private String GOOGLE_API = "https://www.googleapis.com/books/v1/volumes?q=isbn:";

    public BookListFragment() {
    }

    public static BookListFragment newInstance() {

        return new BookListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_list, container, false);

        mBooks = loadBooks();

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentIntentIntegrator scanIntegrator = new FragmentIntentIntegrator(BookListFragment.this);
                scanIntegrator.setCaptureActivity(VerticalOrientationCA.class);
                scanIntegrator.setPrompt("Scan a Book");
                scanIntegrator.initiateScan();
            }
        });

        TextView noBooks = (TextView) view.findViewById(R.id.nobooks_text);

        noBooks.setVisibility(View.GONE);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.book_list);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);

        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setAdapter(new MyBookRecyclerViewAdapter(mBooks, getActivity()));

        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //retrieve result of scanning - instantiate ZXing object
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        Toast toast;

        Log.v("SCANSIONATO",scanningResult.toString());
        //check we have a valid result
        if (scanningResult != null) {
            //get content from Intent Result
            final String scanContent = scanningResult.getContents();


            if (scanContent == null) {

                toast = Toast.makeText(getActivity(), getResources().getString(R.string.no_scandata), Toast.LENGTH_SHORT);
                toast.show();

            } else {

                toast = Toast.makeText(getActivity(), "ISBN " + scanContent + " " + getResources().getString(R.string.found), Toast.LENGTH_SHORT);
                toast.show();

                final ProgressDialog progressDialog =
                        ProgressDialog.show(getActivity(),
                                getResources().getString(R.string.wait),
                                getResources().getString(R.string.search_for_books), true, false);

                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

                new SearchForBooks(new OnBookSearchCompleted() {
                    @Override
                    public void onBookSearchCompleted() {

                        progressDialog.dismiss();
                        if (book != null) {

                            Intent bookIntent = new Intent(getActivity(), BookDetailActivity.class);
                            bookIntent.putExtra("book", (Parcelable) book);
                            bookIntent.putExtra("button", "add");
                            getActivity().startActivity(bookIntent);

                        } else {

                            Toast toast = Toast.makeText(getActivity(), getResources().getString(R.string.booksfinder_error), Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                }).execute(scanContent);

            }

        } else {
            //invalid scan data or scan canceled
            toast = Toast.makeText(getActivity(), getResources().getString(R.string.no_scandata), Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public ArrayList<Book> loadBooks() {

        DataHandler dh = new DataHandler(getActivity().getApplicationContext());
        return (ArrayList<Book>) dh.getDatabaseHandler().getBookList();
    }

    @SuppressWarnings("WeakerAccess")
    class SearchForBooks extends AsyncTask<String, Void, Void> {
        private OnBookSearchCompleted listener;

        public SearchForBooks(OnBookSearchCompleted listener) {
            this.listener = listener;
        }

        @Override
        protected Void doInBackground(String... isbn) {

            book = new Book();
            // Try to find book info on Amazon, Google Books and LibraryThings
            amazonBook = new AmazonFinder().getBook(isbn[0]);
            googleBook = new Book();
            String url = GOOGLE_API + isbn[0];

            // Make AsyncRequest to Google Books
            JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {

                    googleBook = new GoogleBooksFinder().findBook(response, googleBook);

                    //  Mix info from different sources
                    //  Priority : Amazon -> Google -> LibraryThing
                    book = new MergeBookSources().mergeBooks(amazonBook, googleBook, null);

                    SearchForBooks.this.onPostExecute();
                }

            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {

                    //  Google failed mix info from different sources
                    book = new MergeBookSources().mergeBooks(amazonBook, null, null);
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(jsObjRequest);

            return null;
        }

        protected void onPostExecute() {

            listener.onBookSearchCompleted();
        }
    }

    private interface OnBookSearchCompleted {
        void onBookSearchCompleted();
    }
}