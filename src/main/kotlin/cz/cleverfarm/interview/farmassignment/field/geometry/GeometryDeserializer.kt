package cz.cleverfarm.interview.farmassignment.field.geometry

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.vividsolutions.jts.geom.Geometry
import com.vividsolutions.jts.io.WKTReader

class GeometryDeserializer : JsonDeserializer<Geometry>() {
    private val wktReader = WKTReader()

    override fun deserialize(parser: JsonParser?, context: DeserializationContext?): Geometry? {
        val json = parser?.valueAsString ?: return null
        return wktReader.read(json)
    }
}