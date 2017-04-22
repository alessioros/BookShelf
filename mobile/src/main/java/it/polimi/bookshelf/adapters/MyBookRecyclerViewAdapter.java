package it.polimi.bookshelf.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import it.polimi.bookshelf.R;
import it.polimi.bookshelf.data.DataHandler;
import it.polimi.bookshelf.model.Book;

public class MyBookRecyclerViewAdapter extends RecyclerView.Adapter<MyBookRecyclerViewAdapter.ViewHolder> {

    private final List<Book> mBooks;
    private DataHandler dH;
    private Context context;

    public MyBookRecyclerViewAdapter(List<Book> books, Context context) {
        mBooks = books;
        dH = new DataHandler(context);
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_book, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mBooks.get(position);

        if(holder.mItem.getImgUrl() != null)
            Picasso.with(context).load(holder.mItem.getImgUrl()).into(holder.mBookCover);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


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
                dialogMessage.setText("Delete book: "+holder.mItem.getTitle()+"?");

                builder.setPositiveButton(holder.mView.getContext().getResources().getString(R.string.alert_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dH.getDatabaseHandler().deleteBook(holder.mItem.getISBN());
                        mBooks.remove(holder.getAdapterPosition());
                        notifyItemRemoved(holder.getAdapterPosition());
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
        return mBooks.size();
    }

    @SuppressWarnings("WeakerAccess")
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mBookCover;

        public Book mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mBookCover = (ImageView) view.findViewById(R.id.bookCover);

        }
    }
}
