package com.epita.pricer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.epita.pricer.entity.Station;
import com.epita.pricer.repository.StationRepository;

@Controller
public class StationController {

	@Autowired
	private StationRepository stationRepository;
	
	@RequestMapping("/api/station")
	@ResponseBody
	public List<Station> getAll() {
		return stationRepository.findAll();
	}
	
	@RequestMapping("/api/station/{id}")
	@ResponseBody
	public Station getById(@PathVariable("id") Integer id) {
		return stationRepository.findById(id).get();
	}
}
