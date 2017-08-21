package com.example.enduser.library;

import android.icu.util.ULocale;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by EndUser on 8/17/2017.
 */

public final class NetworkUtils {
    private NetworkUtils(){

    }
    // this method will read in from the input stream and create a new string that represents the JSON obcject
    //throws IOException due to the bufferedReader
    public static String readInputStream(InputStream input) throws IOException{
        //used to create the string we need for our JSON object
        StringBuilder jsonBuilder = new StringBuilder();
        if(input != null) {
            //this tellts the inputStreamReader what kind of raw data we are getting and how to properly convert it
            InputStreamReader inputStreamReader = new InputStreamReader(input, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            //this string will be appended to our StringBuilder
            String line = bufferedReader.readLine();
            while (line != null) {
                jsonBuilder.append(line);
                line = bufferedReader.readLine();
            }
        }
        else{
            Log.e("readINputString", "null input stream");
        }

        return jsonBuilder.toString();
    }

    //creates a URL from a string
    public static URL createURL(String stringURL){
        URL url = null;
        try{
            url = new URL(stringURL);
        }
        catch(MalformedURLException e){
            Log.e("createURLinNetworkUtils", "Couldn't create URL");
        }
        return url;
    }

    //this methods handles all of the HTTP requests logic and return a string of the soon to be JSON object which
    //we'll use to extract the information we need
    public static String makeHTTPRequest(String urlString){
        String JSONString = "";
        //create a url from the user requested search
        URL connectionUrl = createURL(urlString);
        //Used to establish a connection. Must be done in a try catch block
        HttpURLConnection connection = null;
        //Will be used to extract the JSON string
        InputStream inputStream = null;
        try{
            connection = (HttpURLConnection) connectionUrl.openConnection();
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(10000);
            connection.setRequestMethod("GET");
            connection.connect();
            //if the connection is succesful we will extract the information
            if(connection.getResponseCode() == 200){
                inputStream = connection.getInputStream();
                //used the readInputStream method which will format the input stream for us
                JSONString = readInputStream(inputStream);
            }

        }
        catch(IOException e){
            Log.e("MakeHTTPRequest", "Could not make a conncetion ");
        }
        finally {
            if(connection != null){
                connection.disconnect();
            }
            if( inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Log.e("makeHTTPRequest", "Could not close the input stream");
                }
            }

        }

        return JSONString;
    }

    //extracts the information we need from the JSON string onject and updates our listview
    public static ArrayList<Book> extractJSONInfo( String jsonString){
        ArrayList<Book> bookArrayList = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray items = jsonObject.getJSONArray("items");
            for(int i =0 ; i < items.length(); i++){
                JSONObject jsonIteration = items.getJSONObject(i);
                JSONObject currentJSONObject = jsonIteration.getJSONObject("volumeInfo");
                JSONObject imageObejct = currentJSONObject.getJSONObject("imageLinks");
                JSONArray jsonArray = currentJSONObject.getJSONArray("authors");

                String stringUrl = imageObejct.getString("smallThumbnail");
                String title = currentJSONObject.getString("title");
                String author = jsonArray.getString(0);
                String description;
                if(currentJSONObject.has("description")) {
                    description = currentJSONObject.getString("description");
                }
                else{
                    description = "No description available";
                }
                //may need a different to make a Json array to get this data
                //String thumbNail = currentJSONObject.getString("imageLinks");
                Book insertionBook = new Book(title,author,description,stringUrl);
                bookArrayList.add(insertionBook);
            }
        } catch (JSONException e) {
            Log.e("extractJSONINFO()", "Couldn't create a new JSONObject");
        }
        int itemSize = bookArrayList.size();
        if(itemSize == 3){
            Log.e("Arraylist size", "the size is ten");
        }

        return bookArrayList;
    }
    public static List<Book> getBookList(String stringUrl){
        //make an HTTP connections to retrieve the desired data
        //takes in the stringURL to make connect to the web and create a JSON string
        String JSONString= makeHTTPRequest(stringUrl);
        List books = extractJSONInfo(JSONString);

        return books;
    }
}
