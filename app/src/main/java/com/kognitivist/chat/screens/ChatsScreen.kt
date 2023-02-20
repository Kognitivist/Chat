package com.kognitivist.chat.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ChatsScreen (){
    LazyColumn(modifier = Modifier.fillMaxSize()){
        items(20){
            Card(modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(5.dp)) {
                Row(modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Gray),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Солнышко")
                }
            }
        }
    }
}