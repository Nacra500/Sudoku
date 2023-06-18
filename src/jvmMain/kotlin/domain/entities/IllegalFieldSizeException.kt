package domain.entities

/**
 *
 * is thrown when the given arguments do not belong to a sudoku field.
 *
 */
class IllegalFieldSizeException(text: String?) : IllegalArgumentException(text)
