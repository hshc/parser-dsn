SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP SCHEMA `DSNversion`;
CREATE SCHEMA IF NOT EXISTS `DSNversion` DEFAULT CHARACTER SET latin1 ;
USE `DSNversion`;


CREATE TABLE IF NOT EXISTS `dsnsuivi` (
  ID_DSN 				int(11) 	NOT NULL AUTO_INCREMENT,
  idTechniqueFichier 	int(11) 				DEFAULT NULL COMMENT 'Identifiant technique interne au concentrateur IP du fichier de déclaration DSN',
  idFlux 				varchar(50) 			DEFAULT NULL COMMENT 'S10.G00.95.900: [0-9A-Za-z\.-]{1,50}',
  rang 					varchar(10) 			DEFAULT NULL COMMENT 'S20.G00.96.902: [0-9]{1,6}',
  typeFlux 				varchar(10) 			DEFAULT NULL COMMENT '01: flux test, 02: flux réel (cf.S10.G00.00.005)',
  natureDeclaration 	varchar(10) 			DEFAULT NULL COMMENT 'Enum (01|02) 01: DSN Mensuelle, 02: Signalement Fin du contrat de travail (cf.S20.G00.05.001)',
  origine 				varchar(10) 			DEFAULT NULL COMMENT 'point de dépôt: Enum (01|02) 01:Net-entreprises, 02:MSA (cf.S10.G00.00.007)',
  idOps 				varchar(50) 			DEFAULT NULL COMMENT 'code organisme prev: "P[0-9]{4}|A[0-9A-Z]{5}|[0-9]{9}" (cf.S21.G00.70.002)',
  versionDSN 			varchar(20) 			DEFAULT NULL COMMENT 'Numéro de version de la norme DSN utilisée (cf.S10.G00.00.006)',
  nomFichierOrigine 	varchar(255) 			DEFAULT NULL,
  nomFichierEcrit 		varchar(255) 			DEFAULT NULL,
  DATE_RECP 			timestamp 	NOT NULL 	DEFAULT CURRENT_TIMESTAMP,
  DATE_MAJ 				timestamp 	NOT NULL 	DEFAULT '2000-01-01 00:00:00',
  FILE 					varchar(255) 			DEFAULT NULL,
  FTP 					char(2) 				DEFAULT NULL,
  cRetour 				char(5) 				DEFAULT NULL,
  mesRetour 			char(250) 				DEFAULT NULL,
  isFileDepose 			SMALLINT 	NOT NULL 	DEFAULT '0',
  isDBupdate 			SMALLINT 	NOT NULL 	DEFAULT '0',
  delegataire 			varchar(255) 			DEFAULT NULL COMMENT 'Identifiant délégataire de gestion (S21.G00.70.003) == (D|G)[0-9A-Z]{5}',
  PRIMARY KEY (`ID_DSN`)
) 
ENGINE=InnoDB 
DEFAULT  CHARACTER SET = latin1;

