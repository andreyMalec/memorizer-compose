package com.malec.main.internal.presentation.view

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.DisposableEffectResult
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.github.terrakok.modo.Screen
import com.github.terrakok.modo.ScreenKey
import com.github.terrakok.modo.generateScreenKey
import com.github.terrakok.modo.model.OnScreenRemoved
import com.github.terrakok.modo.model.rememberScreenModel
import com.malec.main.internal.presentation.viewcontroller.MainViewController
import com.malec.ui.lifecycle.viewController
import kotlinx.parcelize.Parcelize

@Parcelize
class MainScreen(
    override val screenKey: ScreenKey = generateScreenKey(),
) : Screen {

    @Composable
    override fun Content() {
        Row {
            Content2()
            Text(text = "Hello world", modifier = Modifier.fillMaxSize())

        }
    }

    @Composable
    fun Content2(
        viewController: MainViewController = viewController()
    ) {
        val state by viewController.renderState()
        Button(onClick = { viewController.onButtonClicked() }) {
            Text(text = state.text)
        }
    }
}