package com.dicoding.picodiploma.githubusersubmission2.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.githubusersubmission2.R
import com.dicoding.picodiploma.githubusersubmission2.adapter.SectionsPagerAdapter
import com.dicoding.picodiploma.githubusersubmission2.databinding.ActivityDetailUserBinding
import com.dicoding.picodiploma.githubusersubmission2.model.UserModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.lang.Exception

class DetailUser : AppCompatActivity(){

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.following_,
            R.string.followers_
        )
        const val EXTRA_USERNAME = "extra_username"
    }

    private lateinit var binding: ActivityDetailUserBinding

    private var userName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showLoading(true)
        val username = intent.getStringExtra(EXTRA_USERNAME)
        userName = username

        setUserDetail(username)

        tabLayout()

    }

    private fun tabLayout(){
        val username = intent.getStringExtra(EXTRA_USERNAME)

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = username
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }

    private fun setUserDetail(userNames : String?){
        val listData = ArrayList<UserModel>()

        val client = AsyncHttpClient()
        val url = "https://api.github.com/users/$userNames"
        client.addHeader("Authorization", "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")
        client.addHeader("User-Agent", "Request")
        client.get(url, object : AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val userItems = UserModel()

                    userItems.username = responseObject.getString("login")
                    userItems.avatar = responseObject.getString("avatar_url")
                    userItems.name = responseObject.getString("name")
                    userItems.company = responseObject.getString("company")
                    userItems.location = responseObject.getString("location")
                    userItems.public_repos = responseObject.getInt("public_repos")
                    userItems.followers = responseObject.getInt("followers")
                    userItems.following = responseObject.getInt("following")

                    listData.add(userItems)

                    Glide.with(this@DetailUser)
                        .load(userItems.avatar)
                        .apply(RequestOptions()).override(350,550)
                        .into(binding.imgItemAvatar)

                    binding.tvItemName.text = userItems.name
                    binding.tvItemUsername.text = userItems.username
                    binding.tvCompany.text = userItems.company
                    binding.tvLocation.text = userItems.location
                    binding.tvCompany.text = userItems.company

                    val repository = resources.getString(R.string.repository, userItems.public_repos.toString())
                    binding.tvRepository.text = repository

                    val followers = resources.getString(R.string.followers, userItems.followers.toString())
                    binding.tvFollowers.text = followers

                    val following = resources.getString(R.string.following, userItems.following.toString())
                    binding.tvFollowing.text = following

                    showLoading(false)

                }catch (e: Exception){
                    Log.d("Exception", e.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Log.d("onFailure", error?.message.toString())
            }

        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}