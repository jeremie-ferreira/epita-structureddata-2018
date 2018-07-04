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
public class MarshallingYard {

	@Id
	@Column(length=3)
	private String code;
	
	@ManyToOne
	@JoinColumn(foreignKey=@ForeignKey(name="fk_marchalling_yard_station"))
	private Station station;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}
}
