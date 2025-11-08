drop schema if exists lpoo2;
create schema if not exists lpoo2;
use lpoo2;


/* Criar e popular */
create table cliente (
	nome varchar(40),
    sobrenome varchar (40),
    rg int(9) zerofill,
    cpf bigint(11) zerofill,
    endereco varchar (200),
    constraint pkCliente primary key (cpf)
);

insert into cliente
values
('Maria', 'Oliveira', 987654321, 98765432100, 'Av. Paulista, 456'),
('Pedro', 'Santos', 456123789, 45612378911, 'Rua do Sol, 789'),
('Ana', 'Souza', 321654987, 32165498722, 'Rua Central, 101'),
('Lucas', 'Pereira', 654987321, 65498732133, 'Rua das Árvores, 202'),
('Juliana', 'Almeida', 741852963, 74185296344, 'Av. Brasil, 303'),
('Gabriel', 'Lima', 852963741, 85296374155, 'Rua da Paz, 404'),
('Fernanda', 'Gomes', 963741852, 96374185266, 'Rua Nova, 505'),
('Mateus', 'Rocha', 159357258, 15935725877, 'Av. Paraná, 606'),
('Camila', 'Ribeiro', 258159357, 25815935788, 'Rua Bahia, 707'),
('Ricardo', 'Barbosa', 357258159, 35725815999, 'Rua Amazonas, 808'),
('Beatriz', 'Carvalho', 456789123, 45678912310, 'Av. Rio Branco, 909'),
('Rafael', 'Moura', 789123456, 78912345621, 'Rua Ceará, 1001'),
('Larissa', 'Castro', 123789456, 12378945632, 'Rua São Paulo, 1102'),
('Thiago', 'Martins', 987321654, 98732165443, 'Av. Goiás, 1203'),
('Mariana', 'Fernandes', 654123987, 65412398754, 'Rua Paraná, 1304'),
('Eduardo', 'Nascimento', 321987654, 32198765465, 'Rua Sergipe, 1405'),
('Aline', 'Araújo', 741963852, 74196385276, 'Rua Minas Gerais, 1506'),
('Bruno', 'Cardoso', 852741963, 85274196387, 'Rua Pará, 1607'),
('Vanessa', 'Teixeira', 963852741, 96385274198, 'Av. Amazonas, 1708'),
('Diego', 'Vieira', 159258357, 15925835709, 'Rua Acre, 1809'),
('Patrícia', 'Correia', 258357159, 25835715910, 'Rua Tocantins, 1901'),
('André', 'Freitas', 357159258, 35715925821, 'Rua Piauí, 2002'),
('Letícia', 'Faria', 456123987, 45612398732, 'Av. Maranhão, 2103'),
('Fábio', 'Monteiro', 789456123, 78945612343, 'Rua Santa Catarina, 2204'),
('Isabela', 'Mendes', 123654789, 12365478954, 'Rua Espírito Santo, 2305'),
('Leonardo', 'Ramos', 987456321, 98745632165, 'Rua Alagoas, 2406'),
('Tatiane', 'Sales', 654789123, 65478912376, 'Rua Rondônia, 2507'),
('Felipe', 'Pinto', 321654987, 32165498787, 'Rua Roraima, 2608'),
('Carolina', 'Santiago', 741852963, 74185296398, 'Av. Acre, 2709'),
('Gustavo', 'Batista', 852963741, 85296374109, 'Rua Pará, 2801'),
('Renata', 'Lopes', 963741852, 96374185210, 'Rua Amazonas, 2902'),
('Marcelo', 'Sousa', 159357258, 15935725821, 'Rua São Paulo, 3003'),
('Débora', 'Barros', 258159357, 25815935732, 'Rua Bahia, 3104'),
('Vinícius', 'Campos', 357258159, 35725815943, 'Rua Pernambuco, 3205'),
('Simone', 'Peixoto', 456789123, 45678912354, 'Rua Ceará, 3306'),
('Rodrigo', 'Dias', 789123456, 78912345665, 'Rua Sergipe, 3407'),
('Amanda', 'Moraes', 123789456, 12378945676, 'Rua Goiás, 3508'),
('Alexandre', 'Ferreira', 987321654, 98732165487, 'Rua Mato Grosso, 3609'),
('Daniele', 'Costa', 654123987, 65412398798, 'Av. Rio Branco, 3701'),
('Bruno', 'Santana', 321987654, 32198765409, 'Rua Paraná, 3802'),
('Carla', 'Rezende', 741963852, 74196385210, 'Rua Pará, 3903'),
('Rafael', 'Machado', 852741963, 85274196321, 'Rua Tocantins, 4004'),
('Priscila', 'Borges', 963852741, 96385274132, 'Av. Brasil, 4105'),
('Leandro', 'Azevedo', 159258357, 15925835743, 'Rua Minas Gerais, 4206'),
('Gisele', 'Souza', 258357159, 25835715954, 'Rua Santa Catarina, 4307'),
('Fernando', 'Morais', 357159258, 35715925865, 'Rua Goiás, 4408'),
('Bruna', 'Marques', 456123987, 45612398776, 'Rua Espírito Santo, 4509'),
('Carlos', 'Dantas', 789456123, 78945612387, 'Rua Alagoas, 4601'),
('Natália', 'Cavalcanti', 123654789, 12365478998, 'Rua Rondônia, 4702'),
('Paulo', 'Antunes', 987456321, 98745632109, 'Rua Roraima, 4803');


