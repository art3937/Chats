import chatResources.ChatService
import chatResources.Message

fun main() {
    ChatService.addMessage(1, Message("hello"))
    ChatService.addMessage(1, Message("by"))
    ChatService.addMessage(2, Message("hello2"))
    ChatService.addMessage(3, Message("help"))
    println(ChatService.deleteMessage(4))
    println(ChatService.getChatMessageCount(1, 2))
//  println(ChatService.getChatMessage(2))
// println(ChatService.lastMessage())
//  println(ChatService.getUnreadChatsCount())
//  println(ChatService.deleteChat(1))
   ChatService.getChats()
}






