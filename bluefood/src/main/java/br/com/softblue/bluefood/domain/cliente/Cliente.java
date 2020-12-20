package br.com.softblue.bluefood.domain.cliente;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import br.com.softblue.bluefood.domain.usuario.Usuario;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Entity
@Table(name = "cliente")
@SuppressWarnings("serial")
public class Cliente extends Usuario{

	
	@NotBlank(message ="O CPF n�o pode estar vazio")
	@Pattern(regexp = "[0-9]{11}", message = "CPF com formato invalido")
	@Column(length = 11, nullable = false)
	private String cpf;
	
	@NotBlank(message ="O cep n�o pode estar vazio")
	@Pattern(regexp = "[0-9]{8}", message = "CEP com formato invalido")
	@Column(length = 8)
	private String cep;

}
