import chatResources.ChatService
import chatResources.Message
import exceptions.NotFoundException
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.assertTrue

class ChatServiceTest {
    private var output: ByteArrayOutputStream? = ByteArrayOutputStream()

    @Before
    fun setUpStreams() {
        System.setOut(output?.let { PrintStream(it) })
        ChatService.clear()
    }

    @Test
    fun getUnreadChatsCount() {
        ChatService.addMessage(4, Message("hello"))
val count = ChatService.getUnreadChatsCount()
        assertTrue { count>0 }
    }

    @Test
    fun addMessage() {
        ChatService.addMessage(4, Message("hello"))
        assertTrue(ChatService.lastMessage().isNotEmpty())
    }

    @Test
    fun getChats() {
        ChatService.addMessage(4, Message("hello"))
        ChatService.getChats()
        assertTrue(output.toString().isNotEmpty())

    }

    @Test
    fun lastMessage() {
        ChatService.addMessage(4, Message("hello"))
        val chats = ChatService.lastMessage()
        assertTrue { chats.isNotEmpty() }
    }

    @Test
    fun getChatMessage() {
        ChatService.addMessage(4, Message("hello"))
        val messages = ChatService.getChatMessage(4)
        assertTrue { messages.isNotEmpty() }
    }

    @Test
    fun deleteMessage() {
        ChatService.addMessage(4, Message("hello"))
        assertTrue { ChatService.deleteMessage(1) }
    }

    @Test
    fun deleteChat() {
        ChatService.addMessage(4, Message("hello"))
        assertTrue { ChatService.deleteChat(4) }
    }

    @Test(expected = NotFoundException::class)
    fun shouldThrow() {
        ChatService.addMessage(4, Message("hello"))
        ChatService.getChatMessage(3)
    }

    @After
    fun cleanUpStreams() {
        System.setOut(null)
    }
}