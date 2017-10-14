package com.example.android.booklistingapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by karenulmer on 7/1/2017.
 */

public class Book implements Parcelable {

    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    /**
     * Book title
     */
    private String mTitle;
    /**
     * Book author
     */
    private String mAuthor;
    /**
     * Publish date
     */
    private String mDate;

    /**
     * Book URL
     */
    private String mBookUrl;

    /**
     * Book Category
     */
    private String mCategory;

    /**
     * Create a new Book object from five inputs
     *
     * @param title           is the title of the book
     * @param author          is the author of the book
     * @param category        is the category of the book
     * @param publicationDate is the publication date of the book
     * @param bookUrl         is the URL of the book
     */
    public Book(String title, String author, String category, String publicationDate, String bookUrl) {
        mTitle = title;
        mAuthor = author;
        mCategory = category;
        mDate = publicationDate;
        mBookUrl = bookUrl;

    }

    protected Book(Parcel in) {
        mTitle = in.readString();
        mAuthor = in.readString();
        mDate = in.readString();
        mBookUrl = in.readString();
    }


    /**
     * Get the title of the book
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * Get the author of the book
     */
    public String getAuthor() {
        return mAuthor;
    }

    /**
     * Get category of the book
     */
    public String getCategory() {
        return mCategory;
    }

    /**
     * Get the publication date of the book
     */
    public String getDate() {
        return mDate;
    }

    /**
     * Get the URL of the book
     */
    public String getBookUrl() {
        return mBookUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(mAuthor);
        dest.writeString(mDate);

    }


}
