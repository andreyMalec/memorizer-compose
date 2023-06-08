package com.malec.main.internal.presentation.view

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.malec.main.internal.presentation.viewcontroller.MainViewController
import com.malec.ui.lifecycle.viewController
import com.malec.ui.navigation.Screen
import com.malec.ui.navigation.ScreenParams
import com.malec.ui.theme.MemorizercomposeTheme

object MainScreen : Screen<MainScreen.MainScreenParams>() {

    data class MainScreenParams(val count: Int, val id: String? = null) : ScreenParams(count, id)

    @Composable
    override fun Content() {
        InnerContent()
    }

    @Composable
    fun InnerContent(
        viewController: MainViewController = viewController()
    ) {
        MemorizercomposeTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.background
            ) {
                val state by viewController.renderState()
                if (state.items.isEmpty())
                    Button(onClick = { viewController.onBackClicked() }) {
                        Text(text = "GG WP")
                    }
                else
                    LazyColumn(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        items(state.items, itemContent = { item ->
                            viewController.questAdapterManager.ItemContent(item)
                        })
                    }
                BackHandler {
                    viewController.onBackClicked()
                }
            }
        }
    }
}
