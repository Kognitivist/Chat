package com.kognitivist.chat

import android.annotation.SuppressLint
import android.app.Application
import android.os.Bundle
import android.widget.CheckBox
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import com.kognitivist.chat.screens.ChatsScreen
import com.kognitivist.chat.screens.StartScreen
import com.kognitivist.chat.ui.theme.ChatTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kognitivist.chat.screens.MessagesScreen

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            val mViewModel: MainViewModel = viewModel(factory = MainViewModelFactory(context.applicationContext as Application))

            val navigation = remember {
                mutableStateOf("Start Screen")
            }
            BackHandler(true) {
                navigation.value = "Start Screen"
            }

            ChatTheme {
                Crossfade(targetState = navigation.value) { screen ->
                    when (screen) {
                        "Start Screen" -> StartScreen(viewModel = mViewModel, navigation = navigation)
                        "Chat Screen"  -> ChatsScreen()
                        "Messages Screen" -> MessagesScreen()
                    }
                }


            }
        }
    }
}
