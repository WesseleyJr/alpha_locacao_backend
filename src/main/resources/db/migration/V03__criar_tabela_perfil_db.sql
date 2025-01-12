CREATE TABLE perfil (
id serial primary key,
nome varchar(40)
);

CREATE TABLE colaborador_perfil (
id_colaborador int references colaborador(id),
id_perfil int references perfil(id),
constraint pk_colaborador_perfil primary key (id_colaborador, id_perfil)
);

insert into perfil (id, nome) values
(1, 'ROLE_ADMIN'),
(2, 'ROLE_FINANCEIRO'),
(3, 'ROLE_ADMINISTRACAO');
