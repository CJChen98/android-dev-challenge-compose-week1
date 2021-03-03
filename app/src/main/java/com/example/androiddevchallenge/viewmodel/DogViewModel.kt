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
package com.example.androiddevchallenge.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.androiddevchallenge.data.Dog
import com.example.androiddevchallenge.data.Respository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * @Author:       Chen
 * @Date:         2021/2/25 16:13
 * @Description:
 */
class DogViewModel(application: Application) : AndroidViewModel(application) {
    private val _dogs = MutableStateFlow(listOf<Dog>())
    val dogs: StateFlow<List<Dog>> get() = _dogs
    private val _title = MutableStateFlow("")
    val title: StateFlow<String> get() = _title
    private val respository = Respository.getInstance(application.applicationContext)

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            _dogs.emit(respository.getDogs())
        }
    }

    fun setTitle(title: String) {
        viewModelScope.launch {
            _title.emit(title)
        }
    }

    fun adoptDog(position: Int) {
        if (position > dogs.value.lastIndex || position < 0) return
        viewModelScope.launch(Dispatchers.IO) {
            val list = mutableListOf<Dog>().apply { addAll(_dogs.value) }
            val dog = list[position].apply {
                adopted = true
            }
//            list[position] = dog
            _dogs.emit(list)
        }
    }
}
