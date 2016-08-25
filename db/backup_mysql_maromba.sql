# Host: msxacademic.mysql.dbaas.com.br  (Version: 5.6.30-76.3-log)
# Date: 2016-08-24 21:43:41
# Generator: MySQL-Front 5.3  (Build 4.233)

/*!40101 SET NAMES latin1 */;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "logs"
#


#
# Structure for table "modalidades"
#

DROP TABLE IF EXISTS `modalidades`;
CREATE TABLE `modalidades` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) COLLATE latin1_general_ci DEFAULT NULL,
  `valor_mensal` double NOT NULL DEFAULT '0',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;

#
# Data for table "modalidades"
#


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
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

#
# Data for table "pessoas"
#

INSERT INTO `pessoas` VALUES (19,'Iuri Carneiro','1f0e3dad99908345f7439f8ffabdffc4','19','g',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(20,'Orlando Baptista','98f13708210194c475687be6106a3b84','20','t',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(21,'Gabriel Baptista','3c59dc048e8850243be8079a5c74d079','21','t',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(22,'William Shaeffer','b6d767d2f8ed5d21a44b0e5886680cb9','22','t',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(23,'Fabiana Souza',NULL,NULL,'c',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(24,'Carlos Fernandes',NULL,NULL,'c',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(25,'Janaina Fornerolli',NULL,NULL,'c',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(26,'Fernando Alonso',NULL,NULL,'c',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(27,'James Hunt',NULL,NULL,'c','04817039930',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(28,'Pat Simons','33e75ff09dd601bbe69f351039152189','28','t',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
