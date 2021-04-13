package com.dicoding.picodiploma.githubusersubmission2.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.githubusersubmission2.model.FollowersModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import java.lang.Exception

class FollowersViewModel : ViewModel(){
    val listData = ArrayList<FollowersModel>()
    val listFollowers = MutableLiveData<ArrayList<FollowersModel>>()

    fun getFollowers(userNames : String){
        val client = AsyncHttpClient()
        val url = "https://api.github.com/users/$userNames/followers"
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
                    val responseArray = JSONArray(result)
                    for (i in 0 until responseArray.length()) {
                        val item = responseArray.getJSONObject(i)
                        val userItems= FollowersModel()
                        userItems.username = item.getString("login")
                        userItems.avatar = item.getString("avatar_url")
                        listData.add(userItems)
                    }
                    listFollowers.postValue(listData)

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

    fun getListFollowers(): LiveData<ArrayList<FollowersModel>> {
        return listFollowers
    }


}