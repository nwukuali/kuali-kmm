package org.kuali.ext.mm.businessobject;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MM_WAREHOUSE_OBJECT_TYPE_T")
public class WarehouseObjectType extends MMPersistableBusinessObjectBase implements
        java.io.Serializable {
    private static final long serialVersionUID = -5367877435260919672L;
    private String objectTypeCd;
    private String name;

    public WarehouseObjectType() {
    }

    @Id
    @Column(name = "OBJECT_TYPE_CD", unique = true, nullable = false, length = 4)
    public String getObjectTypeCd() {
        return this.objectTypeCd;
    }

    public void setObjectTypeCd(String objectTypeCd) {
        this.objectTypeCd = objectTypeCd;
    }

    @Column(name = "NM", length = 45)
    public String getName() {
        return this.name;
    }

    public void setName(String nm) {
        this.name = nm;
    }


}
