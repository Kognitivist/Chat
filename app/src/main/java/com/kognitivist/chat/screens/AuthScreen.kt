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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AuthScreen (viewModel: MainViewModel, navigation: MutableState<String>) {

    val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()
    val login = remember { mutableStateOf("qwerty@mail.ru") }
    val password = remember { mutableStateOf("qwerty") }

    val scaffoldState = rememberScaffoldState()
    val textButtonEnter = remember { mutableStateOf(EMPTY) }
    val openDialog = remember { mutableStateOf(false) }


    Log.d("MuLog", viewModel.getMailUser())

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text(text = "Подождите") },
            text = {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    CircularProgressIndicator()
                }
            },
            buttons = {}
        )
    }

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetElevation = 8.dp,
        sheetShape = RoundedCornerShape(topEnd = 30.dp),
        sheetContent = {
            Surface {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp)
                ) {
                    Text(
                        text = LOG_IN,
                        fontSize = 18.sp, fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    OutlinedTextField(
                        value = login.value,
                        onValueChange = { login.value = it },
                        label = { Text(text = LOG_IN_TEXT) },
                        isError = login.value.isEmpty()
                    )
                    OutlinedTextField(
                        value = password.value,
                        onValueChange = { password.value = it },
                        label = { Text(text = PASSWORD_TEXT) },
                        isError = password.value.isEmpty()
                    )
                    Button(
                        modifier = Modifier.padding(top = 16.dp),
                        onClick = {
                            when (textButtonEnter.value) {
                                "Регистрация" -> {
                                    coroutineScope.launch {
                                        launch(Dispatchers.IO) { openDialog.value = true }.join()
                                        launch(Dispatchers.IO) {
                                            viewModel.registrationOfDataBase(
                                                login = login.value,
                                                password = password.value
                                            ) {}
                                        }.join()
                                        launch(Dispatchers.IO) {
                                            while (viewModel.getMailUser() == "null") {
                                                delay(1000)
                                                viewModel.enterDataBase(
                                                    login = login.value,
                                                    password = password.value,
                                                ) {}
                                                Log.d("registration", viewModel.getMailUser())
                                            }
                                        }.join()
                                        openDialog.value = false
                                        if (viewModel.getMailUser() != "null") {
                                            navigation.value = "Chat Screen"
                                        } else {
                                            navigation.value = "Auth Screen"
                                        }
                                    }

                                }
                                "Войти" -> {
                                    coroutineScope.launch {
                                        launch (Dispatchers.IO) {openDialog.value = true}.join()
                                        launch (Dispatchers.IO) {
                                            while (viewModel.getMailUser() == "null") {
                                                delay(1000)
                                                viewModel.enterDataBase(
                                                    login = login.value,
                                                    password = password.value,
                                                ) {}
                                            }
                                        }.join()
                                        openDialog.value = false
                                        if (viewModel.getMailUser() != "null") {
                                            navigation.value = "Chat Screen"
                                        } else {
                                            navigation.value = "Auth Screen"
                                        }
                                    }
                                }
                                EMPTY -> {}
                            }

                        },
                        enabled = login.value.isNotEmpty() && password.value.isNotEmpty()
                    ) {
                        Text(text = textButtonEnter.value)
                    }
                }
            }
        }
    ) {
        Scaffold(
            scaffoldState = scaffoldState,
            modifier = Modifier,
            topBar = {
                TopAppBar(
                    modifier = Modifier
                ) {
                    Text("Chat", fontSize = 22.sp)
                    Spacer(Modifier.weight(1f, true))
                    IconButton(onClick = { }) {
                        Icon(
                            Icons.Filled.Search,
                            contentDescription = "Поиск"
                        )
                    }
                    IconButton(onClick = { }) {
                        Icon(
                            Icons.Filled.Menu,
                            contentDescription = "Меню"
                        )
                    }
                }
            },
            bottomBar = {
                BottomAppBar {
                    IconButton(onClick = { }) {
                        Icon(
                            Icons.Filled.Favorite,
                            contentDescription = "Избранное"
                        )
                    }
                    Spacer(Modifier.weight(1f, true))
                    IconButton(onClick = { }) {
                        Icon(
                            Icons.Filled.Info,
                            contentDescription = "Информация о приложении"
                        )
                    }
                }
            },
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            textButtonEnter.value = "Регистрация"
                            bottomSheetState.show()
                        }
                    },
                    modifier = Modifier
                        .width(200.dp)
                        .padding(vertical = 8.dp)
                ) {
                    Text(text = "Регистрация")
                }
                Button(
                    onClick = {
                        coroutineScope.launch {
                            textButtonEnter.value = "Войти"
                            bottomSheetState.show()
                        }
                    },
                    modifier = Modifier
                        .width(200.dp)
                        .padding(vertical = 8.dp)
                ) {
                    Text(text = "Войти")
                }
            }
        }

    }
}

