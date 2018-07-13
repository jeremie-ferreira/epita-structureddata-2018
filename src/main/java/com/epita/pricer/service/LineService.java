package com.epita.pricer.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.epita.pricer.domain.Dijkstra;
import com.epita.pricer.domain.Graph;
import com.epita.pricer.domain.Node;
import com.epita.pricer.entity.Line;
import com.epita.pricer.entity.MarshallingYard;
import com.epita.pricer.repository.LineRepository;
import com.epita.pricer.repository.MarshallingYardRepository;

@Service
public class LineService {
	
	@Autowired
	private LineRepository lineRepository;
	
	@Autowired
	private MarshallingYardRepository marshallingYardRepository;
	
	//@EventListener(ApplicationReadyEvent.class)
	public void testPath() {
		getPath(marshallingYardRepository.getOne("BYE"), marshallingYardRepository.getOne("WPY"));
	}
	
	public List<Line> getPath(MarshallingYard origin, MarshallingYard destination) {
		List<Line> result = new ArrayList<>();
		
		List<MarshallingYard> yards = marshallingYardRepository.findAll();
		Map<String, Node> nodeMap = new HashMap<>();
		List<Line> lines = lineRepository.findAll();
		
		Graph graph = new Graph();
		
		for (MarshallingYard yard : yards) {
			Node node = new Node(yard.getCode());
			node.setYard(yard);
			nodeMap.put(yard.getCode(), node);
			graph.addNode(node);
		}
		
		for (Line line : lines) {
			Node n1 = nodeMap.get(line.getOrigin().getCode());
			Node n2 = nodeMap.get(line.getDestination().getCode());
			n1.addDestination(n2, line.getCost());
		}
		
		Node originNode = nodeMap.get(origin.getCode());
		
		graph = Dijkstra.calculateShortestPathFromSource(graph, originNode);
		
		for (Node n : graph.getNodes()) {
			System.out.print(n.getName() + ": ");
			for (Node p : originNode.getShortestPath()) {
				System.out.print(p.getName() + ">" );
			}
			System.out.println(" ");
		}
		
		
		return result;
	}

}
