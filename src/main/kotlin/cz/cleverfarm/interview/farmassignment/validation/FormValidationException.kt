package cz.cleverfarm.interview.farmassignment.validation

/** Exception used when user input does not pass business validation. */
class FormValidationException(
    /** Input's field which was detected as invalid. `null` means input is invalid as whole. */
    val field: String?,

    /** Description of the invalid value. */
    message: String
) : Exception(message) {
    constructor(message: String) : this(null, message)
}