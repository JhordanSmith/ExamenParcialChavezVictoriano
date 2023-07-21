package com.chavez.infraccionservice.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chavez.infraccionservice.entity.Infraccion;


@Repository
public interface InfraccionRepository extends JpaRepository<Infraccion, Integer> {
	public Infraccion findByDni(String Dni);
	public List<Infraccion> findByDniContaining(String Dni,Pageable page);
}
