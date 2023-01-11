CREATE DATABASE IF NOT EXISTS swepr_test /*!40100 COLLATE 'utf8mb4_unicode_520_ci' */
;

USE swepr_test;

CREATE TABLE IF NOT EXISTS gender (
	pk_unique_id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(255) NOT NULL,
	PRIMARY KEY (pk_unique_id)
)
COLLATE='utf8mb4_unicode_520_ci'
;

INSERT IGNORE INTO gender VALUES
	(1, "Herr"),
	(2, "Frau"),
	(3, "")
;

CREATE TABLE IF NOT EXISTS college_employee (
	pk_unique_id INT NOT NULL AUTO_INCREMENT,
	first_name VARCHAR(255) NOT NULL,
	last_name VARCHAR(255) NOT NULL,
	title VARCHAR(255) NOT NULL,
	fk_gender_pk_unique_id INT NOT NULL,
	email VARCHAR(255) NOT NULL,
	PRIMARY KEY (pk_unique_id),
	CONSTRAINT college_employee_fk_gender_pk_unique_id FOREIGN KEY (fk_gender_pk_unique_id) REFERENCES gender (pk_unique_id) ON UPDATE NO ACTION ON DELETE NO ACTION
)
COLLATE='utf8mb4_unicode_520_ci'
;

INSERT IGNORE INTO college_employee VALUES
	(1, 'Volkhard', 'Pfeiffer', 'Prof.', 1, 'Volkhard.Pfeiffer@hs-coburg.de'),
	(2, 'Dieter', 'Landes', 'Prof. Dr.', 1, 'Dieter.Landes@hs-coburg.de'),
	(3, 'Dieter', 'Wißmann', 'Prof. Dr.', 1, 'Dieter.Wissmann@hs-coburg.de'),
	(4, 'Thomas', 'Wieland', 'Prof. Dr.', 1, 'Thomas.Wieland@hs-coburg.de'),
	(5, 'Quirin', 'Meyer', 'Prof. Dr.', 1, 'Quirin.Meyer@hs-coburg.de'),
	(6, 'Michaela', 'Ihlau', '', 2, 'Michaela.Ihlau@hs-coburg.de'),
	(7, 'Test_User_1_A3_First_Name', 'Test_User_1_A3_Last_Name', '', 1, 'Test_Mail_1@test.com'),
	(8, 'Test_User_2_A3_First_Name', 'Test_User_2_A3_Last_Name', 'Prof.', 2, 'Test_Mail_1@test.com'),
	(9, 'Test_1_A5', 'Test_1_A5', 'Prof.', 1, 'Test_1_A5@test.com'),
	(10, 'Test_PDF_User', 'Test_PDF_User', 'Prof. Dr.', 1, 'Test_PDF_User@mail.com')
;



CREATE TABLE IF NOT EXISTS degree(
	pk_unique_id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(255) NOT NULL,
	PRIMARY KEY (pk_unique_id)
)
COLLATE='utf8mb4_unicode_520_ci'
;

INSERT IGNORE INTO degree VALUES
	(1, "Bachelor"),
	(2, "Master")
;



CREATE TABLE IF NOT EXISTS spo (
	pk_unique_id INT NOT NULL AUTO_INCREMENT,
	link VARCHAR(255) NOT NULL,
	start_date DATE NOT NULL,
	end_date DATE NULL,
	course VARCHAR(255) NOT NULL,
	fk_degree_pk_unique_id INT NOT NULL,
	PRIMARY KEY (pk_unique_id),
	CONSTRAINT spo_fk_degree_pk_unique_id FOREIGN KEY (fk_degree_pk_unique_id) REFERENCES degree (pk_unique_id) ON UPDATE NO ACTION ON DELETE NO ACTION
)
COLLATE='utf8mb4_unicode_520_ci'
;

