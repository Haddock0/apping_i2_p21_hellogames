package fr.epita.game_list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telecom.Call
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Step 4: Call the WebService --------------------------------------------------------------
        // Finally, use the service to enqueue the callback
        // This will asynchronously call the method


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
        val callback: Callback<List<Game>> = object : Callback<List<Game>> {
            override fun onFailure(call: retrofit2.Call<List<Game>>, t: Throwable) {
                // Code here what happens if calling the WebService fails
                Log.w("TAG", "WebService call failed")
            }

            override fun onResponse(
                call: retrofit2.Call<List<Game>>, response:
                Response<List<Game>>
            ) {
                Log.d("TAG", "Log service response")
                if (response.code() == 200) {
                    // We got our data !
                    val responseData = response.body()!!
                    if (responseData != null) {
                        // display performance optimization when list widget size does not change
                        Recyclerlist.setHasFixedSize(true)
                        // here we specify this is a standard vertical list
                        Recyclerlist.layoutManager = LinearLayoutManager(
                            this@MainActivity,
                            LinearLayoutManager.VERTICAL,
                            false)
                        val myItemClickListener = View.OnClickListener {
                            // we retrieve the row position from its tag
                            val position = it.tag as Int
                            val clickedItem = responseData[position]
                            // do stuff

                            // Create an explicit intent
                            val explicitIntent = Intent(this@MainActivity, GameDetailsActivity::class.java)

                            // Insert extra data in the intent
                            explicitIntent.putExtra("GAME_ID", clickedItem.id.toString())

                            // Start the other activity by sending the intent
                            startActivity(explicitIntent)

                            Toast.makeText(
                                this@MainActivity,
                                "Clicked " + clickedItem.name,
                                Toast.LENGTH_SHORT)
                                .show()
                        }
                        // attach an adapter and provide some data
                        Recyclerlist.adapter = GameAdapterList(this@MainActivity, responseData, myItemClickListener)
                        Recyclerlist.addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))
                    }
                }
            }
        }
        service.listAllGames().enqueue(callback)
    }
}
