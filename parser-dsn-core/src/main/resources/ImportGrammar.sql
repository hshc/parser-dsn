USE `DSNversion`;
DROP TABLE `DSNStructureByVersion`;
CREATE TABLE IF NOT EXISTS `DSNStructureByVersion` (
  `versionNorme` VARCHAR(10) NOT NULL COMMENT 'numero de version de la norme applicable',
  `numOrdre` INT NOT NULL COMMENT 'numero d ordre de la rubrique dans la structure de la norme',
  `rubriqueName` VARCHAR(20) NOT NULL COMMENT 'nom de la rubrique',
  `obligatoireFacultatifFlag` CHAR(1) NOT NULL COMMENT 'la rubrique est elle obligatoire ou facultative',
  `beginEndFlag` VARCHAR(20) NULL COMMENT 'la rubrique ouvre-t-elle (B?) ou ferme-t-elle (E*) un ou plusieurs blocs',
  `loopingFlag` CHAR(1) NULL COMMENT 'la rubrique commence-t-elle une structure redondante(cardinalite multiple)?',
  `obligatoireFacultatifBlocFlag` CHAR(1) NOT NULL COMMENT 'la rubrique ouvre-t-elle un bloc obligatoire ou facultatif',
  `dataTypes` CHAR(1) NOT NULL COMMENT 'type de donnee (X,E,R,D,N)',
  `fieldSize` VARCHAR(10) NOT NULL COMMENT 'taille du champ sous la forme [min,max]',
  `regex` VARCHAR(300) NULL COMMENT 'expression reguliere de controle du champ',
  PRIMARY KEY (`versionNorme`, `numOrdre`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COMMENT = 'Table de description des structures DSN evoluant en fonction du numero de la norme';


USE `DSNversion`;
LOAD DATA 
INFILE "/home/code/mysql/V01DSNNTA.csv" 
INTO TABLE `DSNStructureByVersion` 
COLUMNS TERMINATED BY ';'
OPTIONALLY ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 0 LINES;
USE `DSNversion`;
LOAD DATA 
INFILE "/home/code/mysql/V2_2.csv" 
INTO TABLE `DSNStructureByVersion` 
COLUMNS TERMINATED BY ';'
OPTIONALLY ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 0 LINES;

USE `DSNversion`;
LOCK TABLES `DSNStructureByVersion` WRITE;
/*!40000 ALTER TABLE `DSNStructureByVersion` DISABLE KEYS */;
INSERT INTO `DSNStructureByVersion` VALUES 
('V01',1,'G01.001','F','B','','O'),
('V01',2,'G01.002','O','','',''),
('V01',3,'G01.003','F','','',''),
('V01',4,'G02.001','F','B','L','F'),
('V01',5,'G02.003','F','','',''),
('V01',6,'G02.004','O','','',''),
('V01',7,'G03.001','F','B','L','O'),
('V01',8,'G03.002','O','','',''),
('V01',9,'G03.003','F','E','',''),
('V01',10,'G02.005','F','E','',''),
('V01',11,'G04.001','F','B','L','O'),
('V01',12,'G04.002','O','','',''),
('V01',13,'G05.001','F','B','L','F'),
('V01',14,'G05.002','O','','',''),
('V01',15,'G05.003','F','EE','',''),
('V01',16,'G01.004','F','E','','');
/*!40000 ALTER TABLE `DSNStructureByVersion` ENABLE KEYS */;
UNLOCK TABLES;

