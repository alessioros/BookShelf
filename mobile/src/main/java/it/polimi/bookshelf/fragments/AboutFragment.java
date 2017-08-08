package it.polimi.bookshelf.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import it.polimi.bookshelf.R;
import it.polimi.bookshelf.data.DataHandler;
import it.polimi.bookshelf.data.StorageHandler;

public class AboutFragment extends Fragment {

    StorageHandler storageHandler;

    public AboutFragment() {
        // Required empty public constructor
    }

    /*
     * instantiate AboutFragment
     *
     */
    public static AboutFragment newInstance() {

        return new AboutFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        TextView aboutDesc = (TextView) view.findViewById(R.id.about_desc);

        try {
            storageHandler = new DataHandler(getActivity()).getStorageHandler();
            String desc = storageHandler.readfile("about.txt", false);
            aboutDesc.setText(desc);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
