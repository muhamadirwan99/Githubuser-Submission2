package com.dicoding.picodiploma.githubusersubmission2.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.dicoding.picodiploma.githubusersubmission2.db.DatabaseContract.FavoriteColumns.Companion.TABLE_NAME

internal class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "dbgithubuserapp"
        private const val DATABASE_VERSION = 1

        private const val SQL_CREATE_TABLE_USER = "CREATE TABLE $TABLE_NAME" +
                "(${DatabaseContract.FavoriteColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${DatabaseContract.FavoriteColumns.USERNAME} TEXT NOT NULL," +
                "${DatabaseContract.FavoriteColumns.AVATAR} TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE_USER)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}