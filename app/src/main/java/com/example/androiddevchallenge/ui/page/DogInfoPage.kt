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
package com.example.androiddevchallenge.ui.page

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.androiddevchallenge.data.Dog
import com.example.androiddevchallenge.viewmodel.DogViewModel

/**
 * @Author:       Chen
 * @Date:         2021/2/25 15:57
 * @Description:
 */
const val DOG_INFO_PAGE = "DOG_INFO_PAGE"
var showConfirmDialog by mutableStateOf(false)

@Composable
fun DogInfoPage(viewModel: DogViewModel, navController: NavHostController, position: Int) {
    if (position == -1) navController.navigateUp()
    val dogs by viewModel.dogs.collectAsState()
    val alpha: Float by animateFloatAsState(
        if (dogs.isEmpty()) 0f else 1f,
        animationSpec = tween(1000)
    )
    Box(modifier = Modifier.fillMaxSize()) {
        if (position < dogs.lastIndex) {
            DogInfo(dog = dogs[position], modifier = Modifier.graphicsLayer { this.alpha = alpha })
            if (showConfirmDialog) {
                AdoptConfirmDialog(viewModel = viewModel, position = position)
            }
        } else {
            Text(
                text = "Loading...",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .graphicsLayer { this.alpha = 1f - alpha }
                    .fillMaxSize()
            )
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
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.fillMaxWidth().clip(MaterialTheme.shapes.medium),
            painter = painterResource(id = identifier),
            contentDescription = dog.name,
            contentScale = ContentScale.FillWidth
        )
        Spacer(modifier = Modifier.height(18.dp))
        Text(text = dog.introduction)
        Spacer(modifier = Modifier.height(18.dp))
        Button(onClick = { showConfirmDialog = !showConfirmDialog }, enabled = !dog.adopted) {
            Text(text = "Adopt me!", style = MaterialTheme.typography.body1)
        }
    }
}

@Composable
fun AdoptConfirmDialog(viewModel: DogViewModel, position: Int) {
    AlertDialog(
        onDismissRequest = {
            showConfirmDialog = false
        },
        text = {
            Text(
                text = "Do you want to adopt this lovely dog?",
                style = MaterialTheme.typography.body2
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    showConfirmDialog = false
                    viewModel.adoptDog(position)
                }
            ) {
                Text(
                    text = "Yes"
                )
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    showConfirmDialog = false
                }
            ) {
                Text(
                    text = "No"
                )
            }
        }
    )
}
