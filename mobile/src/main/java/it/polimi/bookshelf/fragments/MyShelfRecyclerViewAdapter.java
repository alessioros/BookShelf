package it.polimi.bookshelf.fragments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import it.polimi.bookshelf.R;
import it.polimi.bookshelf.data.DataHandler;
import it.polimi.bookshelf.model.Shelf;

import java.util.List;

public class MyShelfRecyclerViewAdapter extends RecyclerView.Adapter<MyShelfRecyclerViewAdapter.ViewHolder> {

    private final List<Shelf> mShelves;
    private DataHandler dH;

    public MyShelfRecyclerViewAdapter(List<Shelf> shelves, Context context) {
        mShelves = shelves;
        dH = new DataHandler(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_shelf, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mShelves.get(position);
        holder.mIdView.setText(holder.mItem.getName());
        holder.mContentView.setText(String.valueOf(holder.mItem.getBookCount()));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dH.getDatabaseHandler().deleteShelf(holder.mItem.getName());
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mShelves.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public Shelf mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
        }
    }
}
