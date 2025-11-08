create table locacao (
	id_locacao serial,
    dias_locacao int,
    valor_locacao double,
    data_locacao date,
    cpf_cliente_fk bigint(11),
    constraint pkLocacao primary key (id_locacao),
    constraint fkClienteLocacao foreign key (cpf_cliente_fk)
		references cliente (cpf)
);