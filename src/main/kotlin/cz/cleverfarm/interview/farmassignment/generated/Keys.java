/*
 * This file is generated by jOOQ.
 */
package cz.cleverfarm.interview.farmassignment.generated;

import cz.cleverfarm.interview.farmassignment.generated.tables.Farm;
import cz.cleverfarm.interview.farmassignment.generated.tables.Field;
import cz.cleverfarm.interview.farmassignment.generated.tables.records.FarmRecord;
import cz.cleverfarm.interview.farmassignment.generated.tables.records.FieldRecord;
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

  public static final UniqueKey<FarmRecord> FARM_PKEY = UniqueKeys0.FARM_PKEY;
  public static final UniqueKey<FieldRecord> FIELD_PKEY = UniqueKeys0.FIELD_PKEY;

  // -------------------------------------------------------------------------
  // FOREIGN KEY definitions
  // -------------------------------------------------------------------------

  public static final ForeignKey<FieldRecord, FarmRecord> FIELD__FK_FIELD_FARM =
      ForeignKeys0.FIELD__FK_FIELD_FARM;

  // -------------------------------------------------------------------------
  // [#1459] distribute members to avoid static initialisers > 64kb
  // -------------------------------------------------------------------------

  private static class UniqueKeys0 {
    public static final UniqueKey<FarmRecord> FARM_PKEY =
        Internal.createUniqueKey(Farm.FARM, "farm_pkey", new TableField[] {Farm.FARM.ID}, true);
    public static final UniqueKey<FieldRecord> FIELD_PKEY =
        Internal.createUniqueKey(
            Field.FIELD, "field_pkey", new TableField[] {Field.FIELD.ID}, true);
  }

  private static class ForeignKeys0 {
    public static final ForeignKey<FieldRecord, FarmRecord> FIELD__FK_FIELD_FARM =
        Internal.createForeignKey(
            Keys.FARM_PKEY,
            Field.FIELD,
            "fk_field_farm",
            new TableField[] {Field.FIELD.FARM_ID},
            true);
  }
}
