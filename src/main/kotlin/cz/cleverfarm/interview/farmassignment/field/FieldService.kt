package cz.cleverfarm.interview.farmassignment.field

import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class FieldService @Autowired constructor(private val jooq: DSLContext)