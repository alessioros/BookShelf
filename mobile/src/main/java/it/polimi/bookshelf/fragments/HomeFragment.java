package it.polimi.bookshelf.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import it.polimi.bookshelf.R;
import it.polimi.bookshelf.activities.BookDetailActivity;
import it.polimi.bookshelf.activities.VerticalOrientationCA;
import it.polimi.bookshelf.model.Book;
import it.polimi.bookshelf.utilities.AmazonFinder;
import it.polimi.bookshelf.utilities.GoogleBooksFinder;
import it.polimi.bookshelf.utilities.ISBNDbFinder;
import it.polimi.bookshelf.utilities.MergeBookSources;

public class HomeFragment extends Fragment {

    Book book, amazonBook, googleBook, isbndbBook;
    private String GOOGLE_API = "https://www.googleapis.com/books/v1/volumes?q=isbn:";
    private boolean onlySCan = false;

    public HomeFragment() {
        // Required empty public constructor
    }

    /*
     * instantiate HomeFragment
     * @param onlyScan if the fragment is called for an ISBN scan
     */
    public static HomeFragment newInstance(boolean onlySCan) {
        HomeFragment fragment = new HomeFragment();

        fragment.onlySCan = onlySCan;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        final Button scanButton = (Button) view.findViewById(R.id.scan_button);

        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                scanBook();
            }
        });


        return view;
    }

    @Override
    public void onAttach(Context context) {

        if (onlySCan) scanBook();
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void scanBook() {
        FragmentIntentIntegrator scanIntegrator = new FragmentIntentIntegrator(HomeFragment.this);
        scanIntegrator.setCaptureActivity(VerticalOrientationCA.class);
        scanIntegrator.setPrompt(getResources().getString(R.string.scan_book_prompt));
        scanIntegrator.initiateScan();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        //retrieve result of scanning - instantiate ZXing object
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        Toast toast;

        //check we have a valid result
        if (scanningResult.getContents() != null) {

            //get content from Intent Result
            final String scanContent = scanningResult.getContents();

            toast = Toast.makeText(getActivity(), "ISBN " + scanContent + " " + getResources().getString(R.string.found), Toast.LENGTH_SHORT);
            toast.show();

            final ProgressDialog progressDialog =
                    ProgressDialog.show(getActivity(),
                            getResources().getString(R.string.wait),
                            getResources().getString(R.string.search_for_books), true, false);

            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

            new SearchForBooks(new HomeFragment.OnBookSearchCompleted() {
                @Override
                public void onBookSearchCompleted() {

                    progressDialog.dismiss();
                    if (book.getISBN() != null) {

                        Intent bookIntent = new Intent(getActivity(), BookDetailActivity.class);
                        bookIntent.putExtra("book", book);
                        bookIntent.putExtra("button", "add");
                        bookIntent.putExtra("shelf_name", "none");
                        getActivity().startActivity(bookIntent);

                    } else {

                        Toast toast = Toast.makeText(getActivity(), getResources().getString(R.string.booksfinder_error), Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            }).execute(scanContent);

        } else {
            //invalid scan data or scan canceled
            toast = Toast.makeText(getActivity(), getResources().getString(R.string.no_scandata), Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @SuppressWarnings("WeakerAccess")
    class SearchForBooks extends AsyncTask<String, Void, Void> {
        private OnBookSearchCompleted listener;

        public SearchForBooks(OnBookSearchCompleted listener) {
            this.listener = listener;
        }

        @Override
        protected Void doInBackground(final String... isbn) {

            book = new Book();

            // Try to find book info on Amazon, Google Books and ISBNDB
            amazonBook = new AmazonFinder().getBook(isbn[0]);
            googleBook = new Book();
            isbndbBook = new Book();

            String url = GOOGLE_API + isbn[0];
            String ISBNDbKEY = "WHSHHYLX";
            final String requestUrl = "http://isbndb.com/api/v2/json/" + ISBNDbKEY + "/book/" + isbn[0];

            // Make AsyncRequest to Google Books
            JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {

                    googleBook = new GoogleBooksFinder().findBook(response, googleBook);

                    JsonObjectRequest jsObjRequest2 = new JsonObjectRequest(Request.Method.GET, requestUrl, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {

                            Log.v("ISBNDB RESPONSE", response.toString());
                            isbndbBook = new ISBNDbFinder().getBook(response);
                            book = new MergeBookSources().mergeBooks(amazonBook, googleBook, isbndbBook);
                            SearchForBooks.this.onPostExecute();

                        }

                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Log.v("NO ISBNDB", "");
                            book = new MergeBookSources().mergeBooks(amazonBook, googleBook, null);
                            HomeFragment.SearchForBooks.this.onPostExecute();
                        }
                    });

                    RequestQueue requestQueue2 = Volley.newRequestQueue(getActivity());
                    requestQueue2.add(jsObjRequest2);

                }

            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {

                    JsonObjectRequest jsObjRequest2 = new JsonObjectRequest(Request.Method.GET, requestUrl, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {

                            Log.v("NO GOOGLE BOOK", "");
                            isbndbBook = new ISBNDbFinder().getBook(response);
                            book = new MergeBookSources().mergeBooks(amazonBook, null, isbndbBook);
                            SearchForBooks.this.onPostExecute();
                        }

                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Log.v("ONLY AMAZON", "");
                            book = new MergeBookSources().mergeBooks(amazonBook, null, null);
                            SearchForBooks.this.onPostExecute();
                        }
                    });

                    RequestQueue requestQueue2 = Volley.newRequestQueue(getActivity());
                    requestQueue2.add(jsObjRequest2);
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

