create table marca_veiculo (
	id_marca int,
    nome_marca varchar(20),
    constraint pkMarcaVeiculo primary key (id_marca)
);

insert into marca_veiculo
values
(1, 'WOLKSWAGEN'),
(2, 'FIAT'),
(3, 'RENAULT'),
(4, 'MERCEDES'),
(5, 'PORSCHE'),
(6, 'HONDA'),
(7, 'YAMAHA'),
(8, 'HARLEY_DAVIDSON');

select * from marca_veiculo;