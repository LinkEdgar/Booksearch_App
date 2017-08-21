package com.example.enduser.library;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class BookSearch extends AppCompatActivity {
    private ProgressBar progressBar;
    //use this view if the listview returns empty
    private TextView emptyListView;
    private ListView listView;
    private BookAdapter bookAdapter;
    //the original address
    private String originalWebaddress = "https://www.googleapis.com/books/v1/volumes?q=";
    //final string which we will use to query for our search
    private String finalQueryString;
    //controls the quantity of results
    private String searchLimitParamater = "&maxResult=10";
    //string builder uesed to create the final string
    private StringBuilder completeURLBuilder = new StringBuilder();
    //TODO add code to verify the seach parrameters
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.books_listview);
        emptyListView = (TextView) findViewById(R.id.book_not_found);
        listView = (ListView) findViewById(R.id.list_view);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        //creates a new blank adapter
        bookAdapter = new BookAdapter(BookSearch.this, new ArrayList<Book>());
        //sets the blank adapter to our listview
        listView.setAdapter(bookAdapter);

        listView.setEmptyView(emptyListView);
        //TODO make the empty case textview invisible until it's done loading
        //get the extra information from the intent and then use the stringBuilder global variable to build it to the URL we wish
        //to query
        String newString = getIntent().getStringExtra("search_params");
        finalQueryString = createFinalUrlString(newString);


        //Checks whether or not we have an internet connection
        ConnectivityManager cm =
                (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        //creates a boolean stating whether we are connected or not
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if(isConnected){
            //so it isn't displayed with the progress bar
            setEmptyListViewInvisible();
            BookASYNCTask bookASYNCTask = new BookASYNCTask();
            bookASYNCTask.execute(finalQueryString);
        }
    }
    public class BookASYNCTask extends AsyncTask<String,Void,List<Book>>{

        @Override
        protected List<Book> doInBackground(String... params) {
            List<Book> books = null;
            if(params[0] != null && params[0].length() > 1 ) {
                books = NetworkUtils.getBookList(params[0]);
            }
            return books;
        }

        @Override
        protected void onPostExecute(List<Book> books) {
            //TODO
            bookAdapter.clear();
            if(books != null ||!books.isEmpty()){
                bookAdapter.addAll(books);
                progressBar.setVisibility(View.INVISIBLE);
            }
        }
    }
    //takes in the intent extra String and creates the final string we will use to query
    public String createFinalUrlString(String string){
        completeURLBuilder.append(originalWebaddress).append(string).append(searchLimitParamater);
        return completeURLBuilder.toString();
    }
    public void setEmptyListViewInvisible(){
        emptyListView.setVisibility(View.INVISIBLE);
    }



}
