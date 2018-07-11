package com.epita.pricer.domain;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Set;

public class Dijkstra {
	public static Graph calculateShortestPathFromSource(Graph graph, Node source) {
	    source.setDistance(0f);
	 
	    Set<Node> settledNodes = new HashSet<>();
	    Set<Node> unsettledNodes = new HashSet<>();
	    
	    for (Node n : graph.getNodes()) {
	    	unsettledNodes.add(n);
	    }
	 	 
	    while (unsettledNodes.size() != 0) {
	        Node currentNode = getLowestDistanceNode(unsettledNodes);
	        unsettledNodes.remove(currentNode);
	        for (Entry<Node, Float> adjacencyPair: currentNode.getAdjacentNodes().entrySet()) {
	            Node adjacentNode = adjacencyPair.getKey();
	            Float edgeWeight = adjacencyPair.getValue();
	            if (!settledNodes.contains(adjacentNode)) {
	                calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
	                unsettledNodes.add(adjacentNode);
	            }
	        }
	        settledNodes.add(currentNode);
	    }
	    return graph;
	}
	
	private static Node getLowestDistanceNode(Set<Node> unsettledNodes) {
	    Node lowestDistanceNode = null;
	    float lowestDistance = Float.MAX_VALUE;
	    for (Node node: unsettledNodes) {
	        float nodeDistance = node.getDistance();
	        if (nodeDistance < lowestDistance) {
	            lowestDistance = nodeDistance;
	            lowestDistanceNode = node;
	        }
	    }
	    return lowestDistanceNode;
	}
	
	private static void calculateMinimumDistance(Node evaluationNode,
			  Float edgeWeigh, Node sourceNode) {
	    Float sourceDistance = sourceNode.getDistance();
	    if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
	        evaluationNode.setDistance(sourceDistance + edgeWeigh);
	        LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
	        shortestPath.add(sourceNode);
	        evaluationNode.setShortestPath(shortestPath);
	    }
	}
}
