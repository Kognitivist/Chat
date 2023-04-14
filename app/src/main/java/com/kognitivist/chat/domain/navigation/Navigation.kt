package com.kognitivist.chat.domain.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kognitivist.chat.presentation.MainViewModel
import com.kognitivist.chat.presentation.screens.AuthScreen
import com.kognitivist.chat.presentation.screens.ChatsScreen
import com.kognitivist.chat.presentation.screens.MessagesScreen
import com.kognitivist.chat.tools.Screens.AUTH_SCREEN
import com.kognitivist.chat.tools.Screens.CHATS_SCREEN
import com.kognitivist.chat.tools.Screens.MESSAGES_SCREEN

sealed class Navigation (val route: String){
    object AuthScreen: Navigation (AUTH_SCREEN)
    object ChatsScreen: Navigation (CHATS_SCREEN)
    object MessagesScreen: Navigation (MESSAGES_SCREEN)
}

@Composable
fun ChatNavHost(mViewModel: MainViewModel, navigator: NavHostController){


    NavHost(navController = navigator, startDestination = if (mViewModel.getMailUser() == "null") Navigation.AuthScreen.route else Navigation.ChatsScreen.route){
        composable(Navigation.AuthScreen.route){ AuthScreen(viewModel = mViewModel, navigator = navigator) }
        composable(Navigation.ChatsScreen.route){ ChatsScreen(viewModel = mViewModel, navigator = navigator) }
        composable(Navigation.MessagesScreen.route){ MessagesScreen(viewModel = mViewModel, navigator = navigator) }
    }
}