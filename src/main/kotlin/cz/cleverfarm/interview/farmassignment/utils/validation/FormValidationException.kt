package cz.cleverfarm.interview.farmassignment.utils.validation

class FormValidationException(val field: String?, message: String) : Exception(message) {
    constructor(message: String) : this(null, message)
}