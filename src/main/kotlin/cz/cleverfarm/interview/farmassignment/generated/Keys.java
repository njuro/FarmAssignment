/*
 * This file is generated by jOOQ.
 */
package cz.cleverfarm.interview.farmassignment.generated;

import cz.cleverfarm.interview.farmassignment.generated.tables.Country;
import cz.cleverfarm.interview.farmassignment.generated.tables.Farm;
import cz.cleverfarm.interview.farmassignment.generated.tables.Field;
import cz.cleverfarm.interview.farmassignment.generated.tables.FlywaySchemaHistory;
import cz.cleverfarm.interview.farmassignment.generated.tables.records.CountryRecord;
import cz.cleverfarm.interview.farmassignment.generated.tables.records.FarmRecord;
import cz.cleverfarm.interview.farmassignment.generated.tables.records.FieldRecord;
import cz.cleverfarm.interview.farmassignment.generated.tables.records.FlywaySchemaHistoryRecord;
import org.jooq.ForeignKey;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.Internal;

/**
 * A class modelling foreign key relationships and constraints of tables of the <code>public</code>
 * schema.
 */
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class Keys {

  // -------------------------------------------------------------------------
  // IDENTITY definitions
  // -------------------------------------------------------------------------

  // -------------------------------------------------------------------------
  // UNIQUE and PRIMARY KEY definitions
  // -------------------------------------------------------------------------

  public static final UniqueKey<CountryRecord> COUNTRY_PKEY = UniqueKeys0.COUNTRY_PKEY;
  public static final UniqueKey<FarmRecord> FARM_PKEY = UniqueKeys0.FARM_PKEY;
  public static final UniqueKey<FieldRecord> FIELD_PKEY = UniqueKeys0.FIELD_PKEY;
  public static final UniqueKey<FlywaySchemaHistoryRecord> FLYWAY_SCHEMA_HISTORY_PK =
      UniqueKeys0.FLYWAY_SCHEMA_HISTORY_PK;

  // -------------------------------------------------------------------------
  // FOREIGN KEY definitions
  // -------------------------------------------------------------------------

  public static final ForeignKey<FarmRecord, CountryRecord> FARM__FK_FARM_COUNTRY =
      ForeignKeys0.FARM__FK_FARM_COUNTRY;
  public static final ForeignKey<FieldRecord, FarmRecord> FIELD__FK_FIELD_FARM =
      ForeignKeys0.FIELD__FK_FIELD_FARM;

  // -------------------------------------------------------------------------
  // [#1459] distribute members to avoid static initialisers > 64kb
  // -------------------------------------------------------------------------

  private static class UniqueKeys0 {
    public static final UniqueKey<CountryRecord> COUNTRY_PKEY =
        Internal.createUniqueKey(
            Country.COUNTRY, "country_pkey", new TableField[] {Country.COUNTRY.ISO3}, true);
    public static final UniqueKey<FarmRecord> FARM_PKEY =
        Internal.createUniqueKey(Farm.FARM, "farm_pkey", new TableField[] {Farm.FARM.ID}, true);
    public static final UniqueKey<FieldRecord> FIELD_PKEY =
        Internal.createUniqueKey(
            Field.FIELD, "field_pkey", new TableField[] {Field.FIELD.ID}, true);
    public static final UniqueKey<FlywaySchemaHistoryRecord> FLYWAY_SCHEMA_HISTORY_PK =
        Internal.createUniqueKey(
            FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY,
            "flyway_schema_history_pk",
            new TableField[] {FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY.INSTALLED_RANK},
            true);
  }

  private static class ForeignKeys0 {
    public static final ForeignKey<FarmRecord, CountryRecord> FARM__FK_FARM_COUNTRY =
        Internal.createForeignKey(
            Keys.COUNTRY_PKEY,
            Farm.FARM,
            "fk_farm_country",
            new TableField[] {Farm.FARM.COUNTRY},
            true);
    public static final ForeignKey<FieldRecord, FarmRecord> FIELD__FK_FIELD_FARM =
        Internal.createForeignKey(
            Keys.FARM_PKEY,
            Field.FIELD,
            "fk_field_farm",
            new TableField[] {Field.FIELD.FARM_ID},
            true);
  }
}
