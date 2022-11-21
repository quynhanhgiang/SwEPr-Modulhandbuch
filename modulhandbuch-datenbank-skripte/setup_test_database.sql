CREATE DATABASE IF NOT EXISTS swepr_test /*!40100 COLLATE 'utf8mb4_unicode_520_ci' */
;

USE swepr_test;


CREATE TABLE IF NOT EXISTS college_employee (
	pk_unique_id INT NOT NULL AUTO_INCREMENT,
	first_name VARCHAR(255) NOT NULL,
	last_name VARCHAR(255) NOT NULL,
	title VARCHAR(255) NULL,
	gender ENUM('Herr','Frau', '') NULL,
	email VARCHAR(255) NOT NULL,
	PRIMARY KEY (pk_unique_id)
)
COMMENT='This table holds all required employees of Hochschule Coburg. Both profs and ModuleOwners.\r\nTitle are the academic titles of that employee, like Pro. Dr. habil., etc.\r\nGender is an ENUM with (\'m\', \'f\', \'d\') for male, female and diverse.'
COLLATE='utf8mb4_unicode_520_ci'
;

INSERT IGNORE INTO college_employee VALUES
	(1, 'Volkhard', 'Pfeiffer', 'Prof.', 'Herr', 'Volkhard.Pfeiffer@hs-coburg.de'),
	(2, 'Dieter', 'Landes', 'Prof. Dr.', 'Herr', 'Dieter.Landes@hs-coburg.de'),
	(3, 'Dieter', 'Wißmann', 'Prof. Dr.', 'Herr', 'Dieter.Wissmann@hs-coburg.de'),
	(4, 'Thomas', 'Wieland', 'Prof. Dr.', 'Herr', 'Thomas.Wieland@hs-coburg.de'),
	(5, 'Quirin', 'Meyer', 'Prof. Dr.', 'Herr', 'Quirin.Meyer@hs-coburg.de'),
	(6, 'Michaela', 'Ihlau', NULL, 'Frau', 'Michaela.Ihlau@hs-coburg.de'),
	(7, 'Missing', 'NO', NULL, 'TEST', 'example@mail.com')
;



CREATE TABLE IF NOT EXISTS spo (
	pk_unique_id INT NOT NULL AUTO_INCREMENT,
	link VARCHAR(255) NOT NULL,
	start_date DATE NOT NULL,
	end_date DATE NULL,
	course VARCHAR(255) NOT NULL,
	degree ENUM('Bachelor', 'Master') NOT NULL DEFAULT 'Bachelor',
	module_plan BLOB NULL,
	PRIMARY KEY (pk_unique_id)
)
COMMENT='SPO is a table that references SPOs.\r\nLink contains an URL to the SPO on myCampus.\r\nStartDate and EndDate is the time frame in which this SPO is valid.'
COLLATE='utf8mb4_unicode_520_ci'
;

INSERT IGNORE INTO spo VALUES
	(1, 'https://mycampus.hs-coburg.de/sites/default/files/files/documents/SPO%20B%20IF%204.pdf', '2020-10-01', NULL, 'IF', 'Bachelor', NULL),
	(2, 'https://mycampus.hs-coburg.de/sites/default/files/files/documents/SPO_B_IF_neu.pdf', '2014-10-01', '2020-09-30', 'IF', 'Bachelor', NULL),
	(3, 'https://mycampus.hs-coburg.de/sites/default/files/files/documents/SPO_B_IF_alt.pdf', NULL, '2014-09-30', 'IF', 'Bachelor', NULL),
	(4, 'https://mycampus.hs-coburg.de/sites/default/files/files/documents/SPO%20B%20VC.pdf', '2020-10-01', NULL, 'VC', 'Bachelor', NULL)
;



