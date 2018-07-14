package com.epita.pricer.controller;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.epita.pricer.domain.Path;
import com.epita.pricer.service.LineService;

@Controller
public class LineController {
	
	@Autowired
	private LineService lineService;
	
	@GetMapping("/api/line/{originId}/{destinationId}")
	@ResponseBody
	public Path getPath(@PathVariable("originId") Integer originId, @PathVariable("destinationId") Integer destinationId) {
		return lineService.getPath(originId, destinationId);
	}
}
