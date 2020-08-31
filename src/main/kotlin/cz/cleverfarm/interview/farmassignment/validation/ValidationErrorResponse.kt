package cz.cleverfarm.interview.farmassignment.validation

const val OBJECT_ERROR = "object"

class ValidationErrorResponse(val errors: Map<String, String?>)