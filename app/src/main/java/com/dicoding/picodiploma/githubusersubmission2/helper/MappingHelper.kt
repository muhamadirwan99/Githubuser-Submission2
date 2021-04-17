package com.dicoding.picodiploma.githubusersubmission2.helper

import android.database.Cursor
import com.dicoding.picodiploma.githubusersubmission2.db.DatabaseContract
import com.dicoding.picodiploma.githubusersubmission2.model.FavoriteModel

object MappingHelper {
    fun mapCursorToArrayList(favoriteCursor: Cursor?) : ArrayList<FavoriteModel>{
        val favoriteList = ArrayList<FavoriteModel>()

        favoriteCursor?.apply {
            while (moveToNext()) {
                val username = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.USERNAME))
                val avatar = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.AVATAR))
                favoriteList.add(FavoriteModel(username, avatar))
            }
        }
        return favoriteList
    }

}