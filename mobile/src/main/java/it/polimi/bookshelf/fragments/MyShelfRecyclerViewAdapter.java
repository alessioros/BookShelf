package it.polimi.bookshelf.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cn.nekocode.badge.BadgeDrawable;
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
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mShelves.get(position);
        holder.mShelfName.setText(holder.mItem.getName());

        holder.mBookCount.setText(String.valueOf(holder.mItem.getBookCount()));



        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(holder.mView.getContext());
                View promptsView = LayoutInflater.from(holder.mView.getContext()).inflate(R.layout.custom_confirm, null);
                builder.setView(promptsView);
                builder.setTitle("Confirm Delete");

                final TextView dialogMessage = (TextView) promptsView.findViewById(R.id.alert_message);
                dialogMessage.setText("Delete shelf: "+holder.mItem.getName()+"?");

                builder.setPositiveButton(holder.mView.getContext().getResources().getString(R.string.alert_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dH.getDatabaseHandler().deleteShelf(holder.mItem.getName());
                        mShelves.remove(position);
                        notifyItemRemoved(position);
                    }
                });
                builder.setNegativeButton(holder.mView.getContext().getString(R.string.alert_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mShelves.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mShelfName;
        public final TextView mBookCount;
        public Shelf mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mShelfName = (TextView) view.findViewById(R.id.shelfName);
            mBookCount = (TextView) view.findViewById(R.id.bookCountBadge);
        }
    }
}
