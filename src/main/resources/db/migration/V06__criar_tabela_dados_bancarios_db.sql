CREATE TABLE dados_bancarios(
	id SERIAL PRIMARY KEY,
	instituicao_financeira VARCHAR(255) NOT NULL,
	codigo_instituicao VARCHAR(3) NOT NULL,
	tipo_conta VARCHAR(50) NOT NULL,
	digito_conta VARCHAR(5) NOT NULL,
	agencia VARCHAR(20) NOT NULL,
	conta VARCHAR(20) NOT NULL,
	outros VARCHAR(100),
	pix VARCHAR(100),
	
	id_colaborador BIGINT,
	id_pessoa_fisica BIGINT,
	id_pessoa_juridica BIGINT,
	
	
	CONSTRAINT fk_colaborador FOREIGN KEY (id_colaborador) REFERENCES colaborador(id),
	CONSTRAINT fk_pessoa_fisica FOREIGN KEY (id_pessoa_fisica) REFERENCES pessoa_fisica(id),
	CONSTRAINT fk_pessoa_juridica FOREIGN KEY (id_pessoa_juridica) REFERENCES pessoa_juridica(id)
)