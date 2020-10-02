/*
 * This file is generated by jOOQ.
 */
package cz.cleverfarm.interview.farmassignment.generated;

import cz.cleverfarm.interview.farmassignment.generated.tables.Country;
import cz.cleverfarm.interview.farmassignment.generated.tables.Farm;
import cz.cleverfarm.interview.farmassignment.generated.tables.Field;
import cz.cleverfarm.interview.farmassignment.generated.tables.FlywaySchemaHistory;
import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;

import java.util.Arrays;
import java.util.List;

/** This class is generated by jOOQ. */
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class Public extends SchemaImpl {

  private static final long serialVersionUID = 919789232;

  /** The reference instance of <code>public</code> */
  public static final Public PUBLIC = new Public();

  /** The table <code>public.country</code>. */
  public final Country COUNTRY = Country.COUNTRY;

  /** The table <code>public.farm</code>. */
  public final Farm FARM = Farm.FARM;

  /** The table <code>public.field</code>. */
  public final Field FIELD = Field.FIELD;

  /** The table <code>public.flyway_schema_history</code>. */
  public final FlywaySchemaHistory FLYWAY_SCHEMA_HISTORY =
      FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY;

  /** No further instances allowed */
  private Public() {
    super("public", null);
  }

  @Override
  public Catalog getCatalog() {
    return DefaultCatalog.DEFAULT_CATALOG;
  }

  @Override
  public final List<Table<?>> getTables() {
    return Arrays.<Table<?>>asList(
        Country.COUNTRY, Farm.FARM, Field.FIELD, FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY);
  }
}