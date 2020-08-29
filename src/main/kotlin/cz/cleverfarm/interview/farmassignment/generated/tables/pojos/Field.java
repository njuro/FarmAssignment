/*
 * This file is generated by jOOQ.
 */
package cz.cleverfarm.interview.farmassignment.generated.tables.pojos;


import java.io.Serializable;
import java.util.UUID;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class Field implements Serializable {

    private static final long serialVersionUID = -1573829077;

    private UUID id;
    private String name;
    private UUID farmId;

    public Field() {
    }

    public Field(Field value) {
        this.id = value.id;
        this.name = value.name;
        this.farmId = value.farmId;
    }

    public Field(
            UUID id,
            String name,
            UUID farmId
    ) {
        this.id = id;
        this.name = name;
        this.farmId = farmId;
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getFarmId() {
        return this.farmId;
    }

    public void setFarmId(UUID farmId) {
        this.farmId = farmId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Field other = (Field) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (farmId == null) {
            if (other.farmId != null)
                return false;
        } else if (!farmId.equals(other.farmId))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
        result = prime * result + ((this.farmId == null) ? 0 : this.farmId.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Field (");

        sb.append(id);
        sb.append(", ").append(name);
        sb.append(", ").append(farmId);

        sb.append(")");
        return sb.toString();
    }
}
