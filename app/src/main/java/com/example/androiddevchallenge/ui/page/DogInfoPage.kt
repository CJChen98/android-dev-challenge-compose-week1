package com.example.androiddevchallenge.ui.page

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.androiddevchallenge.data.Dog
import com.example.androiddevchallenge.viewmodel.DogViewModel

/**
 * @Author:       Chen
 * @Date:         2021/2/25 15:57
 * @Description:
 */
const val DOG_INFO_PAGE = "DOG_INFO_PAGE"

@Composable
fun DogInfoPage(navController: NavHostController, position: Int) {
    if (position == -1) navController.navigateUp()
    val viewModel: DogViewModel = viewModel()
    val dogs by viewModel.dogs.collectAsState()
    val alpha: Float by animateFloatAsState(if (dogs.isEmpty()) 0f else 1f,animationSpec = tween(1000))
    Box(modifier = Modifier.fillMaxSize()) {
        if (position < dogs.lastIndex) {
            DogInfo(dog = dogs[position], modifier = Modifier.graphicsLayer { this.alpha = alpha })
        } else {
            Text(
                text = "Loading...",
                textAlign = TextAlign.Center,
                modifier = Modifier.graphicsLayer { this.alpha = 1f - alpha }.fillMaxSize())
        }
    }
}

@Composable
fun DogInfo(dog: Dog, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val identifier =
        context.resources.getIdentifier(dog.avatar_filename, "drawable", context.packageName)
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(id = identifier),
            contentDescription = dog.name,
            contentScale = ContentScale.FillWidth
        )
        Text(text = dog.introduction)
    }
}