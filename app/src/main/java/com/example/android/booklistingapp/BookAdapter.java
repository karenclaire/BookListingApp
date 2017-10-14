package com.example.android.booklistingapp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by karenulmer on 7/1/2017.
 */

public class BookAdapter extends ArrayAdapter<Book> {

    static class ViewHolder {
        TextView title;
        TextView author;
        TextView category;
        TextView publicationDate;


    }

    public BookAdapter(Activity context, ArrayList<Book> books) {
        super(context, 0, books);
    }

    /**
     * @param position    The position in the list of data that should be displayed in the
     *                    list item view.
     * @param convertView The recycled view to populate.
     * @param parent      The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;

        final Book currentBook = getItem(position);
        ViewHolder holder;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.book_list_view, parent, false);

            holder = new ViewHolder();
            holder.title = (TextView) listItemView.findViewById(R.id.title);
            holder.author = (TextView) listItemView.findViewById(R.id.author);
            holder.category = (TextView) listItemView.findViewById(R.id.category);
            holder.publicationDate = (TextView) listItemView.findViewById(R.id.publication_date);

            listItemView.setTag(holder);

        } else {
            holder = (ViewHolder) listItemView.getTag();
        }

        holder.title.setText(currentBook.getTitle());
        holder.author.setText(currentBook.getAuthor());
        holder.category.setText(currentBook.getCategory());
        holder.publicationDate.setText(currentBook.getDate());


        return listItemView;

    }


}
