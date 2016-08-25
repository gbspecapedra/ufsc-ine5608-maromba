# Host: localhost  (Version: 5.6.26-log)
# Date: 2016-07-01 00:39:33
# Generator: MySQL-Front 5.3  (Build 4.233)

/*!40101 SET NAMES utf8 */;

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
# Structure for table "logs"
#

DROP TABLE IF EXISTS `logs`;
CREATE TABLE `logs` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `descricao` text,
  `idpessoa` int(11) DEFAULT NULL,
  `data` datetime DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

#
# Data for table "logs"
#

INSERT INTO `logs` VALUES (4,'null',0,'2016-06-30 17:48:00'),(5,'Marca: ERICSON deletada.',0,'2016-06-30 17:48:54'),(6,'Marca: HAUWEI deletada.',3,'2016-06-30 17:49:51');

#
# Structure for table "manutencoes"
#

DROP TABLE IF EXISTS `manutencoes`;
CREATE TABLE `manutencoes` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `relato_cliente` text,
  `status` varchar(244) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "manutencoes"
#


#
# Structure for table "marcas"
#

DROP TABLE IF EXISTS `marcas`;
CREATE TABLE `marcas` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;

#
# Data for table "marcas"
#

INSERT INTO `marcas` VALUES (28,'Samsung'),(29,'LG'),(33,'Apple'),(34,'Nokiaa'),(35,'Motorola'),(36,'asdfff');

#
# Structure for table "modelos"
#

DROP TABLE IF EXISTS `modelos`;
CREATE TABLE `modelos` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) DEFAULT NULL,
  `idmarca` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

#
# Data for table "modelos"
#

INSERT INTO `modelos` VALUES (1,'5C',29),(2,'Galaxy',28);

#
# Structure for table "pessoas"
#

DROP TABLE IF EXISTS `pessoas`;
CREATE TABLE `pessoas` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) DEFAULT NULL,
  `senha` varchar(255) DEFAULT NULL,
  `usuario` varchar(255) DEFAULT NULL,
  `tipo` varchar(1) DEFAULT NULL,
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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

#
# Data for table "pessoas"
#

INSERT INTO `pessoas` VALUES (1,'admin','admin','admin',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(3,'Ciclano','demo','demo',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(4,'Ciclano','',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(6,'Giselee',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
