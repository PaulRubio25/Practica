package co.edu.ue.client.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import co.edu.ue.client.model.Municipio;

@Service
public class MunicipioService implements MunicipioServiceI{

	final String URL="https://www.datos.gov.co/resource/xdk5-pm3f.json";
    @Autowired
    RestTemplate template;

    @Override
    public List<Municipio> listMunicipios() {

        //La respuesta esta manipulada como un String muy grande
        String response = template.getForObject(URL, String.class);
        Municipio municipio;
        ObjectMapper mapper = new ObjectMapper();
        List<Municipio> municipios = new ArrayList<>();
        ArrayNode array;
        try{
            //se obtiene un ArrayJSON con los datos que tiene reponse
            array =(ArrayNode) mapper.readTree(response); 
            for(Object obj:array) {//obtenemos el Objeto JSON y 
                //extraemos las propiedades que necesitamos
                ObjectNode json = (ObjectNode) obj; 
                municipio = new Municipio(json.get("region").asText(),json.get("departamento").asText(),
                json.get("municipio").asText(),json.get("c_digo_dane_del_departamento").asInt());
                municipios.add(municipio); }
          }catch(Exception e){
            e.printStackTrace();
          }
          return municipios;
    }

    @Override
    public List<Municipio> searchDepartamento(String name) {
        
         return listMunicipios()
         .stream()
         .filter(m->m.getDepartamento().equals(name))
         .collect(Collectors.toList());     
    }
}
