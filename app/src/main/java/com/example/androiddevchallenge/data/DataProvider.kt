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

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.io.InputStreamReader

/**
 * @Author:       Chen
 * @Date:         2021/3/2 10:15
 * @Description:
 */
object DataProvider {
    suspend fun getDogs(context: Context) = coroutineScope {
        val dogs = mutableListOf<Dog>()
        val job = launch(Dispatchers.IO) {
            val assetsManager = context.assets
            val inputReader = InputStreamReader(assetsManager.open("dogs.json"))
            val string = inputReader.readText()
            val typeOf = object : TypeToken<List<Dog>>() {}.type
            dogs.addAll(Gson().fromJson(string, typeOf))
        }
        job.join()
        dogs
    }
}
