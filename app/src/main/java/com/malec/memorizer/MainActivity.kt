package com.malec.memorizer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.malec.memorizer.coordinator.base.CoordinatorRegistration
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var coordinatorRegistration: CoordinatorRegistration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = coordinatorRegistration.startRoute
            ) {
                coordinatorRegistration.registerGraph(this, navController)
            }
//            println("____, test recomposition MainActivity onCreate")
//            Content()
        }
    }

    @Composable
    private fun Content() {
        println("____, test recomposition MainActivity Content 1")
        var counter by remember { mutableStateOf(0) }
        println("____, test recomposition MainActivity Content 2")
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Button(
                onClick = { counter++ },
                Modifier.padding(bottom = 12.dp)
            ) {
                Text("Click to recompose")
            }

            Text("Button clicked $counter times")
        }
    }
}
