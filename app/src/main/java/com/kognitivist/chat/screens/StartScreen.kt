package com.kognitivist.chat.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kognitivist.chat.*
import com.kognitivist.chat.data.models.Message
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun StartScreen (viewModel: MainViewModel, navigation: MutableState<String>) {
    val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()
    val login = remember { mutableStateOf(EMPTY) }
    val password = remember { mutableStateOf(EMPTY) }

    val scaffoldState = rememberScaffoldState()
    val isAdded = remember{ mutableStateOf(false) }

    ModalBottomSheetLayout(
    sheetState = bottomSheetState,
    sheetElevation = 8.dp,
    sheetShape = RoundedCornerShape(topEnd = 30.dp),
    sheetContent = {
        Surface{
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp)) {
                Text(text = LOG_IN,
                    fontSize = 18.sp, fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                OutlinedTextField(
                    value = login.value,
                    onValueChange = {login.value = it},
                    label = { Text(text = LOG_IN_TEXT)},
                    isError = login.value.isEmpty())
                OutlinedTextField(
                    value = password.value,
                    onValueChange = {password.value = it},
                    label = { Text(text = PASSWORD_TEXT)},
                    isError = password.value.isEmpty())
                Button(
                    modifier = Modifier.padding(top = 16.dp),
                    onClick = {
                        LOGIN = login.value
                        PASSWORD = password.value
                        viewModel.initDataBase(){
                            Log.d("mylog","Init Database success")
                        }
                        navigation.value = "Chat Screen"
                    },
                    enabled = login.value.isNotEmpty() && password.value.isNotEmpty()
                ) {
                    Text(text = SIGN_IN)
                }
            }
        }
    }
    ){
        Scaffold (
            scaffoldState = scaffoldState,
            modifier = Modifier,
            topBar = {
                TopAppBar (
                    modifier = Modifier){
                    Text("Chat", fontSize = 22.sp)
                    Spacer(Modifier.weight(1f, true))
                    IconButton(onClick = { }) { Icon(Icons.Filled.Search, contentDescription = "Поиск" ) }
                    IconButton(onClick = { }) {Icon(Icons.Filled.Menu, contentDescription = "Меню") }
                }
            },
            bottomBar = {
                BottomAppBar{
                    IconButton(onClick = {  }) { Icon(Icons.Filled.Favorite, contentDescription = "Избранное")}
                    Spacer(Modifier.weight(1f, true))
                    IconButton(onClick = {  }) { Icon(Icons.Filled.Info, contentDescription = "Информация о приложении")}
                }
            },
            floatingActionButton = {
                FloatingActionButton(
                    content = {
                        if(isAdded.value) Icon(Icons.Filled.Clear, contentDescription = "Удалить")
                        else Icon(Icons.Filled.Add, contentDescription = "Добавить") },
                    onClick = {
                        viewModel.addMessage(Message(
                        nameSander = login.value,
                        nameRecipient = "Musia",
                        message = "Hello Musia"
                    )){}}
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
                Button(
                    onClick = {
                        coroutineScope.launch {
                            bottomSheetState.show()
                        }
                    },
                    modifier = Modifier
                        .width(200.dp)
                        .padding(vertical = 8.dp)
                ) {
                    Text(text = "Регистрация")
                }
                /*Button(
                onClick = {
                          TODO()
                },
                modifier = Modifier
                    .width(200.dp)
                    .padding(vertical = 8.dp)
            ) {
                Text(text = "Войти")
            }*/
            }
        }

    }
}