INSERT IGNORE INTO spo VALUES
	(1, 'https://mycampus.hs-coburg.de/sites/default/files/files/documents/SPO%20B%20IF%204.pdf', '2020-10-01', NULL, 'IF', 1),
	(2, 'https://mycampus.hs-coburg.de/sites/default/files/files/documents/SPO_B_IF_neu.pdf', '2014-10-01', '2020-09-30', 'IF', 1),
	(3, 'https://mycampus.hs-coburg.de/sites/default/files/files/documents/SPO_B_IF_alt.pdf', NULL, '2014-09-30', 'IF', 1),
	(4, 'https://mycampus.hs-coburg.de/sites/default/files/files/documents/SPO%20B%20VC.pdf', '2020-10-01', NULL, 'VC', 1),
	(5, 'https://85.214.225.164/dev/home', '2022-10-01', NULL, 'IF', 1)
;



CREATE TABLE IF NOT EXISTS maternity_protection(
		pk_unique_id INT NOT NULL AUTO_INCREMENT,
		name VARCHAR(255) NOT NULL,
		PRIMARY KEY (pk_unique_id)
)
COLLATE='utf8mb4_unicode_520_ci'
;

INSERT IGNORE INTO maternity_protection VALUES
	(1, "Rot"),
	(2, "Grün"),
	(3, "Gelb")
;

CREATE TABLE IF NOT EXISTS cycle(
		pk_unique_id INT NOT NULL AUTO_INCREMENT,
		name VARCHAR(255) NOT NULL,
		PRIMARY KEY (pk_unique_id)
)
COLLATE='utf8mb4_unicode_520_ci'
;

INSERT IGNORE INTO cycle VALUES
	(1, "Jährlich"),
	(2, "Halbjährlich")
;

CREATE TABLE IF NOT EXISTS duration(
		pk_unique_id INT NOT NULL AUTO_INCREMENT,
		name VARCHAR(255) NOT NULL,
		PRIMARY KEY (pk_unique_id)
)
COLLATE='utf8mb4_unicode_520_ci'
;

INSERT IGNORE INTO duration VALUES
	(1, "Einsemestrig"),
	(2, "Zweisemestrig")
;

CREATE TABLE IF NOT EXISTS language(
		pk_unique_id INT NOT NULL AUTO_INCREMENT,
		name VARCHAR(255) NOT NULL,
		PRIMARY KEY (pk_unique_id)
)
COLLATE='utf8mb4_unicode_520_ci'
;

INSERT IGNORE INTO language VALUES
	(1, "Deutsch"),
	(2, "Englisch"),
	(3, "Französisch"),
	(4, "Italienisch"),
	(5, "Spanisch"),
	(6, "Russisch"),
	(7, "Chinesisch")
;



CREATE TABLE IF NOT EXISTS module (
	pk_unique_id INT NOT NULL AUTO_INCREMENT,
	fk_college_employee_pk_unique_id INT NOT NULL,
	module_name VARCHAR(255) NOT NULL,
	abbreviation VARCHAR(255) NULL,
	fk_cycle_pk_unique_id INT NOT NULL,
	fk_duration_pk_unique_id INT NOT NULL,
	fk_language_pk_unique_id INT NULL,
	course_usage TEXT NULL,
	knowledge_requirements TEXT NULL,
	skills TEXT NULL,
	content TEXT NULL,
	exam_type VARCHAR(255) NOT NULL,
	certificates VARCHAR(255) NULL,
	media_type TEXT NULL,
	literature TEXT NULL,
	fk_maternity_protection_pk_unique_id INT NOT NULL,
	PRIMARY KEY (pk_unique_id),
	CONSTRAINT module_fk_college_employee_pk_unique_id FOREIGN KEY (fk_college_employee_pk_unique_id) REFERENCES college_employee (pk_unique_id) ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT module_fk_cycle_pk_unique_id FOREIGN KEY (fk_cycle_pk_unique_id) REFERENCES cycle (pk_unique_id) ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT module_fk_duration_pk_unique_id FOREIGN KEY (fk_duration_pk_unique_id) REFERENCES duration (pk_unique_id) ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT module_fk_language_pk_unique_id FOREIGN KEY (fk_language_pk_unique_id) REFERENCES language (pk_unique_id) ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT module_fk_maternity_protection_pk_unique_id FOREIGN KEY (fk_maternity_protection_pk_unique_id) REFERENCES maternity_protection (pk_unique_id) ON UPDATE NO ACTION ON DELETE NO ACTION
)
COLLATE='utf8mb4_unicode_520_ci'
;

