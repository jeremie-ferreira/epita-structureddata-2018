package com.epita.pricer.entity;

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
@Table(uniqueConstraints=@UniqueConstraint(columnNames= {"origin", "destination"}))
public class Axe {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="origin", foreignKey=@ForeignKey(name="fk_axe_origin"))
	private Plateforme origin;
	
	@ManyToOne
	@JoinColumn(name="destination", foreignKey=@ForeignKey(name="fk_axe_destination"))
	private Plateforme destination;
	
	@Column(precision=2)
	private Float cost;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Plateforme getOrigin() {
		return origin;
	}

	public void setOrigin(Plateforme origin) {
		this.origin = origin;
	}

	public Plateforme getDestination() {
		return destination;
	}

	public void setDestination(Plateforme destination) {
		this.destination = destination;
	}

	public float getCost() {
		return cost;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}
}
