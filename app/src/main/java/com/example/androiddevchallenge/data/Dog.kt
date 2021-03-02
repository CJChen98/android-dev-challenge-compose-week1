package com.example.androiddevchallenge.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * @Author:       Chen
 * @Date:         2021/2/25 15:59
 * @Description:
 */
@Suppress("DEPRECATED_ANNOTATION")
@Parcelize
data class Dog(
    @SerializedName("name") val name: String = "",
    @SerializedName("avatar_filename") val avatar_filename: String = "",
    @SerializedName("introduction") val introduction: String = "",
    var adopted: Boolean = false
) : Parcelable
