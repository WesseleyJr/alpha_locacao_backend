package br.com.alpha.locacao.dto;

import br.com.alpha.locacao.domain.Endereco;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EnderecoDTO(
		Long id,
		
		String logradouro,
		
		Integer numero,

		String complemento,

		String bairro,

		String cidade,

		String uf,

		String codigoPostal,

		String pais
		) {
	
	
		public Endereco toEntity(){
			Endereco endereco = new Endereco();
			endereco.setId(this.id);
			endereco.setBairro(this.bairro);
			endereco.setCidade(this.cidade);
			endereco.setCodigoPostal(this.codigoPostal);
			endereco.setComplemento(this.complemento);
			endereco.setLogradouro(this.logradouro);
			endereco.setNumero(this.numero);
			endereco.setPais(this.pais);
			endereco.setUf(this.uf);
			return endereco;
		}
		
		public static EnderecoDTO toDto(Endereco endereco) {
			return new EnderecoDTO(
					endereco.getId(),
					endereco.getLogradouro(),
					endereco.getNumero(),
					endereco.getComplemento(),
					endereco.getBairro(),
					endereco.getCidade(),
					endereco.getUf(),
					endereco.getCodigoPostal(),
					endereco.getPais()
					);
		}

}
