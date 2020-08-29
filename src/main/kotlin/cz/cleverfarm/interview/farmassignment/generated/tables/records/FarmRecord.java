/*
 * This file is generated by jOOQ.
 */
package cz.cleverfarm.interview.farmassignment.generated.tables.records;


import cz.cleverfarm.interview.farmassignment.generated.tables.Farm;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;

import java.util.UUID;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class FarmRecord extends UpdatableRecordImpl<FarmRecord> implements Record3<UUID, String, String> {

    private static final long serialVersionUID = -1344342193;

    /**
     * Setter for <code>public.farm.id</code>.
     */
    public void setId(UUID value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.farm.id</code>.
     */
    public UUID getId() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>public.farm.name</code>.
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.farm.name</code>.
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.farm.note</code>.
     */
    public void setNote(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.farm.note</code>.
     */
    public String getNote() {
        return (String) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<UUID> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row3<UUID, String, String> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    @Override
    public Row3<UUID, String, String> valuesRow() {
        return (Row3) super.valuesRow();
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
    public FarmRecord values(UUID value1, String value2, String value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached FarmRecord
     */
    public FarmRecord() {
        super(Farm.FARM);
    }

    /**
     * Create a detached, initialised FarmRecord
     */
    public FarmRecord(UUID id, String name, String note) {
        super(Farm.FARM);

        set(0, id);
        set(1, name);
        set(2, note);
    }
}
