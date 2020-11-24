package com.resmana.githubuser

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Users(
    var avatar: String? = null,
    var username: String? = null,
    var name: String? = null,
    var company: String? = null,
    var location: String? = null,
    var repository: String? = null,
    var following: String? = null,
    var followers: String? = null,
) : Parcelable