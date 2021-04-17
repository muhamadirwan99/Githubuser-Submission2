package com.dicoding.picodiploma.consumerapp


import android.database.ContentObserver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.consumerapp.DatabaseContract.FavoriteColumns.Companion.CONTENT_URI
import com.dicoding.picodiploma.consumerapp.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "History Favorite User"

        adapter = FavoriteAdapter()
        loadFavoriteAsync()

        val handlerThread = HandlerThread ("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        val myObserver = object : ContentObserver(handler) {
            override fun onChange(selfChange: Boolean) {
                super.onChange(selfChange)
                loadFavoriteAsync()
            }
        }

        contentResolver.registerContentObserver(CONTENT_URI, true, myObserver)



        binding.rvFavorite.layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.setHasFixedSize(true)
        binding.rvFavorite.adapter = adapter

    }

    private fun loadFavoriteAsync(){
        GlobalScope.launch(Dispatchers.Main) {
            binding.progressBar.visibility = View.VISIBLE
            val deferredFavorite = async(Dispatchers.IO) {
                val cursor = contentResolver.query(CONTENT_URI, null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor)
            }
            val favorite = deferredFavorite.await()
            binding.progressBar.visibility = View.INVISIBLE
            if (favorite.size > 0){
                adapter.setData(favorite)
            } else {
                adapter.setData(ArrayList())
                showSnackbarMessage("Tidak ada data saat ini")
            }
        }
    }


    private fun showSnackbarMessage(message: String) {
        Snackbar.make(binding.rvFavorite, message, Snackbar.LENGTH_SHORT).show()
    }
}