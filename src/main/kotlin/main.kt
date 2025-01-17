import chatResources.*
import exceptions.NotFoundException

fun main() {
    ChatService.addMessage(1, Message("hello"))
    ChatService.addMessage(1, Message("by"))
    ChatService.addMessage(2, Message("hello2"))
    ChatService.addMessage(3, Message("help"))
//    println(ChatService.getChatMessage(1))
//    println(ChatService.getChatMessage(2))

    println(ChatService.deleteMessage(3))
 println(ChatService.lastMessage())
    println(ChatService.getUnreadChatsCount())
//    println(ChatService.deleteChat(3))

ChatService.getChats()
}






