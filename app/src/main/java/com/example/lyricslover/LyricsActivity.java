package com.example.lyricslover;

import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Base64;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.lyricslover.databinding.ActivityLyricsBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class LyricsActivity extends AppCompatActivity {

    private ActivityLyricsBinding mBinding;
    public static final String ARG_PARAM = "song";

    private static final String styleRedirectionFix = "<style type=\"text/css\"> a {text-decoration: none; color: black; pointer-events: none;}</style>";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Song mSong = (Song) getIntent().getSerializableExtra(ARG_PARAM);

        mBinding = ActivityLyricsBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        Toolbar toolbar = mBinding.toolbar;
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = mBinding.toolbarLayout;
        toolBarLayout.setTitle(mSong.getName());

        TextView authorView = findViewById(R.id.author);
        authorView.setText(mSong.getAuthor());

        WebView lyricsView = findViewById(R.id.lyricsView);
        WebView pictureView = findViewById(R.id.pictureView);

        CompletableFuture<String> data = null;
        String lyricsHtml = "";
        try {
            data = GeniusAPI.searchLyrics(mSong.getId());
            lyricsHtml = parseLyrics(data.get());
        } catch (JSONException | IOException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        String unencodedHtml = "<html><body>" + styleRedirectionFix + lyricsHtml + "</body></html>";
        String encodedHtml = Base64.encodeToString(unencodedHtml.getBytes(),
                Base64.NO_PADDING);
        lyricsView.loadData(encodedHtml, "text/html", "base64");

        pictureView.getSettings().setLoadWithOverviewMode(true);
        pictureView.getSettings().setUseWideViewPort(true);
        pictureView.loadUrl(mSong.getImageURL());
    }

    private String parseLyrics(String json) throws JSONException {
        JSONObject object = new JSONObject(json);
        int status = object.getJSONObject("meta").getInt("status");
        System.out.println("status: " + status);

        JSONObject response = object.getJSONObject("response");
        JSONObject lyricsWrapper = response.getJSONObject("lyrics");
        String lyrics = lyricsWrapper.getJSONObject("lyrics").getJSONObject("body").getString("html");

        return lyrics;
    }
}