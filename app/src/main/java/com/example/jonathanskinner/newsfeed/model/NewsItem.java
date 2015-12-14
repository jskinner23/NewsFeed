package com.example.jonathanskinner.newsfeed.model;

/**
 * Created by jonathanskinner on 12/11/15.
 */

import android.content.ContentValues;

import com.example.jonathanskinner.newsfeed.resolver.NewsFeedContentResolverConstants.NewsItemResolver;

import java.util.Date;

public class NewsItem {

    private Long id;
    private String title;
    private String link;
    private String guid;
    private String category;
    private Date publishDate;
    private String description;

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(NewsItemResolver._ID, id);
        values.put(NewsItemResolver.TITLE, title);
        values.put(NewsItemResolver.LINK, link);
        values.put(NewsItemResolver.GUID, guid);
        values.put(NewsItemResolver.CATEGORY, category);
        values.put(NewsItemResolver.PUBLISH_DATE, getPublishDate()==null ? null : getPublishDate().getTime());
        values.put(NewsItemResolver.DESCRIPTION, description);
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
    public String getGuid() {
        return guid;
    }
    public void setGuid(String guid) {
        this.guid = guid;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public Date getPublishDate() {
        return publishDate;
    }
    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

}
