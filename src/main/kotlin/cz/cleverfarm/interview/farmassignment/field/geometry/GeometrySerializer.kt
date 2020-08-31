package cz.cleverfarm.interview.farmassignment.field.geometry

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.vividsolutions.jts.geom.Geometry
import com.vividsolutions.jts.io.WKTWriter

class GeometrySerializer : JsonSerializer<Geometry>() {
    private val wktWriter = WKTWriter()

    override fun serialize(geom: Geometry?, generator: JsonGenerator?, provider: SerializerProvider?) {
        if (geom == null) {
            generator?.writeNull()
        }

        generator?.writeString(wktWriter.writeFormatted(geom))
    }
}