package com.example.mediastackapp.Data;

import java.io.Serializable;
import java.util.Objects;

public class Article implements Serializable {
    private String author;
    private String title;
    private String description;
    private String url;
    private String source;
    private String image;
    private String category;
    private String language;
    private String country;
    private String published_at;
    private boolean isFav;

    public Article(String author, String title, String description, String url, String source, String image, String category, String language, String country, String published_at) {
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.source = source;
        this.image = image;
        this.category = category;
        this.language = language;
        this.country = country;
        this.published_at = published_at;
        this.isFav = false;
    }

    public Article(){}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return url.equals(article.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPublished_at() {
        return published_at;
    }

    public void setPublished_at(String published_at) {
        this.published_at = published_at;
    }

    public boolean getIsFav() {
        return isFav;
    }

    public void setIsFav(boolean isFav) {
        this.isFav = isFav;
    }
}
