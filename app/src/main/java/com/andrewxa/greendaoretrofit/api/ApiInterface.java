package com.andrewxa.greendaoretrofit.api;

import com.andrewxa.greendaoretrofit.bd.NewsItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("top-headlines?sources=techcrunch&apiKey=801d8c3455fe475bbab9f7e4c6944aa3")
    Call<List<NewsItem>> getLatestNews();
}
