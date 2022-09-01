package com.anderdadev.lavaautosaguazul

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.anderdadev.lavaautosaguazul.adapter.PostAdapter
import com.anderdadev.lavaautosaguazul.databinding.ActivityPostBinding
import com.anderdadev.lavaautosaguazul.resposes.PostResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PostActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityPostBinding
    private lateinit var adapter: PostAdapter
    private var posts = mutableListOf<PostResponse>()

    override fun onCreate(savedInstanceState: Bundle?) {

        mBinding = ActivityPostBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)

        getPosts()
        initRecyclerView()

    }

    private fun initRecyclerView(){
        adapter = PostAdapter(posts)
        mBinding.rvPosts.layoutManager = LinearLayoutManager(this)
        mBinding.rvPosts.adapter = adapter
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://sea-lion-app-ffsmx.ondigitalocean.app/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getPosts(){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIService::class.java).getPosts("posts")
            val services = call.body()

            runOnUiThread {
                if (call.isSuccessful){
                    val postsReturn = services?.posts ?: emptyList()
                    posts.clear()
                    posts.addAll(postsReturn)
                    adapter.notifyDataSetChanged()
                }else
                    showError()
            }
        }
    }

    private fun showError() {
        Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
    }
}