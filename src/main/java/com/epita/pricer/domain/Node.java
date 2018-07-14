package com.epita.pricer.domain;

import com.epita.pricer.entity.MarshallingYard;

public class Node {
	private MarshallingYard yard;
	private float distance;
	private Node predecessor;

	public MarshallingYard getYard() {
		return yard;
	}

	public void setYard(MarshallingYard yard) {
		this.yard = yard;
	}

	public float getDistance() {
		return this.distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	public Node getPredecessor() {
		return this.predecessor;
	}

	public void setPredecessor(Node predecessor) {
		this.predecessor = predecessor;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Node) {
			return ((Node) obj).getYard().getCode().equals(this.getYard().getCode());
		}
		return false;
	}
}
