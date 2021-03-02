package com.example.androiddevchallenge.ui.page

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate
import com.example.androiddevchallenge.data.Dog
import com.example.androiddevchallenge.viewmodel.DogViewModel

/**
 * @Author:       Chen
 * @Date:         2021/2/25 15:38
 * @Description:
 */
const val HOME_PAGE = "HOME_PAGE"

@ExperimentalFoundationApi
@Composable
fun HomePage(navController: NavHostController) {
    val viewModel: DogViewModel = viewModel()
    val dogs by viewModel.dogs.collectAsState()
    val alpha: Float by animateFloatAsState(
        if (dogs.isEmpty()) 0f else 1f,
        animationSpec = tween(1000)
    )
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .graphicsLayer {
            this.alpha = alpha
        }) {
        itemsIndexed(dogs) { position, dog ->
            if (!dog.adopted) {
                DogListItem(item = dog) {
//                    navController.currentBackStackEntry?.arguments?.putParcelable("dog", dog)
                    navController.navigate("$DOG_INFO_PAGE/$position") {
                        this.anim {
                            exit
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DogListItem(item: Dog, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxSize()
            .clickable {
                onClick()
            },
        elevation = 4.dp,
    ) {
        val context = LocalContext.current
        val identifier =
            context.resources.getIdentifier(
                item.avatar_filename,
                "drawable",
                context.packageName
            )
        Box(
            modifier = Modifier
                .padding(6.dp)
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = identifier),
                    contentDescription = item.name,
                    contentScale = ContentScale.Crop, modifier = Modifier
                        .size(60.dp)
                        .clip(
                            CircleShape
                        )
                )
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun InformationItem(title: String, content: String) {
    Column() {
        Text(text = title, style = MaterialTheme.typography.subtitle2)
        Spacer(modifier = Modifier.height(10.dp))

    }
}