INSERT IGNORE INTO module VALUES
	(1, 1, 'Programmieren 1', 'Prog1', 1, 1, 1, 
	'<p>Betriebswirtschaft - Schwerpunkt Wirtschaftsinformatik, Bachelor Visual Computing</p>',
	NULL,
	'<p><span style=\'color: rgb(0, 0, 0);\'>Fachlich-methodische Kompetenzen:</span></p><p><span style=\'color: rgb(0, 0, 0);\'>Studierende sollen </span></p><ul><li><span style=\'color: rgb(0, 0, 0);\'>die zentralen Konzepte von Programmiersprachen (z.B. Variablen, Prozeduren, Kontrollstrukturen, Zeiger) kennen, verstehen und auf Problemstellungen anwenden können</span></li><li><span style=\'color: rgb(0, 0, 0);\'>die Grundlagen der objektorientierten Programmierung kennen, verstehen und auf Problemstellungen anwenden können</span></li></ul><p><br></p>',
	'<ul><li><span style=\'color: rgb(0, 0, 0);\'>Einführung </span></li><li><span style=\'color: rgb(0, 0, 0);\'>Datentypen und Ausdrücke </span></li><li><span style=\'color: rgb(0, 0, 0);\'>Kontrollstrukturen </span></li><li><span style=\'color: rgb(0, 0, 0);\'>Arrays und Zeiger </span></li><li><span style=\'color: rgb(0, 0, 0);\'>Prozedurale Programmierung </span></li><li><span style=\'color: rgb(0, 0, 0);\'>Rekursion</span></li><li><span style=\'color: rgb(0, 0, 0);\'>Objektorientierte Programmierung – Teil 1</span></li><li><span style=\'color: rgb(0, 0, 0);\'>Strings&nbsp;</span></li></ul><p><br></p>',
	'Schriftliche Prüfung (90 min) als computergestützte Präsenzprüfung',
	NULL,
	'Beamer, Tafel, Overheadprojektor, E-Learning Medien',
	'<p><span style=\'color: rgb(0, 0, 0);\'>Ullenboom, Christian </span><a href=\'http://openbook.galileocomputing.de/javainsel/\' target=\'_blank\' style=\'color: rgb(0, 0, 0);\'>\'Java ist auch eine Insel\'</a><span style=\'color: rgb(0, 0, 0);\'> Galileo Computing jeweils in der neusten Auflage</span></p><p><span style=\'color: rgb(0, 0, 0);\'>Krüger, Guido </span><a href=\'http://www.javabuch.de/\' target=\'_blank\' style=\'color: rgb(0, 0, 0);\'>\'Handbuch der Java Programmierung\'</a><span style=\'color: rgb(0, 0, 0);\'> Addison Wesley&nbsp;jeweils in der neusten Auflage&nbsp;</span></p><p><span style=\'color: rgb(0, 0, 0);\'>Kathy, Sierra; Bates, Bert; „Java von Kopf bis Fuß“ O‘Reilly jeweils in der neusten Auflage</span></p><p><span style=\'color: rgb(0, 0, 0);\'>Schiedermeier R. \'Programmieren mit Java\' Pearson Studium jeweils in der neusten Auflage&nbsp;</span></p>',
	2
	),

	(2, 5, 'Rechnerarchitekturen', 'Ra', 1, 1, 1, 
	NULL,
	NULL,
	'<p><span style=\'color: rgb(0, 0, 0);\'>DUMMY_TEXT: Fachlich-methodische Kompetenzen:</span></p><p><span style=\'color: rgb(0, 0, 0);\'>Studierende sollen </span></p><ul><li><span style=\'color: rgb(0, 0, 0);\'>die zentralen Konzepte von Programmiersprachen (z.B. Variablen, Prozeduren, Kontrollstrukturen, Zeiger) kennen, verstehen und auf Problemstellungen anwenden können</span></li><li><span style=\'color: rgb(0, 0, 0);\'>die Grundlagen der objektorientierten Programmierung kennen, verstehen und auf Problemstellungen anwenden können</span></li></ul><p><br></p>',
	'<ul><li><span style=\'color: rgb(0, 0, 0);\'>DUMMY_TEXT: Einführung </span></li><li><span style=\'color: rgb(0, 0, 0);\'>Datentypen und Ausdrücke </span></li><li><span style=\'color: rgb(0, 0, 0);\'>Kontrollstrukturen </span></li><li><span style=\'color: rgb(0, 0, 0);\'>Arrays und Zeiger </span></li><li><span style=\'color: rgb(0, 0, 0);\'>Prozedurale Programmierung </span></li><li><span style=\'color: rgb(0, 0, 0);\'>Rekursion</span></li><li><span style=\'color: rgb(0, 0, 0);\'>Objektorientierte Programmierung – Teil 1</span></li><li><span style=\'color: rgb(0, 0, 0);\'>Strings&nbsp;</span></li></ul><p><br></p>',
	'Schriftliche Prüfung (90 min)',
	NULL,
	'Tafel, Beamer',
	'<p><span style=\'color: rgb(0, 0, 0);\'>Ullenboom, Christian </span><a href=\'http://openbook.galileocomputing.de/javainsel/\' target=\'_blank\' style=\'color: rgb(0, 0, 0);\'>\'Java ist auch eine Insel\'</a><span style=\'color: rgb(0, 0, 0);\'> Galileo Computing jeweils in der neusten Auflage</span></p><p><span style=\'color: rgb(0, 0, 0);\'>Krüger, Guido </span><a href=\'http://www.javabuch.de/\' target=\'_blank\' style=\'color: rgb(0, 0, 0);\'>\'Handbuch der Java Programmierung\'</a><span style=\'color: rgb(0, 0, 0);\'> Addison Wesley&nbsp;jeweils in der neusten Auflage&nbsp;</span></p><p><span style=\'color: rgb(0, 0, 0);\'>Kathy, Sierra; Bates, Bert; „Java von Kopf bis Fuß“ O‘Reilly jeweils in der neusten Auflage</span></p><p><span style=\'color: rgb(0, 0, 0);\'>Schiedermeier R. \'Programmieren mit Java\' Pearson Studium jeweils in der neusten Auflage&nbsp;</span></p>',
	2
	),

	(3, 10, 'Test_1_A5', 'T1A5', 1, 1, 1, NULL, NULL, 
	'Test_1_A5', 'Test_1_A5', 'Test_1_A5', NULL, 'Test_1_A5', 'Test_1_A5', 2),
	(4, 10, 'Test_Module_PDF_1', 'TMP1', 1, 1, 1, NULL, NULL, 
	'Test_Module_PDF_1', 'Test_Module_PDF_1', 'Test_Module_PDF_1', NULL, 'Test_Module_PDF_1', 'Test_Module_PDF_1', 2),
	(5, 10, 'Test_Module_PDF_2', 'TMP2', 1, 1, 1, NULL, NULL, 
	'Test_Module_PDF_2', 'Test_Module_PDF_2', 'Test_Module_PDF_2', NULL, 'Test_Module_PDF_2', 'Test_Module_PDF_2', 2),
	(6, 10, 'Test_Module_PDF_3', 'TMP3', 1, 1, 1, NULL, NULL, 
	'Test_Module_PDF_3', 'Test_Module_PDF_3', 'swepr_testswepr_testTest_Module_PDF_3', NULL, 'Test_Module_PDF_3', 'Test_Module_PDF_3', 2),
	(7, 10, 'Systemtest_A6', 'SA6', 1, 1, 1, 'Systemtest_A6', 'Systemtest_A6', '<p>Systemtest_A6</p>', '<p>Systemtest_A6</p>', 'Systemtest_A6', 'Systemtest_A6', 'Systemtest_A6', '<p>Systemtest_A6</p>', 2)
