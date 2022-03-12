package com.example.mediastackapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.mediastackapp.Data.Article;
import com.example.mediastackapp.Adapters.ArticlesListAdapter;
import com.example.mediastackapp.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ArticleResultsActivity extends AppCompatActivity {
    private String category;
    public ArrayList<Article> articlesList;
    public HashSet<Article> articlesSet;
    private RecyclerView mRecyclerView;
    public static ArticlesListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String access_key = "478035183f27e8a1b2120b89fc217077";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_results);
        Intent intent = getIntent();
        category=(String) intent.getExtras().getSerializable("category");
        Objects.requireNonNull(getSupportActionBar()).setTitle(category.toUpperCase());  // provide compatibility to all the versions

        mRecyclerView = findViewById(R.id.article_recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        articlesList = new ArrayList<Article>();
        articlesSet=new HashSet<Article>();
        OkHttpClient client = new OkHttpClient();

        String url = "http://api.mediastack.com/v1/news?access_key=" + access_key+"&categories="+category+"&languages=en";

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String myResponse = response.body().string();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {

                                JSONArray articlesData = new JSONObject(myResponse).getJSONArray("data");
                                if (articlesData != null) {
                                    for (int i=0;i<articlesData.length();i++){
                                        JSONObject jsonobject = articlesData.getJSONObject(i);
                                        Article article=new Article();

                                        article.setAuthor(jsonobject.getString("author"));
                                        article.setCategory(jsonobject.getString("category"));
                                        article.setCountry(jsonobject.getString("country"));
                                        article.setDescription(jsonobject.getString("description"));
                                        article.setImage(jsonobject.getString("image"));
                                        article.setLanguage(jsonobject.getString("language"));
                                        article.setPublished_at(jsonobject.getString("published_at"));
                                        article.setTitle(jsonobject.getString("title"));
                                        article.setSource(jsonobject.getString("source"));
                                        article.setUrl(jsonobject.getString("url"));

                                        articlesSet.add(article);

                                    }
                                    articlesList.addAll(articlesSet);
                                    mAdapter = new ArticlesListAdapter(articlesList);
                                    mAdapter.setOnItemClickListener(new ArticlesListAdapter.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(int position) {
                                            Intent intent = new Intent(ArticleResultsActivity.this, ArticleActivity.class);
                                            intent.putExtra("article", articlesList.get(position));
                                            startActivity(intent);

                                        }
                                    });
                                    mRecyclerView.setLayoutManager(mLayoutManager);
                                    mRecyclerView.setAdapter(mAdapter);

                                }

                            } catch (Throwable t) {
                                Log.e("My App", "Could not parse malformed JSON: \"" + myResponse + "\"");
                            }


                        }
                    });

                }
            }
        });
    }
}