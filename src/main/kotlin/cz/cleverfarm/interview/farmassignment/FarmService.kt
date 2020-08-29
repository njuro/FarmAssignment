package cz.cleverfarm.interview.farmassignment

import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class FarmService @Autowired constructor(val create: DSLContext) {

    fun getAll() {
    }
}