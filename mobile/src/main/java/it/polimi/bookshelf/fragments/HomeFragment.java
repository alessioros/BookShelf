package it.polimi.bookshelf.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import it.polimi.bookshelf.R;
import it.polimi.bookshelf.activities.VerticalOrientationCA;

public class HomeFragment extends Fragment {

    private boolean onlySCan = false;
    public HomeFragment() {
        // Required empty public constructor
    }

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

        if(onlySCan) scanBook();
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void scanBook(){
        FragmentIntentIntegrator scanIntegrator = new FragmentIntentIntegrator(HomeFragment.this);
        scanIntegrator.setCaptureActivity(VerticalOrientationCA.class);
        scanIntegrator.setPrompt(getResources().getString(R.string.scan_book_prompt));
        scanIntegrator.initiateScan();
    }
}
