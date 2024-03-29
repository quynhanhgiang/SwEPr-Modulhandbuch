import { Module } from "../shared/module";

export const modules: Module[] = [
    {
        id: 0,
        moduleName: "Analysis",
        abbreviation: "Ana",
        variations: [
          {
            manual: {
              id: 1,
              semester: "Wintersemester 2022/2023",
              spo: {
                id: 1,
                link: "https://mycampus.hs-coburg.de/sites/default/files/files/documents/SPO%20B%20IF%204.pdf",
                degree: "Bachelor",
                course: "Informatik",
                startDate: "2020-10-01",
                endDate: null
              }
            },
            ects: 7,
            sws: 6,
            workLoad: "<p><span style=\color: rgb(0, 0, 0);\>90 h Präsenz (Seminaristischer Unterricht mit integrierten Übungen)</span></p><p><br></p><p><span style=\color: rgb(0, 0, 0);\>120 h Eigenarbeit (40 h Nachbereitung des Lehrstoffs, 30 h Bearbeitung von Übungsaufgaben, 50 h Prüfungsvorbereitung)</span></p>",
            semester: 1,
            moduleType: {
              "id": 1,
              "value": "Pflichtfach"
            },
            admissionRequirement: {
              "id": 0,
              "value": "Zulassungsvorraussetzung nach §4 Abs. 1"
            },
            segment: {
              "id": 0,
              "value": "1. Studienabschnitt"
            }
          }
        ],
        cycle: "Jährlich",
        duration: "Einsemestrig",
        moduleOwner: {
          id: 3,
          firstName: "Ada",
          lastName: "Bäumner",
          title: "Prof. Dr",
          gender: "Sehr geehrte",
          email: "Ada.Baeumner@hs-coburg.de"
        },
        profs: [
          {
            id: 3,
            firstName: "Ada",
            lastName: "Bäumner",
            title: "Prof. Dr",
            gender: "Sehr geehrte",
            email: "Ada.Baeumner@hs-coburg.de"
          }
        ],
        language: "Deutsch",
        usage: "",
        knowledgeRequirements: "",
        skills: "<p><span style=\color: rgb(0, 0, 0);\>Studierende sollen wesentliche Grundlagen der Analysis bis hin zur Differentialrechnung kennen und anwenden können.</span></p>",
        content: "<p><span style=\color: rgb(0, 0, 0);\>Logik, Mengenlehre, Vollständige Induktion, Kombinatorik, rationale und reelle Zahlen, komplexe Zahlen, Folgen und Grenzwerte, Reihen, Funktionen und Stetigkeit, Differenzierbarkeit, Sätze der Differenzialrechnung Extremwerte, Taylorentwicklung</span></p>",
        examType: "Schriftliche Prüfung (120 Minuten)",
        certificates: "",
        mediaType: "Tafel, Skript",
        literature: "<p><span style=\color: rgb(0, 0, 0);\>T. Arens et al., „Mathematik“, Spektrum, Heidelberg, 2008 </span></p><p><span style=\color: rgb(51, 51, 51);\>G. Teschl, S. Teschl, „Mathematik für Informatiker“, Band 1 und 2, Springer Spektrum Berlin, Heidelberg, 2013</span></p><p><span style=\color: rgb(51, 51, 51);\>W. Struckmann, D. Wätjen, „Mathematik für Informatiker“, Springer Vieweg Berlin, Heidelberg, 2016</span></p><p><span style=\color: rgb(51, 51, 51);\>R. Berghammer, „Mathematik für Informatiker“, Springer Vieweg Wiesbaden, 2014</span></p><p>&nbsp;</p><p><span style=\color: rgb(51, 51, 51);\>E. Weitz, „Konkrete Mathematik (nicht nur) für Informatiker“, Springer Spektrum Wiesbaden, 2018</span></p><p>&nbsp;</p><p><span style=\color: rgb(0, 0, 0);\>O. Forster, „Analysis 1“, Vieweg, Wiesbaden, 2004</span></p>",
        maternityProtection: "Grün"
      },
      {
        id: 1,
        moduleName: "Programmieren 1",
        abbreviation: "prog1",
        variations: [
          {
            manual: {
              id: 1,
              semester: "Wintersemester 2022/2023",
              spo: {
                id: 1,
                link: "https://mycampus.hs-coburg.de/sites/default/files/files/documents/SPO%20B%20IF%204.pdf",
                degree: "Bachelor",
                course: "Informatik",
                startDate: "2020-10-01",
                endDate: ""
              }
            },
            ects: 5,
            sws: 4,
            workLoad: "<p><span style=\color: rgb(0, 0, 0);\>150 h,&nbsp;davon </span></p><ul><li><span style=\color: rgb(0, 0, 0);\>60 h Präsenz (30 h Seminaristischer Unterricht, 30 h Übung)</span></li><li><span style=\color: rgb(0, 0, 0);\>90 h Eigenarbeit (30h Vor- und Nachbereitung des Lehrstoffs, 30h Lösung&nbsp;von Übungsaufgaben, 30h Prüfungsvorbereitung)</span></li></ul>",
            semester: 1,
            moduleType: {
              "id": 1,
              "value": "Pflichtfach"
            },
            admissionRequirement: {
              "id": 0,
              "value": "Zulassungsvorraussetzung nach §4 Abs. 1"
            },
            segment: {
              "id": 0,
              "value": "1. Studienabschnitt"
            }
          }
        ],
        cycle: "Jährlich",
        duration: "Einsemestrig",
        moduleOwner: {
          id: 2,
          firstName: "Volkard",
          lastName: "Pfeiffer",
          title: "Prof.",
          gender: "Sehr geehrter",
          email: "Volkard.Pfeiffer@hs-coburg.de"
        },
        profs: [
          {
            id: 2,
            firstName: "Volkard",
            lastName: "Pfeiffer",
            title: "Prof.",
            gender: "Sehr geehrter",
            email: "Volkard.Pfeiffer@hs-coburg.de"
          }
        ],
        language: "Deutsch",
        usage: "Betriebswirtschaft – Schwerpunkt Wirtschaftsinformatik Bachelor Visual Computing",
        knowledgeRequirements: "",
        skills: "<p><span style=\color: rgb(0, 0, 0);\>Fachlich-methodische Kompetenzen:</span></p><p><span style=\color: rgb(0, 0, 0);\>Studierende sollen </span></p><ul><li><span style=\color: rgb(0, 0, 0);\>die zentralen Konzepte von Programmiersprachen (z.B. Variablen, Prozeduren, Kontrollstrukturen, Zeiger) kennen, verstehen und auf Problemstellungen anwenden können</span></li><li><span style=\color: rgb(0, 0, 0);\>die Grundlagen der objektorientierten Programmierung kennen, verstehen und auf Problemstellungen anwenden können</span></li></ul>",
        content: "<ul><li><span style=\color: rgb(0, 0, 0);\>Einführung </span></li><li><span style=\color: rgb(0, 0, 0);\>Datentypen und Ausdrücke </span></li><li><span style=\color: rgb(0, 0, 0);\>Kontrollstrukturen </span></li><li><span style=\color: rgb(0, 0, 0);\>Arrays und Zeiger </span></li><li><span style=\color: rgb(0, 0, 0);\>Prozedurale Programmierung&nbsp;</span></li><li><span style=\color: rgb(0, 0, 0);\>Rekursion</span></li><li><span style=\color: rgb(0, 0, 0);\>Objektorientierte Programmierung – Teil 1</span></li><li><span style=\color: rgb(0, 0, 0);\>Strings </span></li><li><span style=\color: rgb(0, 0, 0);\>Exception Handling</span></li></ul>",
        examType: "Schriftliche Prüfung (90 min) als computergestützte Präsenzprüfung" ,
        certificates: "null",
        mediaType: "Beamer, Tafel, Overheadprojektor, E-Learning Medien",
        literature: "<p><span style=\color: rgb(0, 0, 0);\>Ullenboom, Christian </span><a href=\http://openbook.galileocomputing.de/javainsel/\ rel=\noopener noreferrer\ target=\_blank\ style=\color: rgb(0, 0, 0);\>\Java ist auch eine Insel\</a><span style=\color: rgb(0, 0, 0);\> Galileo Computing jeweils in der neusten Auflage</span></p><p><span style=\color: rgb(0, 0, 0);\>Krüger, Guido </span><a href=\http://www.javabuch.de\ rel=\noopener noreferrer\ target=\_blank\ style=\color: rgb(0, 0, 0);\>\Handbuch der Java Programmierung\</a><span style=\color: rgb(0, 0, 0);\> Addison Wesley&nbsp;jeweils in der neusten Auflage&nbsp;</span></p><p><span style=\color: rgb(0, 0, 0);\>Kathy, Sierra; Bates, Bert; „Java von Kopf bis Fuß“ O‘Reilly jeweils in der neusten Auflage</span></p><p><span style=\color: rgb(0, 0, 0);\>Schiedermeier R. \Programmieren mit Java\ Pearson Studium jeweils in der neusten Auflage</span></p>",
        maternityProtection: "Grün"
      },
]
