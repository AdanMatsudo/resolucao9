package com.example.restservice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class RestserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestserviceApplication.class, args);
	}

	private DAO dao;

	public RestserviceApplication() {
		dao = new DAO();
	}

	@RequestMapping(value = "/cidade/{id}", method = RequestMethod.GET)
	public List<Cidade> getCidade() {
		return dao.getCidades();
	}

	@RequestMapping(value = "/cidade/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> addCidade(@RequestBody Cidade cidade) {

		Cidade retorno = dao.add(cidade);
		if (retorno != null) {
			return ResponseEntity.ok(cidade);
		}
		return ResponseEntity.badRequest().body("ID já cadastrado");
	}

	@RequestMapping(value = "/cidade/{id}", method = RequestMethod.PUT)
	public Cidade updateCidade(@PathVariable("id") Long id, @RequestBody Cidade cidade) {
		return dao.update(id, cidade);
	}

	@RequestMapping(value = "/cidade/{id}", method = RequestMethod.GET)
	public Cidade getCidade(@PathVariable("id") Long id) {
		return dao.find(id);
	}

	@RequestMapping(value = "/cidade/{id}", method = RequestMethod.DELETE)
	public HttpStatus deleteCidade(@PathVariable("id") Long id) {
		if (dao.delete(id)) {
			return HttpStatus.OK;
		}
		return HttpStatus.BAD_REQUEST;
	}

}

class Cidade {

	private Long id;
	private String nome;
	private Estado estado;

	public Cidade() {
		super();
	}

	public Cidade(Long id, String nome, Estado estado) {
		super();
		this.id = id;
		this.nome = nome;
		this.estado = estado;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

}

class Estado {

	private Long id;
	private String nome;

	public Estado(Long id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}

	public Estado() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}

class DAO {

	private List<Cidade> cidades;

	public DAO() {
		this.cidades = new ArrayList<>();
	}

	public List<Cidade> getCidades() {
		return cidades;
	}

	public Cidade add(Cidade cidade) {

		for (Cidade f : cidades) {
			if (f.getId().equals(cidade.getId())) {
				return null;
			}
		}

		cidades.add(cidade);
		return cidade;

	}

	public Cidade find(Long id) {
		for (Cidade f : cidades) {
			if (f.getId().equals(id)) {
				return f;
			}
		}
		return null;
	}

	public Cidade update(Long id, Cidade cidade) {
		for (Cidade f : cidades) {
			if (f.getId().equals(id)) {
				f.setEstado(cidade.getEstado());
				f.setNome(cidade.getNome());
			}
		}

		return cidade;
	}

	public Boolean delete(Long id) {
		for (Cidade cidade : cidades) {
			if (cidade.getId().equals(id)) {
				cidades.remove(cidade);
				return true;
			}
		}
		return false;
	}
}
