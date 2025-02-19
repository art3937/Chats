import chatResources.ChatService
import chatResources.Message
import exceptions.NotFoundException
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.assertNotNull
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
    fun getChatMessageCount() {
        ChatService.addMessage(4, Message("hello"))
        //ChatService.deleteMessage(1)
        val messages = ChatService.getChatMessageCount(4,1)
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
        assertNotNull ( ChatService.deleteChat(4) )
    }

    @Test(expected = NotFoundException::class)
    fun shouldThrow() {
        ChatService.addMessage(4, Message("hello"))
        ChatService.getChatMessageCount(3,2)
    }

    @After
    fun cleanUpStreams() {
        System.setOut(null)
    }
}