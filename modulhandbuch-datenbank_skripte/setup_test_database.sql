CREATE DATABASE IF NOT EXISTS swepr_test /*!40100 COLLATE 'utf8mb4_unicode_520_ci' */
;

USE swepr_test;


CREATE TABLE IF NOT EXISTS college_employee (
	pk_unique_id INT NOT NULL AUTO_INCREMENT,
	first_name VARCHAR(255) NOT NULL,
	last_name VARCHAR(255) NOT NULL,
	title VARCHAR(255) NULL,
	gender ENUM('M','F','D') NULL,
	PRIMARY KEY (pk_unique_id)
)
COMMENT='This table holds all required employees of Hochschule Coburg. Both profs and ModuleOwners.\r\nTitle are the academic titles of that employee, like Pro. Dr. habil., etc.\r\nGender is an ENUM with (\'m\', \'f\', \'d\') for male, female and diverse.'
COLLATE='utf8mb4_unicode_520_ci'
;

INSERT IGNORE INTO college_employee VALUES
	(1, 'Volkhard', 'Pfeiffer', 'Prof.', 'M'),
	(2, 'Dieter', 'Landes', 'Prof. Dr.', 'M'),
	(3, 'Dieter', 'Wißmann', 'Prof. Dr.', 'M'),
	(4, 'Thomas', 'Wieland', 'Prof. Dr.', 'M'),
	(5, 'Quirin', 'Meyer', 'Prof. Dr.', 'M'),
	(6, 'Michaela', 'Ihlau', NULL, 'F')
;



CREATE TABLE IF NOT EXISTS spo (
	pk_unique_id INT NOT NULL AUTO_INCREMENT,
	link VARCHAR(255) NOT NULL,
	start_date DATETIME NULL,
	end_date DATETIME NULL,
	PRIMARY KEY (pk_unique_id)
)
COMMENT='SPO is a table that references SPOs.\r\nLink contains an URL to the SPO on myCampus.\r\nStartDate and EndDate is the time frame in which this SPO is valid.'
COLLATE='utf8mb4_unicode_520_ci'
;

INSERT IGNORE INTO spo VALUES
	(1, 'https://mycampus.hs-coburg.de/sites/default/files/files/documents/SPO%20B%20IF%204.pdf', '2020-10-01', NULL),
	(2, 'https://mycampus.hs-coburg.de/sites/default/files/files/documents/SPO_B_IF_neu.pdf', '2014-10-01', '2020-09-30'),
	(3, 'https://mycampus.hs-coburg.de/sites/default/files/files/documents/SPO_B_IF_alt.pdf', NULL, '2014-09-30')
;



CREATE TABLE IF NOT EXISTS module (
	pk_unique_id INT NOT NULL AUTO_INCREMENT,
	fk_college_employee_pk_unique_id INT NOT NULL,
	module_name VARCHAR(255) NOT NULL,
	abbreviation VARCHAR(255) NOT NULL,
	cycle ENUM('Jährlich','Halbjährlich') NOT NULL DEFAULT 'Jährlich',
	duration ENUM('Einsemestrig') NOT NULL DEFAULT 'Einsemestrig',
	language ENUM('Deutsch','Englisch','Französisch','Spanisch','Chinesisch','Russisch') NOT NULL DEFAULT 'Deutsch',
	course_usage BLOB NULL,
	admission_requirements BLOB NULL,
	knowledge_requirements BLOB NULL,
	skills BLOB NULL,
	content BLOB NULL,
	exam_type VARCHAR(255) NOT NULL,
	certificates VARCHAR(255) NULL,
	media_type BLOB NULL,
	literature BLOB NULL,
	maternity_protection ENUM('R','G','Y') NULL DEFAULT 'G',
	PRIMARY KEY (pk_unique_id),
	CONSTRAINT module_fk_college_employee_pk_unique_id FOREIGN KEY (fk_college_employee_pk_unique_id) REFERENCES college_employee (pk_unique_id) ON UPDATE NO ACTION ON DELETE NO ACTION
)
COLLATE='utf8mb4_unicode_520_ci'
;