;



CREATE TABLE IF NOT EXISTS prof (
	pk_unique_id INT NOT NULL AUTO_INCREMENT,
	fk_college_employee_pk_unique_id INT NOT NULL,
	fk_module_pk_unique_id INT NOT NULL,
	PRIMARY KEY (pk_unique_id),
	CONSTRAINT prof_fk_college_employee_pk_unique_id FOREIGN KEY (fk_college_employee_pk_unique_id) REFERENCES college_employee (pk_unique_id) ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT prof_fk_module_pk_unique_id FOREIGN KEY (fk_module_pk_unique_id) REFERENCES module (pk_unique_id) ON UPDATE NO ACTION ON DELETE NO ACTION
)
COLLATE='utf8mb4_unicode_520_ci'
;

INSERT IGNORE INTO prof VALUES
	(1, 1, 1),
	(2, 2, 5),
	(3, 10, 4),
	(4, 10, 5),
	(5, 10, 6)
;



CREATE TABLE IF NOT EXISTS module_manual (
	pk_unique_id INT NOT NULL AUTO_INCREMENT,
	fk_spo_pk_unique_id INT NOT NULL,
	semester VARCHAR(255) NULL,
	first_page VARCHAR(255) NULL,
	preliminary_note VARCHAR(255) NULL,
	generated_pdf VARCHAR(255) NULL,
	module_plan VARCHAR(255) NULL,
	fk_section_pk_unique_id INT NULL,
	fk_type_pk_unique_id INT NULL,
	PRIMARY KEY (pk_unique_id),
	CONSTRAINT module_manual_fk_spo_pk_unique_id FOREIGN KEY (fk_spo_pk_unique_id) REFERENCES spo (pk_unique_id) ON UPDATE NO ACTION ON DELETE NO ACTION
)
COLLATE='utf8mb4_unicode_520_ci'
;

