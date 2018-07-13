package com.epita.pricer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.epita.pricer.entity.MarshallingYard;
import com.epita.pricer.repository.MarshallingYardRepository;

public class MarshallingYardController {

	@Autowired
	private MarshallingYardRepository marshallingYardRepository;
	
	@GetMapping("api/my")
	@ResponseBody
	public List<MarshallingYard> getAll() {
		return marshallingYardRepository.findAll();
	}
}
