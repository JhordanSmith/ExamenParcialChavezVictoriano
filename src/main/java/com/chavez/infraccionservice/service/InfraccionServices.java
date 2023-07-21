package com.chavez.infraccionservice.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.chavez.infraccionservice.entity.Infraccion;



public interface InfraccionServices {
	public List<Infraccion> findAll(Pageable page);
	public Infraccion findById(int id);
	public Infraccion findByDni(String dni);
	public List<Infraccion> findByDni(String dni, Pageable page);
    public Infraccion create(Infraccion infraccion);
    public Infraccion update(Infraccion infraccion);
    public void delete(int id);
}
