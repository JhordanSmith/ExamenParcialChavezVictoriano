package com.chavez.infraccionservice.service.impl;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chavez.infraccionservice.entity.Infraccion;
import com.chavez.infraccionservice.exception.GeneralServiceException;
import com.chavez.infraccionservice.exception.NoDataServiceException;
import com.chavez.infraccionservice.exception.ValidateServiceException;
import com.chavez.infraccionservice.repository.InfraccionRepository;
import com.chavez.infraccionservice.service.InfraccionServices;
import com.chavez.infraccionservice.validator.InfraccionValidator;

import lombok.extern.slf4j.Slf4j;




@Slf4j
@Service
public class InfraccionServiceImpl implements InfraccionServices {
	
	@Autowired
	private InfraccionRepository repository;
	
	@Override
	@Transactional(readOnly = true)
	public List<Infraccion> findAll(Pageable page) {
		try {
			return repository.findAll();
		} catch (ValidateServiceException | NoDataServiceException e) {
			log.info(e.getMessage(), e);
			throw e;
		}catch(Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException(e.getMessage(), e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Infraccion findById(int id) {
		try {
			return repository.findById(id).orElseThrow(()->new NoDataServiceException("No existe un registro de "+id));
		} catch (ValidateServiceException | NoDataServiceException e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException(e.getMessage(), e);
		}		
	}
	@Override
	@Transactional(readOnly = true)
	public Infraccion findByDni(String dni) {
		try {
			return repository.findByDni(dni);
		} catch (ValidateServiceException | NoDataServiceException e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException(e.getMessage(), e);
		}		
	}
	@Override
	@Transactional(readOnly = true)
	public List<Infraccion> findByDni(String Dni, Pageable page) {
		try {
			return repository.findByDniContaining(Dni,page);
			} catch (ValidateServiceException | NoDataServiceException e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException(e.getMessage(), e);
		}
	}

	@Override
	@Transactional
	public Infraccion create(Infraccion infraccion) {
		try {
			InfraccionValidator.save(infraccion);
			if(repository.findByDni(infraccion.getDni()) !=null) {
				throw new ValidateServiceException("Ya existe un registro con el mismo dni"+infraccion.getDni());
			}
			return repository.save(infraccion);			
		} catch (ValidateServiceException | NoDataServiceException e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException(e.getMessage(), e);
		}		
	}

	@Override
	@Transactional
	public Infraccion update(Infraccion infraccion) {
		try {
			InfraccionValidator.save(infraccion);
			Infraccion categoriaDb=repository.findByDni(infraccion.getDni());
			if(categoriaDb!=null&& infraccion.getId()!=categoriaDb.getId()) {
				throw new ValidateServiceException("No hay un registro con ese DNI"+infraccion.getDni());
			}
			Infraccion obj=repository.findById(infraccion.getId()).orElseThrow(()->new NoDataServiceException("No existe un registro con el ID"+infraccion.getId()));
			obj.setDni(infraccion.getDni());
			obj.setFecha(infraccion.getFecha());
			obj.setFalta(infraccion.getFalta());
			obj.setInfraccion(infraccion.getInfraccion());
			obj.setDescripcion(infraccion.getDescripcion());

			return repository.save(infraccion);
		} catch (ValidateServiceException | NoDataServiceException e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException(e.getMessage(), e);
		}
	}

	public void delete(int id) {
		try {
			Infraccion infraccion=repository.findById(id).orElseThrow(()->new NoDataServiceException("No existe un registro con el ID "+id));
			repository.delete(infraccion);
		}catch (NoDataServiceException e) {
			log.info(e.getMessage());
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new GeneralServiceException(e.getMessage());
		}
	}

}