-- -----------------------------------------------------
-- Table `IR_Entreprise`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `IR_Entreprise` (
	`optimLock` 				INT 			NOT NULL 		COMMENT 'Attribut technique utilisé par le lock optimiste de la couche ORM. CETTE DONNEE N EST PAS PASSEE EN REVISION',
	`idEntreprise` 				VARCHAR(8) 	NOT NULL 		COMMENT 'Identifiant technique',
	`raisonSociale` 			VARCHAR(100) 	DEFAULT NULL 	COMMENT 'Raison Sociale',
	`sigle` 					VARCHAR(32) 	DEFAULT NULL 	COMMENT 'Sigle',
	`dtCreation` 				DATE 			DEFAULT NULL 	COMMENT 'Date de création',
	`numIdDansOrganisme` 		INT 			DEFAULT NULL 	COMMENT 'Numéro d’Identification dans l’Organisme',
	`numSIREN` 					VARCHAR(9) 	DEFAULT NULL 	COMMENT 'Numéro de SIREN',
	`raisonSocialeAbregee` 		VARCHAR(38) 	DEFAULT NULL 	COMMENT 'Raison Sociale abrégée. Cette zone est obligatoirement renseignée si la raison sociale a plus de 35 caractères',
	`cdAPEN` 					VARCHAR(5) 	DEFAULT NULL 	COMMENT 'Code APEN de l’entreprise',
	`dtPrimExploitation` 		DATE 			DEFAULT NULL 	COMMENT 'Date de première exploitation',
	`dtDebSommeil` 				DATE 			DEFAULT NULL 	COMMENT 'Date de début de mise en sommeil',
	`dtFinSommeil` 				DATE 			DEFAULT NULL 	COMMENT 'Date de fin de mise en sommeil',
	`cdIndicateurCession` 		VARCHAR(1) 	DEFAULT NULL 	COMMENT 'Code indicateur de cession partielle/totale: (P = PartielleøT = Totale)',
	`dtFinCessionPartielle` 	DATE 			DEFAULT NULL 	COMMENT 'Date de fin de cession partielle',
	`dtFinContinuation` 		DATE 			DEFAULT NULL 	COMMENT 'Date de fin de plan de continuation',
	`dtDissolution` 			DATE 			DEFAULT NULL 	COMMENT 'Date de dissolution',
	`dtRadiation` 				DATE 			DEFAULT NULL 	COMMENT 'Date de radiation',
	`cdMotifRadiation` 			VARCHAR(2) 	DEFAULT NULL 	COMMENT 'Code motif de la radiation: (B = AbsorptionøCA = Cessation activitéøCE = CessionøFU = FusionøDI = DissolutionøLI = Liquidation judiciaireøSC = Scission)',
	`dtCessationActivite` 		DATE 			DEFAULT NULL 	COMMENT 'Date de cessation de l’activité',
	`dtPremiereEmbauche` 		DATE 			DEFAULT NULL 	COMMENT 'Date première embauche',
	`dtConnaissanceGroupe` 		DATE 			DEFAULT NULL 	COMMENT 'Date de connaissance par le Groupe Médéric',
	`dtDebApplicationCdAPE` 	DATE 			DEFAULT NULL 	COMMENT 'Date de début d’application du code APE',
	`dtFinApplicationCdAPE` 	DATE 			DEFAULT NULL 	COMMENT 'Date de fin d’application du code APE',
	`dtEffetRaisonSociale` 		DATE 			DEFAULT NULL 	COMMENT 'Date effet Raison sociale',
	`optionDecalPaie` 			VARCHAR(1) 	DEFAULT NULL 	COMMENT 'Option décalage de paie: (N=NonøT= Oui (donc pour tout établissement)øP=Pour certains établissements)',
	`dtDebApplicationDecalPaie` DATE 			DEFAULT NULL 	COMMENT 'Date de début d’application du décalage de paie',
	`dtFinApplicationDecalPaie` DATE 			DEFAULT NULL 	COMMENT 'Date de fin d’application du décalage de paie',
	`typeDecalageUtilise` 		VARCHAR(1) 	DEFAULT NULL 	COMMENT 'Type de décalage pratiqué: (A = Type AøB = Type B). Est renseigné si l’option décalage a la valeur T',
	`cdEntrepriseVIP` 			VARCHAR(1) 	DEFAULT NULL 	COMMENT 'Code entreprise VIP',
	PRIMARY KEY (`idEntreprise`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `IR_Contrat`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `IR_Contrat` (
	`optimLock` 				INT 			NOT NULL 		COMMENT 'Attribut technique utilisé par le lock optimiste de la couche ORM. CETTE DONNEE N EST PAS PASSEE EN REVISION',
	`numSequence` 				SMALLINT 		NOT NULL 		COMMENT 'Numero Sequence contrat',
	`NUC` 						VARCHAR(20) 	NOT NULL 		COMMENT 'Numéro unique du contrat (identifiant technique)',
	`cdProduit` 				VARCHAR(16) 	DEFAULT NULL 	COMMENT 'Code Produit',
	`libproduit` 				VARCHAR(30) 	DEFAULT NULL 	COMMENT 'Libellé produit',
	`idEntreprise` 				VARCHAR(8) 	NOT NULL 		COMMENT 'Identifiant technique de l’entreprise',
	`typeCtrt` 					VARCHAR(30) 	DEFAULT NULL 	COMMENT 'Type de contrat (Sante, Prevoyance, Retraite par capitalisation, ..)',
	`indicateurCtrtCCFI` 		VARCHAR(1) 	DEFAULT NULL 	COMMENT 'Indicateur contrat CCFI (oui/non)',
	`dtEffet` 					DATE 			DEFAULT NULL 	COMMENT 'Date d’effet du contrat',
	`dtRadiation` 				DATE 			DEFAULT NULL 	COMMENT 'Date de radiation (eventuelle)',
	`cdOrgaPorteurRisque` 		VARCHAR(5) 	DEFAULT NULL 	COMMENT 'Code organisme porteur de risque',
	`cdOrgaDelegataireCotis` 	VARCHAR(6) 	DEFAULT NULL 	COMMENT 'Code organisme délégataire de cotisation (eventuel, un seul possible)',
	PRIMARY KEY (`NUC`),
	UNIQUE KEY (`numSequence`, `idEntreprise`),
	INDEX `fk_ir_contrat_entreprise_idx` (`idEntreprise` ASC), 
	INDEX `fk_ir_contrat_numSequence_idx` (`numSequence` ASC),
	CONSTRAINT `fk_ir_contrat_entreprise` 
		FOREIGN KEY (`idEntreprise`) 
		REFERENCES `IR_Entreprise` (`idEntreprise`) 
		ON DELETE NO ACTION 
		ON UPDATE NO ACTION)
ENGINE=InnoDB
DEFAULT  CHARACTER SET = latin1;

-- -----------------------------------------------------
-- Table `IR_RisqueDelegue`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `IR_RisqueDelegue` (
	`optimLock` 				INT 			NOT NULL 	COMMENT 'Attribut technique utilisé par le lock optimiste de la couche ORM. CETTE DONNEE N EST PAS PASSEE EN REVISION',
	`riskDelegataire` 			VARCHAR(50) 	NOT NULL 	COMMENT 'Risque delegataire (IJ, DC, Incap, ...)',
	`cdOrgaDelegatairePrest` 	VARCHAR(50) 	NOT NULL 	COMMENT 'Code organisme delegataire de prestation',
	`NUC` 						VARCHAR(20) 	NOT NULL 	COMMENT 'Numéro unique du contrat',
	PRIMARY KEY (`riskDelegataire`,`cdOrgaDelegatairePrest`,`NUC`),
	INDEX `fk_ir_risque_contrat_idx` (`NUC` ASC),
	CONSTRAINT `fk_ir_risque_contrat` 
		FOREIGN KEY (`NUC`) 
		REFERENCES `IR_Contrat` (`NUC`) 
		ON DELETE NO ACTION 
		ON UPDATE NO ACTION)
ENGINE=InnoDB
DEFAULT  CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `IR_PopulationPVC`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `IR_PopulationPVC` (
	`optimLock` 			INT 			NOT NULL 		COMMENT 'Attribut technique utilisé par le lock optimiste de la couche ORM. CETTE DONNEE N EST PAS PASSEE EN REVISION',
	`cdPopulation` 			SMALLINT 		NOT NULL 		COMMENT 'Code population',
	`libPopulation` 		VARCHAR(30) 	DEFAULT NULL 	COMMENT 'Libelle court population',
	`libPopLong` 			VARCHAR(100) 	DEFAULT NULL 	COMMENT 'Libelle long population',
	`dtDebApplication` 		DATE 			DEFAULT NULL 	COMMENT 'Date de debut d’application',
	`dtFinApplication` 		DATE 			DEFAULT NULL 	COMMENT 'Date de fin d’application',
	`NUC` 					VARCHAR(20) 	NOT NULL 		COMMENT 'Numero unique du contrat',
	PRIMARY KEY (`cdPopulation`,`NUC`),
	INDEX `fk_ir_population_contrat_idx` (`NUC` ASC), 
	CONSTRAINT `fk_ir_population_contrat` 
		FOREIGN KEY (`NUC`) 
		REFERENCES `IR_Contrat` (`NUC`) 
		ON DELETE NO ACTION 
		ON UPDATE NO ACTION)
ENGINE=InnoDB
DEFAULT  CHARACTER SET = latin1;

-- -----------------------------------------------------
-- Table `IR_Option`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `IR_Option` (
	`optimLock` 		INT 			NOT NULL 		COMMENT 'Attribut technique utilisé par le lock optimiste de la couche ORM. CETTE DONNEE N EST PAS PASSEE EN REVISION',
	`cdOption` 			SMALLINT 		NOT NULL 		COMMENT 'Code option',
	`libOption` 		VARCHAR(30) 	DEFAULT NULL 	COMMENT 'Libelle option',
	`dtDebApplication` 	DATE 			DEFAULT NULL 	COMMENT 'Date de debut d’application',
	`dtFinApplication` 	DATE 			DEFAULT NULL 	COMMENT 'Date de fin d’application',
	`NUC` 				VARCHAR(20) 	NOT NULL 		COMMENT 'Numero unique du contrat',
	`cdPopulation` 		SMALLINT 		NOT NULL 		COMMENT 'Code population',
	PRIMARY KEY (`cdOption`,`NUC`,`cdPopulation`),
	INDEX `fk_ir_option_population_idx` (`NUC` ASC, `cdPopulation` ASC),
	CONSTRAINT `fk_ir_option_contrat` 
		FOREIGN KEY (`NUC`) 
		REFERENCES `IR_Contrat` (`NUC`) 
		ON DELETE NO ACTION 
		ON UPDATE NO ACTION,
	CONSTRAINT `fk_ir_option_population` 
		FOREIGN KEY (`NUC` , `cdPopulation`) 
		REFERENCES `IR_PopulationPVC` (`NUC` , `cdPopulation`) 
		ON DELETE NO ACTION 
		ON UPDATE NO ACTION)
ENGINE=InnoDB
DEFAULT  CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `IR_Etablissement`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `IR_Etablissement` (
	`optimLock` 							INT 			NOT NULL 		COMMENT 'Attribut technique utilisé par le lock optimiste de la couche ORM. CETTE DONNEE N EST PAS PASSEE EN REVISION',
	`idEtablissement` 						VARCHAR(8) 	NOT NULL 		COMMENT 'Identifiant technique de l’Etablissement',
	`idEntreprise` 							VARCHAR(8) 	NOT NULL 		COMMENT 'Identifiant technique de l’Entreprise auquel appartient l’etablissement',
	`enseigne` 								VARCHAR(35) 	DEFAULT NULL 	COMMENT 'Enseigne de l’Etablissement',
	`dtCreation` 							DATE 			DEFAULT NULL 	COMMENT 'Date de création de l’Etablissement',
--	`idEntrepriseEtablissement` 			INT 			DEFAULT NULL 	COMMENT 'Identifiant de l’Entreprise de l’etablissement',
	`numIdEtablissementDansOrgaEntreprise` 	SMALLINT 		DEFAULT NULL 	COMMENT 'Numero d’Identification de l’Etablissement dans l’Organisme relativement à l’entreprise',
	`NIC` 									VARCHAR(5) 	DEFAULT NULL 	COMMENT 'NIC du numero SIRET (Le numéro de SIRET de l’Etablissement est le N° SIREN + NIC)',
	`dtPrimExploitation` 					DATE 			DEFAULT NULL 	COMMENT 'Date de premiere mise en exploitation',
	`dtDebMiseSommeil` 						DATE 			DEFAULT NULL 	COMMENT 'Date de mise en sommeil',
	`dtFinMiseSommeil` 						DATE 			DEFAULT NULL 	COMMENT 'Date de fin de mise en sommeil',
	`dtCessationActivite` 					DATE 			DEFAULT NULL 	COMMENT 'Date de cessation d’activite',
	`cdMotifFinActivite` 					VARCHAR(2) 	DEFAULT NULL 	COMMENT 'Code motif de fin d’activite (AB = AbsorptionøAD = Changement d’adresseøCE = CessionøFU = FusionøLG = Location GéranceøSC = Scission)',
	`cdIndicateurCessionActivite` 			VARCHAR(1) 	DEFAULT NULL 	COMMENT 'Code indicateur de cession partielle ou total (P= PartielleøT = Totale)',
	`cdIndicateurSiegeSocial` 				VARCHAR(1) 	DEFAULT NULL 	COMMENT 'Code indicateur d’Etablissement Siege Social (O= OuiøN= Non)',
	`dtPrimEmbauche` 						DATE 			DEFAULT NULL 	COMMENT 'Date de premiere embauche',
	`libActivite` 							VARCHAR(65) 	DEFAULT NULL 	COMMENT 'Libelle de l’activité declaree',
	`activitePrincipaleEtablissement` 		VARCHAR(100) 	DEFAULT NULL 	COMMENT 'Activite principale exercee de l’Etablissement',
	`cdFonctionnelActivitePrincipale` 		VARCHAR(5) 	DEFAULT NULL 	COMMENT 'Code fonctionnel de l’Activite principale exercee',
	`dtDebApplicationCdAPE` 				DATE 			DEFAULT NULL 	COMMENT 'Date de debut d’application du code APE',
	`dtDebEtablissementPrincipal` 			DATE 			DEFAULT NULL 	COMMENT 'Date a partir de laquelle l’etablissement est principal',
	`dtDebEffetEnseigne` 					DATE 			DEFAULT NULL 	COMMENT 'Date de debut d’effet de l’enseigne',
	`dtFinEffetEnseigne` 					DATE 			DEFAULT NULL 	COMMENT 'Date de fin d’effet de l’enseigne',
	`optDecalagePaie` 						VARCHAR(1) 	DEFAULT NULL 	COMMENT 'Option decalage de paie de l’etablissement (O= OuiøN= Non)',
	`dtDebDecalagePaie` 					DATE 			DEFAULT NULL 	COMMENT 'Date de debut d’application du decalage de paie',
	`typeDecalagePaie` 						VARCHAR(1) 	DEFAULT NULL 	COMMENT 'Type de decalage pratique (A = Type AøB = Type B)',
	`cdIndEtablissementPrincipal` 			VARCHAR(1) 	DEFAULT NULL 	COMMENT 'Code indicateur d’Etablissement Principal (O= OuiøN= Non)',
	PRIMARY KEY (`idEtablissement`),
	INDEX `fk_ir_etablissement_entreprise_idx` (`idEntreprise` ASC),
	CONSTRAINT `fk_ir_etablissement_entreprise` 
		FOREIGN KEY (`idEntreprise`) 
		REFERENCES `IR_Entreprise` (`idEntreprise`) 
		ON DELETE NO ACTION 
		ON UPDATE NO ACTION)
ENGINE=InnoDB
DEFAULT  CHARACTER SET = latin1;

-- -----------------------------------------------------
-- Table `IR_Contrat_Etablissement`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `IR_Contrat_Etablissement` (
	`optimLock` 		INT 			NOT NULL 	COMMENT 'Attribut technique utilisé par le lock optimiste de la couche ORM. CETTE DONNEE N EST PAS PASSEE EN REVISION',
	`NUC` 				VARCHAR(20) 	NOT NULL 	COMMENT 'Numéro unique du contrat',
	`idEtablissement` 	VARCHAR(8) 	NOT NULL 	COMMENT 'Identifiant technique de l’Etablissement',
	PRIMARY KEY (`NUC`, `idEtablissement`),
	INDEX `fk_ir_contrat_etablissement_idx` (`idEtablissement` ASC),
	INDEX `fk_ir_etablissement_contrat_idx` (`NUC` ASC),
	CONSTRAINT `fk_ir_etablissement_contrat`
		FOREIGN KEY (`NUC`)
		REFERENCES `IR_Contrat` (`NUC`)
		ON DELETE NO ACTION
		ON UPDATE NO ACTION,
	CONSTRAINT `fk_ir_contrat_etablissement`
		FOREIGN KEY (`idEtablissement`)
		REFERENCES `IR_Etablissement` (`idEtablissement`)
		ON DELETE NO ACTION
		ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COMMENT = 'Contrat_Etablissement';



-- -----------------------------------------------------
-- Table `Parametrage_DSN` Parametrage DSN
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Parametrage_DSN` (
	`optimLock` 			INT 	NOT NULL,
	`id_paramDSN` 			BIGINT 	NOT NULL,
	`dtPrevPassageEnDSN` 	DATE 	NULL,
	`dtPremiereReception` 	DATE 	NULL,
	`dtFinTransmission` 	DATE 	NULL,
	`dtLimiteReception` 	DATE 	NULL,
	PRIMARY KEY (`id_paramDSN`)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COMMENT = 'Parametrage DSN';


-- -----------------------------------------------------
-- Table `DSN_06` Entreprise
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DSN_06` (
	`optimLock` 		INT 			NOT NULL 	COMMENT 'Attribut technique utilisé par le lock optimiste de la couche ORM. CETTE DONNEE N EST PAS PASSEE EN REVISION',
	`dtDernierPassage` 	DATE 			NOT NULL 	COMMENT 'Date de passage du batch. Cette DATE doit permettre de gérer les cas de DSN fractionnées et les DSN annulantes. Dans le cas d une DSN normale si la DATE de passage est dans le meme mois que celui du traitement en cours, on ne fait rien car cela signifie que le bloc a déjà été traitement par une autre fraction. Si la DATE de passage est du mois précedent la DATE du traitement en cours, on effectue les modifications si necessaire et on modifie la DATE de passage pour la positionner sur le mois courant. Si la DSN est de type Annule, on repositionne tous les champs à la version precedente et on positionne la DATE de passage au premier jour du mois précedent le mois courant. CETTE DONNEE N EST PAS PASSEE EN REVISION',
	`id_entreprise` 	BIGINT 			NOT NULL,
	`_001` 				VARCHAR(9) 	NOT NULL 	COMMENT 'SIREN',
	`_002` 				VARCHAR(5) 	NULL 		COMMENT 'NIC du siège',
	`_003` 				VARCHAR(5) 	NULL 		COMMENT 'Code APEN',
	`_004` 				VARCHAR(50) 	NULL	 	COMMENT 'Numéro, extension, nature et libellé de la voie',
	`_005` 				VARCHAR(5) 	NULL 		COMMENT 'Code postal',
	`_006` 				VARCHAR(50) 	NULL	 	COMMENT 'Localité',
	`_007` 				VARCHAR(50) 	NULL	 	COMMENT 'Complément de la localisation de la construction',
	`_008` 				VARCHAR(50) 	NULL	 	COMMENT 'Service de distribution, complément de localisation de la voie',
--	`_009` 				VARCHAR(7) 	NULL 		COMMENT 'Effectif moyen de l entreprise au 31 décembre',
	PRIMARY KEY (`id_entreprise`),
	INDEX `SIREN_idx` (`_001` ASC),
	CONSTRAINT `fk_entreprise_parametrage_DSN`
		FOREIGN KEY (`id_entreprise`)
		REFERENCES `Parametrage_DSN` (`id_paramDSN`)
		ON DELETE NO ACTION
		ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COMMENT = 'Entreprise';


-- -----------------------------------------------------
-- Table `DSN_11` Etablissement
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DSN_11` (
	`optimLock` 		INT 			NOT NULL 	COMMENT 'Attribut technique utilisé par le lock optimiste de la couche ORM. CETTE DONNEE N EST PAS PASSEE EN REVISION',
	`dtDernierPassage` 	DATE 			NOT NULL 	COMMENT 'Date de passage du batch. Cette DATE doit permettre de gérer les cas de DSN fractionnées et les DSN annulantes. Ex: a partir du moment où la DATE de passage est dans le meme mois que celui du traitement en cours, cela signifie que l entite Entreprise a déjà été mis à jour lors du traitement d une autre fraction. CETTE DONNEE N EST PAS PASSEE EN REVISION',
	`id_etablissement` 	BIGINT 			NOT NULL,
	`id_entreprise` 	BIGINT 			NOT NULL,
	`_001` 				VARCHAR(5) 	NOT NULL 	COMMENT 'NIC',
	`_002` 				VARCHAR(5) 	NULL 		COMMENT 'Code APET',
	`_003` 				VARCHAR(50) 	NOT NULL 	COMMENT 'Numéro, extension, nature et libellé de la voie',
	`_004` 				VARCHAR(5) 	NOT NULL 	COMMENT 'Code postal',
	`_005` 				VARCHAR(50) 	NOT NULL 	COMMENT 'Localité',
	`_006` 				VARCHAR(50) 	NULL 		COMMENT 'Complément de la localisation de la construction',
	`_007` 				VARCHAR(50) 	NULL 		COMMENT 'Service de distribution, complément de localisation de la voie',
--	`_008` 				VARCHAR(6) 	NULL 		COMMENT 'Effectif de fin de période déclarée de l établissement d affectation',
--	`_009` 				VARCHAR(2) 	NULL 		COMMENT 'Type de rémunération soumise à contributions Assurance chômage pour expatriés',
	PRIMARY KEY (`id_etablissement`), 
	INDEX `fk_etablissement_entreprise_idx` (`id_entreprise` ASC),
	INDEX `NIC_idx` (`_001` ASC),
	CONSTRAINT `fk_etablissement_entreprise` 
		FOREIGN KEY (`id_entreprise`) 
		REFERENCES `DSN_06` (`id_entreprise`) 
		ON DELETE NO ACTION 
		ON UPDATE NO ACTION) 
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COMMENT = 'Etablissement';


-- -----------------------------------------------------
-- Table `DSN_05` DSN
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DSN_05` (
	`optimLock` 		INT 			NOT NULL 	COMMENT 'Attribut technique utilisé par le lock optimiste de la couche ORM. CETTE DONNEE N EST PAS PASSEE EN REVISION',
	`id_envoi` 			BIGINT 			NOT NULL 	COMMENT 'Identifiant technique de la declaration. Reprend l identifiant de l envoi de la table (00) dans la base de donnée DSNstock.',
	`id_paramDSN` 		BIGINT 			NOT NULL,
	`id_etablissement` 	BIGINT 			NOT NULL 	COMMENT 'Identifiant de l etablissement dans la base DSNversion',
	`_001` 				VARCHAR(10) 	NOT NULL 	COMMENT 'Nature de la déclaration',
	`_002` 				VARCHAR(10) 	NOT NULL 	COMMENT 'Type de la déclaration',
	`_003` 				VARCHAR(10) 	NOT NULL 	COMMENT 'Numéro de fraction de déclaration',
	`_004` 				VARCHAR(15) 	NOT NULL 	COMMENT 'Numéro d ordre de la déclaration',
	`_005` 				VARCHAR(8) 	NULL 		COMMENT 'Date du mois principal déclaré',
	`_006` 				VARCHAR(50) 	NULL 		COMMENT 'Identifiant de la déclaration annulée ou remplacée',
	`_007` 				VARCHAR(8) 	NOT NULL 	COMMENT 'Date de constitution du fichier',
	`_008` 				VARCHAR(10) 	NULL 		COMMENT 'Champ de la déclaration',
	`_009` 				VARCHAR(15) 	NULL 		COMMENT 'Identifiant de l évènement',
	PRIMARY KEY (`id_envoi`),
	INDEX `fk_DSN_etablissement_idx` (`id_etablissement` ASC),
	CONSTRAINT `fk_DSN_etablissement`
		FOREIGN KEY (`id_etablissement`)
		REFERENCES `DSN_11` (`id_etablissement`)
		ON DELETE NO ACTION
		ON UPDATE NO ACTION,
	CONSTRAINT `fk_DSN_parametrage_DSN`
		FOREIGN KEY (`id_paramDSN`)
		REFERENCES `Parametrage_DSN` (`id_paramDSN`)
		ON DELETE NO ACTION
		ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COMMENT = 'DSN';


-- -----------------------------------------------------
-- Table `DSN_30` Individu
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DSN_30` (
	`optimLock` 		INT 			NOT NULL 	COMMENT 'Attribut technique utilisé par le lock optimiste de la couche ORM. CETTE DONNEE N EST PAS PASSEE EN REVISION',
	`id_individu` 		BIGINT 			NOT NULL,
	`id_etablissement` 	BIGINT 			NOT NULL,
	`_001` 				VARCHAR(13) 	NULL 		COMMENT 'Numéro d inscription au répertoire',
	`_002` 				VARCHAR(80) 	NOT NULL 	COMMENT 'Nom de famille',
	`_003` 				VARCHAR(80) 	NULL 		COMMENT 'Nom d usage',
	`_004` 				VARCHAR(80) 	NOT NULL 	COMMENT 'Prénoms',
	`_005` 				VARCHAR(2) 	NULL 		COMMENT 'Sexe',
	`_006` 				VARCHAR(8) 	NOT NULL 	COMMENT 'Date de naissance',
	`_007` 				VARCHAR(30) 	NULL 		COMMENT 'Lieu de naissance',
	`_008` 				VARCHAR(50) 	NULL 		COMMENT 'Numéro, extension, nature et libellé de la voie',
	`_009` 				VARCHAR(5) 	NULL 		COMMENT 'Code postal',
	`_010` 				VARCHAR(50) 	NULL 		COMMENT 'Localité',
	`_011` 				VARCHAR(2) 	NULL 		COMMENT 'Code pays',
	`_012` 				VARCHAR(50) 	NULL 		COMMENT 'Code de distribution à l étranger',
--	`_013` 				VARCHAR(2) 	NULL 		COMMENT 'Codification UE',
	`_014` 				VARCHAR(2) 	NULL 		COMMENT 'Code département de naissance',
	`_015` 				VARCHAR(2) 	NULL 		COMMENT 'Code pays de naissance',
	`_016` 				VARCHAR(50) 	NULL 		COMMENT 'Complément de la localisation de la construction',
	`_017` 				VARCHAR(50) 	NULL 		COMMENT 'Service de distribution, complément de localisation de la voie',
	`_018` 				VARCHAR(100) 	NULL 		COMMENT 'Adresse mél',
	`_019` 				VARCHAR(30) 	NULL 		COMMENT 'Matricule de l individu dans l entreprise',
	`_020` 				VARCHAR(39) 	NULL 		COMMENT 'Numéro technique temporaire',
	PRIMARY KEY (`id_individu`),
	INDEX `NIR_idx` (`_001` ASC),
	INDEX `fk_individu_etablissement_idx` (`id_etablissement` ASC),
	CONSTRAINT `fk_individu_etablissement` 
		FOREIGN KEY (`id_etablissement`) 
		REFERENCES `DSN_11` (`id_etablissement`) 
		ON DELETE NO ACTION 
		ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COMMENT = 'Individu';


-- -----------------------------------------------------
-- Table `DSN_40` Contrat
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DSN_40` (
	`optimLock` 	INT 			NOT NULL 	COMMENT 'Attribut technique utilisé par le lock optimiste de la couche ORM. CETTE DONNEE N EST PAS PASSEE EN REVISION',
	`id_contrat` 	BIGINT 			NOT NULL,
	`id_individu` 	BIGINT 			NOT NULL,
	`_001` 			VARCHAR(8) 	NOT NULL 	COMMENT 'Date de début du contrat',
	`_002` 			VARCHAR(2) 	NULL 		COMMENT 'Statut du salarié (conventionnel)',
	`_003` 			VARCHAR(2) 	NULL 		COMMENT 'Code statut catégoriel Retraite Complémentaire obligatoire',
	`_004` 			VARCHAR(4) 	NULL 		COMMENT 'Code profession et catégorie socioprofessionnelle (PCS-ESE)',
	`_005` 			VARCHAR(6) 	NULL 		COMMENT 'Code complément PCS-ESE',
	`_006` 			VARCHAR(120) 	NULL	 	COMMENT 'Libellé de l emploi',
	`_007` 			VARCHAR(2) 	NULL 		COMMENT 'Nature du contrat',
	`_008` 			VARCHAR(2) 	NULL 		COMMENT 'Dispositif de politique publique',
	`_009` 			VARCHAR(20) 	NOT NULL 	COMMENT 'Numéro du contrat',
	`_010` 			VARCHAR(8) 	NULL 		COMMENT 'Date de fin prévisionnelle du contrat',
	`_011` 			VARCHAR(2) 	NULL 		COMMENT 'Unité de mesure de la quotité de travail',
	`_012` 			VARCHAR(7) 	NULL 		COMMENT 'Quotité de travail de référence de l entreprise pour la catégorie de salarié',
	`_013` 			VARCHAR(7) 	NULL 		COMMENT 'Quotité de travail du contrat',
	`_014` 			VARCHAR(2) 	NULL 		COMMENT 'Modalité d exercice du temps de travail',
--	`_015` 			VARCHAR(12) 	NULL	 	COMMENT 'Salaire de référence porté par le contrat',
	`_016` 			VARCHAR(2) 	NULL 		COMMENT 'Régime local Alsace Moselle',
	`_017` 			VARCHAR(4) 	NULL 		COMMENT 'Code convention collective applicable',
	`_018` 			VARCHAR(3) 	NULL 		COMMENT 'Code régime de base risque maladie',
	`_019` 			VARCHAR(14) 	NULL	 	COMMENT 'Identifiant du lieu de travail',
--	`_020` 			VARCHAR(3) 	NULL 		COMMENT 'Code régime de base risque vieillesse',
--	`_021` 			VARCHAR(2) 	NULL 		COMMENT 'Motif de recours',
--	`_022` 			VARCHAR(2) 	NULL 		COMMENT 'Code caisse professionnelle de congés payés',
--	`_023` 			VARCHAR(6) 	NULL 		COMMENT 'Taux de déduction forfaitaire spécifique pour frais professionnels',
--	`_024` 			VARCHAR(2) 	NULL 		COMMENT 'Statut à l étranger',
--	`_025` 			VARCHAR(2) 	NULL 		COMMENT 'Motif d exclusion DSN',
	PRIMARY KEY (`id_contrat`),
	INDEX `numContratTravail_idx` (`_009` ASC),
	INDEX `fk_individu_contrat_idx` (`id_individu` ASC),
	CONSTRAINT `fk_individu_contrat`
		FOREIGN KEY (`id_individu`)
		REFERENCES `DSN_30` (`id_individu`)
		ON DELETE NO ACTION
		ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COMMENT = 'Contrat';


-- -----------------------------------------------------
-- Table `DSN_62` Fin du contrat
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DSN_62` (
	`optimLock` 		INT 			NOT NULL 	COMMENT 'Attribut technique utilisé par le lock optimiste de la couche ORM. CETTE DONNEE N EST PAS PASSEE EN REVISION',
	`id_finContrat` 	BIGINT 			NOT NULL,
	`_001` 				VARCHAR(8) 	NOT NULL 	COMMENT 'Date de fin du contrat',
	`_002` 				VARCHAR(3) 	NOT NULL 	COMMENT 'Motif de la rupture du contrat',
--	`_003` 				VARCHAR(8) 	NULL 		COMMENT 'Date de notification de la rupture de contrat',
--	`_004` 				VARCHAR(8) 	NULL 		COMMENT 'Date de signature de la convention de rupture',
--	`_005` 				VARCHAR(8) 	NULL 		COMMENT 'Date d engagement de la procédure de licenciement',
--	`_006` 				VARCHAR(8) 	NULL 		COMMENT 'Dernier jour travaillé et payé au salaire habituel',
--	`_008` 				VARCHAR(2) 	NULL 		COMMENT 'Transaction en cours',
	`_009` 				VARCHAR(2) 	NULL 		COMMENT 'Portabilité contrat de Prévoyance',
--	`_010` 				VARCHAR(6) 	NULL 		COMMENT 'Nombre d heures de DIF n ayant pas été utilisées',
--	`_011` 				VARCHAR(4) 	NULL 		COMMENT 'Nombre de mois de préavis utilisés dans le cadre du calcul CSP',
--	`_012` 				VARCHAR(12) 	NULL 		COMMENT 'Salaire net horaire du salarié',
--	`_013` 				VARCHAR(12) 	NULL 		COMMENT 'Montant de l indemnité de préavis qui aurait été versée',
--	`_014` 				VARCHAR(2) 	NULL 		COMMENT 'Statut particulier du salarié',
	PRIMARY KEY (`id_finContrat`),
	CONSTRAINT `fk_finContrat_contrat`
		FOREIGN KEY (`id_finContrat`)
		REFERENCES `DSN_40` (`id_contrat`)
		ON DELETE NO ACTION
		ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COMMENT = 'Fin du contrat';


-- -----------------------------------------------------
-- Table `DSN_70` Affiliation Prevoyance
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DSN_70` (
	`optimLock` 		INT 			NOT NULL 	COMMENT 'Attribut technique utilisé par le lock optimiste de la couche ORM. CETTE DONNEE N EST PAS PASSEE EN REVISION',
	`id_affil` 			BIGINT 			NOT NULL,
	`id_contrat` 		BIGINT 			NOT NULL,
	`_001` 				VARCHAR(20) 	NOT NULL 	COMMENT 'Référence du contrat de Prévoyance',
	`_002` 				VARCHAR(9) 	NOT NULL 	COMMENT 'Code organisme de Prévoyance',
	`_003` 				VARCHAR(6) 	NULL 		COMMENT 'Code délégataire de gestion',
	`_004` 				VARCHAR(30) 	NULL 		COMMENT 'Code option retenue par le salarié',
	`_005` 				VARCHAR(30) 	NULL 		COMMENT 'Code population de rattachement',
	`_006` 				VARCHAR(8) 	NOT NULL 	COMMENT 'Date d affiliation',
	PRIMARY KEY (`id_affil`),
	INDEX `fk_affil_contrat_idx` (`id_contrat` ASC),
	INDEX `fk_affil_IRcontrat_idx` (`_001` ASC),
	CONSTRAINT `fk_affil_contrat`
		FOREIGN KEY (`id_contrat`)
		REFERENCES `DSN_40` (`id_contrat`)
		ON DELETE NO ACTION
		ON UPDATE NO ACTION,
	CONSTRAINT `fk_affil_IRcontrat`
		FOREIGN KEY (`_001`)
		REFERENCES `IR_Contrat` (`NUC`)
		ON DELETE NO ACTION
		ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COMMENT = 'Affiliation Prevoyance';


-- -----------------------------------------------------
-- Table `Revision`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Revision` (
	`id_revision` 	BIGINT 			NOT NULL AUTO_INCREMENT,
	`id_version` 	BIGINT 			NOT NULL,
	`revTmStmp` 	BIGINT 			NOT NULL,
	`userName` 		VARCHAR(20) 	NOT NULL,
	PRIMARY KEY (`id_revision`),
	INDEX `fk_revision_version_idx` (`id_version` ASC),
	CONSTRAINT `fk_revision_version`
		FOREIGN KEY (`id_version`)
		REFERENCES `DSN_05` (`id_envoi`)
		ON DELETE NO ACTION
		ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COMMENT = 'Revision';



CREATE TABLE `IR_Entreprise_revision` (
	`idEntreprise` 					VARCHAR(8) 	NOT NULL,
	`REV` 							BIGINT 			NOT NULL,
	`REVTYPE` 						TINYINT 		DEFAULT NULL,
	`REVEND` 						BIGINT 			DEFAULT NULL,
	`REVEND_TSTMP` 					DATETIME 		DEFAULT NULL,
	`raisonSociale` 				VARCHAR(100) 	DEFAULT NULL 	COMMENT 'Raison Sociale',
	`raisonSociale_MOD` 			BOOLEAN 		DEFAULT NULL,
	`sigle` 						VARCHAR(32) 	DEFAULT NULL 	COMMENT 'Sigle',
	`sigle_MOD` 					BOOLEAN 		DEFAULT NULL,
	`dtCreation` 					DATE 			DEFAULT NULL 	COMMENT 'Date de création',
	`dtCreation_MOD` 				BOOLEAN 		DEFAULT NULL,
	`numIdDansOrganisme` 			INT 			DEFAULT NULL 	COMMENT 'Numéro d’Identification dans l’Organisme',
	`numIdDansOrganisme_MOD` 		BOOLEAN 		DEFAULT NULL,
	`numSIREN` 						VARCHAR(9) 	DEFAULT NULL 	COMMENT 'Numéro de SIREN',
	`numSIREN_MOD` 					BOOLEAN 		DEFAULT NULL,
	`raisonSocialeAbregee` 			VARCHAR(38) 	DEFAULT NULL 	COMMENT 'Raison Sociale abrégée. Cette zone est obligatoirement renseignée si la raison sociale a plus de 35 caractères',
	`raisonSocialeAbregee_MOD` 		BOOLEAN 		DEFAULT NULL,
	`cdAPEN` 						VARCHAR(5) 	DEFAULT NULL 	COMMENT 'Code APEN de l’entreprise',
	`cdAPEN_MOD` 					BOOLEAN 		DEFAULT NULL,
	`dtPrimExploitation` 			DATE 			DEFAULT NULL 	COMMENT 'Date de première exploitation',
	`dtPrimExploitation_MOD` 		BOOLEAN 		DEFAULT NULL,
	`dtDebSommeil` 					DATE 			DEFAULT NULL 	COMMENT 'Date de début de mise en sommeil',
	`dtDebSommeil_MOD` 				BOOLEAN 		DEFAULT NULL,
	`dtFinSommeil` 					DATE 			DEFAULT NULL 	COMMENT 'Date de fin de mise en sommeil',
	`dtFinSommeil_MOD` 				BOOLEAN 		DEFAULT NULL,
	`cdIndicateurCession` 			VARCHAR(1) 	DEFAULT NULL 	COMMENT 'Code indicateur de cession partielle/totale: (P = PartielleøT = Totale)',
	`cdIndicateurCession_MOD` 		BOOLEAN 		DEFAULT NULL,
	`dtFinCessionPartielle` 		DATE 			DEFAULT NULL 	COMMENT 'Date de fin de cession partielle',
	`dtFinCessionPartielle_MOD` 	BOOLEAN 		DEFAULT NULL,
	`dtFinContinuation` 			DATE 			DEFAULT NULL 	COMMENT 'Date de fin de plan de continuation',
	`dtFinContinuation_MOD` 		BOOLEAN 		DEFAULT NULL,
	`dtDissolution` 				DATE 			DEFAULT NULL 	COMMENT 'Date de dissolution',
	`dtDissolution_MOD` 			BOOLEAN 		DEFAULT NULL,
	`dtRadiation` 					DATE 			DEFAULT NULL 	COMMENT 'Date de radiation',
	`dtRadiation_MOD` 				BOOLEAN 		DEFAULT NULL,
	`cdMotifRadiation` 				VARCHAR(2) 	DEFAULT NULL 	COMMENT 'Code motif de la radiation: (B = AbsorptionøCA = Cessation activitéøCE = CessionøFU = FusionøDI = DissolutionøLI = Liquidation judiciaireøSC = Scission)',
	`cdMotifRadiation_MOD` 			BOOLEAN 		DEFAULT NULL,
	`dtCessationActivite` 			DATE 			DEFAULT NULL 	COMMENT 'Date de cessation de l’activité',
	`dtCessationActivite_MOD` 		BOOLEAN 		DEFAULT NULL,
	`dtPremiereEmbauche` 			DATE 			DEFAULT NULL 	COMMENT 'Date première embauche',
	`dtPremiereEmbauche_MOD` 		BOOLEAN 		DEFAULT NULL,
	`dtConnaissanceGroupe` 			DATE 			DEFAULT NULL 	COMMENT 'Date de connaissance par le Groupe Médéric',
	`dtConnaissanceGroupe_MOD` 		BOOLEAN 		DEFAULT NULL,
	`dtDebApplicationCdAPE` 		DATE 			DEFAULT NULL 	COMMENT 'Date de début d’application du code APE',
	`dtDebApplicationCdAPE_MOD` 	BOOLEAN 		DEFAULT NULL,
	`dtFinApplicationCdAPE` 		DATE 			DEFAULT NULL 	COMMENT 'Date de fin d’application du code APE',
	`dtFinApplicationCdAPE_MOD` 	BOOLEAN 		DEFAULT NULL,
	`dtEffetRaisonSociale` 			DATE 			DEFAULT NULL 	COMMENT 'Date effet Raison sociale',
	`dtEffetRaisonSociale_MOD` 		BOOLEAN 		DEFAULT NULL,
	`optionDecalPaie` 				VARCHAR(1) 	DEFAULT NULL 	COMMENT 'Option décalage de paie: (N=NonøT= Oui (donc pour tout établissement)øP=Pour certains établissements)',
	`optionDecalPaie_MOD` 			BOOLEAN 		DEFAULT NULL,
	`dtDebApplicationDecalPaie` 	DATE 			DEFAULT NULL 	COMMENT 'Date de début d’application du décalage de paie',
	`dtDebApplicationDecalPaie_MOD` BOOLEAN 		DEFAULT NULL,
	`dtFinApplicationDecalPaie` 	DATE 			DEFAULT NULL 	COMMENT 'Date de fin d’application du décalage de paie',
	`dtFinApplicationDecalPaie_MOD` BOOLEAN 		DEFAULT NULL,
	`typeDecalageUtilise` 			VARCHAR(1) 	DEFAULT NULL 	COMMENT 'Type de décalage pratiqué: (A = Type AøB = Type B). Est renseigné si l’option décalage a la valeur T',
	`typeDecalageUtilise_MOD` 		BOOLEAN 		DEFAULT NULL,
	`cdEntrepriseVIP` 				VARCHAR(1) 	DEFAULT NULL 	COMMENT 'Code entreprise VIP',
	`cdEntrepriseVIP_MOD` 			BOOLEAN 		DEFAULT NULL,
	`irContratsList_MOD` 			BOOLEAN 		DEFAULT NULL,
	`irEtablissementsList_MOD` 		BOOLEAN 		DEFAULT NULL,
	PRIMARY KEY (`idEntreprise`,`REV`),
	CONSTRAINT `fk_entreprise_revision_rev` FOREIGN KEY (`REV`) REFERENCES `Revision` (`id_revision`) ON DELETE NO ACTION ON UPDATE NO ACTION,
	CONSTRAINT `fk_entreprise_revision_revend` FOREIGN KEY (`REVEND`) REFERENCES `Revision` (`id_revision`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `IR_Contrat_revision` (
	`NUC` 								VARCHAR(20) 	NOT NULL 		COMMENT 'Numéro unique du contrat (identifiant technique)',
	`REV` 								BIGINT 			NOT NULL,
	`REVTYPE` 							TINYINT 		DEFAULT NULL,
	`REVEND` 							BIGINT 			DEFAULT NULL,
	`REVEND_TSTMP` 						DATETIME 		DEFAULT NULL,
	`numSequence` 						SMALLINT 		NOT NULL 		COMMENT 'Numero Sequence contrat',
	`numSequence_MOD` 					BOOLEAN 		DEFAULT NULL,
	`cdProduit` 						VARCHAR(16) 	DEFAULT NULL 	COMMENT 'Code Produit',
	`cdProduit_MOD` 					BOOLEAN 		DEFAULT NULL,
	`libproduit` 						VARCHAR(30) 	DEFAULT NULL 	COMMENT 'Libellé produit',
	`libproduit_MOD` 					BOOLEAN 		DEFAULT NULL,
	`typeCtrt` 							VARCHAR(30) 	DEFAULT NULL 	COMMENT 'Type de contrat (Sante, Prevoyance, Retraite par capitalisation, ..)',
	`typeCtrt_MOD` 						BOOLEAN 		DEFAULT NULL,
	`indicateurCtrtCCFI` 				VARCHAR(1) 	DEFAULT NULL 	COMMENT 'Indicateur contrat CCFI (oui/non)',
	`indicateurCtrtCCFI_MOD` 			BOOLEAN 		DEFAULT NULL,
	`dtEffet` 							DATE 			DEFAULT NULL 	COMMENT 'Date d’effet du contrat',
	`dtEffet_MOD` 						BOOLEAN 		DEFAULT NULL,
	`dtRadiation` 						DATE 			DEFAULT NULL 	COMMENT 'Date de radiation (eventuelle)',
	`dtRadiation_MOD` 					BOOLEAN 		DEFAULT NULL,
	`cdOrgaPorteurRisque` 				VARCHAR(5) 	DEFAULT NULL 	COMMENT 'Code organisme porteur de risque',
	`cdOrgaPorteurRisque_MOD` 			BOOLEAN 		DEFAULT NULL,
	`cdOrgaDelegataireCotis` 			VARCHAR(6) 	DEFAULT NULL 	COMMENT 'Code organisme délégataire de cotisation (eventuel, un seul possible)',
	`cdOrgaDelegataireCotis_MOD` 		BOOLEAN 		DEFAULT NULL,
	`affiliationPrevList_MOD` 			BOOLEAN 		DEFAULT NULL,
	`irContratEtablissementsList_MOD` 	BOOLEAN 		DEFAULT NULL,
	`idEntreprise` 						VARCHAR(8) 	DEFAULT NULL 	COMMENT 'Identifiant technique de l’entreprise',
	`irEntreprise_MOD` 					BOOLEAN 		DEFAULT NULL,
	`irOptionsList_MOD` 				BOOLEAN 		DEFAULT NULL,
	`irPopulationPvcsList_MOD` 			BOOLEAN 		DEFAULT NULL,
	`irRisqueDeleguesList_MOD` 			BOOLEAN 		DEFAULT NULL,
	PRIMARY KEY (`NUC`,`REV`),
	CONSTRAINT `fk_contrat_revision_rev` FOREIGN KEY (`REV`) REFERENCES `Revision` (`id_revision`) ON DELETE NO ACTION ON UPDATE NO ACTION,
	CONSTRAINT `fk_contrat_revision_revend` FOREIGN KEY (`REVEND`) REFERENCES `Revision` (`id_revision`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `IR_RisqueDelegue_revision` (
	`riskDelegataire` 			VARCHAR(50) 	NOT NULL,
	`cdOrgaDelegatairePrest` 	VARCHAR(50) 	NOT NULL,
	`NUC` 						VARCHAR(20) 	NOT NULL,
	`REV` 						BIGINT 			NOT NULL,
	`REVTYPE` 					TINYINT 		DEFAULT NULL,
	`REVEND` 					BIGINT 			DEFAULT NULL,
	`REVEND_TSTMP` 				DATETIME 		DEFAULT NULL,
	`irContrat_MOD` 			BOOLEAN 		DEFAULT NULL,
	PRIMARY KEY (`riskDelegataire`,`cdOrgaDelegatairePrest`,`NUC`,`REV`),
	CONSTRAINT `fk_risqueDelegue_revision_rev` FOREIGN KEY (`REV`) REFERENCES `Revision` (`id_revision`) ON DELETE NO ACTION ON UPDATE NO ACTION,
	CONSTRAINT `fk_risqueDelegue_revision_revend` FOREIGN KEY (`REVEND`) REFERENCES `Revision` (`id_revision`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `IR_PopulationPVC_revision` (
	`cdPopulation` 			SMALLINT 		NOT NULL,
	`NUC` 					VARCHAR(20) 	NOT NULL,
	`REV` 					BIGINT 			NOT NULL,
	`REVTYPE` 				TINYINT 		DEFAULT NULL,
	`REVEND` 				BIGINT 			DEFAULT NULL,
	`REVEND_TSTMP` 			DATETIME 		DEFAULT NULL,
	`libPopulation` 		VARCHAR(30) 	DEFAULT NULL 	COMMENT 'Libelle court population',
	`libPopulation_MOD` 	BOOLEAN 		DEFAULT NULL,
	`libPopLong` 			VARCHAR(100) 	DEFAULT NULL 	COMMENT 'Libelle long population',
	`libPopLong_MOD` 		BOOLEAN 		DEFAULT NULL,
	`dtDebApplication` 		DATE 			DEFAULT NULL 	COMMENT 'Date de debut d’application',
	`dtDebApplication_MOD` 	BOOLEAN 		DEFAULT NULL,
	`dtFinApplication` 		DATE 			DEFAULT NULL 	COMMENT 'Date de fin d’application',
	`dtFinApplication_MOD` 	BOOLEAN 		DEFAULT NULL,
	`irContrat_MOD` 		BOOLEAN 		DEFAULT NULL,
	`irOptionsList_MOD` 	BOOLEAN 		DEFAULT NULL,
	PRIMARY KEY (`cdPopulation`,`NUC`,`REV`),
	CONSTRAINT `fk_populationPVC_revision_rev` FOREIGN KEY (`REV`) REFERENCES `Revision` (`id_revision`) ON DELETE NO ACTION ON UPDATE NO ACTION,
	CONSTRAINT `fk_populationPVC_revision_revend` FOREIGN KEY (`REVEND`) REFERENCES `Revision` (`id_revision`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `IR_Option_revision` (
	`cdOption` 				SMALLINT 		NOT NULL,
	`cdPopulation` 			SMALLINT 		NOT NULL,
	`NUC` 					VARCHAR(20) 	NOT NULL,
	`REV` 					BIGINT 			NOT NULL,
	`REVTYPE` 				TINYINT 		DEFAULT NULL,
	`REVEND` 				BIGINT 			DEFAULT NULL,
	`REVEND_TSTMP` 			DATETIME 		DEFAULT NULL,
	`libOption` 			VARCHAR(30) 	DEFAULT NULL 	COMMENT 'Libelle option',
	`libOption_MOD` 		BOOLEAN 		DEFAULT NULL,
	`dtDebApplication` 		DATE 			DEFAULT NULL 	COMMENT 'Date de debut d’application',
	`dtDebApplication_MOD` 	BOOLEAN 		DEFAULT NULL,
	`dtFinApplication` 		DATE 			DEFAULT NULL 	COMMENT 'Date de fin d’application',
	`dtFinApplication_MOD` 	BOOLEAN 		DEFAULT NULL,
	`irContrat_MOD` 		BOOLEAN 		DEFAULT NULL,
	`irPopulationPvc_MOD` 	BOOLEAN 		DEFAULT NULL,
	PRIMARY KEY (`cdOption`,`cdPopulation`,`NUC`,`REV`),
	CONSTRAINT `fk_option_revision_rev` FOREIGN KEY (`REV`) REFERENCES `Revision` (`id_revision`) ON DELETE NO ACTION ON UPDATE NO ACTION,
	CONSTRAINT `fk_option_revision_revend` FOREIGN KEY (`REVEND`) REFERENCES `Revision` (`id_revision`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `IR_Etablissement_revision` (
	`idEtablissement` 							VARCHAR(8) 	NOT NULL,
	`REV` 										BIGINT 			NOT NULL,
	`REVTYPE` 									TINYINT 		DEFAULT NULL,
	`REVEND` 									BIGINT 			DEFAULT NULL,
	`REVEND_TSTMP` 								DATETIME 		DEFAULT NULL,
	`enseigne` 									VARCHAR(35) 	DEFAULT NULL 	COMMENT 'Enseigne de l’Etablissement',
	`enseigne_MOD` 								BOOLEAN 		DEFAULT NULL,
	`dtCreation` 								DATE 			DEFAULT NULL 	COMMENT 'Date de création de l’Etablissement',
	`dtCreation_MOD` 							BOOLEAN 		DEFAULT NULL,
	`numIdEtablissementDansOrgaEntreprise` 		SMALLINT 		DEFAULT NULL 	COMMENT 'Numero d’Identification de l’Etablissement dans l’Organisme relativement à l’entreprise',
	`numIdEtablissementDansOrgaEntreprise_MOD` 	BOOLEAN 		DEFAULT NULL,
	`NIC` 										VARCHAR(5) 	DEFAULT NULL 	COMMENT 'NIC du numero SIRET (Le numéro de SIRET de l’Etablissement est le N° SIREN + NIC)',
	`NIC_MOD` 									BOOLEAN 		DEFAULT NULL,
	`dtPrimExploitation` 						DATE 			DEFAULT NULL 	COMMENT 'Date de premiere mise en exploitation',
	`dtPrimExploitation_MOD` 					BOOLEAN 		DEFAULT NULL,
	`dtDebMiseSommeil` 							DATE 			DEFAULT NULL 	COMMENT 'Date de mise en sommeil',
	`dtDebMiseSommeil_MOD` 						BOOLEAN 		DEFAULT NULL,
	`dtFinMiseSommeil` 							DATE 			DEFAULT NULL 	COMMENT 'Date de fin de mise en sommeil',
	`dtFinMiseSommeil_MOD` 						BOOLEAN 		DEFAULT NULL,
	`dtCessationActivite` 						DATE 			DEFAULT NULL 	COMMENT 'Date de cessation d’activite',
	`dtCessationActivite_MOD` 					BOOLEAN 		DEFAULT NULL,
	`cdMotifFinActivite` 						VARCHAR(2) 	DEFAULT NULL 	COMMENT 'Code motif de fin d’activite (AB = AbsorptionøAD = Changement d’adresseøCE = CessionøFU = FusionøLG = Location GéranceøSC = Scission)',
	`cdMotifFinActivite_MOD` 					BOOLEAN 		DEFAULT NULL,
	`cdIndicateurCessionActivite` 				VARCHAR(1) 	DEFAULT NULL 	COMMENT 'Code indicateur de cession partielle ou total (P= PartielleøT = Totale)',
	`cdIndicateurCessionActivite_MOD` 			BOOLEAN 		DEFAULT NULL,
	`cdIndicateurSiegeSocial` 					VARCHAR(1) 	DEFAULT NULL 	COMMENT 'Code indicateur d’Etablissement Siege Social (O= OuiøN= Non)',
	`cdIndicateurSiegeSocial_MOD` 				BOOLEAN 		DEFAULT NULL,
	`dtPrimEmbauche` 							DATE 			DEFAULT NULL 	COMMENT 'Date de premiere embauche',
	`dtPrimEmbauche_MOD` 						BOOLEAN 		DEFAULT NULL,
	`libActivite` 								VARCHAR(65) 	DEFAULT NULL 	COMMENT 'Libelle de l’activité declaree',
	`libActivite_MOD` 							BOOLEAN 		DEFAULT NULL,
	`activitePrincipaleEtablissement` 			VARCHAR(100) 	DEFAULT NULL 	COMMENT 'Activite principale exercee de l’Etablissement',
	`activitePrincipaleEtablissement_MOD` 		BOOLEAN 		DEFAULT NULL,
	`cdFonctionnelActivitePrincipale` 			VARCHAR(5) 	DEFAULT NULL 	COMMENT 'Code fonctionnel de l’Activite principale exercee',
	`cdFonctionnelActivitePrincipale_MOD` 		BOOLEAN 		DEFAULT NULL,
	`dtDebApplicationCdAPE` 					DATE 			DEFAULT NULL 	COMMENT 'Date de debut d’application du code APE',
	`dtDebApplicationCdAPE_MOD` 				BOOLEAN 		DEFAULT NULL,
	`dtDebEtablissementPrincipal` 				DATE 			DEFAULT NULL 	COMMENT 'Date a partir de laquelle l’etablissement est principal',
	`dtDebEtablissementPrincipal_MOD` 			BOOLEAN 		DEFAULT NULL,
	`dtDebEffetEnseigne` 						DATE 			DEFAULT NULL 	COMMENT 'Date de debut d’effet de l’enseigne',
	`dtDebEffetEnseigne_MOD` 					BOOLEAN 		DEFAULT NULL,
	`dtFinEffetEnseigne` 						DATE 			DEFAULT NULL 	COMMENT 'Date de fin d’effet de l’enseigne',
	`dtFinEffetEnseigne_MOD` 					BOOLEAN 		DEFAULT NULL,
	`optDecalagePaie` 							VARCHAR(1) 	DEFAULT NULL 	COMMENT 'Option decalage de paie de l’etablissement (O= OuiøN= Non)',
	`optDecalagePaie_MOD` 						BOOLEAN 		DEFAULT NULL,
	`dtDebDecalagePaie` 						DATE 			DEFAULT NULL 	COMMENT 'Date de debut d’application du decalage de paie',
	`dtDebDecalagePaie_MOD` 					BOOLEAN 		DEFAULT NULL,
	`typeDecalagePaie` 							VARCHAR(1) 	DEFAULT NULL 	COMMENT 'Type de decalage pratique (A = Type AøB = Type B)',
	`typeDecalagePaie_MOD` 						BOOLEAN 		DEFAULT NULL,
	`cdIndEtablissementPrincipal` 				VARCHAR(1) 	DEFAULT NULL 	COMMENT 'Code indicateur d’Etablissement Principal (O= OuiøN= Non)',
	`cdIndEtablissementPrincipal_MOD` 			BOOLEAN 		DEFAULT NULL,
	`irContratEtablissementsList_MOD` 			BOOLEAN 		DEFAULT NULL,
	`idEntreprise` 								VARCHAR(8) 	DEFAULT NULL 		COMMENT 'Identifiant technique de l’Entreprise auquel appartient l’etablissement',
	`irEntreprise_MOD` 							BOOLEAN 		DEFAULT NULL,
	PRIMARY KEY (`idEtablissement`,`REV`),
	CONSTRAINT `fk_etablissement_revision_rev` FOREIGN KEY (`REV`) REFERENCES `Revision` (`id_revision`) ON DELETE NO ACTION ON UPDATE NO ACTION,
	CONSTRAINT `fk_etablissement_revision_revend` FOREIGN KEY (`REVEND`) REFERENCES `Revision` (`id_revision`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `IR_Contrat_Etablissement_revision` (
	`idEtablissement` 		VARCHAR(8) 	NOT NULL,
	`NUC` 					VARCHAR(20) 	NOT NULL,
	`REV` 					BIGINT 			NOT NULL,
	`REVTYPE` 				TINYINT 		DEFAULT NULL,
	`REVEND` 				BIGINT 			DEFAULT NULL,
	`REVEND_TSTMP` 			DATETIME 		DEFAULT NULL,
	`irContrat_MOD` 		BOOLEAN 		DEFAULT NULL,
	`irEtablissement_MOD` 	BOOLEAN 		DEFAULT NULL,
	PRIMARY KEY (`NUC`,`idEtablissement`,`REV`),
	CONSTRAINT `fk_contrat_etablissement_revision_rev` FOREIGN KEY (`REV`) REFERENCES `Revision` (`id_revision`) ON DELETE NO ACTION ON UPDATE NO ACTION,
	CONSTRAINT `fk_contrat_etablissement_revision_revend` FOREIGN KEY (`REVEND`) REFERENCES `Revision` (`id_revision`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `DSN_06_revision` (
	`id_entreprise` 			BIGINT 			NOT NULL,
	`REV` 						BIGINT 			NOT NULL,
	`REVTYPE` 					TINYINT 		NULL,
	`REVEND` 					BIGINT 			NULL,
	`REVEND_TSTMP` 				DATETIME 		NULL,
	`etablissementList_MOD` 	BOOLEAN 		NULL,
	`_001` 						VARCHAR(9) 	NULL,
	`_001_MOD` 					BOOLEAN 		NULL,
	`_002` 						VARCHAR(5) 	NULL,
	`_002_MOD` 					BOOLEAN 		NULL,
	`_003` 						VARCHAR(5) 	NULL,
	`_003_MOD` 					BOOLEAN 		NULL,
	`_004` 						VARCHAR(50) 	NULL,
	`_004_MOD` 					BOOLEAN 		NULL,
	`_005` 						VARCHAR(5) 	NULL,
	`_005_MOD` 					BOOLEAN 		NULL,
	`_006` 						VARCHAR(50) 	NULL,
	`_006_MOD` 					BOOLEAN 		NULL,
	`_007` 						VARCHAR(50) 	NULL,
	`_007_MOD` 					BOOLEAN 		NULL,
	`_008` 						VARCHAR(50) 	NULL,
	`_008_MOD` 					BOOLEAN 		NULL,
--	`_009` 						VARCHAR(7) 	NULL,
--	`_009_MOD` 					BOOLEAN 		NULL,
	PRIMARY KEY (`id_entreprise`,`REV`),
	CONSTRAINT `fk_06_revision_rev` FOREIGN KEY (`REV`) REFERENCES `Revision` (`id_revision`) ON DELETE NO ACTION ON UPDATE NO ACTION,
	CONSTRAINT `fk_06_revision_revend` FOREIGN KEY (`REVEND`) REFERENCES `Revision` (`id_revision`) ON DELETE NO ACTION ON UPDATE NO ACTION
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


CREATE TABLE `DSN_11_revision` (
	`id_etablissement` 	BIGINT 			NOT NULL,
	`REV` 				BIGINT 			NOT NULL,
	`REVTYPE` 			TINYINT 		NULL,
	`REVEND` 			BIGINT 			NULL,
	`REVEND_TSTMP` 		DATETIME 		NULL,
	`id_entreprise` 	BIGINT 			NULL,
	`entreprise_MOD` 	BOOLEAN 		NULL,
	`individuList_MOD` 	BOOLEAN 		NULL,
	`_001` 				VARCHAR(5) 	NULL,
	`_001_MOD` 			BOOLEAN 		NULL,
	`_002` 				VARCHAR(5) 	NULL,
	`_002_MOD` 			BOOLEAN 		NULL,
	`_003` 				VARCHAR(50) 	NULL,
	`_003_MOD` 			BOOLEAN 		NULL,
	`_004` 				VARCHAR(5) 	NULL,
	`_004_MOD` 			BOOLEAN 		NULL,
	`_005` 				VARCHAR(50) 	NULL,
	`_005_MOD` 			BOOLEAN 		NULL,
	`_006` 				VARCHAR(50) 	NULL,
	`_006_MOD` 			BOOLEAN 		NULL,
	`_007` 				VARCHAR(50) 	NULL,
	`_007_MOD` 			BOOLEAN 		NULL,
--	`_008` 				VARCHAR(6) 	NULL,
--	`_008_MOD` 			BOOLEAN 		NULL,
--	`_009` 				VARCHAR(2) 	NULL,
--	`_009_MOD` 			BOOLEAN 		NULL,
	PRIMARY KEY (`id_etablissement`,`REV`),
	CONSTRAINT `fk_11_revision_rev` FOREIGN KEY (`REV`) REFERENCES `Revision` (`id_revision`) ON DELETE NO ACTION ON UPDATE NO ACTION,
	CONSTRAINT `fk_11_revision_revend` FOREIGN KEY (`REVEND`) REFERENCES `Revision` (`id_revision`) ON DELETE NO ACTION ON UPDATE NO ACTION
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


CREATE TABLE `DSN_30_revision` (
	`id_individu` 			BIGINT 			NOT NULL,
	`REV` 					BIGINT 			NOT NULL,
	`REVTYPE` 				TINYINT 		NULL,
	`REVEND` 				BIGINT 			NULL,
	`REVEND_TSTMP` 			DATETIME 		NULL,
	`id_etablissement` 		BIGINT 			NULL,
	`etablissement_MOD` 	BOOLEAN 		NULL,
	`contratList_MOD` 		BOOLEAN 		NULL,
	`_001` 					VARCHAR(13) 	NULL,
	`_001_MOD` 				BOOLEAN 		NULL,
	`_002` 					VARCHAR(80) 	NULL,
	`_002_MOD` 				BOOLEAN 		NULL,
	`_003` 					VARCHAR(80) 	NULL,
	`_003_MOD` 				BOOLEAN 		NULL,
	`_004` 					VARCHAR(80) 	NULL,
	`_004_MOD` 				BOOLEAN 		NULL,
	`_005` 					VARCHAR(2) 	NULL,
	`_005_MOD` 				BOOLEAN 		NULL,
	`_006` 					VARCHAR(8) 	NULL,
	`_006_MOD` 				BOOLEAN 		NULL,
	`_007` 					VARCHAR(30) 	NULL,
	`_007_MOD` 				BOOLEAN 		NULL,
	`_008` 					VARCHAR(50) 	NULL,
	`_008_MOD` 				BOOLEAN 		NULL,
	`_009` 					VARCHAR(5) 	NULL,
	`_009_MOD` 				BOOLEAN 		NULL,
	`_010` 					VARCHAR(50) 	NULL,
	`_010_MOD` 				BOOLEAN 		NULL,
	`_011` 					VARCHAR(2) 	NULL,
	`_011_MOD` 				BOOLEAN 		NULL,
	`_012` 					VARCHAR(50) 	NULL,
	`_012_MOD` 				BOOLEAN 		NULL,
--	`_013` 					VARCHAR(2) 	NULL,
--	`_013_MOD` 				BOOLEAN 		NULL,
	`_014` 					VARCHAR(2) 	NULL,
	`_014_MOD` 				BOOLEAN 		NULL,
	`_015` 					VARCHAR(2) 	NULL,
	`_015_MOD` 				BOOLEAN 		NULL,
	`_016` 					VARCHAR(50) 	NULL,
	`_016_MOD` 				BOOLEAN 		NULL,
	`_017` 					VARCHAR(50) 	NULL,
	`_017_MOD` 				BOOLEAN 		NULL,
	`_018` 					VARCHAR(100) 	NULL,
	`_018_MOD` 				BOOLEAN 		NULL,
	`_019` 					VARCHAR(30) 	NULL,
	`_019_MOD` 				BOOLEAN 		NULL,
	`_020` 					VARCHAR(39) 	NULL,
	`_020_MOD` 				BOOLEAN 		NULL,
	PRIMARY KEY (`id_individu`,`REV`),
	CONSTRAINT `fk_30_revision_rev` FOREIGN KEY (`REV`) REFERENCES `Revision` (`id_revision`) ON DELETE NO ACTION ON UPDATE NO ACTION,
	CONSTRAINT `fk_30_revision_revend` FOREIGN KEY (`REVEND`) REFERENCES `Revision` (`id_revision`) ON DELETE NO ACTION ON UPDATE NO ACTION
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


CREATE TABLE `DSN_40_revision` (
	`id_contrat` 				BIGINT 			NOT NULL,
	`REV` 						BIGINT 			NOT NULL,
	`REVTYPE` 					TINYINT 		NULL,
	`REVEND` 					BIGINT 			NULL,
	`REVEND_TSTMP` 				DATETIME 		NULL,
	`id_individu` 				BIGINT 			NULL,
	`individu_MOD` 				BOOLEAN 		NULL,
	`affiliationPrevList_MOD` 	BOOLEAN 		NULL,
--	`finContrat_MOD` 			BOOLEAN 		NULL,
	`_001` 						VARCHAR(8) 	NULL,
	`_001_MOD` 					BOOLEAN 		NULL,
	`_002` 						VARCHAR(2) 	NULL,
	`_002_MOD` 					BOOLEAN 		NULL,
	`_003` 						VARCHAR(2) 	NULL,
	`_003_MOD` 					BOOLEAN 		NULL,
	`_004` 						VARCHAR(4) 	NULL,
	`_004_MOD` 					BOOLEAN 		NULL,
	`_005` 						VARCHAR(6) 	NULL,
	`_005_MOD` 					BOOLEAN 		NULL,
	`_006` 						VARCHAR(120) 	NULL,
	`_006_MOD` 					BOOLEAN 		NULL,
	`_007` 						VARCHAR(2) 	NULL,
	`_007_MOD` 					BOOLEAN 		NULL,
	`_008` 						VARCHAR(2) 	NULL,
	`_008_MOD` 					BOOLEAN 		NULL,
	`_009` 						VARCHAR(20) 	NULL,
	`_009_MOD` 					BOOLEAN 		NULL,
	`_010` 						VARCHAR(8) 	NULL,
	`_010_MOD` 					BOOLEAN 		NULL,
	`_011` 						VARCHAR(2) 	NULL,
	`_011_MOD` 					BOOLEAN 		NULL,
	`_012` 						VARCHAR(7) 	NULL,
	`_012_MOD` 					BOOLEAN 		NULL,
	`_013` 						VARCHAR(7) 	NULL,
	`_013_MOD` 					BOOLEAN 		NULL,
	`_014` 						VARCHAR(2) 	NULL,
	`_014_MOD` 					BOOLEAN 		NULL,
--	`_015` 						VARCHAR(12) 	NULL,
--	`_015_MOD` 					BOOLEAN 		NULL,
	`_016` 						VARCHAR(2) 	NULL,
	`_016_MOD` 					BOOLEAN 		NULL,
	`_017` 						VARCHAR(4) 	NULL,
	`_017_MOD` 					BOOLEAN 		NULL,
	`_018` 						VARCHAR(3) 	NULL,
	`_018_MOD` 					BOOLEAN 		NULL,
	`_019` 						VARCHAR(14) 	NULL,
	`_019_MOD` 					BOOLEAN 		NULL,
	`_020` 						VARCHAR(3) 	NULL,
--	`_020_MOD` 					BOOLEAN 		NULL,
--	`_021` 						VARCHAR(2) 	NULL,
--	`_021_MOD` 					BOOLEAN 		NULL,
--	`_022` 						VARCHAR(2) 	NULL,
--	`_022_MOD` 					BOOLEAN 		NULL,
--	`_023` 						VARCHAR(6) 	NULL,
--	`_023_MOD` 					BOOLEAN 		NULL,
--	`_024` 						VARCHAR(2) 	NULL,
--	`_024_MOD` 					BOOLEAN 		NULL,
--	`_025` 						VARCHAR(2) 	NULL,
--	`_025_MOD` 					BOOLEAN 		NULL,
	PRIMARY KEY (`id_contrat`,`REV`),
	CONSTRAINT `fk_40_revision_rev` FOREIGN KEY (`REV`) REFERENCES `Revision` (`id_revision`) ON DELETE NO ACTION ON UPDATE NO ACTION,
	CONSTRAINT `fk_40_revision_revend` FOREIGN KEY (`REVEND`) REFERENCES `Revision` (`id_revision`) ON DELETE NO ACTION ON UPDATE NO ACTION
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- CREATE TABLE `DSN_62_revision` (
-- 	`id_finContrat` BIGINT 			NOT NULL,
-- 	`REV` 			BIGINT 			NOT NULL,
-- 	`REVTYPE` 		TINYINT 		NULL,
-- 	`REVEND` 		BIGINT 			NULL,
-- 	`REVEND_TSTMP` 	DATETIME 		NULL,
-- 	`contrat_MOD` 	BOOLEAN 		NULL,
-- 	`_001` 			VARCHAR(8) 	NULL,
-- 	`_001_MOD` 		BOOLEAN 		NULL,
-- 	`_002` 			VARCHAR(3) 	NULL,
-- 	`_002_MOD` 		BOOLEAN 		NULL,
-- --	`_003` 			VARCHAR(8) 	NULL,
-- --	`_003_MOD` 		BOOLEAN 		NULL,
-- --	`_004` 			VARCHAR(8) 	NULL,
-- --	`_004_MOD` 		BOOLEAN 		NULL,
-- --	`_005` 			VARCHAR(8) 	NULL,
-- --	`_005_MOD` 		BOOLEAN 		NULL,
-- --	`_006` 			VARCHAR(8) 	NULL,
-- --	`_006_MOD` 		BOOLEAN 		NULL,
-- --	`_008` 			VARCHAR(2) 	NULL,
-- --	`_008_MOD` 		BOOLEAN 		NULL,
-- 	`_009` 			VARCHAR(2) 	NULL,
-- 	`_009_MOD` 		BOOLEAN 		NULL,
-- --	`_010` 			VARCHAR(6) 	NULL,
-- --	`_010_MOD` 		BOOLEAN 		NULL,
-- --	`_011` 			VARCHAR(4) 	NULL,
-- --	`_011_MOD` 		BOOLEAN 		NULL,
-- --	`_012` 			VARCHAR(12) 	NULL,
-- --	`_012_MOD` 		BOOLEAN 		NULL,
-- --	`_013` 			VARCHAR(12) 	NULL,
-- --	`_013_MOD` 		BOOLEAN 		NULL,
-- --	`_014` 			VARCHAR(2) 	NULL,
-- --	`_014_MOD` 		BOOLEAN 		NULL,
-- 	PRIMARY KEY (`id_finContrat`,`REV`),
-- 	CONSTRAINT `fk_62_revision_rev` FOREIGN KEY (`REV`) REFERENCES `Revision` (`id_revision`) ON DELETE NO ACTION ON UPDATE NO ACTION,
-- 	CONSTRAINT `fk_62_revision_revend` FOREIGN KEY (`REVEND`) REFERENCES `Revision` (`id_revision`) ON DELETE NO ACTION ON UPDATE NO ACTION
-- )
-- ENGINE = InnoDB
-- DEFAULT CHARACTER SET = latin1;


CREATE TABLE `DSN_70_revision` (
	`id_affil` 		BIGINT 			NOT NULL,
	`REV` 			BIGINT 			NOT NULL,
	`REVTYPE` 		TINYINT 		NULL,
	`REVEND` 		BIGINT 			NULL,
	`REVEND_TSTMP` 	DATETIME 		NULL,
	`id_contrat` 	BIGINT 			NULL,
	`contrat_MOD` 	BOOLEAN 		NULL,
	`_001` 			VARCHAR(20) 	NULL,
	`_001_MOD` 		BOOLEAN 		NULL,
	`_002` 			VARCHAR(9) 	NULL,
	`_002_MOD` 		BOOLEAN 		NULL,
	`_003` 			VARCHAR(6) 	NULL,
	`_003_MOD` 		BOOLEAN 		NULL,
	`_004` 			VARCHAR(30) 	NULL,
	`_004_MOD` 		BOOLEAN 		NULL,
	`_005` 			VARCHAR(30) 	NULL,
	`_005_MOD` 		BOOLEAN 		NULL,
	`_006` 			VARCHAR(8) 	NULL,
	`_006_MOD` 		BOOLEAN 		NULL,
	PRIMARY KEY (`id_affil`,`REV`),
	CONSTRAINT `fk_70_revision_rev` FOREIGN KEY (`REV`) REFERENCES `Revision` (`id_revision`) ON DELETE NO ACTION ON UPDATE NO ACTION,
	CONSTRAINT `fk_70_revision_revend` FOREIGN KEY (`REVEND`) REFERENCES `Revision` (`id_revision`) ON DELETE NO ACTION ON UPDATE NO ACTION
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;