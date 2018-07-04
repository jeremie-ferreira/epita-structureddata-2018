package com.epita.pricer.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class Station {

	@Id
	private Integer code;
	
	@Column
	private String name;
	
	@Column
	private Float latitude;
	
	@Column
	private Float longitude;
	
	@ManyToOne
	@JoinColumn(foreignKey=@ForeignKey(name="fk_station_marchalling_yard"))
	private MarshallingYard marshallingYard;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	public Float getLongitude() {
		return longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	public MarshallingYard getMarshallingYard() {
		return marshallingYard;
	}

	public void setMarshallingYard(MarshallingYard marshallingYard) {
		this.marshallingYard = marshallingYard;
	}

	
}
