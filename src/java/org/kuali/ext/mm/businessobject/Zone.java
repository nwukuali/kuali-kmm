package org.kuali.ext.mm.businessobject;

// Generated Apr 8, 2009 10:12:44 AM by Hibernate Tools 3.2.4.GA


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Zone generated by hbm2java
 */
@Entity
@Table(name = "MM_ZONE_T")
public class Zone extends MMPersistableBusinessObjectBase implements java.io.Serializable {

	private static final long serialVersionUID = 6893549800796721276L;
	/* BEGIN JPA
   @EmbeddedId
   @AttributeOverrides( {
       @AttributeOverride(name="warehouseCd", column=@Column(name="WAREHOUSE_CD", nullable=false, length=2) ),
       @AttributeOverride(name="zoneCd", column=@Column(name="ZONE_CD", nullable=false, length=2) ) } )
	private ZoneId id;
	END JPA */
	//BEGIN OJB
	@Id
	@Column(name = "ZONE_ID", nullable = false, length = 36)
	private String zoneId;

	@Column(name = "WAREHOUSE_CD", nullable = false, length = 2)
	private String warehouseCd;

	@Column(name = "ZONE_CD", nullable = false, length = 2)
	private String zoneCd;
	//END OJB
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "WAREHOUSE_CD", nullable = false, insertable = false, updatable = false)
	private Warehouse warehouse;

	@Column(name = "ZONE_DESC", length = 40)
	private String zoneDesc;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "zone")
	private List<Bin> bins = new ArrayList<Bin>(0);

	public Zone() {
	}

	/* BEGIN JPA
	 public ZoneId getId() {
	 return this.id;
	 }

	 public void setId(ZoneId id) {
	 this.id = id;
	 }
	 END JPA */
	//BEGIN OJB
	public String getWarehouseCd() {
		return this.warehouseCd;
	}

	public void setWarehouseCd(String warehouseCd) {
		this.warehouseCd = warehouseCd;
	}

	public String getZoneCd() {
		return this.zoneCd;
	}

	public void setZoneCd(String zoneCd) {
		this.zoneCd = zoneCd;
	}

	//END OJB
	public Warehouse getWarehouse() {
		return this.warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}

	public String getZoneDesc() {
		return this.zoneDesc;
	}

	public void setZoneDesc(String zoneDesc) {
		this.zoneDesc = zoneDesc;
	}

	public List<Bin> getBins() {
		return this.bins;
	}

	public void setBins(List<Bin> bins) {
		this.bins = bins;
	}

	public String getZoneId() {
		return zoneId;
	}

	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}


	/**
	 * toStringMapper
	 * @return LinkedHashMap
	 */
	@Override
    public LinkedHashMap toStringMapper() {
		LinkedHashMap propMap = new LinkedHashMap();
		//TODO:  Autogenerated method

		return propMap;
	}

}
