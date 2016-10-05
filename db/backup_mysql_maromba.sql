# Host: msxacademic.mysql.dbaas.com.br  (Version 5.6.30-76.3-log)
# Date: 2016-10-05 09:45:22
# Generator: MySQL-Front 5.4  (Build 1.1)

/*!40101 SET NAMES latin1 */;

#
# Structure for table "atividades"
#

DROP TABLE IF EXISTS `atividades`;
CREATE TABLE `atividades` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `tempo` int(11) DEFAULT NULL,
  `idmanutencao` int(11) DEFAULT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "atividades"
#


#
# Structure for table "controle_acesso"
#

DROP TABLE IF EXISTS `controle_acesso`;
CREATE TABLE `controle_acesso` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `idpessoa` int(11) NOT NULL DEFAULT '0',
  `dt_acesso` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `liberado` varchar(1) COLLATE latin1_general_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;

#
# Data for table "controle_acesso"
#


#
# Structure for table "logs"
#

DROP TABLE IF EXISTS `logs`;
CREATE TABLE `logs` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `descricao` text,
  `idpessoa` int(11) DEFAULT NULL,
  `data` datetime DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8;

#
# Data for table "logs"
#

INSERT INTO `logs` VALUES (59,'Marca: Apple cadastrada.',20,'2016-07-12 00:22:21'),(60,'Marca: Samsung cadastrada.',20,'2016-07-12 00:22:27'),(61,'Marca: Nokia cadastrada.',20,'2016-07-12 00:22:32'),(62,'Marca: Motorola cadastrada.',20,'2016-07-12 00:22:36'),(63,'Marca: LG cadastrada.',20,'2016-07-12 00:22:39'),(64,'Manutencao: Tela Quebrada cadastrada.',20,'2016-07-12 00:26:16');

#
# Structure for table "manutencoes"
#

DROP TABLE IF EXISTS `manutencoes`;
CREATE TABLE `manutencoes` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `relato_cliente` text,
  `status` varchar(244) DEFAULT NULL,
  `idcliente` int(11) DEFAULT NULL,
  `idmodelo` int(11) DEFAULT NULL,
  `idfuncionario` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

#
# Data for table "manutencoes"
#

INSERT INTO `manutencoes` VALUES (29,'Tela Quebrada','Aguardando Manutenção',25,4,20);

#
# Structure for table "marcas"
#

DROP TABLE IF EXISTS `marcas`;
CREATE TABLE `marcas` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;

#
# Data for table "marcas"
#

INSERT INTO `marcas` VALUES (36,'Apple'),(37,'Samsung'),(38,'Nokia'),(39,'Motorola'),(40,'LG');

#
# Structure for table "modalidades"
#

DROP TABLE IF EXISTS `modalidades`;
CREATE TABLE `modalidades` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) DEFAULT NULL,
  `valor` int(11) DEFAULT NULL,
  `diasSemana` varchar(7) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

#
# Data for table "modalidades"
#

INSERT INTO `modalidades` VALUES (1,'nome',0,'null'),(2,'Bodyyy!',0,'null'),(3,'asdasdasd',0,'null'),(4,'orlando',23,'null'),(5,'Musculação',12,'null'),(6,'Teste',2,'null');

#
# Structure for table "modelos"
#

DROP TABLE IF EXISTS `modelos`;
CREATE TABLE `modelos` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) DEFAULT NULL,
  `idmarca` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

#
# Data for table "modelos"
#

INSERT INTO `modelos` VALUES (3,'Iphone 4S',36),(4,'Iphone 5C',36),(5,'Iphone 6',36),(6,'Iphone 6S',36),(7,'4G',40),(8,'Galaxy S6',37),(9,'Lumia 720',38),(10,'MotoG',39),(11,'G5',40);

#
# Structure for table "pessoas"
#

DROP TABLE IF EXISTS `pessoas`;
CREATE TABLE `pessoas` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) DEFAULT NULL,
  `senha` varchar(255) DEFAULT NULL,
  `usuario` varchar(255) DEFAULT NULL,
  `funcao` varchar(1) DEFAULT NULL,
  `cpf` varchar(20) DEFAULT NULL,
  `telefone` varchar(20) DEFAULT NULL,
  `dn` date DEFAULT NULL,
  `cep` varchar(20) DEFAULT NULL,
  `endereco` varchar(255) DEFAULT NULL,
  `bairro` varchar(255) DEFAULT NULL,
  `cidade` varchar(255) DEFAULT NULL,
  `uf` varchar(2) DEFAULT NULL,
  `celular` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;

#
# Data for table "pessoas"
#

INSERT INTO `pessoas` VALUES (19,'Iuri Carneiro','1f0e3dad99908345f7439f8ffabdffc4','19','g',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(20,'Orlando Baptista','202cb962ac59075b964b07152d234b70','20','r',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(21,'Gabriel Baptista','3c59dc048e8850243be8079a5c74d079','21','t',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(22,'William Shaeffer','b6d767d2f8ed5d21a44b0e5886680cb9','22','t',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(23,'Fabiana Souza',NULL,NULL,'c',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(24,'Carlos Fernandes',NULL,NULL,'c',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(25,'Janaina Fornerolli',NULL,NULL,'c',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(26,'Fernando Alonso',NULL,NULL,'c',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(27,'James Hunt',NULL,NULL,'c','04817039930',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(28,'Pat Simons','33e75ff09dd601bbe69f351039152189','28','t',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(29,'Musculaacao','6ea9ab1baa0efb9e19094440c317e21b','29','t',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(30,'Body Pump','34173cb38f07f89ddbebc2ac9128303f','30','t',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(31,'kimi',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(32,'kimi',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(33,'Kimi',NULL,NULL,NULL,'Raikonen',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(34,'Kimi',NULL,NULL,NULL,'Raikonen',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(35,'Sebastian Vettel',NULL,NULL,NULL,'12123123',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(36,'asdasda',NULL,NULL,NULL,'qweqwe',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(37,'Fulano',NULL,NULL,NULL,'de Tal',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(38,'mrHax',NULL,NULL,NULL,'04817039930',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
