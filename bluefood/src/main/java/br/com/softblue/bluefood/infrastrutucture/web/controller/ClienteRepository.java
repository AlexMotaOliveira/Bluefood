package br.com.softblue.bluefood.infrastrutucture.web.controller;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.softblue.bluefood.domain.cliente.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	
	public Cliente findByEmail(String email);
		
	
	
}
