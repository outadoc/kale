package chat.willow.kale.irc.message

import chat.willow.kale.core.message.IrcMessage
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.Test

class IrcMessageParserTests {

    // Properly formed test cases

    @Test fun test_singleCommand() {
        val message = parseLine("TEST")

        assertEquals(message, IrcMessage(command = "TEST"))
    }

    @Test fun test_prefixAndCommand() {
        val message = parseLine(":user!host@server TEST")

        assertEquals(message, IrcMessage(prefix = "user!host@server", command = "TEST"))
    }

    @Test fun test_prefixCommandAndParameters() {
        val message = parseLine(":user!host@server TEST some parameters :And some trailing parameters! ")

        assertEquals(message, IrcMessage(prefix = "user!host@server", command = "TEST", parameters = listOf("some", "parameters", "And some trailing parameters! ")))
    }

    @Test fun test_parametersStartingColonIsPreserved() {
        val message = parseLine("TEST ::Trailing parameters")

        assertEquals(message, IrcMessage(command = "TEST", parameters = listOf(":Trailing parameters")))
    }

    @Test fun test_commandAndTrailingParameters() {
        val message = parseLine("TEST :Trailing parameters")

        assertEquals(message, IrcMessage(command = "TEST", parameters = listOf("Trailing parameters")))
    }

    @Test fun test_trailingWhitespaceRemoved() {
        val message = parseLine("WHITESPACETEST parameter1 parameter2    ")

        assertEquals(message, IrcMessage(command = "WHITESPACETEST", parameters = listOf("parameter1", "parameter2")))
    }

    @Test fun test_trailingWhitespaceAfterColonIsPreserved() {
        val message = parseLine("TEST parameter1 parameter2  : ")

        assertEquals(message, IrcMessage(command = "TEST", parameters = listOf("parameter1", "parameter2", " ")))
    }

    @Test fun test_trailingColonInterpretedAsSingleParameter() {
        val message = parseLine("TEST :")

        assertEquals(message, IrcMessage(command = "TEST", parameters = listOf("")))
    }

    @Test fun test_whitespaceAfterCommandIsRemoved() {
        val message = parseLine("TEST      parameter1")

        assertEquals(message, IrcMessage(command = "TEST", parameters = listOf("parameter1")))
    }

    @Test fun test_extremeWhitespace() {
        val message = parseLine(":x ONE        TWO          THREE :  FOUR       FIVE     ")

        assertEquals(message, IrcMessage(prefix = "x", command = "ONE", parameters = listOf("TWO", "THREE", "  FOUR       FIVE     ")))
    }

    @Test fun test_tags_specExample() {
        val message = parseLine("@aaa=bbb;ccc;example.com/ddd=eee :nick!ident@host.com PRIVMSG me :Hello")

        assertEquals(message, IrcMessage(tags = mapOf("aaa" to "bbb", "ccc" to null, "example.com/ddd" to "eee"), prefix = "nick!ident@host.com", command = "PRIVMSG", parameters = listOf("me", "Hello")))
    }

    @Test fun test_tags_singleTagNoValue() {
        val message = parseLine("@test TEST")

        assertEquals(message, IrcMessage(tags = mapOf("test" to null), command = "TEST"))
    }

    @Test fun test_tags_whitespaceRemoved() {
        val message = parseLine("@test=value1    TEST")

        assertEquals(message, IrcMessage(tags = mapOf("test" to "value1"), command = "TEST"))
    }

    @Test fun test_tag_keyButEmptyValue() {
        val message = parseLine("@test= TEST")

        assertEquals(message, IrcMessage(tags = mapOf("test" to ""), command = "TEST"))
    }

    @Test fun test_tag_UnescapesValues() {
        val message = parseLine("@key=\\:\\s\\\\\\r\\nabc TEST")

        assertEquals(IrcMessage(tags = mapOf("key" to "; \\\r\nabc"), command = "TEST"), message)
    }

    // Malformed test cases

    @Test fun test_emptyString() {
        val message = parseLine("")

        assertNull(message)
    }

    @Test fun test_tooLong() {
        val message = parseLine("A".repeat(8192 + 1))

        assertNull(message)
    }

    @Test fun test_whitespaceOnly() {
        val message = parseLine("    ")

        assertNull(message)
    }

    @Test fun test_whitespaceThenCommand() {
        val message = parseLine("  TEST")

        assertNull(message)
    }

    @Test fun test_colonsOnly() {
        val message = parseLine(":::::")

        assertNull(message)
    }

    @Test fun test_prefix_ColonOnly() {
        val message = parseLine(": TEST")

        assertNull(message)
    }

    // Helper functions

    private fun parseLine(line: String): IrcMessage? {
        return IrcMessageParser.parse(line)
    }
}