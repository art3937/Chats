package chatResources

import exceptions.NotFoundException

object ChatService {
    private var chatsList = mutableMapOf<Int, Chat>()
    private var messages = mutableMapOf<String, String>()
    private var count = 1

    fun getUnreadChatsCount() = chatsList.count { it -> it.value.messageList.any { !it.read } }

    fun addMessage(idCompanion: Int, message: Message) {
        chatsList.getOrPut(idCompanion) { Chat() }.messageList.plusAssign(message.copy(idMessage = count++))
    }

    fun getChats() =
        chatsList.forEach { it -> println("пользователь ${it.key} ${it.value.messageList.filter { it.active }}") }

    fun lastMessage(): MutableMap<String, String> {
        chatsList.forEach { it ->
            if (it.value.messageList.any() { it.active }) {
                messages["пользователь ${it.key} "] =
                    " сообщения ${it.value.messageList.last() { it.active }.messageText}"
            } else {
                messages["пользователь ${it.key} "] = " Нет Сообщений"
            }
        }
        return messages
    }

    fun getChatMessageCount(idCompanion: Int, countMessages: Int): List<Message> {
        val messages: List<Message> =
            (chatsList[idCompanion] ?: throw NotFoundException("нет такого ид")).messageList.take(countMessages)
                .filter { it.active }
        chatsList[idCompanion]?.messageList?.take(countMessages)?.forEach { it.read = true }
        return messages.ifEmpty { throw NotFoundException("сообщений нет") }
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

    fun deleteChat(idCompanion: Int) = chatsList.remove(idCompanion) ?: throw NotFoundException("нет такого чата")

    fun clear() {
        chatsList = mutableMapOf()
        count = 1
        messages = mutableMapOf()
    }
}