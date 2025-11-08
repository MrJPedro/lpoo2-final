create table categoria_veiculo (
	id_categoria int,
    nome_categoria varchar (20),
    constraint pkCategoriaVeiculo primary key (id_categoria)
);

insert into categoria_veiculo
values
(1, 'POPULAR'),
(2, 'INTERMEDIARIO'),
(3, 'LUXO');

select * from categoria_veiculo;