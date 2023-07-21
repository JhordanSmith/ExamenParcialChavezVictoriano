package com.chavez.infraccionservice.validator;

import com.chavez.infraccionservice.entity.Infraccion;
import com.chavez.infraccionservice.exception.ValidateServiceException;


public class InfraccionValidator {
	public static void save(Infraccion infraccion) {
		if(infraccion.getDni()==null || infraccion.getDni().trim().isEmpty()) {
			throw new ValidateServiceException("El DNI es requerido");
		}
		if(infraccion.getDni().length()>8) {
			throw new ValidateServiceException("El DNI maximo de 8 caracteres");
		}
		if(infraccion.getFalta().length()>3) {
			throw new ValidateServiceException("El tipo de Falta no debe ser mayor de 3 caracteres");
		}
	}
}
