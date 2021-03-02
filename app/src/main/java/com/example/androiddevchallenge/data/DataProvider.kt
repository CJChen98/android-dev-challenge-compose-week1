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
            dogs.addAll(Gson().fromJson(string,typeOf))
        }
        job.join()
        dogs
    }
}