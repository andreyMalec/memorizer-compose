package com.malec.memorizer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.terrakok.modo.*
import com.github.terrakok.modo.animation.StackTransitionType
import com.github.terrakok.modo.animation.calculateStackTransitionType
import com.github.terrakok.modo.stack.*
import com.malec.main.internal.presentation.view.MainScreen
import com.malec.ui.lifecycle.*
import com.malec.ui.theme.MemorizercomposeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.parcelize.Parcelize

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var rootScreen: StackScreen? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            rootScreen?.Content()
//            val navController = rememberNavController()
//            NavHost(
//                navController = navController,
//                startDestination = "menu"
//            ) {
//                composable(route = "menu") {
//
//                }
//                composable(route = "main") {
//
//                }
//            }
        }
        rootScreen = Modo.init(savedInstanceState, rootScreen) {
            SampleStack(MenuScreen())
        }
//        rootScreen?.dispatch(Forward(MainScreen()))
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Modo.save(outState, rootScreen)
        super.onSaveInstanceState(outState)
    }

    @Parcelize
    class MenuScreen(
        override val screenKey: ScreenKey = generateScreenKey()
    ) : Screen {

        @Composable
        override fun Content() {
            val parent = LocalContainerScreen.current
            MenuContent(parent as StackScreen)
        }

        @Composable
        fun MenuContent(
            navigator: NavigationContainer<StackState>,
//            navController: NavHostController
        ) {
            MemorizercomposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Button("Start", navigator, )
                        Spacer(Modifier.height(Dp(8f)))
                        Button("Continue", navigator, )
                        Spacer(Modifier.height(Dp(8f)))
                        Button("Exit", navigator, )
                    }
                }
            }
        }

        @Composable
        fun Button(text: String, navigator: NavigationContainer<StackState>,) {
            Button({
                navigator.forward(MainScreen())
//                navController.navigate("main")
            }) {
                Text(text = text, fontSize = MaterialTheme.typography.body1.fontSize)
            }
        }
    }
}

@Parcelize
class SampleStack(
    private val stackNavModel: StackNavModel
) : StackScreen(stackNavModel) {

    constructor(rootScreen: Screen) : this(StackNavModel(rootScreen))

    @Composable
    override fun Content() {
        TopScreenContent {
            SlideTransition()
        }
    }
}

@Composable
@OptIn(ExperimentalAnimationApi::class)
fun ComposeRendererScope<StackState>.SlideTransition() {
    ScreenTransition(
        transitionSpec = {
            val transitionType = calculateStackTransitionType(oldState, newState)
            if (transitionType == StackTransitionType.Replace) {
                scaleIn(initialScale = 2f) + fadeIn() with fadeOut()
            } else {
                val (initialOffset, targetOffset) = when (transitionType) {
                    StackTransitionType.Pop -> ({ size: Int -> -size }) to ({ size: Int -> size })
                    else -> ({ size: Int -> size }) to ({ size: Int -> -size })
                }
                slideInHorizontally(initialOffsetX = initialOffset) with
                        slideOutHorizontally(targetOffsetX = targetOffset)
            }
        }
    )


}

typealias ScreenTransitionContent = @Composable AnimatedVisibilityScope.(Screen) -> Unit

@ExperimentalAnimationApi
@Composable
fun ComposeRendererScope<*>.ScreenTransition(
    modifier: Modifier = Modifier,
    transitionSpec: AnimatedContentScope<Screen>.() -> ContentTransform = {
        fadeIn(animationSpec = tween(220, delayMillis = 90)) +
                scaleIn(initialScale = 0.92f, animationSpec = tween(220, delayMillis = 90)) with
                fadeOut(animationSpec = tween(90))
    },
    content: ScreenTransitionContent = { it.SaveableContent() }
) {
    val transition = updateTransition(targetState = screen, label = "ScreenTransition")
    transition.AnimatedContent(
        transitionSpec = transitionSpec,
        modifier = modifier,
        contentKey = { it.screenKey },
        content = content
    )
}