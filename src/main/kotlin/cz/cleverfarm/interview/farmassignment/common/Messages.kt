package cz.cleverfarm.interview.farmassignment.common

const val FARM_NOT_FOUND = "Farm not found"
const val FIELD_NOT_FOUND = "Field not found"

const val NAME_LENGTH_ERROR = "Name must have between {min} and {max} characters"
const val NOTE_LENGTH_ERROR = "Note cannot be longer than {max} characters"
const val GEOMETRY_INVALID_ERROR = "Invalid WKT format"
const val GEOMETRY_SHAPE_ERROR = "Field geometry must be Polygon"
const val GEOMETRY_POLYGON_ERROR = "Invalid Polygon definition"
const val GEOMETRY_AREA_ERROR = "Field geometry must have area larger than zero"
const val GEOMETRY_OVERLAP_ERROR = "Field's geometry overlaps with another field"
const val GEOMETRY_COUNTRY_ERROR = "Field's geometry is outside farm's country"
