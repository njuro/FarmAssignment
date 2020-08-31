package cz.cleverfarm.interview.farmassignment.field.geometry

import com.vividsolutions.jts.geom.Geometry
import com.vividsolutions.jts.geom.GeometryFactory
import com.vividsolutions.jts.geom.Polygon
import com.vividsolutions.jts.geom.PrecisionModel
import com.vividsolutions.jts.io.ParseException
import com.vividsolutions.jts.io.WKTReader
import cz.cleverfarm.interview.farmassignment.common.BORDERS_AREA_ERROR
import cz.cleverfarm.interview.farmassignment.common.BORDERS_COUNTRY_ERROR
import cz.cleverfarm.interview.farmassignment.common.BORDERS_INVALID_ERROR
import cz.cleverfarm.interview.farmassignment.common.BORDERS_OVERLAP_ERROR
import cz.cleverfarm.interview.farmassignment.common.BORDERS_POLYGON_ERROR
import cz.cleverfarm.interview.farmassignment.common.BORDERS_SHAPE_ERROR
import cz.cleverfarm.interview.farmassignment.generated.tables.Country.COUNTRY
import cz.cleverfarm.interview.farmassignment.generated.tables.Field.FIELD
import cz.cleverfarm.interview.farmassignment.validation.FormValidationException
import org.jooq.DSLContext
import org.jooq.impl.DSL.count
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class FieldGeometryService @Autowired constructor(private val jooq: DSLContext) {

    private val WKT_FIELD = "wkt"
    private val SRID = 4326
    private val wktReader = WKTReader(GeometryFactory(PrecisionModel(), SRID))

    fun parseBorders(wkt: String?): Geometry {
        try {
            return wktReader.read(wkt)
        } catch (ex: ParseException) {
            throw FormValidationException(WKT_FIELD, BORDERS_INVALID_ERROR)
        }
    }

    fun validateBorders(borders: Geometry, countryCode: String, updatingId: UUID? = null) {
        if (borders !is Polygon) {
            throw FormValidationException(WKT_FIELD, BORDERS_SHAPE_ERROR)
        }

        if (!borders.isValid()) {
            throw FormValidationException(WKT_FIELD, BORDERS_POLYGON_ERROR)
        }

        if (borders.area <= 0) {
            throw FormValidationException(WKT_FIELD, BORDERS_AREA_ERROR)
        }

        val overlaps = jooq.select(count()).from(FIELD)
            .where("ST_INTERSECTS({0}, ST_GEOMFROMTEXT({1}, $SRID))", FIELD.BORDERS, borders.toText())
            .and(FIELD.ID.notEqual(updatingId))
            .fetchOneInto(Integer::class.java) > 0
        if (overlaps) {
            throw FormValidationException(WKT_FIELD, BORDERS_OVERLAP_ERROR)
        }

        if (!borders.within(getCountryBorders(countryCode))) {
            throw FormValidationException(WKT_FIELD, BORDERS_COUNTRY_ERROR)
        }
    }

    private fun getCountryBorders(countryCode: String): Geometry {
        return jooq.select(COUNTRY.WKB)
            .from(COUNTRY)
            .where(COUNTRY.ISO3.equalIgnoreCase(countryCode))
            .fetchOneInto(Geometry::class.java)
            ?: throw IllegalArgumentException("Invalid country code: $countryCode")
    }
}