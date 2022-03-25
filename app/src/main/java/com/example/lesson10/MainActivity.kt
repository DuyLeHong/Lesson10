package com.example.lesson10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AlbumListAdapter adapter;
    private List<Album> albumList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();

        albumList = new ArrayList<>();

        adapter = new AlbumListAdapter(this);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        callApi();
    }

    private void initUI() {
        recyclerView = findViewById(R.id.recyclerview);
    }

    private void callApi() {
//        ApiService.apiService.getAllAlbums().enqueue(new Callback<List<Album>>() {
//            @Override
//            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
//                albumList = response.body();
//
//                if (albumList != null) {
//                    adapter.setData(albumList);
//                    displayToast("Call Api Successfully");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Album>> call, Throwable t) {
//                displayToast("Call Api Fail");
//            }
//        });

        AsyncTaskExample asyncTask = new AsyncTaskExample();
        asyncTask.execute("https://jsonplaceholder.typicode.com/photos");
    }

    ProgressDialog p;

    private class AsyncTaskExample extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            p = new ProgressDialog(MainActivity.this);
            p.setMessage("Please wait...");
            p.setIndeterminate(false);
            p.setCancelable(false);
            p.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection urlConnection = null;

            String sResult = null;

            try {
                URL url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();

                int code = urlConnection.getResponseCode();
                Log.d("Http Response Code", code + "");

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(urlConnection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                sResult = response.toString();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }

            return sResult;
        }

        @Override
        protected void onPostExecute(String sResult) {
            super.onPostExecute(sResult);

            p.hide();
            albumList.clear();

            if (sResult != null) {
                try {
                    JSONArray arr = new JSONArray(sResult);

                    for (int i=0; i < arr.length(); i++) {
                        JSONObject data = arr.getJSONObject(i);

                        Album album = new Album(
                                data.getInt("id"), data.getString("title"), data.getString("url"));

                        albumList.add(album);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            //cach 2: gson
//            if (sResult != null) {
//                Gson gson = new Gson();
//                Album [] albums = gson.fromJson(sResult, Album[].class);
//                Collections.addAll(albumList, albums);
//            }

            adapter.setData(albumList);
        }
    }

    private void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}