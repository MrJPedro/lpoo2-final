create table modelo_motocicleta (
	id_modelo int,
    id_marca_fk int,
    nome_modelo varchar(20),
    constraint pkModeloMotocicleta primary key (id_modelo),
    constraint fkMarcaveiculolModelomotocicleta foreign key (id_marca_fk) 
		references marca_veiculo(id_marca)
);

insert into modelo_motocicleta
values
(1, 6, 'CG_125'),
(2, 6, 'XRE_300'),
(3, 6, 'FALCON'),

(4, 7, 'FAZER_250'),
(5, 7, 'MT_03'),
(6, 7, 'LANDER_250'),

(7, 8, 'IRON_883'),
(8, 8, 'FAT_BOY'),
(9, 8, 'STREE_GLIDE');

select * from modelo_motocicleta;