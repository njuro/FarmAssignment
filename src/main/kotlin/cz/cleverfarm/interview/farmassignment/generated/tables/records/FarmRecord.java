/*
 * This file is generated by jOOQ.
 */
package cz.cleverfarm.interview.farmassignment.generated.tables.records;

import cz.cleverfarm.interview.farmassignment.generated.tables.Farm;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;

import java.time.LocalDateTime;
import java.util.UUID;

/** This class is generated by jOOQ. */
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class FarmRecord extends UpdatableRecordImpl<FarmRecord>
    implements Record6<UUID, String, String, LocalDateTime, LocalDateTime, String> {

  private static final long serialVersionUID = -2137791343;

  /** Setter for <code>public.farm.id</code>. */
  public void setId(UUID value) {
    set(0, value);
  }

  /** Getter for <code>public.farm.id</code>. */
  public UUID getId() {
    return (UUID) get(0);
  }

  /** Setter for <code>public.farm.name</code>. */
  public void setName(String value) {
    set(1, value);
  }

  /** Getter for <code>public.farm.name</code>. */
  public String getName() {
    return (String) get(1);
  }

  /** Setter for <code>public.farm.note</code>. */
  public void setNote(String value) {
    set(2, value);
  }

  /** Getter for <code>public.farm.note</code>. */
  public String getNote() {
    return (String) get(2);
  }

  /** Setter for <code>public.farm.created_at</code>. */
  public void setCreatedAt(LocalDateTime value) {
    set(3, value);
  }

  /** Getter for <code>public.farm.created_at</code>. */
  public LocalDateTime getCreatedAt() {
    return (LocalDateTime) get(3);
  }

  /** Setter for <code>public.farm.updated_at</code>. */
  public void setUpdatedAt(LocalDateTime value) {
    set(4, value);
  }

  /** Getter for <code>public.farm.updated_at</code>. */
  public LocalDateTime getUpdatedAt() {
    return (LocalDateTime) get(4);
  }

  /** Setter for <code>public.farm.country</code>. */
  public void setCountry(String value) {
    set(5, value);
  }

  /** Getter for <code>public.farm.country</code>. */
  public String getCountry() {
    return (String) get(5);
  }

  // -------------------------------------------------------------------------
  // Primary key information
  // -------------------------------------------------------------------------

  @Override
  public Record1<UUID> key() {
    return (Record1) super.key();
  }

  // -------------------------------------------------------------------------
  // Record6 type implementation
  // -------------------------------------------------------------------------

  @Override
  public Row6<UUID, String, String, LocalDateTime, LocalDateTime, String> fieldsRow() {
    return (Row6) super.fieldsRow();
  }

  @Override
  public Row6<UUID, String, String, LocalDateTime, LocalDateTime, String> valuesRow() {
    return (Row6) super.valuesRow();
  }

  @Override
  public Field<UUID> field1() {
    return Farm.FARM.ID;
  }

  @Override
  public Field<String> field2() {
    return Farm.FARM.NAME;
  }

  @Override
  public Field<String> field3() {
    return Farm.FARM.NOTE;
  }

  @Override
  public Field<LocalDateTime> field4() {
    return Farm.FARM.CREATED_AT;
  }

  @Override
  public Field<LocalDateTime> field5() {
    return Farm.FARM.UPDATED_AT;
  }

  @Override
  public Field<String> field6() {
    return Farm.FARM.COUNTRY;
  }

  @Override
  public UUID component1() {
    return getId();
  }

  @Override
  public String component2() {
    return getName();
  }

  @Override
  public String component3() {
    return getNote();
  }

  @Override
  public LocalDateTime component4() {
    return getCreatedAt();
  }

  @Override
  public LocalDateTime component5() {
    return getUpdatedAt();
  }

  @Override
  public String component6() {
    return getCountry();
  }

  @Override
  public UUID value1() {
    return getId();
  }

  @Override
  public String value2() {
    return getName();
  }

  @Override
  public String value3() {
    return getNote();
  }

  @Override
  public LocalDateTime value4() {
    return getCreatedAt();
  }

  @Override
  public LocalDateTime value5() {
    return getUpdatedAt();
  }

  @Override
  public String value6() {
    return getCountry();
  }

  @Override
  public FarmRecord value1(UUID value) {
    setId(value);
    return this;
  }

  @Override
  public FarmRecord value2(String value) {
    setName(value);
    return this;
  }

  @Override
  public FarmRecord value3(String value) {
    setNote(value);
    return this;
  }

  @Override
  public FarmRecord value4(LocalDateTime value) {
    setCreatedAt(value);
    return this;
  }

  @Override
  public FarmRecord value5(LocalDateTime value) {
    setUpdatedAt(value);
    return this;
  }

  @Override
  public FarmRecord value6(String value) {
    setCountry(value);
    return this;
  }

  @Override
  public FarmRecord values(
      UUID value1,
      String value2,
      String value3,
      LocalDateTime value4,
      LocalDateTime value5,
      String value6) {
    value1(value1);
    value2(value2);
    value3(value3);
    value4(value4);
    value5(value5);
    value6(value6);
    return this;
  }

  // -------------------------------------------------------------------------
  // Constructors
  // -------------------------------------------------------------------------

  /** Create a detached FarmRecord */
  public FarmRecord() {
    super(Farm.FARM);
  }

  /** Create a detached, initialised FarmRecord */
  public FarmRecord(
      UUID id,
      String name,
      String note,
      LocalDateTime createdAt,
      LocalDateTime updatedAt,
      String country) {
    super(Farm.FARM);

    set(0, id);
    set(1, name);
    set(2, note);
    set(3, createdAt);
    set(4, updatedAt);
    set(5, country);
  }
}
