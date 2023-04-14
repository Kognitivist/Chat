package com.kognitivist.chat.presentation

import android.annotation.SuppressLint
import android.app.Application
import android.os.Bundle
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
import androidx.compose.ui.platform.LocalContext
import com.kognitivist.chat.presentation.screens.ChatsScreen
import com.kognitivist.chat.presentation.screens.AuthScreen
import com.kognitivist.chat.presentation.ui.theme.ChatTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.kognitivist.chat.data.database.FirebaseRepository
import com.kognitivist.chat.domain.navigation.ChatNavHost
import com.kognitivist.chat.presentation.screens.MessagesScreen
import com.kognitivist.chat.tools.REPOSITORY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        REPOSITORY = FirebaseRepository(this)


        setContent {
            val context = LocalContext.current
            val mViewModel: MainViewModel = viewModel(factory = MainViewModelFactory(context.applicationContext as Application))
            val navigator = rememberNavController()

            ChatTheme {
                ChatNavHost(mViewModel = mViewModel, navigator = navigator)
            }
        }
    }
}
