package com.chavez.infraccionservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;

import com.chavez.infraccionservice.converter.InfraccionConverter;
import com.chavez.infraccionservice.dto.InfraccionDTO;
import com.chavez.infraccionservice.entity.Infraccion;
import com.chavez.infraccionservice.service.InfraccionServices;




@RestController
@RequestMapping("/infracciones/")
public class InfraccionController {
	@Autowired
	private InfraccionServices service;
	@Autowired
	private InfraccionConverter converterInf;
	@GetMapping()
	public ResponseEntity<List<InfraccionDTO>> getAll(
			@RequestParam(value="dni",required=false,defaultValue="") String dni,
			@RequestParam(value="offset",required=false,defaultValue="0") int pageNumber,
			@RequestParam(value="limit",required=false,defaultValue="5") int pageSize
			){
		Pageable page=PageRequest.of(pageNumber,pageSize);
		List<Infraccion> infracciones;
		if(dni == null) {
			infracciones=service.findAll(page);
		}else {
			infracciones=service.findByDni(dni, page);
		}
		if(infracciones.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		List<InfraccionDTO> infaccionDTO = converterInf.fromEntity(infracciones);
		return ResponseEntity.ok(infaccionDTO);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<InfraccionDTO> getById(@PathVariable("id") int id) {
		Infraccion infraccion = service.findById(id);
		InfraccionDTO infaccionDTO=converterInf.fromEntity(infraccion);
		return ResponseEntity.status(HttpStatus.OK).body(infaccionDTO);
	}
	@GetMapping(value="/usuario/{dni}")
	public ResponseEntity<InfraccionDTO> getByDni(@PathVariable("dni") String dni) {
		Infraccion infraccion = service.findByDni(dni);
		InfraccionDTO infaccionDTO=converterInf.fromEntity(infraccion);
		return ResponseEntity.status(HttpStatus.OK).body(infaccionDTO);
	}
	
	@PostMapping
	public ResponseEntity<Infraccion> create(@RequestBody Infraccion infraccion) {
		Infraccion Dbinfraccion=service.create(infraccion);
		return ResponseEntity.status(HttpStatus.CREATED).body(Dbinfraccion);
	}
	
	@PutMapping(value="/{id}")
	public ResponseEntity<Infraccion> update(@RequestBody Infraccion infraccion) {
		Infraccion categoriaDb=service.update(infraccion);
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaDb);
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Infraccion> delete(@PathVariable("id") int id){
		service.delete(id);
		return ResponseEntity.ok(null); 
	}
}
