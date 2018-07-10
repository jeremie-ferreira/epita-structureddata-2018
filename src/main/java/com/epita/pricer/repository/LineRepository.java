package com.epita.pricer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epita.pricer.entity.Line;

public interface LineRepository extends JpaRepository<Line, Integer> {

}
