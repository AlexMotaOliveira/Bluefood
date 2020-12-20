package br.com.softblue.bluefood.domain.restaurante;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import br.com.softblue.bluefood.infrastructure.web.validator.UploadConstraint;
import br.com.softblue.bluefood.util.FileType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Entity
@Table(name = "item_cardapio")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ItemCardapio implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank(message = "Nome n�o pode ser vazio")
	@Size(max = 100)
	private String nome;

	@NotBlank(message = "Categoria n�o pode ser vazio")
	@Size(max = 80)
	private String categoria;
	
	@NotBlank(message = "Categoria n�o pode ser vazio")
	@Size(max = 80)
	private String descricao;
	
	@Size(max = 50)
	private String imagem;
	
	@NotNull(message = "O Pre�o n�o pode ser vazio")
	@Min(0)
	private BigDecimal preco;
	
	@NotNull
	private Boolean destaque;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "restaurante_id")
	private Restaurante restaurante; 
	
	@UploadConstraint(accepedTypes = {FileType.JPG, FileType.PNG}, message = "O arquivo de imagem n�o � valido para upload")
	private transient MultipartFile imagemFile;
	
	public void setImagemFileName() {

		if (getId() == null) {
			throw new IllegalStateException("� preciso primeiro gravar o registro");
		}

		this.imagem = String.format("%4d-comida.%s", getId(), FileType.of(imagemFile.getContentType()).getExtension());
	}

}
