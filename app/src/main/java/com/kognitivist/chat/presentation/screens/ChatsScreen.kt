package com.kognitivist.chat.presentation.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.kognitivist.chat.data.database.FirebaseRepository
import com.kognitivist.chat.tools.CHAT_ID
import com.kognitivist.chat.tools.EMPTY
import com.kognitivist.chat.presentation.MainViewModel
import com.kognitivist.chat.domain.models.Chat
import com.kognitivist.chat.domain.navigation.Navigation
import com.kognitivist.chat.domain.usecases.CreateChatUseCase
import com.kognitivist.chat.tools.REPOSITORY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ChatsScreen (viewModel: MainViewModel, navigator: NavHostController){

    val scaffoldState = rememberScaffoldState()
    val chatName = remember { mutableStateOf(EMPTY) }
    val chatMail = remember { mutableStateOf(EMPTY) }
    val allChats: List<Chat> = viewModel.readAllChatCurrentUser().observeAsState(setOf()).value.toList()
    val coroutineScope = rememberCoroutineScope()
    val openDialogAddChat = remember { mutableStateOf(false) }


    if (openDialogAddChat.value) {
        AlertDialog(
            onDismissRequest = {
                openDialogAddChat.value = false
            },
            title = { Text(text = "Добавить чат") },
            text = {
                Column(verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier) {
                    OutlinedTextField(
                        value = chatName.value,
                        onValueChange = {chatName.value = it},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        label = { Text(text = "Chat name", textAlign = TextAlign.Center)}
                    )
                    OutlinedTextField(
                        value = chatMail.value,
                        onValueChange = {chatMail.value = it},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        label = { Text(text = "Chat mail", textAlign = TextAlign.Center)})
                }
            },
            buttons = {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            launch {
                                CreateChatUseCase().execute(
                                    chat = Chat(
                                        name = chatName.value.ifEmpty { "unknown" },
                                        mailCompanionFirst = chatMail.value,
                                        mailCompanionSecond = viewModel.getMailUser()
                                    ),
                                    repository = REPOSITORY){}
                            }.join()
                            chatName.value = EMPTY
                            chatMail.value = EMPTY
                            openDialogAddChat.value = false
                        }
                        /*viewModel.addChat(
                            Chat(
                                name = chatName.value.ifEmpty { "unknown" },
                                mailCompanionFirst = chatMail.value,
                                mailCompanionSecond = viewModel.getMailUser()
                            )
                        ){}*/

                    }
                ) {
                    Text("Добавить", fontSize = 22.sp)
                }
            }
        )
    }




    Scaffold (
        scaffoldState = scaffoldState,
        modifier = Modifier,
        topBar = {
            TopAppBar (
                modifier = Modifier){
                Text(viewModel.getMailUser(), fontSize = 22.sp)
                Spacer(Modifier.weight(1f, true))
                IconButton(onClick = {
                    navigator.navigate(route = Navigation.AuthScreen.route)
                    viewModel.signOut {}
                }) { Icon(Icons.Filled.ExitToApp, contentDescription = "Выход из аккаунте" ) }
                IconButton(onClick = { }) { Icon(Icons.Filled.Search, contentDescription = "Поиск" ) }
                IconButton(onClick = { }) { Icon(Icons.Filled.Menu, contentDescription = "Меню") }
            }
        },
        bottomBar = {
            BottomAppBar{
                IconButton(onClick = {  }) { Icon(Icons.Filled.Favorite, contentDescription = "Избранное") }
                Spacer(Modifier.weight(1f, true))
                IconButton(onClick = {  }) { Icon(Icons.Filled.Info, contentDescription = "Информация о приложении") }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                content = {
                    Icon(Icons.Filled.Add, contentDescription = "Добавить") },
                onClick = {
                        openDialogAddChat.value = true
                }
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LazyColumn(modifier = Modifier
                .fillMaxWidth()
                .weight(1f)) {
                items(allChats) {chat ->
                    ChatsItem(
                        chat = chat,
                        navigator = navigator,
                        viewModel = viewModel)
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChatsItem(chat: Chat, navigator: NavHostController, viewModel: MainViewModel){

    val openDialogRemoveChat = remember { mutableStateOf(false) }
    navigator.popBackStack(route = Navigation.AuthScreen.route, inclusive = true)

    if (openDialogRemoveChat.value) {
        AlertDialog(
            onDismissRequest = {
                openDialogRemoveChat.value = false
            },
            title = { Text(text = "Удалить чат?") },
            text = {},
            buttons = {
                Button(
                    onClick = {
                        viewModel.addChat(
                            Chat(
                                name = chat.name,
                                mailCompanionFirst = if (
                                    chat.mailCompanionFirst == viewModel.getMailUser()
                                )"" else chat.mailCompanionFirst,
                                mailCompanionSecond = if (
                                    chat.mailCompanionSecond == viewModel.getMailUser()
                                )"" else chat.mailCompanionSecond
                            )
                        ){}
                        openDialogRemoveChat.value = false
                    }
                ) {
                    Text("Удалить", fontSize = 22.sp)
                }
            }
        )
    }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)) {
        Card(modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            .combinedClickable(
                onClick = {
                    navigator.navigate(route = Navigation.MessagesScreen.route){
                        popUpTo(route = Navigation.ChatsScreen.route){inclusive = false}
                    }
                    CHAT_ID = chat
                },
                onLongClick = {
                    CHAT_ID = chat
                    openDialogRemoveChat.value = true
                }

            )
            ,backgroundColor = Color.Blue)
        {
            Column(modifier = Modifier
                .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = chat.name, color = Color.White, fontSize = 25.sp
                    ,fontStyle = FontStyle.Italic
                )
            }
        }
    }
}