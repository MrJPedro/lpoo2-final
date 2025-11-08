create table motocicleta (
	placa_fk char(7),
    id_modelo_fk int,
    constraint pkMotocicleta primary key (placa_fk),
    constraint fkVeiculoMotocicleta foreign key (placa_fk)
		references veiculo (placa),
	constraint fkModelomotocicletaMotocicleta foreign key (id_modelo_fk)
		references modelo_motocicleta (id_modelo)
);

insert into motocicleta
values
('LLL1012', 1),
('MMM1013', 4),
('NNN1014', 7),
('OOO1015', 3),
('PPP1016', 5),
('QQQ1017', 8),
('RRR1018', 6),
('SSS1019', 9),
('TTT1020', 2),
('JJJ1036', 1),
('KKK1037', 4),
('LLL1038', 7),
('MMM1039', 3),
('NNN1040', 5),
('OOO1041', 8),
('PPP1042', 6),
('QQQ1043', 9),
('RRR1044', 2);

select * from motocicleta;