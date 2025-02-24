package chat.willow.kale.irc.message.rfc1459.rpl

import chat.willow.kale.core.message.IrcMessage
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.BeforeTest
import kotlin.test.Test

class Rpl005MessageTests {

    private lateinit var messageParser: Rpl005Message.Message.Parser
    private lateinit var messageSerialiser: Rpl005Message.Message.Serialiser

    @BeforeTest fun setUp() {
        messageParser = Rpl005Message.Message.Parser
        messageSerialiser = Rpl005Message.Message.Serialiser
    }

    @Test fun test_parse_SingleToken_NoValue() {
        val message = messageParser.parse(IrcMessage(command = "005", prefix = "imaginary.bunnies.io", parameters = listOf("test-nickname", "KEY")))

        assertEquals(Rpl005Message.Message(source = "imaginary.bunnies.io", target = "test-nickname", tokens = mapOf("KEY" to null)), message)
    }

    @Test fun test_parse_SingleToken_NoValue_ButEqualsPresent() {
        val message = messageParser.parse(IrcMessage(command = "005", prefix = "imaginary.bunnies.io", parameters = listOf("test-nickname", "KEY=")))

        assertEquals(Rpl005Message.Message(source = "imaginary.bunnies.io", target = "test-nickname", tokens = mapOf("KEY" to null)), message)
    }

    @Test fun test_parse_SingleToken_WithValue() {
        val message = messageParser.parse(IrcMessage(command = "005", prefix = "imaginary.bunnies.io", parameters = listOf("test-nickname", "KEY=VALUE")))

        assertEquals(Rpl005Message.Message(source = "imaginary.bunnies.io", target = "test-nickname", tokens = mapOf("KEY" to "VALUE")), message)
    }

    @Test fun test_parse_MultipleTokens_MultipleTypesOfValues() {
        val message = messageParser.parse(IrcMessage(command = "005", prefix = "imaginary.bunnies.io", parameters = listOf("test-nickname", "KEY=VALUE", "KEY2", "KEY3=", "KEY4=\uD83D\uDC30")))

        assertEquals(Rpl005Message.Message(source = "imaginary.bunnies.io", target = "test-nickname", tokens = mapOf("KEY" to "VALUE", "KEY2" to null, "KEY3" to null, "KEY4" to "\uD83D\uDC30")), message)
    }
    
    @Test fun test_parse_TooFewParameters() {
        val messageOne = messageParser.parse(IrcMessage(command = "005", parameters = listOf()))
        val messageTwo = messageParser.parse(IrcMessage(command = "005", parameters = listOf("test-nickname2")))

        assertNull(messageOne)
        assertNull(messageTwo)
    }

    @Test fun test_serialise_SingleToken_NoValue() {
        val message = messageSerialiser.serialise(Rpl005Message.Message(source = "imaginary.bunnies.io", target = "test-nickname", tokens = mapOf("KEY" to null)))

        assertEquals(IrcMessage(command = "005", prefix = "imaginary.bunnies.io", parameters = listOf("test-nickname", "KEY")), message)
    }

    @Test fun test_serialise_SingleToken_NoValue_ButEqualsPresent() {
        val message = messageSerialiser.serialise(Rpl005Message.Message(source = "imaginary.bunnies.io", target = "test-nickname", tokens = mapOf("KEY" to null)))

        assertEquals(IrcMessage(command = "005", prefix = "imaginary.bunnies.io", parameters = listOf("test-nickname", "KEY")), message)
    }

    @Test fun test_serialise_SingleToken_WithValue() {
        val message = messageSerialiser.serialise(Rpl005Message.Message(source = "imaginary.bunnies.io", target = "test-nickname", tokens = mapOf("KEY" to "VALUE")))

        assertEquals(IrcMessage(command = "005", prefix = "imaginary.bunnies.io", parameters = listOf("test-nickname", "KEY=VALUE")), message)
    }

    @Test fun test_serialise_MultipleTokens_MultipleTypesOfValues() {
        val message = messageSerialiser.serialise(Rpl005Message.Message(source = "imaginary.bunnies.io", target = "test-nickname", tokens = mapOf("KEY" to "VALUE", "KEY2" to null, "KEY3" to null, "KEY4" to "\uD83D\uDC30")))

        assertEquals(IrcMessage(command = "005", prefix = "imaginary.bunnies.io", parameters = listOf("test-nickname", "KEY=VALUE", "KEY2", "KEY3", "KEY4=\uD83D\uDC30")), message)
    }
    
}