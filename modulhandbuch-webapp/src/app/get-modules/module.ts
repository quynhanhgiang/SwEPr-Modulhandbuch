export interface Module {//Dummy Modul
    id:number;
    name:string;
    prof:string;
    semester:number;
}

export interface Module1 {//richties Modul 
    id:number;
    moduleName:string;
    abbreviation:string;
    sws:number;
    ects:number;
    workLoad:string;
    semester:number;
    cycle:Cycle;
    duration:Duration;
    moduleOwner:string;
    prof:string;
    language: Language;
    usage:string;
    admissionRequirements:AdmissionRequirements;
    knowledgeRequirements:string;
    skills:string;
    content:string;
    examType:string;
    certificates:string;
    mediaType:MediaType[];
    literature:string;
    maternityProtection:MaternityProtection;
}

enum Cycle {
    Yearly="Jährlich",
    Half="Halbjährlich"
}

enum Duration{
    One="Einsemestrig",
    Two="Zweisemestrig"
}

enum Language{
    German="Deutsch",
    English="Englisch",
}

enum AdmissionRequirements{
    If="Vorrückensberechtigung nach §5 Abs. 1 SPO (IF)",
    Vc="Vorrückensberechtigung nach §5 Abs. 1 SPO (VC)",
    El="Vorrückensberechtigung nach §5 Abs. 1 SPO (EL)",
    Au="Vorrückensberechtigung nach §5 Abs. 1 SPO (AU)",
}

enum MediaType{
    Skript,
    Tafel,
    Beamer,
    PowerPoint,
}

enum MaternityProtection{
    Green="Grün",
    Yellow="Gelb",
    Red="Rot"
}