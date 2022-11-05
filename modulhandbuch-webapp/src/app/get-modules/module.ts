export interface flatModule {//flatModule for ModuleList
    id:number;
    name:string;
    prof:string;
    semester:number;
}

export class Module {//Module class for creating new Modules or datailed module view
    public constructor(init?: Partial<Module>) {
        Object.assign(this, init);
    }

    id=-1;
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