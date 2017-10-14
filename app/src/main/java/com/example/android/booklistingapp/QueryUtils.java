package com.example.android.booklistingapp;

import android.text.TextUtils;
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

import static com.example.android.booklistingapp.MainActivity.LOG_TAG;

/**
 * Created by karenulmer on 7/1/2017.
 */

   /**
     * Helper methods related to requesting and receiving book data from Google Books API.
     */

    public final class QueryUtils {

        /**
         * Create a private constructor because no one should ever create a {@link QueryUtils} object.
         * This class is only meant to hold static variables and methods, which can be accessed
         * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
         */
        private QueryUtils() {
        }


        /**
         * Returns new URL object from the given string URL.
         */
        private static URL createUrl(String stringUrl) {
            URL url = null;
            try {
                url = new URL(stringUrl);
            } catch (MalformedURLException e) {
                Log.e(LOG_TAG, "Problem building the URL ", e);
            }
            return url;
        }
        /**
         * Return an {@link Event} object by parsing out information
         * about the first book from the input bookJSON string.
         */
        /**
         * Make an HTTP request to the given URL and return a String as the response.
         */
        private static String makeHttpRequest(URL url) throws IOException {
            String jsonResponse = "";
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(15000 /* milliseconds */);
                urlConnection.connect();
                // if request is successful then read input stream and parse the response
                if (urlConnection.getResponseCode() == 200) {
                    inputStream = urlConnection.getInputStream();
                    jsonResponse = readFromStream(inputStream);
                }else {
                    Log.e(LOG_TAG, "Error response code"+ urlConnection.getResponseCode());
                }
            } catch (IOException e) {
                Log.e(LOG_TAG, "Cannot get the book JSON Result.", e);
                // TODO: Handle the exception
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (inputStream != null) {
                    // function must handle java.io.IOException here
                    inputStream.close();
                }
            }
            return jsonResponse;
        }

        /**
         * Convert the {@link InputStream} into a String which contains the
         * whole JSON response from the server.
         */
        private static String readFromStream(InputStream inputStream) throws IOException {
            StringBuilder output = new StringBuilder();
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }
            }
            return output.toString();
        }

        /**
         * Return a list of {@link Book} objects that has been built up from
         * parsing the given JSON response.
         */
        private static List<Book> extractFeatureFromJson(String bookJSON) {
            // If the JSON string is empty or null, then return early.
            if (TextUtils.isEmpty(bookJSON)) {
                return null;
            }

            // Create an empty ArrayList that we can start adding books to
            List<Book> books = new ArrayList<>();

            // Try to parse the JSON response string. If there's a problem with the way the JSON
            // is formatted, a JSONException exception object will be thrown.
            // Catch the exception so the app doesn't crash, and print the error message to the logs.
            try {

                // Create a JSONObject from the JSON response string
                JSONObject baseJsonResponse = new JSONObject(bookJSON);

                // Extract the JSONArray associated with the key called "items",
                // which represents a list of items (or books).
                JSONArray bookArray = baseJsonResponse.getJSONArray("items");

                // For each book in the bookArray, create an {@link Book} object
                for (int i = 0; i < bookArray.length(); i++) {

                    // Get a single book at position i within the list of books
                    JSONObject currentBook = bookArray.getJSONObject(i);

                    // For a given book extract the JSONObject associated with the
                    // key called "volumeInfo", which represents a list of all properties
                    // for that earthquake.
                    JSONObject volumeInfo = currentBook.getJSONObject("volumeInfo");

                    // Extract the value for the key called "title"
                    String title = volumeInfo.getString("title");

                    String author = "N/A";
                    if (volumeInfo.has("authors")){
                    // Extract the value for the key called "authors"
                       JSONArray authorsArray = volumeInfo.getJSONArray("authors");
                       author = authorsArray.toString();
                    }


                    String category = "N/A";
                    if (volumeInfo.has("categories")){
                        // Extract the value for the key called "categories"
                        JSONArray categoryArray = volumeInfo.getJSONArray("categories");
                        category = categoryArray.toString();
                    }


                    // Extract the value for the key called "published date"
                    String publicationDate = volumeInfo.getString("publishedDate");

                    // Extract the value for the key called Book URL
                    String bookUrl = volumeInfo.getString("infoLink");


                    // Create a new {@link Book} object with the title, author and publication date
                    // and url from the JSON response.
                    Book book = new Book(title, author, category, publicationDate, bookUrl);

                    // Add the new {@link Earthquake} to the list of earthquakes.
                    books.add(book);
                }

            } catch (JSONException e) {
                // If an error is thrown when executing any of the above statements in the "try" block,
                // catch the exception here, so the app doesn't crash. Print a log message
                // with the message from the exception.
                Log.e("QueryUtils", "Problem parsing the books JSON results", e);
            }

            // Return the list of earthquakes
            return books;
        }



        /**
         * Query the Google  dataset and return a list of {@link Book} objects.
         */
        public static List<Book> fetchBookData(String requestUrl) {
            // Create URL object
            URL url = createUrl(requestUrl);

            // Test for loading indicator
            //try {
            //    Thread.sleep(2000);
            // } catch (InterruptedException e) {
            //    e.printStackTrace();
            //}

            // Perform HTTP request to the URL and receive a JSON response back
            String jsonResponse = null;
            try {
                jsonResponse = makeHttpRequest(url);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Problem making the HTTP request.", e);
            }

            // Extract relevant fields from the JSON response and create a list of {@link Book}s
            //Return the list of {@link Book}s
            return extractFeatureFromJson(jsonResponse);

        }

    }

