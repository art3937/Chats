package chatResources;

data class Message
        (
                val messageText: String,
                val idMessage: Int = 0,
                var read: Boolean = false,
                var active: Boolean = true
        )