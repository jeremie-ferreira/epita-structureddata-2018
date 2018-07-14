package com.epita.pricer.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.epita.pricer.domain.DijkstraAlgorithm;
import com.epita.pricer.domain.Edge;
import com.epita.pricer.domain.Graph;
import com.epita.pricer.domain.Node;
import com.epita.pricer.domain.Path;
import com.epita.pricer.entity.Line;
import com.epita.pricer.entity.MarshallingYard;
import com.epita.pricer.entity.Station;
import com.epita.pricer.repository.LineRepository;
import com.epita.pricer.repository.MarshallingYardRepository;
import com.epita.pricer.repository.StationRepository;

@Service
public class LineService {
	
	@Autowired
	private LineRepository lineRepository;
	
	@Autowired
	private StationRepository stationRepository;
	
	@Autowired
	private MarshallingYardRepository marshallingYardRepository;

	public Path getPath(Integer originId, Integer destinationId) {
		Station origin = stationRepository.findById(originId).get();
		Station destination = stationRepository.findById(destinationId).get();
		return getPath(origin.getMarshallingYard(), destination.getMarshallingYard());
	}
	
	public Path getPath(MarshallingYard origin, MarshallingYard destination) {
		List<MarshallingYard> yards = marshallingYardRepository.findAll();
		Map<String, Node> nodeMap = new HashMap<>();
		Map<String, Line> lineMap = new HashMap<>();
		List<Line> lines = lineRepository.findAll();
		
		List<Node> nodes = new ArrayList<>();
		List<Edge> edges = new ArrayList<>();
		
		for (MarshallingYard yard : yards) {
			Node node = new Node();
			node.setYard(yard);
			nodes.add(node);
			nodeMap.put(yard.getCode(), node);
		}
		
		for (Line line : lines) {
			Node n1 = nodeMap.get(line.getOrigin().getCode());
			Node n2 = nodeMap.get(line.getDestination().getCode());
			Edge edge = new Edge(n1, n2, line.getCost());
			edges.add(edge);
			lineMap.put(line.getOrigin().getCode() + line.getDestination().getCode(), line);
		}
		
		Graph graph = new Graph(nodes, edges);
			
		DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph, nodeMap.get(origin.getCode()), nodeMap.get(destination.getCode()));
		List<Node> result = dijkstra.compute();
		
		for(Node y : result) {
			System.out.println(y.getYard().getCode());
		}
		
		List<MarshallingYard> yardPath = result.stream().map((n) -> n.getYard()).collect(Collectors.toList());
		
		Path path = new Path();
		path.setYards(yardPath);
		for (int i = 0; i < yardPath.size() - 1; ++i) {
			Line l = lineMap.get(yardPath.get(i).getCode() + yardPath.get(i+1).getCode());
			path.setTotalCost(path.getTotalCost() + l.getCost());
		}
		
		return path;
	}

}
