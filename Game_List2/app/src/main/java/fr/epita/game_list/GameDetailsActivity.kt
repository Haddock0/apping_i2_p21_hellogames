package fr.epita.game_list

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_games_details.*
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GameDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_games_details)

        // retrieve the intent that caused the activity to open
        val originIntent = intent
        // extract data from the intent
        val game_id = originIntent.getStringExtra("GAME_ID")

        //Step 2: Create the Retrofit and service client objects -----------------------------------
        // A List to store or objects
        // val data = arrayListOf<Game>()
        // The base URL where the WebService is located
        val baseURL = "https://androidlessonsapi.herokuapp.com/api/"
        // Use GSON library to create our JSON parser
        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
        // Create a Retrofit client object targeting the provided URL
        // and add a JSON converter (because we are expecting json responses)
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(jsonConverter)
            .build()
        // Use the client to create a service:
        // an object implementing the interface to the WebService
        val service: GameService = retrofit.create(GameService::class.java)

        //Step 3: The Callback object --------------------------------------------------------------
        val callback: Callback<GameDetails> = object : Callback<GameDetails> {
            override fun onFailure(call: retrofit2.Call<GameDetails>, t: Throwable) {
                // Code here what happens if calling the WebService fails
                Log.w("TAG", "WebService call failed")
            }

            override fun onResponse(
                call: retrofit2.Call<GameDetails>, response:
                Response<GameDetails>
            ) {
                Log.d("TAG", "Log service response")
                if (response.code() == 200) {
                    // We got our data !
                    val responseData = response.body()!!
                    if (responseData != null) {
                        Glide
                            .with(this@GameDetailsActivity)
                            .load(responseData.picture)
                            .into(image)

                        name.text = "" + name.text + responseData.name
                        type.text = "" + type.text + responseData.type
                        Nb_players.text = "" + Nb_players.text + responseData.players
                        Year.text = "" + Year.text + responseData.year
                        description.text = "" + description.text + responseData.description_en
                        button.setOnClickListener{
                            val url = responseData.url
                            // Define an implicit intent
                            val implicitIntent = Intent(Intent.ACTION_VIEW)
                            // Add the required data in the intent (here the URL we want to open)
                            implicitIntent.data = Uri.parse(url)
                            // Launch the intent
                            startActivity(implicitIntent)
                        }
                    }
                }
            }
        }
        service.listGamesForUser(game_id.toInt()).enqueue(callback)
    }

}
