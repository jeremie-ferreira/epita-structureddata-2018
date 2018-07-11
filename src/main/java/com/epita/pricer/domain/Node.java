package com.epita.pricer.domain;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.epita.pricer.entity.MarshallingYard;

public class Node {

    private String name;
     
    private List<Node> shortestPath = new LinkedList<>();
     
    private Float distance = Float.MAX_VALUE;
     
    Map<Node, Float> adjacentNodes = new HashMap<>();
    
    private MarshallingYard yard;
 
    public void addDestination(Node destination, float distance) {
        adjacentNodes.put(destination, distance);
    }
  
    public Node(String name) {
        this.name = name;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Node> getShortestPath() {
		return shortestPath;
	}

	public void setShortestPath(List<Node> shortestPath) {
		this.shortestPath = shortestPath;
	}

	public Float getDistance() {
		return distance;
	}

	public void setDistance(Float distance) {
		this.distance = distance;
	}

	public Map<Node, Float> getAdjacentNodes() {
		return adjacentNodes;
	}

	public void setAdjacentNodes(Map<Node, Float> adjacentNodes) {
		this.adjacentNodes = adjacentNodes;
	}

	public MarshallingYard getYard() {
		return yard;
	}

	public void setYard(MarshallingYard yard) {
		this.yard = yard;
	}
    
    
}
