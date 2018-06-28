package com.epita.pricer.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Gare {
	
	@Id
	@Column(length=6)
	private Integer code;
	
	@Column(length=256)
	private String libelle;
	
	@ManyToOne
	private Plateforme plateforme;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public Plateforme getPlateforme() {
		return plateforme;
	}

	public void setPlateforme(Plateforme plateforme) {
		this.plateforme = plateforme;
	}
	
	
}
