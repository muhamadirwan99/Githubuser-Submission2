package com.dicoding.picodiploma.githubusersubmission2.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.githubusersubmission2.model.UserModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.lang.Exception

class MainViewModel : ViewModel() {

    val listUsers = MutableLiveData<ArrayList<UserModel>>()

    fun setUser(userNames : String){
        val listUser = ArrayList<UserModel>()
        val client = AsyncHttpClient()
        val url = " https://api.github.com/search/users?q=$userNames"
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
                    val items = responseObject.getJSONArray("items")

                    for (i in 0 until items.length()) {
                        val item = items.getJSONObject(i)
                        val userItems = UserModel()
                        userItems.username = item.getString("login")
                        userItems.avatar = item.getString("avatar_url")

                        listUser.add(userItems)
                    }

                    listUsers.postValue(listUser)
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

    fun getDetail(userNames: String) : UserModel {

        val client = AsyncHttpClient()
        val url = "https://api.github.com/users/$userNames"
        client.addHeader("Authorization", "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")
        client.addHeader("User-Agent", "Request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                val responseObject = JSONObject(result)
                val userItems = UserModel()

                userItems.name = responseObject.getString("name")
                userItems.company = responseObject.getString("company")
                userItems.location = responseObject.getString("location")
                userItems.public_repos = responseObject.getInt("public_repos")
                userItems.followers = responseObject.getInt("followers")
                userItems.following = responseObject.getInt("following")

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
        return UserModel()
    }

    fun getUser(): LiveData<ArrayList<UserModel>> {
        return listUsers
    }
}