package fr.epita.hello_games

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Step 4: Call the WebService --------------------------------------------------------------
        // Finally, use the service to enqueue the callback
        // This will asynchronously call the method
        service.listAllGames().enqueue(callback)
    }

    //Step 2: Create the Retrofit and service client objects -----------------------------------
    // A List to store or objects
    val data = arrayListOf<Game>()
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
    val callback: Callback<List<Game>> = object : Callback<List<Game>> {
        override fun onFailure(call: Call<List<Game>>, t: Throwable) {
            // Code here what happens if calling the WebService fails
            Log.w("TAG", "WebService call failed")
        }
        override fun onResponse(call: Call<List<Game>>, response:
        Response<List<Game>>
        ) {
            Log.d("TAG", "Log service response")
            if (response.code() == 200) {
                // We got our data !
                val responseData = response.body()
                if (responseData != null) {
                    Log.d("TAG", "WebService success : " + responseData.size)
                    Glide
                        .with(this@MainActivity)
                        .load(responseData[0].picture)
                        .into(findViewById(R.id.game_one))

                    Glide
                        .with(this@MainActivity)
                        .load(responseData[1].picture)
                        .into(findViewById(R.id.game_two))

                    Glide
                        .with(this@MainActivity)
                        .load(responseData[2].picture)
                        .into(findViewById(R.id.game_three))

                    Glide
                        .with(this@MainActivity)
                        .load(responseData[3].picture)
                        .into(findViewById(R.id.game_four))
                }
            }
        }
    }
}

