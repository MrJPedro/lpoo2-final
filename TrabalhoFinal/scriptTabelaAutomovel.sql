create table automovel (
	placa_fk char(7),
    id_modelo_fk int,
    constraint pkAutomovel primary key (placa_fk),
    constraint fkVeiculoAutomovel foreign key (placa_fk)
		references veiculo (placa),
	constraint fkModeloautomovelAutomovel foreign key (id_modelo_fk)
		references modelo_automovel (id_modelo)
);

insert into automovel
values
('AAA1001', 7),
('BBB1002', 16),
('CCC1003', 12),
('DDD1004', 6),
('EEE1005', 15),
('FFF1006', 1),
('GGG1007', 9),
('HHH1008', 18),
('III1009', 11),
('JJJ1010', 5),
('KKK1011', 14),
('DDD1030', 8),
('EEE1031', 2),
('FFF1032', 13),
('GGG1033', 16),
('HHH1034', 5),
('III1035', 3);

select * 
from automovel
	join modelo_automovel modelo on automovel.id_modelo_fk = modelo.id_modelo;