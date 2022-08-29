package com.deonnao.flixster

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONException

const val TAG = "MainActivity"
const val NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed"

class MainActivity : AppCompatActivity() {
    //list to hold the movies
    private val movies = mutableListOf<Movie>()
    private lateinit var rvMovies: RecyclerView

    //1. Define a data model class as the data source -DONE
    //2. Add the RecyclerView to the layout - DONE
    //3. Create a custom row layout XML file to visualize the item - DONE
    //4. Create an Adapter and ViewHolder to render the item - DONE
    //5. Bind the adapter to the data source to populate the RecyclerView - DONE
    //6. Bind the layout manager to the RecyclerView - DONE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvMovies = findViewById(R.id.rvMovies)

        val movieAdapter = MovieAdapter(this, movies)
        rvMovies.adapter = movieAdapter
        rvMovies.layoutManager = LinearLayoutManager(this)

        //create http client using asyncHttpClient library
        val client = AsyncHttpClient()
        val params = RequestParams()
        //make a get request to the url. The second parameter is a response handler for the data that was requested
        client [NOW_PLAYING_URL, params, object: JsonHttpResponseHandler() {
            override fun onFailure(
                statusCode: Int, headers: Headers?, response: String?, throwable: Throwable?
            ) {
                Log.e(TAG, "OnFailure $statusCode")
            }

            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                Log.i(TAG, "OnSuccess: JSON data $json")
                try {
                    //hold the data that we request
                    val movieJsonArray = json.jsonObject.getJSONArray("results")
                    //pass the json array that we just parsed and add the movies to the movies list
                    movies.addAll(Movie.fromJsonArray(movieJsonArray))
                    movieAdapter.notifyDataSetChanged()
                    Log.i(TAG, "Movie list $movies")
                } catch (e: JSONException) {
                    Log.e(TAG, "Encountered exception $e")
                }
            }
        }]
    }
}