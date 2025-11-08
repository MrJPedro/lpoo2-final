create table modelo_automovel (
	id_modelo int,
    id_marca_fk int,
    nome_modelo varchar(20),
    constraint pkModeloAutomovel primary key (id_modelo),
    constraint fkMarcaveiculolModeloautomovel foreign key (id_marca_fk) 
		references marca_veiculo(id_marca)
);

insert into modelo_automovel
values
(1, 1, 'GOL'),
(2, 1, 'POLO'),
(3, 1, 'JETTA'),

(4, 4, 'CLASSE_A'),
(5, 4, 'CLASSE_B'),
(6, 4, 'CLASSE_C'),

(7, 2, 'UNO'),
(8, 2, 'PALIO'),
(9, 2, 'CRONOS'),

(10, 3, 'SANDERO'),
(11, 3, 'CLIO'),
(12, 3, 'DUSTER'),

(13, 5, 'PANAMERA'),
(14, 5, 'CAYENNE'),
(15, 5, 'CARRERA'),

(16, 6, 'CITY'),
(17, 6, 'CIVIC'),
(18, 6, 'ACCORD');

select *
from modelo_automovel modelo
	join marca_veiculo marca on modelo.id_marca_fk = marca.id_marca;