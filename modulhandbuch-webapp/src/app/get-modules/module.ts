export interface Module {//Dummy Modul

    id:number;
    name:string;
    prof:string;
    semester:number;
}

export class Module1 {//richties Modul
    public constructor(init?: Partial<Module1>) {
        Object.assign(this, init);
    }

    id!:number;
    moduleName!:string;
    abbreviation!:string;
    sws!:number;
    ects!:number;
    workLoad!:string;
    semester!:number;
    cycle!:string;
    duration!:string;
    moduleOwner!:string;
    prof!:string;
    language!: string;
    usage!:string;
    admissionRequirements!:string;
    knowledgeRequirements!:string;
    skills!:string;
    content!:string;
    examType!:string;
    certificates!:string;
    mediaType!:string;
    literature!:string;
    maternityProtection!:String;
}