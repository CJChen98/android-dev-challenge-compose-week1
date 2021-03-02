package com.example.androiddevchallenge.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.androiddevchallenge.data.Dog
import com.example.androiddevchallenge.data.Respository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.FieldPosition

/**
 * @Author:       Chen
 * @Date:         2021/2/25 16:13
 * @Description:
 */
class DogViewModel(application: Application) : AndroidViewModel(application) {
    private val _dogs = MutableStateFlow(listOf<Dog>())
    val dogs: StateFlow<List<Dog>> get() = _dogs
    private val respository = Respository.getInstance(application.applicationContext)

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            _dogs.emit(respository.getDogs())
        }
    }

    fun getDog(position: Int): Dog? {
        return if (position>dogs.value.lastIndex || position<0){
            null
        }else{
            dogs.value[position]
        }
    }
}