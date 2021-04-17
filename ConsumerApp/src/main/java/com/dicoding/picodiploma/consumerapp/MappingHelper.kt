package com.dicoding.picodiploma.consumerapp

import android.database.Cursor

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