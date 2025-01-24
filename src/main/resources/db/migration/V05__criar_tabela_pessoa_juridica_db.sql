CREATE TABLE pessoa_juridica (
	id SERIAL PRIMARY KEY,
	nome_fantasia TEXT NOT NULL,
	razao_social TEXT NOT NULL UNIQUE, 
	cnpj VARCHAR(14) NOT NULL UNIQUE,
	inscricao_estadual VARCHAR(14) NOT NULL,
	inscricao_municipal VARCHAR(30) NOT NULL,
	email VARCHAR(100) NOT NULL UNIQUE,
	id_endereco BIGINT NOT NULL,
	
	
	CONSTRAINT fk_endereco FOREIGN KEY (id_endereco) REFERENCES endereco(id)
)