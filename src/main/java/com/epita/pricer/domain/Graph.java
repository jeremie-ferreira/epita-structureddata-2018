package com.epita.pricer.domain;

import java.util.List;

public class Graph {
	private final List<Node> nodes;
	private final List<Edge> edges;

	public Graph(List<Node> nodes, List<Edge> edges) {
		this.nodes = nodes;
		this.edges = edges;
	}

	public List<Node> getNodes() {
		return this.nodes;
	}

	public List<Edge> getEdges() {
		return this.edges;
	}
}
