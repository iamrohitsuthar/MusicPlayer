package com.rohitsuthar.mediaplayer.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Pair;
import android.widget.ImageView;
import android.widget.Toast;

import com.rohitsuthar.mediaplayer.R;
import com.rohitsuthar.mediaplayer.adapters.MediaListAdapter;
import com.rohitsuthar.mediaplayer.models.MediaListModel;
import com.rohitsuthar.mediaplayer.others.Msg;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class FetchActivity extends AppCompatActivity {

    private Context context;
    private RecyclerView recyclerView;
    private MediaListAdapter mediaListAdapter;
    private MediaListModel mediaListModel;
    private ArrayList<MediaListModel> mediaListModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch);
        initialize();
        readMediaFiles();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.addItemDecoration(new DividerItemDecoration(context,DividerItemDecoration.VERTICAL));
        mediaListAdapter = new MediaListAdapter(context,mediaListModelArrayList);
        recyclerView.setAdapter(mediaListAdapter);
    }

    private void initialize() {
        context = FetchActivity.this;
        mediaListModelArrayList = new ArrayList<>();
        recyclerView = findViewById(R.id.rv_list);
    }

    private void readMediaFiles() {
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String music  = MediaStore.Audio.Media.IS_MUSIC + " != 0";
        String sort  = MediaStore.Audio.Media.ALBUM + " ASC";
        String[] projection = {MediaStore.Audio.Albums.ALBUM_ID, MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.DATA};
        Cursor cursor = contentResolver.query(uri,projection,music,null,sort);

        if(cursor != null) {
            Msg.log(String.valueOf(cursor.getCount()));
            if(cursor.getCount() > 0) {
                while(cursor.moveToNext()) {
                    String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                    String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                    String albumid = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ID));
                    mediaListModel = new MediaListModel(Long.parseLong(albumid),path,title);
                    mediaListModelArrayList.add(mediaListModel);
                }
            }
        }

    }
}