CREATE TABLE IF NOT EXISTS module (
	pk_unique_id INT NOT NULL AUTO_INCREMENT,
	fk_college_employee_pk_unique_id INT NOT NULL,
	module_name VARCHAR(255) NOT NULL,
	abbreviation VARCHAR(255) NULL,
	cycle ENUM('Jährlich','Halbjährlich') NOT NULL DEFAULT 'Jährlich',
	duration ENUM('Einsemestrig') NOT NULL DEFAULT 'Einsemestrig',
	language ENUM('Deutsch','Englisch','Französisch','Spanisch','Italienisch','Chinesisch','Russisch') NULL DEFAULT 'Deutsch',
	course_usage TEXT NULL,
	knowledge_requirements TEXT NULL,
	skills TEXT NULL,
	content TEXT NULL,
	exam_type VARCHAR(255) NOT NULL,
	certificates VARCHAR(255) NULL,
	media_type TEXT NULL,
	literature TEXT NULL,
	maternity_protection ENUM('Rot','Grün','Gelb') NOT NULL DEFAULT 'Grün',
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
	'<p>Betriebswirtschaft - Schwerpunkt Wirtschaftsinformatik, Bachelor Visual Computing</p>',
	NULL,
	'<p><span style=\'color: rgb(0, 0, 0);\'>Fachlich-methodische Kompetenzen:</span></p><p><span style=\'color: rgb(0, 0, 0);\'>Studierende sollen </span></p><ul><li><span style=\'color: rgb(0, 0, 0);\'>die zentralen Konzepte von Programmiersprachen (z.B. Variablen, Prozeduren, Kontrollstrukturen, Zeiger) kennen, verstehen und auf Problemstellungen anwenden können</span></li><li><span style=\'color: rgb(0, 0, 0);\'>die Grundlagen der objektorientierten Programmierung kennen, verstehen und auf Problemstellungen anwenden können</span></li></ul><p><br></p>',
	'<ul><li><span style=\'color: rgb(0, 0, 0);\'>Einführung </span></li><li><span style=\'color: rgb(0, 0, 0);\'>Datentypen und Ausdrücke </span></li><li><span style=\'color: rgb(0, 0, 0);\'>Kontrollstrukturen </span></li><li><span style=\'color: rgb(0, 0, 0);\'>Arrays und Zeiger </span></li><li><span style=\'color: rgb(0, 0, 0);\'>Prozedurale Programmierung </span></li><li><span style=\'color: rgb(0, 0, 0);\'>Rekursion</span></li><li><span style=\'color: rgb(0, 0, 0);\'>Objektorientierte Programmierung – Teil 1</span></li><li><span style=\'color: rgb(0, 0, 0);\'>Strings&nbsp;</span></li></ul><p><br></p>',
	'Schriftliche Prüfung (90 min) als computergestützte Präsenzprüfung',
	NULL,
	'Beamer, Tafel, Overheadprojektor, E-Learning Medien',
	'<p><span style=\'color: rgb(0, 0, 0);\'>Ullenboom, Christian </span><a href=\'http://openbook.galileocomputing.de/javainsel/\' target=\'_blank\' style=\'color: rgb(0, 0, 0);\'>\'Java ist auch eine Insel\'</a><span style=\'color: rgb(0, 0, 0);\'> Galileo Computing jeweils in der neusten Auflage</span></p><p><span style=\'color: rgb(0, 0, 0);\'>Krüger, Guido </span><a href=\'http://www.javabuch.de/\' target=\'_blank\' style=\'color: rgb(0, 0, 0);\'>\'Handbuch der Java Programmierung\'</a><span style=\'color: rgb(0, 0, 0);\'> Addison Wesley&nbsp;jeweils in der neusten Auflage&nbsp;</span></p><p><span style=\'color: rgb(0, 0, 0);\'>Kathy, Sierra; Bates, Bert; „Java von Kopf bis Fuß“ O‘Reilly jeweils in der neusten Auflage</span></p><p><span style=\'color: rgb(0, 0, 0);\'>Schiedermeier R. \'Programmieren mit Java\' Pearson Studium jeweils in der neusten Auflage&nbsp;</span></p>',
	'Grün'
	),

	(2, 5, 'Rechnerarchitekturen', 'Ra',
	'Jährlich', 
	'Einsemestrig', 
	'Deutsch', 
	NULL,
	NULL,
	'<p><span style=\'color: rgb(0, 0, 0);\'>DUMMY_TEXT: Fachlich-methodische Kompetenzen:</span></p><p><span style=\'color: rgb(0, 0, 0);\'>Studierende sollen </span></p><ul><li><span style=\'color: rgb(0, 0, 0);\'>die zentralen Konzepte von Programmiersprachen (z.B. Variablen, Prozeduren, Kontrollstrukturen, Zeiger) kennen, verstehen und auf Problemstellungen anwenden können</span></li><li><span style=\'color: rgb(0, 0, 0);\'>die Grundlagen der objektorientierten Programmierung kennen, verstehen und auf Problemstellungen anwenden können</span></li></ul><p><br></p>',
	'<ul><li><span style=\'color: rgb(0, 0, 0);\'>DUMMY_TEXT: Einführung </span></li><li><span style=\'color: rgb(0, 0, 0);\'>Datentypen und Ausdrücke </span></li><li><span style=\'color: rgb(0, 0, 0);\'>Kontrollstrukturen </span></li><li><span style=\'color: rgb(0, 0, 0);\'>Arrays und Zeiger </span></li><li><span style=\'color: rgb(0, 0, 0);\'>Prozedurale Programmierung </span></li><li><span style=\'color: rgb(0, 0, 0);\'>Rekursion</span></li><li><span style=\'color: rgb(0, 0, 0);\'>Objektorientierte Programmierung – Teil 1</span></li><li><span style=\'color: rgb(0, 0, 0);\'>Strings&nbsp;</span></li></ul><p><br></p>',
	'Schriftliche Prüfung (90 min)',
	NULL,
	'Tafel, Beamer',
	'<p><span style=\'color: rgb(0, 0, 0);\'>Ullenboom, Christian </span><a href=\'http://openbook.galileocomputing.de/javainsel/\' target=\'_blank\' style=\'color: rgb(0, 0, 0);\'>\'Java ist auch eine Insel\'</a><span style=\'color: rgb(0, 0, 0);\'> Galileo Computing jeweils in der neusten Auflage</span></p><p><span style=\'color: rgb(0, 0, 0);\'>Krüger, Guido </span><a href=\'http://www.javabuch.de/\' target=\'_blank\' style=\'color: rgb(0, 0, 0);\'>\'Handbuch der Java Programmierung\'</a><span style=\'color: rgb(0, 0, 0);\'> Addison Wesley&nbsp;jeweils in der neusten Auflage&nbsp;</span></p><p><span style=\'color: rgb(0, 0, 0);\'>Kathy, Sierra; Bates, Bert; „Java von Kopf bis Fuß“ O‘Reilly jeweils in der neusten Auflage</span></p><p><span style=\'color: rgb(0, 0, 0);\'>Schiedermeier R. \'Programmieren mit Java\' Pearson Studium jeweils in der neusten Auflage&nbsp;</span></p>',
	'Grün'
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
	(1, 1),
	(2, 5)
;



CREATE TABLE IF NOT EXISTS module_has_spo (
	pk_module_pk_unique_id INT NOT NULL,
	pk_spo_pk_unique_id INT NOT NULL,
	pk_semester INT NOT NULL,
	sws INT NULL,
	ects INT NOT NULL,
	workload TEXT NULL,
	admission_requirements TEXT NULL,
	category ENUM('Pflichtfach','Wahlfach','Schlüsselqualifikation') NULL DEFAULT NULL,
	PRIMARY KEY (pk_module_pk_unique_id, pk_spo_pk_unique_id, pk_semester),
	CONSTRAINT module_has_spo_fk_module_pk_unique_id FOREIGN KEY (pk_module_pk_unique_id) REFERENCES module (pk_unique_id) ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT module_has_spo_fk_spo_pk_unique_id FOREIGN KEY (pk_spo_pk_unique_id) REFERENCES spo (pk_unique_id) ON UPDATE NO ACTION ON DELETE NO ACTION
)
COLLATE='utf8mb4_unicode_520_ci'
;

INSERT IGNORE INTO module_has_spo VALUES
	(1, 1, 1, 5, 2, '150h', NULL, 'Pflichtfach'),
	(1, 2, 1, 7, 3, '150h', NULL, 'Pflichtfach'),
	(1, 3, 1, 2, 1, '150h', NULL, 'Pflichtfach')
;



CREATE TABLE IF NOT EXISTS module_manual (
	pk_unique_id INT NOT NULL AUTO_INCREMENT,
	fk_spo_pk_unique_id INT NOT NULL,
	semester VARCHAR(255) NULL,
	first_page BLOB NULL,
	preliminary_note TEXT NULL,
	generated_pdf BLOB NULL,
	PRIMARY KEY (pk_unique_id),
	CONSTRAINT module_manual_fk_spo_pk_unique_id FOREIGN KEY (fk_spo_pk_unique_id) REFERENCES spo (pk_unique_id) ON UPDATE NO ACTION ON DELETE NO ACTION
)
COLLATE='utf8mb4_unicode_520_ci'
;

INSERT IGNORE INTO module_manual VALUES
	(1, 1, "Sommersemester 23", NULL, NULL, NULL),
	(2, 2, "Wintersemester 22/23", NULL, NULL, NULL),
	(3, 3, "Wintersemester 22/23", NULL, NULL, NULL),
	(4, 4, "Wintersemester 22/23", NULL, NULL, NULL)
;

