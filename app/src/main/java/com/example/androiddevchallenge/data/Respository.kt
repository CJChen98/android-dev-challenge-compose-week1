package com.example.androiddevchallenge.data

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.ref.WeakReference

/**
 * @Author:       Chen
 * @Date:         2021/3/2 10:03
 * @Description:
 */
class Respository private constructor( context: Context) {
    val appContext  :WeakReference<Context> = WeakReference(context)
    companion object {
        private var INSTANCE: Respository? = null
        fun getInstance(context: Context): Respository {
            return INSTANCE ?: synchronized(this) {
                return INSTANCE ?: Respository(context).also { INSTANCE = it }
            }
        }
    }

    suspend fun getDogs()= withContext(Dispatchers.IO){
        DataProvider.getDogs(appContext.get()?: return@withContext listOf<Dog>())
    }
}