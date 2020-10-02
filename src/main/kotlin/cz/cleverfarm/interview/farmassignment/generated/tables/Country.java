/*
 * This file is generated by jOOQ.
 */
package cz.cleverfarm.interview.farmassignment.generated.tables;

import com.vividsolutions.jts.geom.Geometry;
import cz.cleverfarm.interview.farmassignment.generated.Keys;
import cz.cleverfarm.interview.farmassignment.generated.Public;
import cz.cleverfarm.interview.farmassignment.generated.tables.records.CountryRecord;
import net.dmitry.jooq.postgis.spatial.binding.JTSGeometryBinding;
import org.jooq.Field;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import java.util.Arrays;
import java.util.List;

/** This class is generated by jOOQ. */
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class Country extends TableImpl<CountryRecord> {

  private static final long serialVersionUID = 891427133;

  /** The reference instance of <code>public.country</code> */
  public static final Country COUNTRY = new Country();

  /** The class holding records for this type */
  @Override
  public Class<CountryRecord> getRecordType() {
    return CountryRecord.class;
  }

  /** The column <code>public.country.code</code>. */
  public final TableField<CountryRecord, String> CODE =
      createField(DSL.name("code"), org.jooq.impl.SQLDataType.VARCHAR(3).nullable(false), this, "");

  /** The column <code>public.country.borders</code>. */
  public final TableField<CountryRecord, Geometry> BORDERS =
      createField(
          DSL.name("borders"),
          org.jooq.impl.DefaultDataType.getDefaultDataType("\"public\".\"geometry\""),
          this,
          "",
          new JTSGeometryBinding());

  /** The column <code>public.country.name</code>. */
  public final TableField<CountryRecord, String> NAME =
      createField(DSL.name("name"), org.jooq.impl.SQLDataType.VARCHAR(50), this, "");

  /** Create a <code>public.country</code> table reference */
  public Country() {
    this(DSL.name("country"), null);
  }

  /** Create an aliased <code>public.country</code> table reference */
  public Country(String alias) {
    this(DSL.name(alias), COUNTRY);
  }

  /** Create an aliased <code>public.country</code> table reference */
  public Country(Name alias) {
    this(alias, COUNTRY);
  }

  private Country(Name alias, Table<CountryRecord> aliased) {
    this(alias, aliased, null);
  }

  private Country(Name alias, Table<CountryRecord> aliased, Field<?>[] parameters) {
    super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
  }

  public <O extends Record> Country(Table<O> child, ForeignKey<O, CountryRecord> key) {
    super(child, key, COUNTRY);
  }

  @Override
  public Schema getSchema() {
    return Public.PUBLIC;
  }

  @Override
  public UniqueKey<CountryRecord> getPrimaryKey() {
    return Keys.COUNTRY_PKEY;
  }

  @Override
  public List<UniqueKey<CountryRecord>> getKeys() {
    return Arrays.<UniqueKey<CountryRecord>>asList(Keys.COUNTRY_PKEY);
  }

  @Override
  public Country as(String alias) {
    return new Country(DSL.name(alias), this);
  }

  @Override
  public Country as(Name alias) {
    return new Country(alias, this);
  }

  /** Rename this table */
  @Override
  public Country rename(String name) {
    return new Country(DSL.name(name), null);
  }

  /** Rename this table */
  @Override
  public Country rename(Name name) {
    return new Country(name, null);
  }

  // -------------------------------------------------------------------------
  // Row3 type methods
  // -------------------------------------------------------------------------

  @Override
  public Row3<String, Geometry, String> fieldsRow() {
    return (Row3) super.fieldsRow();
  }
}