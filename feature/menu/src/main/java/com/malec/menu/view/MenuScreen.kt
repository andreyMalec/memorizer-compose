package com.malec.menu.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.malec.menu.dependencies.MenuOutput
import com.malec.ui.navigation.Screen
import com.malec.ui.theme.MemorizercomposeTheme
import kotlin.system.exitProcess

class MenuScreen(
    private val menuOutput: MenuOutput
) : Screen<Screen.ScreenParams>() {

    override val arguments: Map<String, Any> = mapOf()

    @Composable
    override fun Content() {
        InnerContent()
    }

    @Composable
    fun InnerContent() {
        MemorizercomposeTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.background
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Button("Start") { menuOutput.openMainScreen() }
                    Spacer(Modifier.height(Dp(8f)))
                    Button("Continue") { menuOutput.openMainScreen(5) }
                    Spacer(Modifier.height(Dp(8f)))
                    Button("Exit") { exitProcess(0) }
                }
            }
        }
    }

    @Composable
    fun Button(text: String, onClick: () -> Unit) {
        androidx.compose.material.Button(onClick = onClick) {
            Text(text = text, fontSize = MaterialTheme.typography.body1.fontSize)
        }
    }
}