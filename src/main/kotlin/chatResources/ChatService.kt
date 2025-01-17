package chatResources

import exceptions.NotFoundException

object ChatService {
    private var chatsList = mutableMapOf<Int, Chat>()
    private var messages = mutableMapOf<String, String>()
    private var count = 1

    fun getUnreadChatsCount() = chatsList.count { it -> it.value.messageList.any { !it.read  } }

    fun addMessage(idCompanion: Int, message: Message) {
        chatsList.getOrPut(idCompanion) { Chat() }.messageList.plusAssign(message.copy(idMessage = count++))
    }

    fun getChats() =
        chatsList.forEach { it -> println("пользователь ${it.key} ${it.value.messageList.filter { it.active }}") }

    fun lastMessage(): MutableMap<String, String> {
        chatsList.forEach { it ->
            if (it.value.messageList.any() { it.active }) {
                messages["пользователь ${it.key} "] = " сообщения ${it.value.messageList.last() { it.active }.messageText}"
            } else {
                messages["пользователь ${it.key} "] = " Нет Сообщений"
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
        return messages ?: throw NotFoundException("Нет сообщений")
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

    fun clear() {
        chatsList = mutableMapOf()
        count = 1
        messages = mutableMapOf()
    }
}