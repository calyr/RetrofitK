package app.calyr.com.retrofitk

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val restApiAdapter = RestApiAdapter()
        val endPoint = restApiAdapter.connexionApi()
        val bookResponseCall = endPoint.getList()
        bookResponseCall.enqueue( object : Callback<List<PostResponse>> {
            override fun onFailure(call: Call<List<PostResponse>>?, t: Throwable?) {
                t?.printStackTrace()
            }

            override fun onResponse(call: Call<List<PostResponse>>?, response: Response<List<PostResponse>>?) {
                val posts = response?.body()
                Log.d("TESTRESULT", Gson().toJson(posts))
            }
        })
    }
}


data class PostResponse(val userId: Int, val id:Int, val title: String, val body: String )

class ConstantsRestApi {
    companion object {
        const val URL_BASE = "https://jsonplaceholder.typicode.com"
        const val POST = "/posts"

    }
}

interface EndPointApi {
    @GET(ConstantsRestApi.POST)
    fun getList():Call<List<PostResponse>>
}

class RestApiAdapter {
    fun connexionApi(): EndPointApi {
        val retrofit = Retrofit.Builder()
                .baseUrl(ConstantsRestApi.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        return retrofit.create(EndPointApi::class.java)
    }
}