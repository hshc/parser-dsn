SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP SCHEMA `DSNstock`;
CREATE SCHEMA IF NOT EXISTS `DSNstock` DEFAULT CHARACTER SET latin1 ;
USE `DSNstock` ;

CREATE TABLE IF NOT EXISTS `MULTIPLE_HILO_GEN` (
	`GENERATOR_ID` VARCHAR(30) 	DEFAULT NULL,
	`HI_VALUE` INT 					DEFAULT NULL
)
ENGINE=InnoDB 
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `T00` Envoi
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `T00` (
  `id_envoi` 	INT 			NOT NULL,
  `F001` 		VARCHAR(20) 	NULL COMMENT 'Nom du logiciel utilisé',
  `F002` 		VARCHAR(20) 	NULL COMMENT 'Nom de l éditeur',
  `F003` 		VARCHAR(10) 	NULL COMMENT 'Numéro de version du logiciel utilisé',
  `F004` 		VARCHAR(50) 	NULL COMMENT 'Code de conformité en pré-contrôle',
  `F005` 		VARCHAR(2) 	NULL COMMENT 'Code envoi du fichier d essai ou réel',
  `F006` 		VARCHAR(6) 	NULL COMMENT 'Numéro de version de la norme utilisée',
  `F007` 		VARCHAR(2) 	NULL COMMENT 'Point de dépôt',
  `F008` 		VARCHAR(2) 	NULL COMMENT 'Type de l envoi',
  `invalid` 	INT 			NOT NULL DEFAULT 0,
  `recycle` 	INT 			NOT NULL DEFAULT 0,
  PRIMARY KEY (`id_envoi`)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COMMENT = 'Envoi et Total de l envoi';


CREATE TABLE IF NOT EXISTS `GestionDSN` (
  `id_envoi` INT NOT NULL,
  `dateHeureDepot` DATETIME NULL,
  `statut` INT NULL,
  PRIMARY KEY (`id_envoi`),
  INDEX `fk_GestionDSN_idx` (`id_envoi` ASC),
  CONSTRAINT `fk_GestionDSN`
    FOREIGN KEY (`id_envoi`)
    REFERENCES `T00` (`id_envoi`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `T01` Emetteur
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `T01` (
  `id_emetteur` 	INT 			NOT NULL,
  `F001` 			VARCHAR(9) 	NULL COMMENT 'Siren de l émetteur de l envoi',
  `F002` 			VARCHAR(5) 	NULL COMMENT 'Nic de l émetteur de l envoi',
  `F003` 			VARCHAR(60) 	NULL COMMENT 'Nom ou raison sociale de l émetteur',
  `F004` 			VARCHAR(50) 	NULL COMMENT 'Numéro, extension, nature et libellé de la voie',
  `F005` 			VARCHAR(5) 	NULL COMMENT 'Code postal',
  `F006` 			VARCHAR(50) 	NULL COMMENT 'Localité',
  `F007` 			VARCHAR(2) 	NULL COMMENT 'Code pays',
  `F008` 			VARCHAR(50) 	NULL COMMENT 'Code de distribution à l étranger',
  `F009` 			VARCHAR(50) 	NULL COMMENT 'Complément de la localisation de la construction',
  `F010` 			VARCHAR(50) 	NULL COMMENT 'Siren de l émetteur de l envoi',
  `invalid` 		INT 			NOT NULL DEFAULT 0,
  `recycle` 		INT 			NOT NULL DEFAULT 0,
  PRIMARY KEY (`id_emetteur`),
  CONSTRAINT `fk_emetteur_envoi`
    FOREIGN KEY (`id_emetteur`)
    REFERENCES `T00` (`id_envoi`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COMMENT = 'Emetteur';


-- -----------------------------------------------------
-- Table `T02` Contact Emetteur
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `T02` (
  `id_contactEmetteur` INT NOT NULL,
  `id_envoi` 	INT 			NOT NULL,
  `F001` 		VARCHAR(2) 	NULL COMMENT 'Code civilité',
  `F002` 		VARCHAR(80) 	NULL COMMENT 'Nom et prénom de la personne à contacter',
  `F003` 		VARCHAR(2) 	NULL COMMENT 'Code domaine d intervention',
  `F004` 		VARCHAR(100) 	NULL COMMENT 'Adresse mél du contact émetteur',
  `F005` 		VARCHAR(20) 	NULL COMMENT 'Adresse téléphonique',
  `F006` 		VARCHAR(20) 	NULL COMMENT 'Adresse fax',
  `invalid` 	INT 			NOT NULL DEFAULT 0,
  `recycle` 	INT 			NOT NULL DEFAULT 0,
  PRIMARY KEY (`id_contactEmetteur`),
  INDEX `fk_contactEmetteur_envoi_idx` (`id_envoi` ASC),
  CONSTRAINT `fk_contactEmetteur_envoi`
    FOREIGN KEY (`id_envoi`)
    REFERENCES `T00` (`id_envoi`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COMMENT = 'Contact Emetteur';


-- -----------------------------------------------------
-- Table `T03` Destinataire CRE
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `T03` (
  `id_destinataireCRE` 	INT 			NOT NULL,
  `F001` 				VARCHAR(9) 	NULL COMMENT 'Siren de l entreprise destinataire du compte rendu d exploitation',
  `F002` 				VARCHAR(5) 	NULL COMMENT 'Nic de l établissement destinataire du Compte Rendu d Exploitation',
  `F003` 				VARCHAR(100) 	NULL COMMENT 'Adresse mél du destinataire du Compte Rendu d Exploitation',
  `invalid` 			INT 			NOT NULL DEFAULT 0,
  `recycle` 			INT 			NOT NULL DEFAULT 0,
  PRIMARY KEY (`id_destinataireCRE`),
  CONSTRAINT `fk_destinataireCRE_envoi`
    FOREIGN KEY (`id_destinataireCRE`)
    REFERENCES `T00` (`id_envoi`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COMMENT = 'Destinataire CRE';


-- -----------------------------------------------------
-- Table `T05` DSN
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `T05` (
  `id_DSN` 		INT 			NOT NULL,
  `F001` 		VARCHAR(2) 	NULL COMMENT 'Nature de la déclaration',
  `F002` 		VARCHAR(2) 	NULL COMMENT 'Type de la déclaration',
  `F003` 		VARCHAR(2) 	NULL COMMENT 'Numéro de fraction de déclaration',
  `F004` 		VARCHAR(15) 	NULL COMMENT 'Numéro d ordre de la déclaration',
  `F005` 		VARCHAR(8) 	NULL COMMENT 'Date du mois principal déclaré',
  `F006` 		VARCHAR(23) 	NULL COMMENT 'Identifiant de la déclaration annulée ou remplacée',
  `F007` 		VARCHAR(8) 	NULL COMMENT 'Date de constitution du fichier',
  `F008` 		VARCHAR(2) 	NULL COMMENT 'Champ de la déclaration',
  `F009` 		VARCHAR(15) 	NULL COMMENT 'Identifiant de l évènement',
  `invalid` 	INT 			NOT NULL DEFAULT 0,
  `recycle` 	INT 			NOT NULL DEFAULT 0,
  PRIMARY KEY (`id_DSN`),
  CONSTRAINT `fk_DSN_envoi`
    FOREIGN KEY (`id_DSN`)
    REFERENCES `T00` (`id_envoi`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COMMENT = 'DSN';


-- -----------------------------------------------------
-- Table `T06` Entreprise
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `T06` (
  `id_entreprise` 	INT 			NOT NULL,
  `F001` 			VARCHAR(9) 	NULL COMMENT 'SIREN',
  `F002` 			VARCHAR(5) 	NULL COMMENT 'NIC du siège',
  `F003` 			VARCHAR(5) 	NULL COMMENT 'Code APEN',
  `F004` 			VARCHAR(50) 	NULL COMMENT 'Numéro, extension, nature et libellé de la voie',
  `F005` 			VARCHAR(5) 	NULL COMMENT 'Code postal',
  `F006` 			VARCHAR(50) 	NULL COMMENT 'Localité',
  `F007` 			VARCHAR(50) 	NULL COMMENT 'Complément de la localisation de la construction',
  `F008` 			VARCHAR(50) 	NULL COMMENT 'Service de distribution, complément de localisation de la voie',
--  `F009` 			VARCHAR(7) 	NULL COMMENT 'Effectif moyen de l entreprise au 31 décembre',
  `F010` 			VARCHAR(2) 	NULL COMMENT 'Code pays',
  `F011` 			VARCHAR(50) 	NULL COMMENT 'Code de distribution à l étranger',
  `invalid` 		INT 			NOT NULL DEFAULT 0,
  `recycle` 		INT 			NOT NULL DEFAULT 0,
  PRIMARY KEY (`id_entreprise`),
  CONSTRAINT `fk_entreprise_envoi`
    FOREIGN KEY (`id_entreprise`)
    REFERENCES `T00` (`id_envoi`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COMMENT = 'Entreprise';


-- -----------------------------------------------------
-- Table `T11` Etablissement
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `T11` (
  `id_etablissement` 	INT 			NOT NULL,
  `F001` 				VARCHAR(5) 	NULL COMMENT 'NIC',
  `F002` 				VARCHAR(5) 	NULL COMMENT 'Code APET',
  `F003` 				VARCHAR(50) 	NULL COMMENT 'Numéro, extension, nature et libellé de la voie',
  `F004` 				VARCHAR(5) 	NULL COMMENT 'Code postal',
  `F005` 				VARCHAR(50) 	NULL COMMENT 'Localité',
  `F006` 				VARCHAR(50) 	NULL COMMENT 'Complément de la localisation de la construction',
  `F007` 				VARCHAR(50) 	NULL COMMENT 'Service de distribution, complément de localisation de la voie',
--  `F008` 				VARCHAR(6) 	NULL COMMENT 'Effectif de fin de période déclarée de l établissement d affectation',
--  `F009` 				VARCHAR(2) 	NULL COMMENT 'Type de rémunération soumise à contributions Assurance chômage pour expatriés',
--  `F012` 				VARCHAR(4) 	NULL COMMENT 'Code catégorie juridique',
  `F015` 				VARCHAR(2) 	NULL COMMENT 'Code pays',
  `F016` 				VARCHAR(4) 	NULL COMMENT 'Code de distribution à l étranger',
--  `F017` 				VARCHAR(30) 	NULL COMMENT 'Nature juridique de l employeur',
  `invalid` 			INT 			NOT NULL DEFAULT 0,
  `recycle` 			INT 			NOT NULL DEFAULT 0,
  PRIMARY KEY (`id_etablissement`),
  CONSTRAINT `fk_etablissement_envoi`
    FOREIGN KEY (`id_etablissement`)
    REFERENCES `T00` (`id_envoi`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COMMENT = 'Etablissement';


-- -----------------------------------------------------
-- Table `T15` Adhesion Prevoyance sans personnel couvert
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `T15` (
  `id_adhesionPrev` 	INT 			NOT NULL,
  `id_etablissement` 	INT 			NOT NULL,
  `F001` 				VARCHAR(30) 	NULL COMMENT 'Référence du contrat de Prévoyance',
  `F002` 				VARCHAR(9) 	NULL COMMENT 'Code organisme de Prévoyance',
  `F003` 				VARCHAR(6) 	NULL COMMENT 'Code délégataire de gestion',
  `invalid` 			INT 			NOT NULL DEFAULT 0,
  `recycle` 			INT 			NOT NULL DEFAULT 0,
  PRIMARY KEY (`id_adhesionPrev`),
  INDEX `fk_adhesionPrev_etablissement_idx` (`id_etablissement` ASC),
  CONSTRAINT `fk_adhesionPrev_envoi`
    FOREIGN KEY (`id_etablissement`)
    REFERENCES `T00` (`id_envoi`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COMMENT = 'Adhesion Prevoyance sans personnel couvert';


-- -- -----------------------------------------------------
-- -- Table `T20` Versement organisme de protection sociale
-- -- -----------------------------------------------------
-- CREATE TABLE IF NOT EXISTS `T20` (
--   `id_versementOPS` 	INT 			NOT NULL,
--   `id_etablissement` 	INT 			NOT NULL,
--   `F001` 				VARCHAR(14) 	NULL COMMENT 'Identifiant Organisme de Protection Sociale',
--   `F002` 				VARCHAR(14) 	NULL COMMENT 'Entité d affectation des opérations',
--   `F003` 				VARCHAR(11) 	NULL COMMENT 'BIC',
--   `F004` 				VARCHAR(34) 	NULL COMMENT 'IBAN',
--   `F005` 				VARCHAR(18) 	NULL COMMENT 'Montant du versement',
--   `F006` 				VARCHAR(8) 	NULL COMMENT 'Date de début de période de rattachement',
--   `F007` 				VARCHAR(8) 	NULL COMMENT 'Date de fin de période de rattachement',
--   `invalid` 			INT 			NOT NULL DEFAULT 0,
--   `recycle` 			INT 			NOT NULL DEFAULT 0,
--   PRIMARY KEY (`id_versementOPS`),
--   INDEX `fk_versementOPS_etablissement_idx` (`id_etablissement` ASC),
--   CONSTRAINT `fk_versementOPS_envoi`
--     FOREIGN KEY (`id_etablissement`)
--     REFERENCES `T00` (`id_envoi`)
--     ON DELETE NO ACTION
--     ON UPDATE NO ACTION)
-- ENGINE = InnoDB
-- DEFAULT CHARACTER SET = latin1
-- COMMENT = 'Versement organisme de protection sociale';
-- 
-- 
-- -- -----------------------------------------------------
-- -- Table `T22` Bordereau cotisation due
-- -- -----------------------------------------------------
-- CREATE TABLE IF NOT EXISTS `T22` (
--   `id_cotDue` 			INT 			NOT NULL,
--   `id_etablissement` 	INT 			NOT NULL,
--   `F001` 				VARCHAR(14) 	NULL COMMENT 'Identifiant Organisme de Protection Sociale',
--   `F002` 				VARCHAR(14) 	NULL COMMENT 'Entité d affectation des opérations',
--   `F003` 				VARCHAR(8) 	NULL COMMENT 'Date de début de période de rattachement',
--   `F004` 				VARCHAR(8) 	NULL COMMENT 'Date de fin de période de rattachement',
--   `F005` 				VARCHAR(18) 	NULL COMMENT 'Montant total de cotisations',
--   `invalid` 			INT 			NOT NULL DEFAULT 0,
--   `recycle` 			INT 			NOT NULL DEFAULT 0,
--   PRIMARY KEY (`id_cotDue`),
--   INDEX `fk_coteDue_etablissement_idx` (`id_etablissement` ASC),
--   CONSTRAINT `fk_coteDue_envoi`
--     FOREIGN KEY (`id_etablissement`)
--     REFERENCES `T00` (`id_envoi`)
--     ON DELETE NO ACTION
--     ON UPDATE NO ACTION)
-- ENGINE = InnoDB
-- DEFAULT CHARACTER SET = latin1
-- COMMENT = 'Bordereau cotisation due';
-- 
-- 
-- -- -----------------------------------------------------
-- -- Table `T23` Cotisation agrégée
-- -- -----------------------------------------------------
-- CREATE TABLE IF NOT EXISTS `T23` (
--   `id_cotAgregee` 	INT 			NOT NULL,
--   `id_cotDue` 		INT 			NOT NULL,
--   `F001` 			VARCHAR(3) 	NULL COMMENT 'Code de cotisation',
--   `F002` 			VARCHAR(3) 	NULL COMMENT 'Qualifiant d assiette',
--   `F003` 			VARCHAR(6) 	NULL COMMENT 'Taux de cotisation',
--   `F004` 			VARCHAR(18) 	NULL COMMENT 'Montant d assiette',
--   `F005` 			VARCHAR(18) 	NULL COMMENT 'Montant de cotisation',
--   `F006` 			VARCHAR(5) 	NULL COMMENT 'Code INSEE commune',
--   `invalid` 		INT 			NOT NULL DEFAULT 0,
--   `recycle` 		INT 			NOT NULL DEFAULT 0,
--   PRIMARY KEY (`id_cotAgregee`),
--   INDEX `fk_cotAgregee_coteDue_idx` (`id_cotDue` ASC),
--   CONSTRAINT `fk_cotAgregee_coteDue`
--     FOREIGN KEY (`id_cotDue`)
--     REFERENCES `T22` (`id_cotDue`)
--     ON DELETE NO ACTION
--     ON UPDATE NO ACTION)
-- ENGINE = InnoDB
-- DEFAULT CHARACTER SET = latin1
-- COMMENT = 'Cotisation agrégée';


-- -----------------------------------------------------
-- Table `T30` Individu
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `T30` (
  `id_individu` 		INT 			NOT NULL,
  `id_etablissement` 	INT 			NOT NULL,
  `F001` 				VARCHAR(13) 	NULL COMMENT 'Numéro d inscription au répertoire',
  `F002` 				VARCHAR(80) 	NULL COMMENT 'Nom de famille',
  `F003` 				VARCHAR(80) 	NULL COMMENT 'Nom d usage',
  `F004` 				VARCHAR(80) 	NULL COMMENT 'Prénoms',
  `F005` 				VARCHAR(2) 	NULL COMMENT 'Sexe',
  `F006` 				VARCHAR(8) 	NULL COMMENT 'Date de naissance',
  `F007` 				VARCHAR(30) 	NULL COMMENT 'Lieu de naissance',
  `F008` 				VARCHAR(50) 	NULL COMMENT 'Numéro, extension, nature et libellé de la voie',
  `F009` 				VARCHAR(5) 	NULL COMMENT 'Code postal',
  `F010` 				VARCHAR(50) 	NULL COMMENT 'Localité',
  `F011` 				VARCHAR(2) 	NULL COMMENT 'Code pays',
  `F012` 				VARCHAR(50) 	NULL COMMENT 'Code de distribution à l étranger',
--  `F013` 				VARCHAR(2) 	NULL COMMENT 'Codification UE',
  `F014` 				VARCHAR(2) 	NULL COMMENT 'Code département de naissance',
  `F015` 				VARCHAR(2) 	NULL COMMENT 'Code pays de naissance',
  `F016` 				VARCHAR(50) 	NULL COMMENT 'Complément de la localisation de la construction',
  `F017` 				VARCHAR(50) 	NULL COMMENT 'Service de distribution, complément de localisation de la voie',
  `F018` 				VARCHAR(100) 	NULL COMMENT 'Adresse mél',
  `F019` 				VARCHAR(30) 	NULL COMMENT 'Matricule de l individu dans l entreprise',
  `F020` 				VARCHAR(40) 	NULL COMMENT 'Numéro technique temporaire',
  `invalid` 			INT 			NOT NULL DEFAULT 0,
  `recycle` 			INT 			NOT NULL DEFAULT 0,
  PRIMARY KEY (`id_individu`),
  INDEX `fk_individu_etablissement_idx` (`id_etablissement` ASC),
  CONSTRAINT `fk_individu_envoi`
    FOREIGN KEY (`id_etablissement`)
    REFERENCES `T00` (`id_envoi`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COMMENT = 'Individu';


-- -----------------------------------------------------
-- Table `T31` Changement individu
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `T31` (
  `id_changementIndividu` 	INT 			NOT NULL,
  `id_individu` 			INT 			NOT NULL,
  `F001` 					VARCHAR(8) 	NULL COMMENT 'Date de la modification',
  `F008` 					VARCHAR(13) 	NULL COMMENT 'Ancien NIR',
  `F009` 					VARCHAR(80) 	NULL COMMENT 'Ancien Nom de famille',
  `F010` 					VARCHAR(80) 	NULL COMMENT 'Anciens Prénoms',
  `F011` 					VARCHAR(8) 	NULL COMMENT 'Ancienne Date de naissance',
  `ordre` 					INT 			NOT NULL COMMENT 'Numéro d ordre de survenance du bloc changement dans le bloc Individu (30)',
  `invalid` 				INT 			NOT NULL DEFAULT 0,
  `recycle` 				INT 			NOT NULL DEFAULT 0,
  PRIMARY KEY (`id_changementIndividu`),
  INDEX `fk_changementIndividu_individu_idx` (`id_individu` ASC),
  CONSTRAINT `fk_changementIndividu_individu`
    FOREIGN KEY (`id_individu`)
    REFERENCES `T30` (`id_individu`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COMMENT = 'Changement Individu';


-- -----------------------------------------------------
-- Table `T40` Contrat
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `T40` (
  `id_contrat` 		INT 			NOT NULL,
  `id_individu` 	INT 			NOT NULL,
  `F001` 			VARCHAR(8) 	NULL COMMENT 'Date de début du contrat',
  `F002` 			VARCHAR(2) 	NULL COMMENT 'Statut du salarié (conventionnel)',
  `F003` 			VARCHAR(2) 	NULL COMMENT 'Code statut catégoriel Retraite Complémentaire obligatoire',
  `F004` 			VARCHAR(4) 	NULL COMMENT 'Code profession et catégorie socioprofessionnelle (PCS-ESE)',
  `F005` 			VARCHAR(6) 	NULL COMMENT 'Code complément PCS-ESE',
  `F006` 			VARCHAR(120) 	NULL COMMENT 'Libellé de l emploi',
  `F007` 			VARCHAR(2) 	NULL COMMENT 'Nature du contrat',
  `F008` 			VARCHAR(2) 	NULL COMMENT 'Dispositif de politique publique',
  `F009` 			VARCHAR(20) 	NULL COMMENT 'Numéro du contrat',
  `F010` 			VARCHAR(8) 	NULL COMMENT 'Date de fin prévisionnelle du contrat',
  `F011` 			VARCHAR(2) 	NULL COMMENT 'Unité de mesure de la quotité de travail',
  `F012` 			VARCHAR(7) 	NULL COMMENT 'Quotité de travail de référence de l entreprise pour la catégorie de salarié',
  `F013` 			VARCHAR(7) 	NULL COMMENT 'Quotité de travail du contrat',
  `F014` 			VARCHAR(2) 	NULL COMMENT 'Modalité d exercice du temps de travail',
--  `F015` 			VARCHAR(12) 	NULL COMMENT 'Salaire de référence porté par le contrat',
  `F016` 			VARCHAR(2) 	NULL COMMENT 'Régime local Alsace Moselle',
  `F017` 			VARCHAR(4) 	NULL COMMENT 'Code convention collective applicable',
  `F018` 			VARCHAR(3) 	NULL COMMENT 'Code régime de base risque maladie',
  `F019` 			VARCHAR(14) 	NULL COMMENT 'Identifiant du lieu de travail',
--  `F020` 			VARCHAR(3) 	NULL COMMENT 'Code régime de base risque vieillesse',
--  `F021` 			VARCHAR(2) 	NULL COMMENT 'Motif de recours',
--  `F022` 			VARCHAR(2) 	NULL COMMENT 'Code caisse professionnelle de congés payés',
--  `F023` 			VARCHAR(6) 	NULL COMMENT 'Taux de déduction forfaitaire spécifique pour frais professionnels',
--  `F024` 			VARCHAR(2) 	NULL COMMENT 'Statut à l étranger',
--  `F025` 			VARCHAR(2) 	NULL COMMENT 'Motif d exclusion DSN',
--  `F026` 			VARCHAR(2) 	NULL COMMENT 'Statut d emploi du salarié',
--  `F027` 			VARCHAR(6) 	NULL COMMENT 'Code affectation Assurance chômage',
--  `F028` 			VARCHAR(12) 	NULL COMMENT 'Numéro interne employeur public',
--  `F029` 			VARCHAR(2) 	NULL COMMENT 'Type de gestion de l Assurance chômage',
--  `F030` 			VARCHAR(8) 	NULL COMMENT 'Date d adhésion',
--  `F031` 			VARCHAR(8) 	NULL COMMENT 'Date de dénonciation',
--  `F032` 			VARCHAR(8) 	NULL COMMENT 'Date d effet de la convention de gestion',
--  `F033` 			VARCHAR(10) 	NULL COMMENT 'Numéro de convention de gestion',
  `invalid` 		INT 			NOT NULL DEFAULT 0,
  `recycle` 		INT 			NOT NULL DEFAULT 0,
  PRIMARY KEY (`id_contrat`),
  INDEX `fk_individu_contrat_idx` (`id_individu` ASC),
  CONSTRAINT `fk_individu_contrat`
    FOREIGN KEY (`id_individu`)
    REFERENCES `T30` (`id_individu`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COMMENT = 'Contrat';


-- -----------------------------------------------------
-- Table `T41` Changement Contrat
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `T41` (
  `id_changtContrat` 	INT 			NOT NULL,
  `id_contrat` 			INT 			NOT NULL,
  `F001` 				VARCHAR(8) 	NULL 		COMMENT 'Date de la modification',
  `F002` 				VARCHAR(2) 	NULL 		COMMENT 'Ancien statut du salarié (conventionnel)',
  `F003` 				VARCHAR(2) 	NULL 		COMMENT 'Ancien Code statut catégoriel Retraite Complémentaire obligatoire',
  `F004` 				VARCHAR(2) 	NULL 		COMMENT 'Ancienne Nature du contrat',
  `F005` 				VARCHAR(2) 	NULL 		COMMENT 'Ancien Dispositif de politique publique',
  `F006` 				VARCHAR(2) 	NULL 		COMMENT 'Ancienne Unité de mesure de la quotité de travail',
  `F007` 				VARCHAR(7) 	NULL 		COMMENT 'Ancienne Quotité de travail du contrat',
  `F008` 				VARCHAR(2) 	NULL 		COMMENT 'Ancienne Modalité d exercice du temps de travail',
--  `F009` 				VARCHAR(12) 	NULL 		COMMENT 'Ancien Salaire de référence porté par le contrat',
  `F010` 				VARCHAR(2) 	NULL 		COMMENT 'Ancien Régime local Alsace Moselle',
  `F011` 				VARCHAR(4) 	NULL 		COMMENT 'Ancien Code convention collective applicable',
  `F012` 				VARCHAR(14) 	NULL 		COMMENT 'SIRET ancien établissement d affectation',
  `F013` 				VARCHAR(14) 	NULL 		COMMENT 'Ancien identifiant du lieu de travail',
  `F014` 				VARCHAR(20) 	NULL 		COMMENT 'Ancien Numéro du contrat',
--   `F016` 				VARCHAR(2) 	NULL 		COMMENT 'Ancien motif de recours',
--   `F017` 				VARCHAR(6) 	NULL 		COMMENT 'Ancien taux de déduction forfaitaire spécifique pour frais professionnels',
--   `F018` 				VARCHAR(2) 	NULL 		COMMENT 'Ancien statut à l étranger',
  `F019` 				VARCHAR(4) 	NULL 		COMMENT 'Ancien Code profession et catégorie socioprofessionnelle (PCS‐ESE)',
  `F020` 				VARCHAR(6) 	NULL 		COMMENT 'Ancien Code complément PCS‐ESE',
  `F021` 				VARCHAR(8) 	NULL 		COMMENT 'Ancienne Date de début du contrat',
  `F022` 				VARCHAR(7) 	NULL 		COMMENT 'Ancienne Quotité de travail de référence de l entreprise ',
--  `F023` 				VARCHAR(2) 	NULL 		COMMENT 'Ancienne Code caisse professionnelle de congés payés ',
  `ordre` 				INT 			NOT NULL 	COMMENT 'Numéro d ordre de survenance du bloc changement dans le bloc Contrat (40)',
  `invalid` 			INT 			NOT NULL DEFAULT 0,
  `recycle` 			INT 			NOT NULL DEFAULT 0,
  PRIMARY KEY (`id_changtContrat`),
  INDEX `fk_changtContrat_contrat_idx` (`id_contrat` ASC),
  CONSTRAINT `fk_changtContrat_contrat`
    FOREIGN KEY (`id_contrat`)
    REFERENCES `T40` (`id_contrat`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COMMENT = 'Changement Contrat';


-- -- -----------------------------------------------------
-- -- Table `T50` Versement Individu
-- -- -----------------------------------------------------
-- CREATE TABLE IF NOT EXISTS `T50` (
--   `id_verstIndividu` 	INT 			NOT NULL,
--   `id_individu` 		INT 			NOT NULL,
--   `F001` 				VARCHAR(8) 	NULL COMMENT 'Date de versement',
--   `F003` 				VARCHAR(2) 	NULL COMMENT 'Numéro de versement',
--   `F004` 				VARCHAR(12) 	NULL COMMENT 'Montant net versé',
--   `F002` 				VARCHAR(12) 	NULL COMMENT 'Rémunération nette fiscale',
--   `invalid` 			INT 			NOT NULL DEFAULT 0,
--   `recycle` 			INT 			NOT NULL DEFAULT 0,
--   PRIMARY KEY (`id_verstIndividu`),
--   INDEX `fk_verstIndividu_individu_idx` (`id_individu` ASC),
--   CONSTRAINT `fk_verstIndividu_individu`
--     FOREIGN KEY (`id_individu`)
--     REFERENCES `T30` (`id_individu`)
--     ON DELETE NO ACTION
--     ON UPDATE NO ACTION)
-- ENGINE = InnoDB
-- DEFAULT CHARACTER SET = latin1
-- COMMENT = 'Versement Individu';
-- 
-- 
-- -- -----------------------------------------------------
-- -- Table `T51` Remuneration
-- -- -----------------------------------------------------
-- CREATE TABLE IF NOT EXISTS `T51` (
--   `id_remuneration` 	INT 			NOT NULL,
--   `id_verstIndividu` 	INT 			NOT NULL,
--   `F001` 				VARCHAR(8) 	NULL COMMENT 'Date de début de période de paie',
--   `F002` 				VARCHAR(8) 	NULL COMMENT 'Date de fin de période de paie',
--   `F009` 				VARCHAR(8) 	NULL COMMENT 'Date de début de contrat',
--   `F010` 				VARCHAR(20) 	NULL COMMENT 'Numéro du contrat',
--   `F011` 				VARCHAR(3) 	NULL COMMENT 'Type',
--   `F012` 				VARCHAR(6) 	NULL COMMENT 'Nombre d heure supplémentaire, d équivalence, d habillage et de déshabillage',
--   `F013` 				VARCHAR(18) 	NULL COMMENT 'Montant',
--   `invalid` 			INT 			NOT NULL DEFAULT 0,
--   `recycle` 			INT 			NOT NULL DEFAULT 0,
--   PRIMARY KEY (`id_remuneration`),
--   INDEX `fk_verstIndividu_remuneration_idx` (`id_verstIndividu` ASC),
--   CONSTRAINT `fk_verstIndividu_remuneration`
--     FOREIGN KEY (`id_verstIndividu`)
--     REFERENCES `T50` (`id_verstIndividu`)
--     ON DELETE NO ACTION
--     ON UPDATE NO ACTION)
-- ENGINE = InnoDB
-- DEFAULT CHARACTER SET = latin1
-- COMMENT = 'Remuneration';
-- 
-- 
-- -- -----------------------------------------------------
-- -- Table `T52` Prime, gratification et indemnite
-- -- -----------------------------------------------------
-- CREATE TABLE IF NOT EXISTS `T52` (
--   `id_prime` 			INT 			NOT NULL,
--   `id_verstIndividu` 	INT 			NOT NULL,
--   `F001` 				VARCHAR(3) 	NULL COMMENT 'Type',
--   `F002` 				VARCHAR(12) 	NULL COMMENT 'Montant',
--   `F003` 				VARCHAR(8) 	NULL COMMENT 'Date de début de période de rattachement',
--   `F004` 				VARCHAR(8) 	NULL COMMENT 'Date de fin de période de rattachement',
--   `F005` 				VARCHAR(8) 	NULL COMMENT 'Date de début de contrat',
--   `F006` 				VARCHAR(20) 	NULL COMMENT 'Numéro de contrat',
--   `invalid` 			INT 			NOT NULL DEFAULT 0,
--   `recycle` 			INT 			NOT NULL DEFAULT 0,
--   PRIMARY KEY (`id_prime`),
--   INDEX `fk_prime_verstIndividu_idx` (`id_verstIndividu` ASC),
--   CONSTRAINT `fk_prime_verstIndividu`
--     FOREIGN KEY (`id_verstIndividu`)
--     REFERENCES `T50` (`id_verstIndividu`)
--     ON DELETE NO ACTION
--     ON UPDATE NO ACTION)
-- ENGINE = InnoDB
-- DEFAULT CHARACTER SET = latin1
-- COMMENT = 'Prime, gratification et indemnite';
-- 
-- 
-- -- -----------------------------------------------------
-- -- Table `T53` Activite
-- -- -----------------------------------------------------
-- CREATE TABLE IF NOT EXISTS `T53` (
--   `id_activite` 		INT 			NOT NULL,
--   `id_remuneration` 	INT 			NOT NULL,
--   `F001` 				VARCHAR(2) 	NULL COMMENT 'Type',
--   `F002` 				VARCHAR(7) 	NULL COMMENT 'Mesure',
--   `F003` 				VARCHAR(2) 	NULL COMMENT 'Unité de mesure',
--   `invalid` 			INT 			NOT NULL DEFAULT 0,
--   `recycle` 			INT 			NOT NULL DEFAULT 0,
--   PRIMARY KEY (`id_activite`),
--   INDEX `fk_activite_remuneration_idx` (`id_remuneration` ASC),
--   CONSTRAINT `fk_activite_remuneration`
--     FOREIGN KEY (`id_remuneration`)
--     REFERENCES `T51` (`id_remuneration`)
--     ON DELETE NO ACTION
--     ON UPDATE NO ACTION)
-- ENGINE = InnoDB
-- DEFAULT CHARACTER SET = latin1
-- COMMENT = 'Activite';
-- 
-- 
-- -- -----------------------------------------------------
-- -- Table `T54` Autre element de revenu brut
-- -- -----------------------------------------------------
-- CREATE TABLE IF NOT EXISTS `T54` (
--   `id_autreRevenu` 		INT 			NOT NULL,
--   `id_verstIndividu` 	INT 			NOT NULL,
--   `F001` 				VARCHAR(2) 	NULL COMMENT 'Type',
--   `F002` 				VARCHAR(18) 	NULL COMMENT 'Montant',
--   `F003` 				VARCHAR(8) 	NULL COMMENT 'Date de début de période de rattachement',
--   `F004` 				VARCHAR(8) 	NULL COMMENT 'Date de fin de période de rattachement',
--   `invalid` 			INT 			NOT NULL DEFAULT 0,
--   `recycle` 			INT 			NOT NULL DEFAULT 0,
--   PRIMARY KEY (`id_autreRevenu`),
--   INDEX `fk_autreRevenu_verstIndividu_idx` (`id_verstIndividu` ASC),
--   CONSTRAINT `fk_autreRevenu_verstIndividu`
--     FOREIGN KEY (`id_verstIndividu`)
--     REFERENCES `T50` (`id_verstIndividu`)
--     ON DELETE NO ACTION
--     ON UPDATE NO ACTION)
-- ENGINE = InnoDB
-- DEFAULT CHARACTER SET = latin1
-- COMMENT = 'Autre element de revenu brut';
-- 
-- 
-- -- -----------------------------------------------------
-- -- Table `T60` Arret de travail
-- -- -----------------------------------------------------
-- CREATE TABLE IF NOT EXISTS `T60` (
--   `id_arretTravail` 	INT 			NOT NULL,
--   `id_contrat` 			INT 			NOT NULL,
--   `F001` 				VARCHAR(2) 	NULL COMMENT 'Motif de l arrêt',
--   `F002` 				VARCHAR(8) 	NULL COMMENT 'Date du dernier jour travaillé',
--   `F003` 				VARCHAR(8) 	NULL COMMENT 'Date de fin prévisionnelle',
--   `F004` 				VARCHAR(2) 	NULL COMMENT 'Subrogation',
--   `F005` 				VARCHAR(8) 	NULL COMMENT 'Date de début de subrogation',
--   `F006` 				VARCHAR(8) 	NULL COMMENT 'Date de fin de subrogation',
--   `F007` 				VARCHAR(34) 	NULL COMMENT 'IBAN',
--   `F008` 				VARCHAR(11) 	NULL COMMENT 'BIC',
--   `F010` 				VARCHAR(8) 	NULL COMMENT 'Date de la reprise',
--   `F011` 				VARCHAR(2) 	NULL COMMENT 'Motif de la reprise',
--   `F012` 				VARCHAR(8) 	NULL COMMENT 'Date de l accident ou de la première constatation',
--   `invalid` 			INT 			NOT NULL DEFAULT 0,
--   `recycle` 			INT 			NOT NULL DEFAULT 0,
--   PRIMARY KEY (`id_arretTravail`),
--   INDEX `fk_arretTravail_contrat_idx` (`id_contrat` ASC),
--   CONSTRAINT `fk_arretTravail_contrat`
--     FOREIGN KEY (`id_contrat`)
--     REFERENCES `T40` (`id_contrat`)
--     ON DELETE NO ACTION
--     ON UPDATE NO ACTION)
-- ENGINE = InnoDB
-- DEFAULT CHARACTER SET = latin1
-- COMMENT = 'Arret de travail';


-- -----------------------------------------------------
-- Table `T62` Fin du contrat
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `T62` (
  `id_finContrat` 	INT 			NOT NULL,
  `F001` 			VARCHAR(8) 	NULL COMMENT 'Date de fin du contrat',
  `F002` 			VARCHAR(3) 	NULL COMMENT 'Motif de la rupture du contrat',
  `F003` 			VARCHAR(8) 	NULL COMMENT 'Date de notification de la rupture de contrat',
  `F004` 			VARCHAR(8) 	NULL COMMENT 'Date de signature de la convention de rupture',
  `F005` 			VARCHAR(8) 	NULL COMMENT 'Date d engagement de la procédure de licenciement',
  `F006` 			VARCHAR(8) 	NULL COMMENT 'Dernier jour travaillé et payé au salaire habituel',
  `F008` 			VARCHAR(2) 	NULL COMMENT 'Transaction en cours',
  `F009` 			VARCHAR(2) 	NULL COMMENT 'Portabilité contrat de Prévoyance',
  `F010` 			VARCHAR(6) 	NULL COMMENT 'Nombre d heures de DIF n ayant pas été utilisées',
  `F011` 			VARCHAR(4) 	NULL COMMENT 'Nombre de mois de préavis utilisés dans le cadre du calcul CSP',
  `F012` 			VARCHAR(12) 	NULL COMMENT 'Salaire net horaire du salarié',
  `F013` 			VARCHAR(12) 	NULL COMMENT 'Montant de l indemnité de préavis qui aurait été versée',
  `F014` 			VARCHAR(2) 	NULL COMMENT 'Statut particulier du salarié',
  `invalid` 		INT 			NOT NULL DEFAULT 0,
  `recycle` 		INT 			NOT NULL DEFAULT 0,
  PRIMARY KEY (`id_finContrat`),
  CONSTRAINT `fk_finContrat_contrat`
    FOREIGN KEY (`id_finContrat`)
    REFERENCES `T40` (`id_contrat`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COMMENT = 'Fin du contrat';


-- -- -----------------------------------------------------
-- -- Table `T63` Preavis Fin Contrat
-- -- -----------------------------------------------------
-- CREATE TABLE IF NOT EXISTS `T63` (
--   `id_preavis` 		INT 			NOT NULL,
--   `id_finContrat` 	INT 			NOT NULL,
--   `F001` 			VARCHAR(2) 	NULL COMMENT 'Type réalisation et paiement du préavis',
--   `F002` 			VARCHAR(8) 	NULL COMMENT 'Date de début de préavis',
--   `F003` 			VARCHAR(8) 	NULL COMMENT 'Date de fin de préavis',
--   `invalid` 		INT 			NOT NULL DEFAULT 0,
--   `recycle` 		INT 			NOT NULL DEFAULT 0,
--   PRIMARY KEY (`id_preavis`),
--   INDEX `fk_preavis_finContrat_idx` (`id_finContrat` ASC),
--   CONSTRAINT `fk_preavis_contrat`
--     FOREIGN KEY (`id_finContrat`)
--     REFERENCES `T40` (`id_contrat`)
--     ON DELETE NO ACTION
--     ON UPDATE NO ACTION)
-- ENGINE = InnoDB
-- DEFAULT CHARACTER SET = latin1
-- COMMENT = 'Preavis fin de contrat';
-- 
-- 
-- -----------------------------------------------------
-- Table `T65` Autre suspension de l exucution du contrat
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `T65` (
  `id_suspensionCtrt` 	INT 			NOT NULL,
  `id_contrat` 			INT 			NOT NULL,
  `F001` 				VARCHAR(3) 	NULL COMMENT 'Motif de suspension',
  `F002` 				VARCHAR(8) 	NULL COMMENT 'Date de début de la suspension',
  `F003` 				VARCHAR(8) 	NULL COMMENT 'Date de fin de la suspension',
  `invalid` 			INT 			NOT NULL DEFAULT 0,
  `recycle` 			INT 			NOT NULL DEFAULT 0,
  PRIMARY KEY (`id_suspensionCtrt`),
  INDEX `fk_suspensionCtrt_contrat_idx` (`id_contrat` ASC),
  CONSTRAINT `fk_suspensionCtrt_contrat`
    FOREIGN KEY (`id_contrat`)
    REFERENCES `T40` (`id_contrat`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COMMENT = 'Autre suspension de l exucution du contrat';


-- -----------------------------------------------------
-- Table `T70` Affiliation Prevoyance
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `T70` (
  `id_affil` 	INT 			NOT NULL,
  `id_contrat` 	INT 			NOT NULL,
  `F001` 		VARCHAR(30) 	NULL COMMENT 'Référence du contrat de Prévoyance',
  `F002` 		VARCHAR(9) 	NULL COMMENT 'Code organisme de Prévoyance',
  `F003` 		VARCHAR(6) 	NULL COMMENT 'Code délégataire de gestion',
  `F004` 		VARCHAR(30) 	NULL COMMENT 'Code option retenue par le salarié',
  `F005` 		VARCHAR(30) 	NULL COMMENT 'Code population de rattachement',
  `F006` 		VARCHAR(8) 	NULL COMMENT 'Date d affiliation',
  `invalid` 	INT 			NOT NULL DEFAULT 0,
  `recycle` 	INT 			NOT NULL DEFAULT 0,
  PRIMARY KEY (`id_affil`),
  INDEX `fk_affil_contrat_idx` (`id_contrat` ASC),
  CONSTRAINT `fk_affil_contrat`
    FOREIGN KEY (`id_contrat`)
    REFERENCES `T40` (`id_contrat`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COMMENT = 'Affiliation Prevoyance';


-- -----------------------------------------------------
-- Table `T72` Changements Affiliation Prévoyance
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `T72` (
  `id_chgtAffilPrev` 	INT 			NOT NULL,
  `id_affil` 			INT 			NOT NULL,
  `F001` 				VARCHAR(8) 	NULL 		COMMENT 'Date de la modification',
  `F002` 				VARCHAR(9) 	NULL 		COMMENT 'Ancien Code organisme de Prévoyance',
  `F003` 				VARCHAR(6) 	NULL 		COMMENT 'Ancien Code délégataire de gestion',
  `ordre` 				INT 			NOT NULL 	COMMENT 'Numéro d ordre de survenance du bloc changement dans le bloc Affiliation (70)',
  `invalid` 			INT 			NOT NULL DEFAULT 0,
  `recycle` 			INT 			NOT NULL DEFAULT 0,
  PRIMARY KEY (`id_chgtAffilPrev`),
  INDEX `fk_chgtAffil_affiliation_idx` (`id_affil` ASC),
  CONSTRAINT `fk_chgtAffil_affiliation`
    FOREIGN KEY (`id_affil`)
    REFERENCES `T70` (`id_affil`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COMMENT = 'Changements Affiliation Prévoyance';


-- -- -----------------------------------------------------
-- -- Table `T71` Retraite complementaire
-- -- -----------------------------------------------------
-- CREATE TABLE IF NOT EXISTS `T71` (
--   `id_retraiteComplem` 	INT 			NOT NULL,
--   `id_contrat` 			INT 			NOT NULL,
--   `F002` 				VARCHAR(5) 	NULL COMMENT 'Code régime Retraite Complémentaire',
--   `invalid` 			INT 			NOT NULL DEFAULT 0,
--   `recycle` 			INT 			NOT NULL DEFAULT 0,
--   PRIMARY KEY (`id_retraiteComplem`),
--   INDEX `fk_retraiteComplem_contrat_idx` (`id_contrat` ASC),
--   CONSTRAINT `fk_retraiteComplem_contrat`
--     FOREIGN KEY (`id_contrat`)
--     REFERENCES `T40` (`id_contrat`)
--     ON DELETE NO ACTION
--     ON UPDATE NO ACTION)
-- ENGINE = InnoDB
-- DEFAULT CHARACTER SET = latin1
-- COMMENT = 'Retraite complementaire';
-- 
-- 
-- -- -----------------------------------------------------
-- -- Table `T78` Base assujettie
-- -- -----------------------------------------------------
-- CREATE TABLE IF NOT EXISTS `T78` (
--   `id_BA` 				INT 			NOT NULL,
--   `id_verstIndividu` 	INT 			NOT NULL,
--   `F001` 				VARCHAR(2) 	NULL COMMENT 'Code de base assujettie',
--   `F002` 				VARCHAR(8) 	NULL COMMENT 'Date de début de période de rattachement',
--   `F003` 				VARCHAR(8) 	NULL COMMENT 'Date de fin de période de rattachement',
--   `F004` 				VARCHAR(18) 	NULL COMMENT 'Montant de base assujettie',
--   `invalid` 			INT 			NOT NULL DEFAULT 0,
--   `recycle` 			INT 			NOT NULL DEFAULT 0,
--   PRIMARY KEY (`id_BA`),
--   INDEX `fk_baseAssujeti_verstIndividu_idx` (`id_verstIndividu` ASC),
--   CONSTRAINT `fk_baseAssujeti_verstIndividu`
--     FOREIGN KEY (`id_verstIndividu`)
--     REFERENCES `T50` (`id_verstIndividu`)
--     ON DELETE NO ACTION
--     ON UPDATE NO ACTION)
-- ENGINE = InnoDB
-- DEFAULT CHARACTER SET = latin1
-- COMMENT = 'Base assujettie';
-- 
-- 
-- -- -----------------------------------------------------
-- -- Table `T79` Composant de base assujettie
-- -- -----------------------------------------------------
-- CREATE TABLE IF NOT EXISTS `T79` (
--   `id_compoBA` 	INT 			NOT NULL,
--   `id_BA` 		INT 			NOT NULL,
--   `F001` 		VARCHAR(2) 	NULL COMMENT 'Type de composant de base assujettie',
--   `F002` 		VARCHAR(8) 	NULL COMMENT 'Date de début de période de rattachement',
--   `F003` 		VARCHAR(8) 	NULL COMMENT 'Date de fin de période de rattachement',
--   `F004` 		VARCHAR(18) 	NULL COMMENT 'Montant de composant de base assujettie',
--   `invalid` 	INT 			NOT NULL DEFAULT 0,
--   `recycle` 	INT 			NOT NULL DEFAULT 0,
--   PRIMARY KEY (`id_compoBA`),
--   INDEX `fk_compoBA_BA_idx` (`id_BA` ASC),
--   CONSTRAINT `fk_compoBA_BA`
--     FOREIGN KEY (`id_BA`)
--     REFERENCES `T78` (`id_BA`)
--     ON DELETE NO ACTION
--     ON UPDATE NO ACTION)
-- ENGINE = InnoDB
-- DEFAULT CHARACTER SET = latin1
-- COMMENT = 'Composant de base assujettie';
-- 
-- 
-- -- -----------------------------------------------------
-- -- Table `T81` Cotisation proportionnelle
-- -- -----------------------------------------------------
-- CREATE TABLE IF NOT EXISTS `T81` (
--   `id_cotProp` 	INT 			NOT NULL,
--   `id_BA` 		INT 			NOT NULL,
--   `F001` 		VARCHAR(3) 	NULL COMMENT 'Code de cotisation',
--   `F002` 		VARCHAR(14) 	NULL COMMENT 'Identifiant Organisme de Protection Sociale',
--   `F003` 		VARCHAR(18) 	NULL COMMENT 'Montant d assiette',
--   `F004` 		VARCHAR(18) 	NULL COMMENT 'Montant de cotisation',
--   `F005` 		VARCHAR(5) 	NULL COMMENT 'Code INSEE commune',
--   `invalid` 	INT 			NOT NULL DEFAULT 0,
--   `recycle` 	INT 			NOT NULL DEFAULT 0,
--   PRIMARY KEY (`id_cotProp`),
--   INDEX `fk_cotProp_BA_idx` (`id_BA` ASC),
--   CONSTRAINT `fk_cotProp_BA`
--     FOREIGN KEY (`id_BA`)
--     REFERENCES `T78` (`id_BA`)
--     ON DELETE NO ACTION
--     ON UPDATE NO ACTION)
-- ENGINE = InnoDB
-- DEFAULT CHARACTER SET = latin1
-- COMMENT = 'Cotisation proportionnelle';


-- -----------------------------------------------------
-- Table `T85` Lieu de travail
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `T85` (
  `id_lieuTravail` 	INT 			NOT NULL,
  `id_DSN` 			INT 			NOT NULL,
  `F001` 			VARCHAR(14) 	NULL COMMENT 'Code de cotisation (SIRET ou représentation libre)',
  `F002` 			VARCHAR(5) 	NULL COMMENT 'Identifiant Organisme de Protection Sociale',
  `F003` 			VARCHAR(50) 	NULL COMMENT 'Montant d assiette',
  `F004` 			VARCHAR(5) 	NULL COMMENT 'Montant de cotisation',
  `F005` 			VARCHAR(50) 	NULL COMMENT 'Code INSEE commune',
  `F006` 			VARCHAR(2) 	NULL COMMENT 'Code Pays',
  `F007` 			VARCHAR(50) 	NULL COMMENT 'Code de distribution à l etranger',
  `F008` 			VARCHAR(50) 	NULL COMMENT 'Complement de la localisation de la construction',
  `F009` 			VARCHAR(50) 	NULL COMMENT 'Service de distribution, complement de localisation de la voie',
  `F010` 			VARCHAR(2) 	NULL COMMENT 'Nature juridique',
  `invalid` 		INT 			NOT NULL DEFAULT 0,
  `recycle` 		INT 			NOT NULL DEFAULT 0,
  PRIMARY KEY (`id_lieuTravail`),
  INDEX `fk_lieuTravail_DSN_idx` (`id_DSN` ASC),
  CONSTRAINT `fk_lieuTravail_envoi`
    FOREIGN KEY (`id_DSN`)
    REFERENCES `T00` (`id_envoi`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COMMENT = 'Lieu de travail: Unité géographiquement localisée dans laquelle l individu exécute habituellement sa prestation de travail. Ce bloc présente les références d identification des lieux de travail où travaillent les individus de la présente déclaration. Créer un bloc pour chaque lieu de travail cité au moins une fois dans le contrat et qui n est pas
l établissement d affectation';


-- -----------------------------------------------------
-- Table `T90` Total de l envoi
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `T90` (
  `id_totalEnvoi` 	INT 			NOT NULL,
  `F001` 			VARCHAR(12) 	NULL COMMENT 'Nombre total de rubriques',
  `F002` 			VARCHAR(5) 	NULL COMMENT 'Nombre de DSN',
  `invalid` 		INT 			NOT NULL DEFAULT 0,
  `recycle` 		INT 			NOT NULL DEFAULT 0,
  PRIMARY KEY (`id_totalEnvoi`),
  CONSTRAINT `fk_totalEnvoi_envoi`
    FOREIGN KEY (`id_totalEnvoi`)
    REFERENCES `T00` (`id_envoi`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COMMENT = 'Total de l envoi';

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;