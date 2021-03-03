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
package com.example.androiddevchallenge

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.example.androiddevchallenge.ui.page.DOG_INFO_PAGE
import com.example.androiddevchallenge.ui.page.DogInfoPage
import com.example.androiddevchallenge.ui.page.HOME_PAGE
import com.example.androiddevchallenge.ui.page.HomePage
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.viewmodel.DogViewModel

class MainActivity : AppCompatActivity() {
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            MyTheme {
                MyApp()
            }
        }
    }
}

// Start building your app here!
@SuppressLint("RestrictedApi")
@ExperimentalFoundationApi
@Composable
fun MyApp() {
    val navController = rememberNavController()
    var canBack by mutableStateOf(false)
    val viewModel: DogViewModel = viewModel()
    val title by viewModel.title.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (canBack) title else stringResource(id = R.string.app_name)
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            if (canBack) navController.navigateUp()
                        },
                        enabled = canBack
                    ) {
                        Icon(
                            painter = painterResource(
                                id = if (canBack) R.drawable.ic_arrow_back
                                else R.drawable.ic_home
                            ),
                            contentDescription = DOG_INFO_PAGE,
                            modifier = Modifier
                                .padding(8.dp)
                                .size(28.dp)

                        )
                    }
                }
            )
        },
    ) {
        NavHost(navController = navController, startDestination = HOME_PAGE) {
            composable(HOME_PAGE) { HomePage(viewModel, navController) }
            composable(
                "$DOG_INFO_PAGE/{position}",
                arguments = listOf(navArgument("position") { type = NavType.IntType })
            ) {
                DogInfoPage(
                    viewModel,
                    navController,
                    it.arguments?.getInt("position") ?: -1
                )
            }
        }
    }
    navController.addOnDestinationChangedListener { controller, _, _ ->
        canBack = controller.backStack.size > 2
        Log.d("My", "MyApp: ${controller.backStack.size}")
    }
}
