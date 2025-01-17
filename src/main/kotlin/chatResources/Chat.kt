package chatResources

data class Chat(
    val messageList: MutableList<Message> = mutableListOf(),
)