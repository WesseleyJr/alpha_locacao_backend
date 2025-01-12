CREATE TABLE colaborador (
	
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    senha VARCHAR(255) NOT NULL,
    telefone VARCHAR(11),
    cpf VARCHAR(11) NOT NULL UNIQUE CHECK (cpf ~ '^\d{11}$'),
    email VARCHAR(100) NOT NULL UNIQUE,
    data_nascimento DATE NOT NULL,
    id_endereco BIGINT NOT NULL,
    
    CONSTRAINT fk_endereco FOREIGN KEY (id_endereco) REFERENCES endereco(id)
    
);