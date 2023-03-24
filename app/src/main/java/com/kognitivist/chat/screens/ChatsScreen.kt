package com.kognitivist.chat.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kognitivist.chat.CHAT_ID
import com.kognitivist.chat.EMPTY
import com.kognitivist.chat.MainViewModel
import com.kognitivist.chat.data.models.Chat

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ChatsScreen (viewModel: MainViewModel, navigation: MutableState<String>){

    val scaffoldState = rememberScaffoldState()
    val chatName = remember { mutableStateOf(EMPTY) }
    val chatMail = remember { mutableStateOf(EMPTY) }
    val allChats: List<Chat> = viewModel.readAllChatCurrentUser().observeAsState(setOf()).value.toList()
    val openDialog = remember { mutableStateOf(false) }

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
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
                        viewModel.addChat(
                            Chat(
                                name = chatName.value,
                                mail = chatMail.value,
                                myId = "${ chatMail.value } && ${ viewModel.getMailUser() }"
                            )
                        ){}
                        chatName.value = EMPTY
                        chatMail.value = EMPTY
                        openDialog.value = false
                        Log.d("AllChats", "")
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
                Text(viewModel.getMailUser()?: "Chat", fontSize = 22.sp)
                Spacer(Modifier.weight(1f, true))
                IconButton(onClick = {
                    viewModel.signOut {  }
                    navigation.value = "Auth Screen"
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
                        openDialog.value = true
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
                    ChatsItem(chat = chat, navigation)
                }
            }
        }
    }
}

@Composable
fun ChatsItem(chat: Chat, navigation: MutableState<String>){

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)) {
        Card(modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            .clickable {
                navigation.value = "Messages Screen"
                CHAT_ID = chat
            }
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