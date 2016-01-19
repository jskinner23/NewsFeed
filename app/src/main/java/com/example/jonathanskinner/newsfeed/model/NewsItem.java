package com.example.jonathanskinner.newsfeed.model;

/**
 * Created by jonathanskinner on 12/11/15.
 */

import android.content.ContentValues;

import com.example.jonathanskinner.newsfeed.resolver.NewsFeedContentResolverConstants.NewsItemResolver;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NewsItem {

    private Long id;
    private String title;
    private String link;
    private String author;
    private Date publishedDate;
    private String contentSnippet;
    private String content;
    private List<String> categories = new ArrayList<>();

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(NewsItemResolver._ID, id);
        values.put(NewsItemResolver.TITLE, title);
        values.put(NewsItemResolver.LINK, link);
        values.put(NewsItemResolver.AUTHOR, author);
        values.put(NewsItemResolver.PUBLISHED_DATE, getPublishedDate()==null ? null : getPublishedDate().getTime());
        values.put(NewsItemResolver.CONTENT_SNIPPET, contentSnippet);
        values.put(NewsItemResolver.CONTENT, content);
        values.put(NewsItemResolver.CATEGORIES, getCategoriesString());
        return values;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getLink() {
        return link;
    }
    public void setLink(String link) {
        this.link = link;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public Date getPublishedDate() {
        return publishedDate;
    }
    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }
    public String getContentSnippet() {
        return contentSnippet;
    }
    public void setContentSnippet(String contentSnippet) {
        this.contentSnippet = contentSnippet;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public List<String> getCategories() {
        return categories;
    }
    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    private String getCategoriesString() {
        StringBuilder categoriesStringBuilder = new StringBuilder();
        for (String category : this.categories) {
            if (category.equals(this.categories.get(this.categories.size()-1))) {
                categoriesStringBuilder.append(category);
            } else {
                categoriesStringBuilder.append(category + ",");
            }
        }
        return categoriesStringBuilder.toString();
    }

    @Override
    public String toString() {
        return "NewsItem{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", author='" + author + '\'' +
                ", publishedDate=" + publishedDate +
                ", contentSnippet='" + contentSnippet + '\'' +
                ", content='" + content + '\'' +
                ", categories=" + categories +
                '}';
    }
}
