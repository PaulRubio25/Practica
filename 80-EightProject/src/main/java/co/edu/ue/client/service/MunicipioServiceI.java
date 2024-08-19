package co.edu.ue.client.service;

import java.util.List;

import co.edu.ue.client.model.Municipio;

public interface MunicipioServiceI {
	List<Municipio> listMunicipios();
    List<Municipio> searchDepartamento(String name);

}
