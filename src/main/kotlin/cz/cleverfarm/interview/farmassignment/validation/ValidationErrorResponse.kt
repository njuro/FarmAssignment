package cz.cleverfarm.interview.farmassignment.validation

const val OBJECT_ERROR = "object"

/** Response returned in case the user's input did not pass business validation. */
class ValidationErrorResponse(
    /** Errors detected in input. Keys are field names, values are descriptions of the invalid values. */
    val errors: Map<String, String?>
)