INSERT IGNORE INTO module VALUES
	(1, 1, 'Programmieren 1', 'Prog1', 
	'Jährlich', 
	'Einsemestrig', 
	'Deutsch', 
	'Betriebswirtschaft – Schwerpunkt Wirtschaftsinformatik
	Bachelor Visual Computing',
	NULL,
	NULL,
	'Fachlich-methodische Kompetenzen:
	Studierende sollen
	• die zentralen Konzepte von Programmiersprachen (z.B. Variablen, Prozeduren, Kontrollstrukturen, Zeiger) kennen, verstehen und auf Problemstellungen anwenden können
	• die Grundlagen der objektorientierten Programmierung kennen, verstehen und auf Problemstellungen anwenden können',
	'• Einführung
	• Datentypen und Ausdrücke
	• Kontrollstrukturen
	• Arrays und Zeiger
	• Prozedurale Programmierung
	• Rekursion
	• Objektorientierte Programmierung – Teil 1
	• Strings
	• Exception Handling',
	'Schriftliche Prüfung (90 min) als computergestützte Präsenzprüfung',
	NULL,
	'Beamer, Tafel, Overheadprojektor, E-Learning Medien',
	'Ullenboom, Christian "Java ist auch eine Insel" Galileo
	Computing jeweils in der neusten Auflage
	Krüger, Guido "Handbuch der Java Programmierung"
	Addison Wesley jeweils in der neusten Auflage
	Kathy, Sierra; Bates, Bert; „Java von Kopf bis Fuß“ O‘Reilly
	jeweils in der neusten Auflage
	Schiedermeier R. "Programmieren mit Java" Pearson
	Studium jeweils in der neusten Auflage',
	'G'
	),
	
	(2, 5, 'Rechnerarchitekturen', 'Ra',
	'Jährlich', 
	'Einsemestrig', 
	'Deutsch', 
	NULL,
	NULL,
	NULL,
	'1. Studierende sollen die Fähigkeit erlangen, Rechnerkonzepte in Hard- und Software als Gesamtsystem zu durchdringen.
	2. Durch Verständnis von logischen Funktionen sowie von elektronischen Verknüpfungsgliedern können Studierende Funktionen in digitale Hardware umzusetzen.
	3. Studierende verstehen Codierungen, Zahlendarstellungen und grundlegende Algorithmen der Datenverarbeitung und können angepasste Rechensysteme entwerfen.',
	'Axiome der Boolschen Algebra, Normalformen (DNF, KNF)
	Minimierung von kombinatorischen Schaltungen mit
	Karnaugh-Veitch, Quine McCluskey
	Digitale Hardware: MOS-Transistor, Grundgatter der
	Digitaltechnik, Komplexgatter
	Stand 27.09.2022 – gültig für WiSe 2022/23 – SPO IF ab 2014 - Änderungen vorbehalten! 19
	Standardschaltnetze: Codierer, Decodierer, Multiplexer,
	Komparatoren, Addierer, Carry Look Ahead
	Zahlendarstellung und Arithmetikfunktionen:
	Zweierkomplement, Festkomma, Gleitkomma
	Bitspeicher, Register: SR-Flipflop, D-Latch, D-Flipflop
	Halbleiterspeicher: DRAM, SRAM, PROM, Flash
	Architektur von Prozessorsystemen: CPU: ALU,
	Registerbank, Steuerwerk, Funktionsregister
	Ablaufsteuerung mit Befehlsverarbeitung, Interrupt, Stack,
	Pipelining',
	'Schriftliche Prüfung (90 min)',
	NULL,
	'Tafel, Beamer',
	'Becker, Drechsler, Molitor: Technische Informatik (Pearson Studium)
	Tanenbaum / Goodman: Computerarchitektur (Pearson Studium)
	Obermann / Schelp: Rechneraufbau und Rechnerstrukturen (Oldenbourg)
	Floyd: Digital Fundamentals (Prentice Hall)',
	'G'
	)
;



CREATE TABLE IF NOT EXISTS prof (
	pk_college_employee_pk_unique_id INT NOT NULL,
	pk_module_pk_unique_id INT NOT NULL,
	PRIMARY KEY (pk_college_employee_pk_unique_id, pk_module_pk_unique_id),
	CONSTRAINT prof_fk_college_employee_pk_unique_id FOREIGN KEY (pk_college_employee_pk_unique_id) REFERENCES college_employee (pk_unique_id) ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT prof_fk_module_pk_unique_id FOREIGN KEY (pk_module_pk_unique_id) REFERENCES module (pk_unique_id) ON UPDATE NO ACTION ON DELETE NO ACTION
)
COLLATE='utf8mb4_unicode_520_ci'
;

INSERT IGNORE INTO prof VALUES
	(1, 1)
;



CREATE TABLE IF NOT EXISTS module_has_spo (
	pk_module_pk_unique_id INT NOT NULL,
	pk_spo_pk_unique_id INT NOT NULL,
	pk_semester INT NOT NULL,
	category ENUM('Pflichtfach','Wahlfach','Schlüsselqualifikation') NULL DEFAULT NULL,
	PRIMARY KEY (pk_module_pk_unique_id, pk_spo_pk_unique_id, pk_semester),
	CONSTRAINT module_has_spo_fk_module_pk_unique_id FOREIGN KEY (pk_module_pk_unique_id) REFERENCES module (pk_unique_id) ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT module_has_spo_fk_spo_pk_unique_id FOREIGN KEY (pk_spo_pk_unique_id) REFERENCES spo (pk_unique_id) ON UPDATE NO ACTION ON DELETE NO ACTION
)
COLLATE='utf8mb4_unicode_520_ci'
;

INSERT IGNORE INTO module_has_spo VALUES
	(1, 1, 1, 'Pflichtfach'),
	(1, 2, 1, 'Pflichtfach'),
	(1, 3, 1, 'Pflichtfach')
;
