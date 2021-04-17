package com.dicoding.picodiploma.githubusersubmission2.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FavoriteModel(
    var username: String? = null,
    var avatar: String? = null,
) : Parcelable
