package com.example.lyricslover;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class GeniusAPI {
    private static CompletableFuture<String> performRequest(Request request) throws IOException
    {
        OkHttpClient client = new OkHttpClient();

        CompletableFuture<String> result = new CompletableFuture<String>();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                result.completeExceptionally(e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                    result.complete(response.body().string());
                }
            }
        });
        return result;
    }

    public static CompletableFuture<String> searchSongsByName(String name) throws IOException {
        Request request = new Request.Builder()
                .url("https://genius-song-lyrics1.p.rapidapi.com/search?q=" + name)
                .get()
                .addHeader("X-RapidAPI-Key", "")
                .addHeader("X-RapidAPI-Host", "genius-song-lyrics1.p.rapidapi.com")
                .build();

        return performRequest(request);
    }

    public static CompletableFuture<String> searchLyrics(int id) throws IOException {
        Request request = new Request.Builder()
                .url("https://genius-song-lyrics1.p.rapidapi.com/songs/" + id +"/lyrics")
                .get()
                .addHeader("X-RapidAPI-Key", "")
                .addHeader("X-RapidAPI-Host", "genius-song-lyrics1.p.rapidapi.com")
                .build();

        return performRequest(request);
    }
}