INSERT IGNORE INTO module_manual VALUES
	(1, 1, "Sommersemester 23", NULL, NULL, NULL, NULL, NULL, NULL),
	(2, 2, "Wintersemester 22/23", NULL, NULL, NULL, NULL, NULL, NULL),
	(3, 3, "Wintersemester 22/23", NULL, NULL, NULL, NULL, NULL, NULL),
	(4, 5, "Wintersemester 08/09", NULL, NULL, NULL, NULL, NULL, NULL),
	(5, 5, "Sommersemester 01", NULL, NULL, NULL, NULL, NULL, NULL)
;



CREATE TABLE IF NOT EXISTS admission_requirement(
		pk_unique_id INT NOT NULL AUTO_INCREMENT,
		fk_module_manual_pk_unique_id INT NOT NULL,
		name VARCHAR(255) NOT NULL,
		PRIMARY KEY (pk_unique_id),
		CONSTRAINT admission_requirement_fk_module_manual_pk_unique_id FOREIGN KEY (fk_module_manual_pk_unique_id) REFERENCES module_manual (pk_unique_id) ON UPDATE NO ACTION ON DELETE NO ACTION
)
COLLATE='utf8mb4_unicode_520_ci'
;

CREATE TABLE IF NOT EXISTS section(
		pk_unique_id INT NOT NULL AUTO_INCREMENT,
		fk_section_pk_unique_id INT NULL,
		fk_module_manual_pk_unique_id INT NOT NULL,
		name VARCHAR(255) NOT NULL,
		PRIMARY KEY (pk_unique_id),
		CONSTRAINT section_fk_section_pk_unique_id FOREIGN KEY (fk_section_pk_unique_id) REFERENCES section (pk_unique_id) ON UPDATE NO ACTION ON DELETE NO ACTION,
		CONSTRAINT section_fk_module_manual_pk_unique_id FOREIGN KEY (fk_module_manual_pk_unique_id) REFERENCES module_manual (pk_unique_id) ON UPDATE NO ACTION ON DELETE NO ACTION
)
COLLATE='utf8mb4_unicode_520_ci'
;

