create table estado_veiculo (
	id_estado int,
    nome_estado varchar (20),
    constraint pkEstadoVeiculo primary key (id_estado)
);

insert into estado_veiculo
values
(1, 'NOVO'),
(2, 'LOCADO'),
(3, 'DISPONIVEL'),
(4, 'VENDIDO');

select * from estado_veiculo;