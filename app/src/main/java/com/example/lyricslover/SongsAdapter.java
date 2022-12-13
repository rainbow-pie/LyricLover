package com.example.lyricslover;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.ViewHolder> {

    public SongsAdapterListener onClickListener;
    private List<Song> mSongsList;

    public  SongsAdapter(List<Song> songsList, SongsAdapterListener listener) {
        mSongsList = songsList;
        onClickListener = listener;
    }

    public interface SongsAdapterListener {
        void onClick(View v, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView authorTextView;
        public WebView imageView;
        public ImageButton lyricsButton;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.songName);
            authorTextView = itemView.findViewById(R.id.authorName);
            imageView = itemView.findViewById(R.id.imageView);
            lyricsButton = itemView.findViewById(R.id.lyricsButton);

            nameTextView.setOnClickListener(v -> onClickListener.onClick(v, getAdapterPosition()));
            lyricsButton.setOnClickListener(v -> onClickListener.onClick(v, getAdapterPosition()));
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View songView = inflater.inflate(R.layout.song_layout, parent, false);
        return new ViewHolder(songView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Song song = mSongsList.get(position);
        holder.nameTextView.setText(song.getName());
        holder.authorTextView.setText(song.getAuthor());

        WebView imageView = holder.imageView;
        imageView.getSettings().setLoadWithOverviewMode(true);
        imageView.getSettings().setUseWideViewPort(true);
        imageView.loadUrl(song.getImageURL());
    }

    @Override
    public int getItemCount() {
        return mSongsList.size();
    }
}
