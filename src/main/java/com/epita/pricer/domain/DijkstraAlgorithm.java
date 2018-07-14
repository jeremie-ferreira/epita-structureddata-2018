package com.epita.pricer.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DijkstraAlgorithm {

	private Graph graph;
	private Node origin;
	private Node destination;

	private List<Node> remainingNodes = new ArrayList<>();

	public DijkstraAlgorithm(Graph graph, Node origin, Node destination) {
		super();
		this.graph = graph;
		this.origin = origin;
		this.destination = destination;

		for (Node node : this.graph.getNodes()) {
			node.setDistance(Float.MAX_VALUE);
			this.remainingNodes.add(node);
		}

		this.origin.setDistance(0);
	}

	public List<Node> compute() {
		while (!this.remainingNodes.isEmpty()) {
			Node n1 = this.getMinNode();
			this.remainingNodes.remove(n1);

			for (Edge edge : this.graph.getEdges()) {
				if (edge.getOrigin() == n1) {
					this.updateDistances(edge);
				}
			}
		}

		List<Node> shortestPath = new ArrayList<>();
		Node n = this.destination;
		while (n != this.origin && n != null) {
			shortestPath.add(n);
			n = n.getPredecessor();
		}
		shortestPath.add(this.origin);

		Collections.reverse(shortestPath);

		return shortestPath;
	}

	private void updateDistances(Edge edge) {
		Float newDistance = edge.getOrigin().getDistance() + edge.getWeight();
		if (edge.getDestination().getDistance() > newDistance) {
			edge.getDestination().setDistance(newDistance);
			edge.getDestination().setPredecessor(edge.getOrigin());
		}
	}

	private Node getMinNode() {
		Float min = Float.MAX_VALUE;
		Node n = null;

		for (Node node : this.remainingNodes) {
			if (node.getDistance() < min) {
				min = node.getDistance();
				n = node;
			}
		}

		return n;
	}

}
