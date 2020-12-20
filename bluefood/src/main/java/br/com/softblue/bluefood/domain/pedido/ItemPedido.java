package br.com.softblue.bluefood.domain.pedido;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

import br.com.softblue.bluefood.domain.restaurante.ItemCardapio;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@Entity
@Table(name = "item_pedido")
public class ItemPedido implements Serializable {
	
	@EmbeddedId
	@EqualsAndHashCode.Include
	private ItemPedidoPK id;
	
	@EqualsAndHashCode.Include
	private Integer Id;
	
	@NotNull
	@ManyToOne
	private ItemCardapio itemCardapio;
	
	@NotNull
	private Integer quantidade;
	
	@Size(max = 50)
	private String observacoes;
	
	@NotNull
	private BigDecimal preco;
	
	
	public BigDecimal getPrecoCalculado() {
		return preco.multiply(BigDecimal.valueOf(quantidade));
	}
	

}
