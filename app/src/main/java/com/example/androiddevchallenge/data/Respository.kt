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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.ref.WeakReference

/**
 * @Author:       Chen
 * @Date:         2021/3/2 10:03
 * @Description:
 */
class Respository private constructor(context: Context) {
    val appContext: WeakReference<Context> = WeakReference(context)

    companion object {
        private var INSTANCE: Respository? = null
        fun getInstance(context: Context): Respository {
            return INSTANCE ?: synchronized(this) {
                return INSTANCE ?: Respository(context).also { INSTANCE = it }
            }
        }
    }

    suspend fun getDogs() = withContext(Dispatchers.IO) {
        DataProvider.getDogs(appContext.get() ?: return@withContext listOf<Dog>())
    }
}
