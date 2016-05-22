package engineer.carrot.warren.kale.irc

object CharacterCodes {
    val LF: Char = 0xA.toChar()
    val CR: Char = 0xD.toChar()
    val AT: Char = 0x40.toChar()
    val SPACE: Char = 0x20.toChar()
    val EXCLAM: Char = 0x21.toChar()
    val LEFT_BRACKET: Char = 0x28.toChar()
    val RIGHT_BRACKET: Char = 0x29.toChar()
    val PLUS: Char = 0x2B.toChar()
    val COMMA: Char = 0x2C.toChar()
    val MINUS: Char = 0x2D.toChar()
    val COLON: Char = 0x3A.toChar()
    val SEMICOLON: Char = 0x3B.toChar()
    val EQUALS: Char = 0x3D.toChar()
    val BACKSLASH: Char = 0x5C.toChar()

    val CTCP = '\u0001'
}
