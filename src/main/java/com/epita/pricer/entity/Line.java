package com.epita.pricer.entity;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table
public class Line {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(foreignKey=@ForeignKey(name="fk_line_origin"), nullable=false)
	private MarshallingYard origin;
	
	@ManyToOne
	@JoinColumn(foreignKey=@ForeignKey(name="fk_line_destination"), nullable=false)
	private MarshallingYard destination;
	
	@Column(precision=2, nullable=false)
	private Float cost;

	public MarshallingYard getOrigin() {
		return origin;
	}

	public void setOrigin(MarshallingYard origin) {
		this.origin = origin;
	}

	public MarshallingYard getDestination() {
		return destination;
	}

	public void setDestination(MarshallingYard destination) {
		this.destination = destination;
	}

	public Float getCost() {
		return cost;
	}

	public void setCost(Float cost) {
		this.cost = cost;
	}
	
	
}
