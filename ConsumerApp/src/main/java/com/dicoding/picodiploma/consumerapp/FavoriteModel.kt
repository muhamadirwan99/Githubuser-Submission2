package com.dicoding.picodiploma.consumerapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FavoriteModel(
    var username: String? = null,
    var avatar: String? = null,
) : Parcelable
