create table van (
	placa_fk char(7),
    id_modelo_fk int,
    constraint pkVan primary key (placa_fk),
    constraint fkVeiculoVan foreign key (placa_fk)
		references veiculo (placa),
	constraint fkModelovanVan foreign key (id_modelo_fk)
		references modelo_van (id_modelo)
);

insert into van
values
('UUU1021', 1),
('VVV1022', 4),
('WWW1023', 8),
('XXX1024', 5),
('YYY1025', 2),
('ZZZ1026', 7),
('AAA1027', 6),
('BBB1028', 3),
('CCC1029', 9),
('SSS1045', 1),
('TTT1046', 4),
('UUU1047', 8),
('VVV1048', 5),
('WWW1049', 2),
('XXX1050', 7);

select * from van;