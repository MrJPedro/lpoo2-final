create table modelo_van (
	id_modelo int,
    id_marca_fk int,
    nome_modelo varchar(20),
    constraint pkModeloVan primary key (id_modelo),
    constraint fkMarcaveiculolModelovan foreign key (id_marca_fk) 
		references marca_veiculo(id_marca)
);

insert into modelo_van
values
(1, 1, 'KOMBI'),
(2, 1, 'TRANSPORTER'),
(3, 1, 'CARAVELLE'),

(4, 2, 'FIORINO'),
(5, 2, 'DOBLO'),
(6, 2, 'SCUDO'),

(7, 4, 'SPRINTER'),
(8, 4, 'VITO'),
(9, 4, 'CLASSE_V');

select * from modelo_van;