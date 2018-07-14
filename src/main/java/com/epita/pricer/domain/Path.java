package com.epita.pricer.domain;

import java.util.List;

import com.epita.pricer.entity.MarshallingYard;

public class Path {
	private List<MarshallingYard> yards;
	private Float totalCost = 0f;
	public List<MarshallingYard> getYards() {
		return yards;
	}
	public void setYards(List<MarshallingYard> yards) {
		this.yards = yards;
	}
	public Float getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(Float totalCost) {
		this.totalCost = totalCost;
	}
	
	
}
