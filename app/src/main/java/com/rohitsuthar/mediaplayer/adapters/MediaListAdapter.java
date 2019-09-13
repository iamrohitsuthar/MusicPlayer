package com.rohitsuthar.mediaplayer.adapters;

import android.content.ContentUris;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.rohitsuthar.mediaplayer.R;
import com.rohitsuthar.mediaplayer.models.MediaListModel;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

public class MediaListAdapter extends RecyclerView.Adapter<MediaListAdapter.MyViewHolder> {
    private ArrayList<MediaListModel> mediaListModelArrayList;
    private Context context;
    private MediaPlayer mediaPlayer;

    public MediaListAdapter(Context context, ArrayList<MediaListModel> mediaListModels) {
        this.context = context;
        this.mediaListModelArrayList = mediaListModels;
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_model,parent,false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final MediaListModel mediaListModel = mediaListModelArrayList.get(position);
        holder.tvName.setText(mediaListModel.getName());

        Uri sArtworkUri = Uri
                .parse("content://media/external/audio/albumart");
        Uri albumArtUri = ContentUris.withAppendedId(sArtworkUri, mediaListModel.getAlbumid());
        Picasso.get().load(albumArtUri).placeholder(R.drawable.ic_compact_disc).error(R.drawable.ic_compact_disc).into(holder.imageView);

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying())
                    mediaPlayer.reset();
                try {
                    mediaPlayer.setDataSource(mediaListModel.getUrl());
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mediaListModelArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private ConstraintLayout constraintLayout;
        private ImageView imageView;
        MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_media_name);
            constraintLayout = itemView.findViewById(R.id.constraint_layout);
            imageView = itemView.findViewById(R.id.imageView2);
        }
    }
}
