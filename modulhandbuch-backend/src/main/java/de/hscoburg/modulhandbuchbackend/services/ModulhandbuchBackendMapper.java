package de.hscoburg.modulhandbuchbackend.services;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import de.hscoburg.modulhandbuchbackend.converters.EnumEntityToStringConverter;

@Service
public class ModulhandbuchBackendMapper extends ModelMapper {

	public ModulhandbuchBackendMapper() {
		super();
		super.addConverter(new EnumEntityToStringConverter());
	}
}