INSERT IGNORE INTO section VALUES
	(2, NULL, 5, '2. Studienabschnitt'),
	(1, 2, 5, '1. Studienabschnitt'),
	(4, NULL, 1, '2. Studienabschnitt'),
	(3, 4, 1, '1. Studienabschnitt'),
	(6, NULL, 2, '2. Studienabschnitt'),
	(5, 6, 2, '1. Studienabschnitt'),
	(8, NULL, 3, '2. Studienabschnitt'),
	(7, 8, 3, '1. Studienabschnitt'),
	(10, NULL, 4, '2. Studienabschnitt'),
	(9, 10, 4, '1. Studienabschnitt'),
	(12, NULL, 6, '2. Studienabschnitt'),
	(11, 12, 6, '1. Studienabschnitt'),
	(14, NULL, 5, '2. Studienabschnitt'),
	(13, 14, 5, '1. Studienabschnitt'),
	(16, NULL, 1, '2. Studienabschnitt'),
	(15, 16, 1, '1. Studienabschnitt')
;

CREATE TABLE IF NOT EXISTS type(
		pk_unique_id INT NOT NULL AUTO_INCREMENT,
		fk_type_pk_unique_id INT NULL,
		fk_module_manual_pk_unique_id INT NOT NULL,
		name VARCHAR(255) NOT NULL,
		PRIMARY KEY (pk_unique_id),
		CONSTRAINT type_fk_type_pk_unique_id FOREIGN KEY (fk_type_pk_unique_id) REFERENCES type (pk_unique_id) ON UPDATE NO ACTION ON DELETE NO ACTION,
		CONSTRAINT type_fk_module_manual_pk_unique_id FOREIGN KEY (fk_module_manual_pk_unique_id) REFERENCES module_manual (pk_unique_id) ON UPDATE NO ACTION ON DELETE NO ACTION
)
COLLATE='utf8mb4_unicode_520_ci'
;

INSERT IGNORE INTO type VALUES
	(2, NULL, 5, 'Wahlpflichtfach'),
	(1, 2, 5, 'Pflichtfach'),
	(4, NULL, 1, 'Praktischer Studienabschnitt'),
	(3, 4, 1, 'Pflichtfach'),
	(17, 3, 1, 'Wahlpflichtfach'),
	(6, NULL, 2, 'Wahlpflichtfach'),
	(5, 6, 2, 'Pflichtfach'),
	(8, NULL, 3, 'Wahlpflichtfach'),
	(7, 8, 3, 'Pflichtfach'),
	(10, NULL, 4, 'Wahlpflichtfach'),
	(9, 10, 4, 'Pflichtfach'),
	(12, NULL, 6, 'Wahlpflichtfach'),
	(11, 12, 6, 'Pflichtfach'),
	(14, NULL, 5, 'Wahlpflichtfach'),
	(13, 14, 5, 'Pflichtfach'),
	(16, NULL, 1, 'Wahlpflichtfach'),
	(15, 16, 1, 'Pflichtfach')
;



CREATE TABLE IF NOT EXISTS module_manual_archive (
	pk_unique_id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(255) NOT NULL,
	generated_pdf VARCHAR(255) NULL,
	PRIMARY KEY (pk_unique_id)
)
COLLATE='utf8mb4_unicode_520_ci'
;



