package chatResources

import exceptions.NotFoundException

object ChatService {
    private var chatsList = mutableMapOf<Int, Chat>()
    private var messages = mutableMapOf<String, String>()
    private var count = 1

    fun getUnreadChatsCount() = chatsList.asSequence().count { it -> it.value.messageList.any { !it.read } }

    fun addMessage(idCompanion: Int, message: Message) {
        chatsList.getOrPut(idCompanion) { Chat() }.messageList.plusAssign(message.copy(idMessage = count++))
    }

    fun getChats() =
        chatsList.asSequence()
            .forEach { it -> println("пользователь ${it.key} ${it.value.messageList.filter { it.active }}") }

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
        val messages: Sequence<Message> =
            (chatsList[idCompanion] ?: throw NotFoundException("нет такого ид")).messageList.asSequence()
                .take(countMessages)
                .filter { it.active }.onEach { it.read = true }
        return messages.ifEmpty { throw NotFoundException("сообщений нет") }.toList()
    }

    fun deleteMessage(idMessage: Int): Boolean {
        var resultDelete = false
        chatsList.asSequence().forEach { it ->
            it.value.messageList.asSequence().forEach {
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
        val pair = Pair(1,1)
    }

    fun getUnreadMessage() = chatsList.values.fold(mutableListOf<Message>()){ acc, chat -> (acc + chat.messageList.filter { !it.read && it.active }).toMutableList() }
}

class Pair <A, B>(val a: A, val b: B){

}