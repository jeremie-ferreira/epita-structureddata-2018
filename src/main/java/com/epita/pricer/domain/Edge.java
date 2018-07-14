package com.epita.pricer.domain;

public class Edge {
	private final Node origin;
	private final Node destination;
	private final float weight;

	public Edge(Node origin, Node destination, float weight) {
		this.origin = origin;
		this.destination = destination;
		this.weight = weight;
	}

	public Node getDestination() {
		return this.destination;
	}

	public Node getOrigin() {
		return this.origin;
	}

	public float getWeight() {
		return this.weight;
	}

	@Override
	public String toString() {
		return this.origin + " " + this.destination;
	}
}
