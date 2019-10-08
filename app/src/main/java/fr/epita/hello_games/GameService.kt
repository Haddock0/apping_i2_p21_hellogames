package fr.epita.hello_games

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GameService {
    //Step 1: Create the Interface -------------------------------------------------------------
    @GET("game/list")
    fun listAllGames(): Call<List<Game>>
    @GET("game/details")
    fun listGamesForUser(@Query("id") userId: Int): Call<List<Game>>
}