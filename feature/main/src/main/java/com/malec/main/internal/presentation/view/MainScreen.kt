package com.malec.main.internal.presentation.view

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.malec.main.internal.presentation.viewcontroller.MainViewController
import com.malec.ui.lifecycle.viewController
import com.malec.ui.navigation.Screen
import com.malec.ui.theme.MemorizercomposeTheme

object MainScreen : Screen<MainScreen.MainScreenParams>() {

    class MainScreenParams(key: Int): ScreenParams(key)

    override val arguments: Map<String, Any> = mapOf(
        "key" to Int
    )

    @Composable
    override fun Content() {
        println("____, test recomposition MainScreen Content")
        val viewController: MainViewController = viewController()
        InnerContent(viewController)
    }

    @Composable
    fun InnerContent(
        viewController2: MainViewController,
        viewController: MainViewController = viewController()
    ) {
        println("____, viewController2 = ${viewController2.hashCode()}, viewController = ${viewController.hashCode()}")
        MemorizercomposeTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.background
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    val state by viewController.renderState()
                    Button(onClick = { viewController.onButtonClicked() }) {
                        Text(text = state.text)
                    }
                    Spacer(modifier = Modifier.height(Dp(8f)))
                    Button(onClick = { viewController.onBackClicked() }) {
                        Text(text = "Back")
                    }
                }
                BackHandler {
                    viewController.onBackClicked()
                }
            }
        }
    }

}
