export interface FlatModule {//Dummy Modul
    id:number;
    abbreviation:string;
    moduleName:string;
    moduleOwner:string; 
}

export class Module {//richties Modul 
    public constructor(init?: Partial<Module>) {
        Object.assign(this, init);
    }
  
    public id!:number;
    public moduleName!:string;
    public publicabbreviation!:string;
    public publicsws!:number;
    public ects!:number;
    public workLoad!:string;
    public semester!:number;
    public cycle!:string;
    public duration!:string;
    public moduleOwner!:string;
    public prof!:string;
    public language!: string;
    public  usage!:string;
    public admissionRequirements!:string;
    public knowledgeRequirements!:string;
    public skills!:string;
    public content!:string;
    public examType!:string;
    public certificates!:string;
    public  mediaType!:string;
    public literature!:string;
    public maternityProtection!:string;
}
