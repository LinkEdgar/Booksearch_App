package com.example.enduser.library;
import android.app.SearchManager;
import android.util.Log;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {
    private SearchView searchView;
    private ImageView firstImage;
    private ImageView secondImage;
    private ImageView thirdImage;
    //second category
    private ImageView firstImage2;
    private ImageView secondImage2;
    private ImageView thirdImage2;





    private StringBuilder searchText = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchView = (SearchView) findViewById(R.id.searchview);
        searchView.setQueryHint("Search for your favorite book");
        //TODO set the imageview for firstimage
        firstImage = (ImageView) findViewById(R.id.suggestion_1);
        Picasso.with(this).load("http://books.google.com/books/content?id=qJJmDQAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api").into(firstImage);

        secondImage = (ImageView) findViewById(R.id.suggestion_2);
        Picasso.with(this).load("https://books.google.com/books/content?id=X4Z-6_UjUK8C&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api").into(secondImage);

        thirdImage = (ImageView) findViewById(R.id.suggestion_3);
        Picasso.with(this).load("https://books.google.com/books/content?id=z2z_6hLoPmgC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api").into(thirdImage);
        //second category
        firstImage2 = (ImageView) findViewById(R.id.best_1);
        Picasso.with(this).load("https://books.google.com/books/content?id=tcSMCwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api").into(firstImage2);

        secondImage2 = (ImageView) findViewById(R.id.best_2);
        Picasso.with(this).load("https://books.google.com/books/content?id=4sQNAAAAYAAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api").into(secondImage2);

        thirdImage2 = (ImageView) findViewById(R.id.best_3);
        Picasso.with(this).load("https://books.google.com/books/content?id=PY40AQAAQBAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api").into(thirdImage2);



        searchView.setOnQueryTextListener(new OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(searchView.getQuery().toString() != null && searchView.getQuery().toString().length() > 0 ){
                    searchText.append(searchView.getQuery().toString());
                    //used for debugging
                    String searchQuery = searchView.getQuery().toString();
                    Intent intent = new Intent(MainActivity.this,BookSearch.class);
                    intent.putExtra("search_params",searchQuery);
                    startActivity(intent);
                    searchView.setQueryHint("Search for your favorite book");
                }
                else{
                    searchView.setQueryHint("Must Have at least one character");
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        /*
        searchEdittext = (EditText) findViewById(R.id.search_edittext);
        searchImageView = (ImageView) findViewById(R.id.search_imageview);
        //I might need to delete the onclick listener since I used hints in xml
        searchEdittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //searchEdittext.setTextColor(Color.BLACK);
            }
        });
        searchImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searchEdittext.getText().toString() != null && searchEdittext.getText().toString().length() > 0 ){
                    searchText.append(searchEdittext.getText().toString());
                    //used for debugging
                    String searchQuery = searchEdittext.getText().toString();
                    Intent intent = new Intent(MainActivity.this,BookSearch.class);
                    intent.putExtra("search_params",searchQuery);
                    startActivity(intent);
                    searchEdittext.setHint("Search for your favorite book");


                }
                else{
                    searchEdittext.setHint("Must have at least one character");
                }
            }
        });
        */
    }
}
