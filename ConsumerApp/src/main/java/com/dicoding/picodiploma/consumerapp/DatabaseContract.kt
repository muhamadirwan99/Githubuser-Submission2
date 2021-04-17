package com.dicoding.picodiploma.consumerapp

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {

    const val AUTHORITY = "com.dicoding.picodiploma.githubusersubmission2"
    const val SCHEME = "content"

    class FavoriteColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "favorite_user"
            const val _ID = "_id"
            const val USERNAME = "username"
            const val AVATAR = "AVATAR"

            // untuk membuat URI content://com.dicoding.picodiploma.mynotesapp/note
            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                    .authority(AUTHORITY)
                    .appendPath(TABLE_NAME)
                    .build()
        }
    }
}