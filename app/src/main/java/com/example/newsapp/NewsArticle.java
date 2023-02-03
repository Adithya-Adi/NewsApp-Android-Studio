package com.example.newsapp;

public class NewsArticle {

    String title;
    String description;
    String link;
    String image_url;
    String pubDate;
    String content;

    public NewsArticle() {
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
        return link;
    }

    public void setUrl(String url) {
        this.link = url;
    }

    public String getUrlToImage() {
        return image_url;
    }

    public void setUrlToImage(String image_url) {
        this.image_url = image_url;
    }

    public String getPublishedAt() {
        return pubDate;
    }

    public void setPublishedAt(String publishedAt) {
        this.pubDate = publishedAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
