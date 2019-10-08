package fr.epita.game_list

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GameService {
    //Step 1: Create the Interface -------------------------------------------------------------
    @GET("game/list")
    fun listAllGames(): Call<List<Game>>
    @GET("game/details")
    fun listGamesForUser(@Query("game_id") userId: Int): Call<GameDetails>
}