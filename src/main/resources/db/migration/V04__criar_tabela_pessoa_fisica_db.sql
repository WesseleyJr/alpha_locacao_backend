CREATE TABLE pessoa_fisica (
	id SERIAL PRIMARY KEY,
	nome VARCHAR(255) NOT NULL,
	cpf VARCHAR(11) NOT NULL UNIQUE CHECK (cpf ~ '^\d{11}$'),
	email VARCHAR(100) NOT NULL UNIQUE,
	profissao VARCHAR(255) NOT NULL,
	estado_civil VARCHAR(100) NOT NULL,
	nacionalidade VARCHAR(100) NOT NULL,
	rg VARCHAR(11) NOT NULL UNIQUE,
	cnh VARCHAR(9) UNIQUE,
	orgao_emissor VARCHAR(100) NOT NULL,
	data_emissao DATE NOT NULL,
	sexo VARCHAR(100) NOT NULL,
	data_nascimento DATE NOT NULL,
	id_endereco BIGINT NOT NULL,
	
	
	CONSTRAINT fk_endereco FOREIGN KEY (id_endereco) REFERENCES endereco(id)
)