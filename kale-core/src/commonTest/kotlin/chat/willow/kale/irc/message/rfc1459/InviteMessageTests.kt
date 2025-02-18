package chat.willow.kale.irc.message.rfc1459

import chat.willow.kale.core.message.IrcMessage
import chat.willow.kale.irc.prefix.Prefix
import chat.willow.kale.irc.prefix.prefix
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.BeforeTest
import kotlin.test.Test

class InviteMessageTests {

    private lateinit var messageParser: InviteMessage.Message.Parser
    private lateinit var messageSerialiser: InviteMessage.Message.Serialiser

    @BeforeTest fun setUp() {
        messageParser = InviteMessage.Message.Parser
        messageSerialiser = InviteMessage.Message.Serialiser
    }

    @Test fun test_parse_Source_User_Channel() {
        val message = messageParser.parse(IrcMessage(command = "INVITE", prefix = "someone", parameters = listOf("nickname", "#channel")))

        assertEquals(InviteMessage.Message(source = Prefix(nick = "someone"), user = "nickname", channel = "#channel"), message)
    }

    @Test fun test_parse_User_Channel_NoSource() {
        val message = messageParser.parse(IrcMessage(command = "INVITE", prefix = "someone", parameters = listOf("nickname", "#channel")))

        assertEquals(InviteMessage.Message(source = prefix("someone"), user = "nickname", channel = "#channel"), message)
    }

    @Test fun test_parse_TooFewParameters() {
        val messageOne = messageParser.parse(IrcMessage(command = "INVITE", parameters = listOf("nickname")))
        val messageTwo = messageParser.parse(IrcMessage(command = "INVITE", parameters = listOf()))

        assertNull(messageOne)
        assertNull(messageTwo)
    }

    @Test fun test_serialise_Source_User_Channel() {
        val message = messageSerialiser.serialise(InviteMessage.Message(source = Prefix(nick = "source"), user = "user", channel = "channel"))

        assertEquals(IrcMessage(command = "INVITE", prefix = "source", parameters = listOf("user", "channel")), message)
    }

    @Test fun test_serialise_User_Channel_NoSource() {
        val message = messageSerialiser.serialise(InviteMessage.Message(source = prefix("someone"), user = "user", channel = "channel"))

        assertEquals(IrcMessage(command = "INVITE", prefix = "someone", parameters = listOf("user", "channel")), message)
    }

}