/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