CREATE TABLE IF NOT EXISTS additional_data (
	pk_unique_id INT NOT NULL AUTO_INCREMENT,
	path VARCHAR(255) NOT NULL,
	PRIMARY KEY (pk_unique_id)
)
COLLATE='utf8mb4_unicode_520_ci'
;



CREATE TABLE IF NOT EXISTS module_manual_has_module (
	pk_unique_id INT NOT NULL AUTO_INCREMENT,
	fk_module_manual_pk_unique_id INT NOT NULL,
	fk_module_pk_unique_id INT NOT NULL,
	semester INT NOT NULL,
	fk_section_pk_unique_id INT NULL,
	fk_type_pk_unique_id INT NULL,
	sws INT NULL,
	ects INT NOT NULL,
	workload TEXT NULL,
	fk_admission_requirement_pk_unique_id INT NULL,
	PRIMARY KEY (pk_unique_id),
	CONSTRAINT module_manual_has_module_fk_module_pk_unique_id FOREIGN KEY (fk_module_pk_unique_id) REFERENCES module (pk_unique_id) ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT module_manual_has_module_fk_module_manual_pk_unique_id FOREIGN KEY (fk_module_manual_pk_unique_id) REFERENCES spo (pk_unique_id) ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT module_manual_has_module_fk_section_pk_unique_id FOREIGN KEY (fk_section_pk_unique_id) REFERENCES section (pk_unique_id) ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT module_manual_has_module_fk_type_pk_unique_id FOREIGN KEY (fk_type_pk_unique_id) REFERENCES type (pk_unique_id) ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT module_manual_has_module_fk_admission_requirement_pk_unique_id FOREIGN KEY (fk_admission_requirement_pk_unique_id) REFERENCES admission_requirement (pk_unique_id) ON UPDATE NO ACTION ON DELETE NO ACTION
)
COLLATE='utf8mb4_unicode_520_ci'
;

INSERT IGNORE INTO module_manual_has_module VALUES
	(1, 1, 1, 1, 3, 4, 5, 2, '150h', NULL),
	(2, 1, 2, 1, 3, 17, 7, 3, '150h', NULL),
	(3, 1, 3, 1, 4, 4, 2, 1, '150h', NULL),
	(4, 5, 4, 1, 1, 1, 5, 2, '150h', NULL),
	(5, 5, 5, 2, 1, 2, 5, 2, '150h', NULL),
	(6, 5, 6, 3, 2, 1, 5, 2, '150h', NULL)
;


CREATE TABLE IF NOT EXISTS easteregg (
	pk_unique_id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(255) NULL,
	PRIMARY KEY (pk_unique_id)
)
;
INSERT IGNORE INTO easteregg VALUES
	(1, 'Severin Dippold'),
	(2, 'Guido Gebhard'),
	(3, 'Lennart Köpper'),
	(4, 'Felix Ebert'),
	(5, 'Simon Vetter'),
	(6, 'Quynh Anh Giang'),
	(7, 'Christoph Euskirchen')
;


INSERT IGNORE INTO admission_requirement VALUES
	(1, 1, 'TestAdmissionRequirement'),
	(2, 5, 'Test')
;



ALTER TABLE module_manual ADD CONSTRAINT module_manual_fk_section_pk_unique_id FOREIGN KEY (fk_section_pk_unique_id) REFERENCES section (pk_unique_id) ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE module_manual ADD CONSTRAINT module_manual_fk_type_pk_unique_id FOREIGN KEY (fk_type_pk_unique_id) REFERENCES type (pk_unique_id) ON UPDATE NO ACTION ON DELETE NO ACTION;

UPDATE module_manual SET fk_section_pk_unique_id = 1, fk_type_pk_unique_id = 1 WHERE pk_unique_id = 5;
UPDATE module_manual SET fk_section_pk_unique_id = 3, fk_type_pk_unique_id = 3 WHERE pk_unique_id = 1;
