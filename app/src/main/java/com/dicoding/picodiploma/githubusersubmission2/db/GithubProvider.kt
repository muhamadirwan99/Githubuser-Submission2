package com.dicoding.picodiploma.githubusersubmission2.db

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.dicoding.picodiploma.githubusersubmission2.db.DatabaseContract.AUTHORITY
import com.dicoding.picodiploma.githubusersubmission2.db.DatabaseContract.FavoriteColumns.Companion.CONTENT_URI
import com.dicoding.picodiploma.githubusersubmission2.db.DatabaseContract.FavoriteColumns.Companion.TABLE_NAME

class GithubProvider : ContentProvider() {

    companion object {
        private const val GITHUB = 1
        private const val GITHUB_ID = 2
        private lateinit var favoriteHelper: FavoriteHelper
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        init {
            sUriMatcher.addURI(AUTHORITY, TABLE_NAME, GITHUB)
            sUriMatcher.addURI(AUTHORITY, "$TABLE_NAME/#", GITHUB_ID)
        }
    }

    override fun onCreate(): Boolean {
        favoriteHelper = FavoriteHelper.getInstance(context as Context)
        favoriteHelper.open()
        return true
    }

    override fun query(uri: Uri, strings: Array<String>?, s: String?, strings1: Array<String>?, s1: String?): Cursor? {
        return when (sUriMatcher.match(uri)) {
            GITHUB -> favoriteHelper.queryAll()
            else -> null
        }
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val added: Long = when (GITHUB) {
            sUriMatcher.match(uri) -> favoriteHelper.insert(values)
            else -> 0
        }

        context?.contentResolver?.notifyChange(CONTENT_URI, null)

        return Uri.parse("$CONTENT_URI/$added")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val deleted: Int = when (GITHUB_ID) {
            sUriMatcher.match(uri) -> favoriteHelper.deleteByUsername(uri.lastPathSegment.toString())
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)

        return deleted
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        val updated: Int = when (GITHUB_ID) {
            sUriMatcher.match(uri) -> favoriteHelper.update(uri.lastPathSegment.toString(),values)
            else -> 0
        }

        context?.contentResolver?.notifyChange(CONTENT_URI, null)

        return updated
    }


}