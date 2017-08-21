package com.example.enduser.library;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by EndUser on 8/18/2017.
 */

public class BookAdapter extends ArrayAdapter<Book> {


    public BookAdapter(Activity context, ArrayList<Book> list){
        super(context, 0, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listView = convertView;
        //if the listview is empty we inflate the resources
        if(listView == null){
            listView = LayoutInflater.from(getContext()).inflate(R.layout.book_list,parent,false);
        }
        //we need a reference to the current book
        Book currentBook = getItem(position);
        //title textview
        TextView title = (TextView) listView.findViewById(R.id.titleofBook);
        title.setText(currentBook.getTitle());

        TextView description = (TextView) listView.findViewById(R.id.description);
        description.setText(currentBook.getDescription());

        TextView author = (TextView) listView.findViewById(R.id.author);
        author.setText(currentBook.getAuthor());

        ImageView thumbNail = (ImageView) listView.findViewById(R.id.thumbnail);
        Picasso.with(listView.getContext()).load(currentBook.getImageURl()).into(thumbNail);





        return listView;
    }
}
