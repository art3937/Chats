fun main() {
    ChatService.addMessage(1, Message("hello"))
    ChatService.addMessage(1, Message("by"))
    ChatService.addMessage(2, Message("hello2"))
    ChatService.addMessage(3, Message("help"))
    println(ChatService.getChatMessage(1))
    println(ChatService.getChatMessage(2))
    println(ChatService.deleteMessage(0))
    println(ChatService.deleteMessage(1))
    println(ChatService.lastMessage())
    println(ChatService.getUnreadChatsCount())
    println(ChatService.deleteChat(3))
    ChatService.getChats()
}

object ChatService {
    private var chatsList = mutableMapOf<Int, Chat>()
    private var messages = mutableMapOf<String, String>()
    private var count = 0

    fun getUnreadChatsCount() = chatsList.filter { it -> it.value.messageList.any { !it.read } }

    fun addMessage(idCompanion: Int, message: Message) {
        chatsList.getOrPut(idCompanion) { Chat() }.messageList.plusAssign(message.copy(idMessage = count++))
    }

    fun getChats() =
        chatsList.forEach { it -> println("пользователь ${it.key}  ${it.value.messageList.filter { it.active }}") }

    fun lastMessage(): MutableMap<String, String> {
        chatsList.forEach { it ->
            if (it.value.messageList.any() { it.active }) {
                messages.getOrPut("пользователь ${it.key} ") { " сообщения ${it.value.messageList.last() { it.active }.messageText}" }
            } else {
                messages.getOrPut("пользователь ${it.key} ") { " Нет Сообщений" }
            }
        }
        return messages
    }

    fun getChatMessage(idCompanion: Int): MutableList<Message> {
        var messages: MutableList<Message>? = null
        chatsList.forEach { it ->
            if (it.key == idCompanion) {
                it.value.messageList.forEach {
                    it.read = true
                    messages = chatsList.getValue(idCompanion).messageList
                }
            }
        }
        return messages ?: throw Exception("Нет сообщений")
    }

    fun deleteMessage(idMessage: Int): Boolean {
        var resultDelete = false
        chatsList.forEach { it ->
            it.value.messageList.forEach {
                if (it.idMessage == idMessage) {
                    it.active = false
                    resultDelete = true
                }
            }
        }
        return resultDelete
    }

    fun deleteChat(idCompanion: Int): Boolean {
        var resultDelete = false
        chatsList.forEach {
            if (it.key == idCompanion) {
                chatsList.remove(idCompanion)
                resultDelete = true
            }
        }
        return resultDelete
    }
}

data class Chat(
    val messageList: MutableList<Message> = mutableListOf(),
)

data class Message
    (
    val messageText: String,
    val idMessage: Int = 0,
    var read: Boolean = false,
    var active: Boolean = true
)
