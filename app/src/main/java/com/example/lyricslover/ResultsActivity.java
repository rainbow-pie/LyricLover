package com.example.lyricslover;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ResultsActivity extends AppCompatActivity {

    public static final String ARG_PARAM = "query";

    private List<Song> mSongsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        ImageView noDataView = findViewById(R.id.noDataImage);

        String songName = getIntent().getStringExtra(ARG_PARAM);

        CompletableFuture<String> data = null;
        try {
            data = GeniusAPI.searchSongsByName(songName);
            parseSongs(data.get());
        } catch (JSONException | IOException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }


        if (mSongsList.size() != 0) {

            SongsAdapter songsAdapter = new SongsAdapter(mSongsList, (v, position) -> {
                Intent intent = new Intent(ResultsActivity.this, LyricsActivity.class);
                Song song = mSongsList.get(position);
                intent.putExtra(LyricsActivity.ARG_PARAM, song);
                startActivity(intent);
            });

            recyclerView.setAdapter(songsAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

        } else
            noDataView.setImageResource(R.drawable.nodata);
    }

    private void parseSongs(String json) throws JSONException {
        JSONObject object = new JSONObject(json);
        int status = object.getJSONObject("meta").getInt("status");
        System.out.println("status: " + status);

        JSONArray hitsArray = object.getJSONObject("response").getJSONArray("hits");

        for (int i = 0; i < hitsArray.length(); ++i)
        {
            JSONObject hit = hitsArray.getJSONObject(i);
            if (hit.getString("type").equals("song")) {
                JSONObject songProperties = hit.getJSONObject("result");
                Song song = getSongFromProperties(songProperties);
                mSongsList.add(song);
            }
        }
    }

    private Song getSongFromProperties(JSONObject props) throws JSONException {
        int id = props.getInt("id");
        String name = props.getString("title");
        String author = props.getString("artist_names");
        String imageURL = props.getString("song_art_image_thumbnail_url");

        return new Song(name, author, imageURL, id);
    }
}