/* Criar e popular */
create table locacao (
	id_locacao serial,
    dias_locacao int,
    valor_locacao double,
    data_locacao date,
    cpf_cliente_fk bigint(11) zerofill,
    constraint pkLocacao primary key (id_locacao),
    constraint fkClienteLocacao foreign key (cpf_cliente_fk)
		references cliente (cpf)
);

create table marca_veiculo (
	id_marca int,
    nome_marca varchar(20),
    constraint pkMarcaVeiculo primary key (id_marca)
);


/* Criar e popular */
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


/* Criar e popular */
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


/* Criar e popular */
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


/* Criar e popular */
create table veiculo (
	id_marca_fk int,
    id_estado_fk int,
    id_categoria_fk int,
    id_locacao_fk bigint unsigned,
    valor_compra double not null,
    placa char(7),
    ano year not null,
    constraint pkVeiculo primary key (placa),
    constraint fkMarcaveiculoVeiculo foreign key (id_marca_fk)
		references marca_veiculo (id_marca),
	constraint fkEstadoveiculoVeiculo foreign key (id_estado_fk)
		references estado_veiculo (id_estado),
	constraint fkCategoriaveiculoVeiculo foreign key (id_categoria_fk)
		references categoria_veiculo (id_categoria),
	constraint fkLocacaoVeiculo foreign key (id_locacao_fk)
		references locacao (id_locacao)
);

insert into veiculo
values
(2, 3, 1, null, 18000, 'AAA1001', 2015),
(6, 3, 2, null, 25000, 'BBB1002', 2017),
(3, 3, 3, null, 40000, 'CCC1003', 2020),
(4, 3, 3, null, 55000, 'DDD1004', 2021),
(5, 3, 3, null, 85000, 'EEE1005', 2022),
(1, 3, 1, null, 20000, 'FFF1006', 2016),
(2, 3, 2, null, 30000, 'GGG1007', 2018),
(6, 3, 3, null, 60000, 'HHH1008', 2021),
(3, 3, 1, null, 22000, 'III1009', 2017),
(4, 3, 2, null, 45000, 'JJJ1010', 2019),
(5, 3, 3, null, 80000, 'KKK1011', 2022),
(6, 3, 1, null, 8000, 'LLL1012', 2014),
(7, 3, 2, null, 15000, 'MMM1013', 2016),
(8, 3, 3, null, 35000, 'NNN1014', 2019),
(6, 3, 3, null, 28000, 'OOO1015', 2018),
(7, 3, 3, null, 30000, 'PPP1016', 2020),
(8, 3, 3, null, 50000, 'QQQ1017', 2021),
(7, 3, 2, null, 17000, 'RRR1018', 2017),
(8, 3, 3, null, 60000, 'SSS1019', 2022),
(6, 3, 2, null, 18000, 'TTT1020', 2017),
(1, 3, 1, null, 25000, 'UUU1021', 2015),
(2, 3, 1, null, 27000, 'VVV1022', 2016),
(4, 3, 2, null, 40000, 'WWW1023', 2019),
(2, 3, 2, null, 35000, 'XXX1024', 2018),
(1, 3, 2, null, 38000, 'YYY1025', 2020),
(4, 3, 3, null, 60000, 'ZZZ1026', 2021),
(2, 3, 3, null, 42000, 'AAA1027', 2020),
(1, 3, 3, null, 50000, 'BBB1028', 2022),
(4, 3, 3, null, 65000, 'CCC1029', 2022),
(2, 3, 1, null, 21000, 'DDD1030', 2016),
(1, 3, 2, null, 32000, 'EEE1031', 2018),
(5, 3, 3, null, 90000, 'FFF1032', 2023),
(6, 3, 1, null, 26000, 'GGG1033', 2017),
(4, 3, 2, null, 47000, 'HHH1034', 2019),
(1, 3, 3, null, 50000, 'III1035', 2021),
(6, 3, 1, null, 8500, 'JJJ1036', 2014),
(7, 3, 2, null, 16000, 'KKK1037', 2016),
(8, 3, 3, null, 36000, 'LLL1038', 2019),
(6, 3, 3, null, 29000, 'MMM1039', 2018),
(7, 3, 3, null, 31000, 'NNN1040', 2020),
(8, 3, 3, null, 51000, 'OOO1041', 2021),
(7, 3, 2, null, 17500, 'PPP1042', 2017),
(8, 3, 3, null, 61000, 'QQQ1043', 2022),
(6, 3, 2, null, 18500, 'RRR1044', 2017),
(1, 3, 1, null, 26000, 'SSS1045', 2015),
(2, 3, 1, null, 28000, 'TTT1046', 2016),
(4, 3, 2, null, 41000, 'UUU1047', 2019),
(2, 3, 2, null, 36000, 'VVV1048', 2018),
(1, 3, 2, null, 39000, 'WWW1049', 2020),
(4, 3, 3, null, 61000, 'XXX1050', 2021);


/* Criar e popular */
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


/* Criar e popular */
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


/* Criar e popular */
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


/* Criar e popular */
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


/* Criar e popular */
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


/* Criar e popular */
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