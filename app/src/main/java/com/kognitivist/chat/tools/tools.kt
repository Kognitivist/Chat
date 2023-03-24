package com.kognitivist.chat.tools

fun formatMail(mail: String): String {
    return mail.replace(".", ",")
}
