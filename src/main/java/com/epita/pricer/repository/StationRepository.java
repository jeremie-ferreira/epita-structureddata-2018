package com.epita.pricer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epita.pricer.entity.MarshallingYard;
import com.epita.pricer.entity.Station;

public interface StationRepository extends JpaRepository<Station, Integer> {
	
	public List<Station> getByMarshallingYard(MarshallingYard my);
}
