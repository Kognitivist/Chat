package com.kognitivist.chat

import android.annotation.SuppressLint
import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import com.kognitivist.chat.screens.ChatsScreen
import com.kognitivist.chat.screens.AuthScreen
import com.kognitivist.chat.ui.theme.ChatTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseAuth
import com.kognitivist.chat.data.database.FirebaseRepository
import com.kognitivist.chat.screens.MessagesScreen

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            val context = LocalContext.current
            val mViewModel: MainViewModel = viewModel(factory = MainViewModelFactory(context.applicationContext as Application))

            val navigation = remember {
                mutableStateOf("Auth Screen")
            }

            LaunchedEffect(key1 = mViewModel.getMailUser()){
                if (mViewModel.getMailUser() != "null") {
                    navigation.value = "Chat Screen"
                }else{
                    navigation.value = "Auth Screen" }
            }

            BackHandler(true) {
                when(navigation.value){
                    "Chat Screen" -> navigation.value = "Auth Screen"
                    "Messages Screen" -> navigation.value = "Chat Screen"
                }
            }

            ChatTheme {
                Crossfade(targetState = navigation.value, animationSpec = tween(1000)) { screen ->
                    when (screen) {
                        "Auth Screen" -> AuthScreen(viewModel = mViewModel, navigation = navigation)
                        "Chat Screen"  -> ChatsScreen(viewModel = mViewModel, navigation = navigation)
                        "Messages Screen" -> MessagesScreen(viewModel = mViewModel, navigation = navigation)
                    }
                }
            }
        }
    }
}
