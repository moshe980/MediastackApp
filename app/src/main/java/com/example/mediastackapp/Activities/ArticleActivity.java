package com.example.mediastackapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.mediastackapp.Data.Article;
import com.example.mediastackapp.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class ArticleActivity extends AppCompatActivity {
    private TextView authorTV;
    private TextView titleTV;
    private TextView descriptionTV;
    private TextView urlTV;
    private TextView sourceTV;
    private ImageView imageView;
    private TextView categoryTV;
    private TextView languageTV;
    private TextView countryTV;
    private TextView published_atTV;
    private Article currentArticle;
    private ToggleButton favBtn;
    private SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_article);
        Intent intent = getIntent();
        currentArticle = (Article) intent.getExtras().getSerializable("article");

        sp = getSharedPreferences("favorites", MODE_PRIVATE);

        authorTV = findViewById(R.id.authorTV);
        titleTV = findViewById(R.id.titleTV);
        descriptionTV = findViewById(R.id.descriptionTV);
        urlTV = findViewById(R.id.urlTV);
        sourceTV = findViewById(R.id.sourceTV);
        imageView = findViewById(R.id.imageId);
        categoryTV = findViewById(R.id.categoryTV);
        languageTV = findViewById(R.id.languageTV);
        countryTV = findViewById(R.id.countryTV);
        published_atTV = findViewById(R.id.published_atTV);
        favBtn = findViewById(R.id.favBtn);

        Picasso.get()
                .load(currentArticle.getImage())
                .fit()
                .placeholder(R.drawable.ic_launcher_foreground)
                .centerCrop()
                .into(imageView);

        authorTV.setText(currentArticle.getAuthor());
        titleTV.setText(currentArticle.getTitle());
        descriptionTV.setText(currentArticle.getDescription());
        urlTV.setText(currentArticle.getUrl());
        sourceTV.setText(currentArticle.getSource());
        categoryTV.setText(currentArticle.getCategory());
        languageTV.setText(currentArticle.getLanguage());
        countryTV.setText(currentArticle.getCountry());
        published_atTV.setText(currentArticle.getPublished_at());
        favBtn.setChecked(currentArticle.getIsFav());

        favBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    SharedPreferences.Editor editor = sp.edit();
                    Gson gson = new Gson();
                    currentArticle.setIsFav(true);
                    String json = gson.toJson(currentArticle);

                    String serializedObject = sp.getString("a", null);
                    if (serializedObject != null) {
                        serializedObject += json;
                        serializedObject += "&";

                        editor.putString("a", serializedObject);

                    } else {
                        editor.putString("a", json);
                    }
                    editor.apply();

                    Toast.makeText(ArticleActivity.this, "Add to favorites", Toast.LENGTH_SHORT).show();
                } else {//remove from favorites
                    String serializedObject = sp.getString("a", null);
                    if (serializedObject != null) {
                        String[] separted = serializedObject.split("&");
                        SharedPreferences.Editor editor = sp.edit();
                        Gson gson = new Gson();
                        StringBuilder favoritesSb=new StringBuilder();
                        for (int i = separted.length - 1; i >= 0; i--) {
                            Article article = gson.fromJson(separted[i], Article.class);
                            if (!article.getUrl().equals(currentArticle.getUrl())) {
                                favoritesSb.append(separted[i]);
                                favoritesSb.append("&");
                            }

                        }
                        editor.putString("a", favoritesSb.toString());
                        editor.apply();

                        Toast.makeText(ArticleActivity.this, "Remove from favorites", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });

    }
}
