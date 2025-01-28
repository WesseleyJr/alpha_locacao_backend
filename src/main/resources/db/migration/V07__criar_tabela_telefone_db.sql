CREATE TABLE telefone(
	id SERIAL PRIMARY KEY,
	numero VARCHAR(30) NOT NULL,
	tipo VARCHAR(20) NOT NULL,
	ddd VARCHAR(5) NOT NULL,
	codigo_pais VARCHAR(5) NOT NULL,
	
	id_pessoa_fisica BIGINT,
	id_pessoa_juridica BIGINT,
	id_colaborador BIGINT,
	
	
	CONSTRAINT fk_pessoa_fisica FOREIGN KEY (id_pessoa_fisica) REFERENCES pessoa_fisica(id),
	CONSTRAINT fk_pessoa_juridica FOREIGN KEY (id_pessoa_juridica) REFERENCES pessoa_juridica(id),
	CONSTRAINT fk_colaborador FOREIGN KEY (id_colaborador) REFERENCES colaborador(id)
)