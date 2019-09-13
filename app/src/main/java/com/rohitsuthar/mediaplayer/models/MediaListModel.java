package com.rohitsuthar.mediaplayer.models;

import android.net.Uri;

public class MediaListModel {
    private long albumid;
    private String url;
    private String name;

    public MediaListModel(long albumid, String url, String name) {
        this.albumid = albumid;
        this.url = url;
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public long getAlbumid() {
        return albumid;
    }
}
