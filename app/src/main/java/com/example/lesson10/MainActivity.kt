package com.example.lesson10

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.lesson10.AlbumListAdapter
import com.example.lesson10.Album
import android.os.Bundle
import com.example.lesson10.R
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lesson10.ApiService
import android.app.ProgressDialog
import android.os.AsyncTask
import android.util.Log
import com.google.gson.Gson
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class MainActivity : AppCompatActivity(), Callback<MutableList<Album>> {
    private var recyclerView: RecyclerView? = null
    private var adapter: AlbumListAdapter? = null
    private var albumList: MutableList<Album>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUI()
        albumList = ArrayList()
        adapter = AlbumListAdapter(this)
        recyclerView!!.adapter = adapter
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerView!!.layoutManager = linearLayoutManager
        callApi()
    }

    private fun initUI() {
        recyclerView = findViewById(R.id.recyclerview)
    }

    private fun callApi() {
//        ApiService.apiService?.allAlbums?.enqueue(obj : Callback<List<Album>!> {
//            override fun onResponse(
//                call: Call<MutableList<Album>?>,
//                response: Response<MutableList<Album>?>
//            ) {
//                albumList = response.body()
//                if (albumList != null) {
//                    adapter!!.setData(albumList)
//                    displayToast("Call Api Successfully")
//                }
//            }
//
//            override fun onFailure(call: Call<MutableList<Album>?>, t: Throwable) {
//                displayToast("Call Api Fail")
//            }
//        })

        ApiService.apiService.allAlbums.enqueue(this)

//        AsyncTaskExample asyncTask = new AsyncTaskExample();
//        asyncTask.execute("https://jsonplaceholder.typicode.com/photos");
    }

    override fun onResponse(call: Call<MutableList<Album>>, response: Response<MutableList<Album>>) {
        albumList = response.body()
        if (albumList != null) {
            adapter!!.setData(albumList)
            displayToast("Call Api Successfully")
        }
    }

    override fun onFailure(call: Call<MutableList<Album>>, t: Throwable) {
        displayToast("Call Api Fail")
    }

    var p: ProgressDialog? = null

    private inner class AsyncTaskExample : AsyncTask<String?, Void?, String?>() {
        override fun onPreExecute() {
            super.onPreExecute()
            p = ProgressDialog(this@MainActivity)
            p!!.setMessage("Please wait...")
            p!!.isIndeterminate = false
            p!!.setCancelable(false)
            p!!.show()
        }

        override fun doInBackground(vararg params: String?): String? {
            var urlConnection: HttpURLConnection? = null
            var sResult: String? = null
            try {
                val url = URL(params[0])
                urlConnection = url.openConnection() as HttpURLConnection
                val code = urlConnection.responseCode
                Log.d("HttpResponseCode", code.toString() + "")
                val `in` = BufferedReader(
                    InputStreamReader(urlConnection!!.inputStream)
                )
                var inputLine: String?
                val response = StringBuffer()
                while (`in`.readLine().also { inputLine = it } != null) {
                    response.append(inputLine)
                }
                `in`.close()
                sResult = response.toString()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                urlConnection?.disconnect()
            }
            return sResult
        }

//        override fun doInBackground(vararg strings: String): String? {
//            var urlConnection: HttpURLConnection? = null
//            var sResult: String? = null
//            try {
//                val url = URL(strings[0])
//                urlConnection = url.openConnection() as HttpURLConnection
//                val code = urlConnection.responseCode
//                Log.d("HttpResponseCode", code.toString() + "")
//                val `in` = BufferedReader(
//                    InputStreamReader(urlConnection!!.inputStream)
//                )
//                var inputLine: String?
//                val response = StringBuffer()
//                while (`in`.readLine().also { inputLine = it } != null) {
//                    response.append(inputLine)
//                }
//                `in`.close()
//                sResult = response.toString()
//            } catch (e: Exception) {
//                e.printStackTrace()
//            } finally {
//                urlConnection?.disconnect()
//            }
//            return sResult
//        }

        override fun onPostExecute(sResult: String?) {
            super.onPostExecute(sResult)
            p!!.hide()
            albumList!!.clear()

//            if (sResult != null) {
//                try {
//                    JSONArray arr = new JSONArray(sResult);
//
//                    for (int i=0; i < arr.length(); i++) {
//                        JSONObject data = arr.getJSONObject(i);
//
//                        Album album = new Album(
//                                data.getInt("id"), data.getString("title"), data.getString("url"));
//
//                        albumList.add(album);
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }

            //cach 2: gson
            if (sResult != null) {
                val gson = Gson()
                val albums = gson.fromJson(sResult, Array<Album>::class.java)
                Collections.addAll(albumList, *albums)
            }
            adapter!!.setData(albumList)
        }


    }

    private fun displayToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }


}