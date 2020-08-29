package cz.cleverfarm.interview.farmassignment.common

const val FARM_ID_VARIABLE_NAME = "farmId"
const val FARM_ID_VARIABLE = "/{$FARM_ID_VARIABLE_NAME}"
const val FIELD_ID_VARIABLE_NAME = "fieldId"
const val FIELD_ID_VARIABLE = "/{$FIELD_ID_VARIABLE_NAME}"

const val API_ROOT_FARMS = "/farms"
const val API_ROOT_FIELDS = "$API_ROOT_FARMS$FARM_ID_VARIABLE/fields"