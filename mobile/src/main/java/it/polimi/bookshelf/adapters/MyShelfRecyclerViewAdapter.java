package it.polimi.bookshelf.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import it.polimi.bookshelf.R;
import it.polimi.bookshelf.activities.ShelfActivity;
import it.polimi.bookshelf.data.DataHandler;
import it.polimi.bookshelf.model.Book;
import it.polimi.bookshelf.model.Shelf;

public class MyShelfRecyclerViewAdapter extends RecyclerView.Adapter<MyShelfRecyclerViewAdapter.ViewHolder> {

    private final List<Shelf> mShelves;
    private DataHandler dH;
    private Activity activity;

    public MyShelfRecyclerViewAdapter(List<Shelf> shelves, Context context, Activity activity) {
        mShelves = shelves;
        this.activity = activity;
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
        holder.mShelfName.setText(holder.mItem.getName());

        holder.mBookCount.setText(String.valueOf(holder.mItem.getBookCount()));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity, ShelfActivity.class);
                intent.putExtra("shelf_name", holder.mItem.getName());
                activity.startActivity(intent);
            }
        });


        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(holder.mView.getContext());
                View promptsView = LayoutInflater.from(holder.mView.getContext()).inflate(R.layout.custom_confirm, null);
                builder.setView(promptsView);
                builder.setTitle("Confirm Delete");

                final TextView dialogMessage = (TextView) promptsView.findViewById(R.id.alert_message);
                dialogMessage.setText("Delete shelf: " + holder.mItem.getName() + "?");

                builder.setPositiveButton(holder.mView.getContext().getResources().getString(R.string.alert_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {
                            List<Book> shelfBooks = dH.getDatabaseHandler().getBookList(holder.mItem.getName());
                            dH.getDatabaseHandler().deleteShelf(holder.mItem.getName());
                            mShelves.remove(holder.getAdapterPosition());
                            notifyItemRemoved(holder.getAdapterPosition());

                            for (Book book : shelfBooks) {
                                dH.getDatabaseHandler().deleteBook(book.getISBN());
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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

    @SuppressWarnings("WeakerAccess")
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
