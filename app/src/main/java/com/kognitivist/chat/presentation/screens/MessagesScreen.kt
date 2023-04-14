package com.kognitivist.chat.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.kognitivist.chat.tools.CHAT_ID
import com.kognitivist.chat.tools.EMPTY
import com.kognitivist.chat.presentation.MainViewModel
import com.kognitivist.chat.domain.models.Message
import com.kognitivist.chat.presentation.ui.theme.myMessage
import com.kognitivist.chat.presentation.ui.theme.notMyMessage

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MessagesScreen (viewModel: MainViewModel, navigator: NavHostController) {

    val scaffoldState = rememberScaffoldState()
    val message = remember { mutableStateOf(EMPTY) }
    val allMessages: List<Message> = viewModel.readAllMessages().observeAsState(listOf()).value


    Scaffold (
        scaffoldState = scaffoldState,
        modifier = Modifier,
        topBar = {
            TopAppBar (
                modifier = Modifier
            ){
                Text(viewModel.getMailUser()?: "Chat", fontSize = 22.sp)
                Spacer(Modifier.weight(1f, true))
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
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)) {
                items(allMessages) {message ->
                    MessagesItem(
                        message = message,
                        arrangement = if (message.mailSender == viewModel.getMailUser()){
                            Arrangement.End
                        }else Arrangement.Start,
                        colorMessage = if (message.mailSender == viewModel.getMailUser()){
                            myMessage
                        }else notMyMessage
                    )
                }
            }
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = message.value,
                    onValueChange = {message.value = it},
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .padding(vertical = 10.dp))
                Icon(
                    Icons.Filled.Send,
                    contentDescription = "Отправить",
                    modifier = Modifier.clickable {
                        viewModel.addMessage(
                            CHAT_ID,
                            Message(
                                nameSender = viewModel.getMailUser()?: "",
                                mailSender = viewModel.getMailUser()?: "",
                                mailRecipient = CHAT_ID.mailCompanionFirst,
                                nameRecipient = CHAT_ID.name,
                                message = message.value)
                        ){}
                        message.value = EMPTY
                    })
            }

            Spacer(modifier = Modifier.weight(0.2f))

        }
    }
}

@Composable
fun MessagesItem(message: Message, arrangement: Arrangement.Horizontal, colorMessage: Color ){
    val messageId = message.firebaseId

    Row(
        horizontalArrangement = arrangement,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Card(modifier = Modifier
            .fillMaxWidth(0.5f)
            .height(IntrinsicSize.Max)
            .padding(end = 20.dp),
            backgroundColor = colorMessage,
            shape = RoundedCornerShape(20)
        )
        {
            Text(
                text = message.message,
                color = Color.Black,
                fontSize = 15.sp,
                textAlign = TextAlign.Center)
        }
    }


}
