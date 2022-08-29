package com.deonnao.flixster

import org.json.JSONArray

data class Movie(
    val movieId : Int,
    private val posterPath : String,
    val title : String,
    val overview : String
) {
    val posterImageUrl = "https://image.tmdb.org/t/p/w500/$posterPath"
    //parse json data
    companion object {
        fun fromJsonArray(movieJsonArray : JSONArray): List<Movie> {
            //list of movies
            val movies = mutableListOf<Movie>()
            //populate the list of movies
            for( i in 0 until movieJsonArray.length()) {
                //grab the json object at the particular position
                val movieJson = movieJsonArray.getJSONObject(i)
                //add a movie for each json object
                movies.add(
                    //Movie constructor
                    Movie(
                        //pass the parameters
                        movieJson.getInt("id"),
                        movieJson.getString("poster_path"),
                        movieJson.getString("title"),
                        movieJson.getString("overview")
                    )
                )
            }
            return movies
        }
    }
}