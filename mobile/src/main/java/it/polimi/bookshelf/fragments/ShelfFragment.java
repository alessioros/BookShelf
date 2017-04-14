package it.polimi.bookshelf.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import it.polimi.bookshelf.R;
import it.polimi.bookshelf.activities.VerticalOrientationCA;
import it.polimi.bookshelf.data.DataHandler;
import it.polimi.bookshelf.data.DatabaseHandler;
import it.polimi.bookshelf.model.Shelf;

public class ShelfFragment extends Fragment {

    private ArrayList<Shelf> mShelves;

    public ShelfFragment() {
    }

    public static ShelfFragment newInstance() {

        return new ShelfFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shelf_list, container, false);

        mShelves = loadShelves();
        Log.v("SHELF LIST SIZE: ", ""+mShelves.size() );

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Enter shelf name");

                DataHandler dh = new DataHandler(getActivity().getApplicationContext());
                final DatabaseHandler dbH = dh.getDatabaseHandler();

                View promptsView = LayoutInflater.from(getActivity()).inflate(R.layout.custom_settings_dialog, container, false);
                builder.setView(promptsView);

                final EditText dialogEdit = (EditText) promptsView.findViewById(R.id.dialog_edittxt);
                dialogEdit.setInputType(InputType.TYPE_CLASS_TEXT);

                builder.setPositiveButton(getResources().getString(R.string.alert_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String shelfName = dialogEdit.getText().toString();

                        if (!shelfName.equals("")){
                            dbH.insertShelf(new Shelf(shelfName, 0));

                        }else{
                            Toast toast = Toast.makeText(getActivity(), "PLEASE INSERT A VALID NAME", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                });
                builder.setNegativeButton(getResources().getString(R.string.alert_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();

            }
        });

        // Set the adapter
        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        recyclerView.setAdapter(new MyShelfRecyclerViewAdapter(mShelves));

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

    public ArrayList<Shelf> loadShelves() {

        DataHandler dh = new DataHandler(getActivity().getApplicationContext());
        return (ArrayList<Shelf>) dh.getDatabaseHandler().getShelfList();
    }
}
