package com.andrewxa.greendaoretrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.andrewxa.greendaoretrofit.adapter.NewsAdapter;
import com.andrewxa.greendaoretrofit.api.ApiClient;
import com.andrewxa.greendaoretrofit.api.ApiInterface;
import com.andrewxa.greendaoretrofit.bd.DaoSession;
import com.andrewxa.greendaoretrofit.bd.NewsItem;
import com.andrewxa.greendaoretrofit.bd.NewsItemDao;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private DaoSession mDaoSession = null;
    private NewsItemDao mNewsItemDao = null;
    private RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.news_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mDaoSession = ((App)getApplication()).getDaoSession();
        mNewsItemDao = mDaoSession.getNewsItemDao();

        /*mNewsItemDao.deleteAll();*/

        fetchNews();
        setAdapter();

    }


    private void setAdapter() {

        // loading data from database by loadAll method
        List<NewsItem> newsItemList = mNewsItemDao.loadAll();


        // Creating adapter for recyclerview
        NewsAdapter newsAdapter = new NewsAdapter(newsItemList);
        mRecyclerView.setAdapter(newsAdapter);
    }


    private void fetchNews() {
        ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);

        Call<List<NewsItem>> call = apiInterface.getLatestNews();

        call.enqueue(new Callback<List<NewsItem>>() {
            @Override
            public void onResponse(Call<List<NewsItem>> call, Response<List<NewsItem>> response) {
                List<NewsItem> news = response.body();

                mNewsItemDao.insertOrReplaceInTx(news);

                setAdapter();


            }

            @Override
            public void onFailure(Call<List<NewsItem>> call, Throwable t) {

            }
        });
    }
}
