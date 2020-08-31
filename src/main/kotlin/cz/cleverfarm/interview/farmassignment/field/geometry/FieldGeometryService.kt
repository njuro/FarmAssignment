package cz.cleverfarm.interview.farmassignment.field.geometry

import com.vividsolutions.jts.geom.Geometry
import com.vividsolutions.jts.geom.GeometryFactory
import com.vividsolutions.jts.geom.Polygon
import com.vividsolutions.jts.geom.PrecisionModel
import com.vividsolutions.jts.io.ParseException
import com.vividsolutions.jts.io.WKTReader
import cz.cleverfarm.interview.farmassignment.common.GEOMETRY_AREA_ERROR
import cz.cleverfarm.interview.farmassignment.common.GEOMETRY_COUNTRY_ERROR
import cz.cleverfarm.interview.farmassignment.common.GEOMETRY_INVALID_ERROR
import cz.cleverfarm.interview.farmassignment.common.GEOMETRY_OVERLAP_ERROR
import cz.cleverfarm.interview.farmassignment.common.GEOMETRY_POLYGON_ERROR
import cz.cleverfarm.interview.farmassignment.common.GEOMETRY_SHAPE_ERROR
import cz.cleverfarm.interview.farmassignment.generated.tables.Country.COUNTRY
import cz.cleverfarm.interview.farmassignment.generated.tables.Field.FIELD
import cz.cleverfarm.interview.farmassignment.validation.FormValidationException
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class FieldGeometryService @Autowired constructor(private val jooq: DSLContext) {

    private val WKT_FIELD = "wkt"
    private val SRID = 4326
    private val wktReader = WKTReader(GeometryFactory(PrecisionModel(), SRID))

    fun parseGeometry(wkt: String?): Geometry {
        try {
            return wktReader.read(wkt)
        } catch (ex: ParseException) {
            throw FormValidationException(WKT_FIELD, GEOMETRY_INVALID_ERROR)
        }
    }

    fun validateGeometry(geom: Geometry, countryCode: String? = "CZE", updatingId: UUID? = null) {
        if (geom !is Polygon) {
            throw FormValidationException(WKT_FIELD, GEOMETRY_SHAPE_ERROR)
        }

        if (!geom.isValid()) {
            throw FormValidationException(WKT_FIELD, GEOMETRY_POLYGON_ERROR)
        }

        if (geom.area <= 0) {
            throw FormValidationException(WKT_FIELD, GEOMETRY_AREA_ERROR)
        }

        val overlapping = jooq.select(DSL.count()).from(FIELD)
            .where("ST_INTERSECTS({0}, ST_GEOMFROMTEXT({1}, $SRID))", FIELD.GEOM, geom.toText())
            .and(FIELD.ID.notEqual(updatingId))
            .fetchOneInto(Integer::class.java)
        if (overlapping > 0) {
            throw FormValidationException(WKT_FIELD, GEOMETRY_OVERLAP_ERROR)
        }

        if (countryCode != null && !geom.within(getCountryGeometry(countryCode))) {
            throw FormValidationException(WKT_FIELD, GEOMETRY_COUNTRY_ERROR)
        }
    }

    private fun getCountryGeometry(countryCode: String): Geometry {
        return jooq.select(COUNTRY.WKB)
            .from(COUNTRY)
            .where(COUNTRY.ISO3.equalIgnoreCase(countryCode))
            .fetchOneInto(Geometry::class.java)
            ?: throw IllegalArgumentException("Invalid country code: $countryCode")
    }
}