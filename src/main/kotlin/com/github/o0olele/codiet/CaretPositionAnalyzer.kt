package com.github.o0olele.codiet

/**
 * Determines if the caret position, represented by `offset` in the given `String`, should be skipped for code
 * completion suggestions. This decision is based on the syntactical context immediately surrounding the caret,
 * such as specific characters or structures that typically do not require suggestions.
 *
 * @param offset The current position of the caret within the text.
 * @return `true` if the caret position should be skipped for suggestions, `false` otherwise.
 */
fun String.shouldBeSkippedOnPosition(offset: Int) = checkElementUnderCaret(this, offset) {
    afterSemicolon()
            || afterLBrace()
            || afterRBrace()
            || beforeLParenthesis()
            || afterRParenthesis()
            || insideIdentifier()
            || atTheBoundaryOfStringLiteral()
            || atTheBoundaryOfCharLiteral()
            || atTheEndOfDigitalLiteral()
}

/**
 * Checks if the caret is immediately after a semicolon.
 *
 * @return `true` if the caret is after a semicolon, `false` otherwise.
 */
private fun Pair<Char, Char>.afterSemicolon(): Boolean {
    return first == ';'
}

/**
 * Checks if the caret is immediately after a left brace.
 *
 * @return `true` if the caret is after a left brace, `false` otherwise.
 */
private fun Pair<Char, Char>.afterLBrace(): Boolean {
    return first == '{'
}

/**
 * Checks if the caret is immediately after a right brace.
 *
 * @return `true` if the caret is after a right brace, `false` otherwise.
 */
private fun Pair<Char, Char>.afterRBrace(): Boolean {
    return first == '}'
}

/**
 * Checks if the caret is immediately before a left parenthesis.
 *
 * @return `true` if the caret is before a left parenthesis, `false` otherwise.
 */
private fun Pair<Char, Char>.beforeLParenthesis(): Boolean {
    return second == '('
}

/**
 * Checks if the caret is immediately after a right parenthesis.
 *
 * @return `true` if the caret is after a right parenthesis, `false` otherwise.
 */
private fun Pair<Char, Char>.afterRParenthesis(): Boolean {
    return first == ')'
}

/**
 * Checks if the caret is inside an identifier, determined by both characters around the caret being digit or letter.
 *
 * @return `true` if the caret is inside an identifier, `false` otherwise.
 */
private fun Pair<Char, Char>.insideIdentifier(): Boolean {
    return first.isLetterOrDigit() && second.isLetterOrDigit()
}

/**
 * Checks if the caret is at the boundary of a string literal. This is identified by checking if either
 * character immediately before or after the caret is a double quote character (`"`).
 *
 * @return `true` if the caret is immediately before or after a double quote, indicating the boundary of a string literal; `false` otherwise.
 */
private fun Pair<Char, Char>.atTheBoundaryOfStringLiteral(): Boolean {
    return first == '"' || second == '"'
}

/**
 * Checks if the caret is at the boundary of a char literal. This is identified by checking if either
 * character immediately before or after the caret is a single quote character (`'`).
 *
 * @return `true` if the caret is immediately before or after a single quote, indicating the boundary of a char literal; `false` otherwise.
 */
private fun Pair<Char, Char>.atTheBoundaryOfCharLiteral(): Boolean {
    return first == '\'' || second == '\''
}

/**
 * Checks if the caret is at the end of a digital literal. This method checks if the character immediately
 * before the caret is a digit, 'L' (denoting a long literal), or 'f' (denoting a float literal), and the character
 * immediately after the caret is not part of a digital literal (i.e., not a digit or a letter indicating a type),
 * but could be a semicolon, space, or an arithmetic or logical operator.
 *
 * @return `true` if the caret is immediately after a digital literal and before a character that is not part of
 * a digital literal; `false` otherwise.
 */
private fun Pair<Char, Char>.atTheEndOfDigitalLiteral(): Boolean {
    fun Char.isArithmeticOperator() = this == '+' || this == '-' || this == '*' || this == '/'
    fun Char.isLogicalOperator() = this == '&' || this == '|' || this == '!' || this == '=' || this == '<' || this == '>'
    return (first.isDigit() || first == 'L' || first == 'f')
            && (second == ';' || second == ' ' || second.isArithmeticOperator() || second.isLogicalOperator())
}

/**
 * Evaluates the text at the caret position against a set of criteria to determine if the position should be skipped.
 * This function uses a pair of characters surrounding the caret to make this determination.
 *
 * @param text The entire text of the document.
 * @param offset The current position of the caret within the text.
 * @param satisfySkippedCriteria A lambda function that defines the criteria for skipping the caret position.
 * @return `true` if the caret position satisfies the criteria for being skipped, `false` otherwise.
 */
private fun checkElementUnderCaret(
    text: String,
    offset: Int,
    satisfySkippedCriteria: Pair<Char, Char>.() -> Boolean
): Boolean {
    val beforeCaretChar = if (offset in 0..text.lastIndex) text[offset] else '\n'
    val afterCaretChar = if (offset + 1 in 0..text.lastIndex) text[offset + 1] else '\n'
    return (beforeCaretChar to afterCaretChar).satisfySkippedCriteria()
}