package com.epita.pricer.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.epita.pricer.entity.MarshallingYard;

public interface MarshallingYardRepository extends JpaRepository<MarshallingYard, Integer> {
}
