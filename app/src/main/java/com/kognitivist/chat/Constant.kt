package com.kognitivist.chat

import com.kognitivist.chat.data.database.DataBaseRepository
import com.kognitivist.chat.data.models.Chat


const val FIREBASE_ID = "firebaseId"
const val MY_ID = "myId"
const val NAME_SENDER = "nameSender"
const val MAIL_SENDER = "mailSender"
const val NAME_RECIPIENT = "nameRecipient"
const val MAIL_RECIPIENT = "mailRecipient"
const val MESSAGE = "message"
const val MESSAGES = "messages"

const val NAME_CHAT = "name"
const val MAIL_CHAT = "mail"


const val ADD_NOTE = "Add Note"
const val WHAT_WILL_BE_USED = "What will be used?"
const val FIREBASE_DATABASE = "Firebase database"
const val ID = "Id"
const val NONE = "None"
const val UPDATE ="Update"
const val DELETE ="Delete"
const val GO_BACK ="Go_back"
const val EDIT_NOTE = "Edit note"
const val EMPTY = ""
const val SIGN_IN = "Sign in"
const val LOG_IN = "Log in"
const val LOG_IN_TEXT = "Login"
const val PASSWORD_TEXT = "Password"

lateinit var REPOSITORY: DataBaseRepository
lateinit var CHAT_ID: Chat


