import { Variation } from "./variation";

/**
 * "Flat Module"-Interface, for Module-Overview
 */
export interface FlatModule {   //
    id: number;
    abbreviation: string;
    moduleName: string;
    moduleOwner: string; 
}

/**
 * "Module"-Class, for Module-Creation and -Update
 */
export class Module {   
    public constructor(init?: Partial<Module>) {
        Object.assign(this, init);
    }
  
    public id!:number;
    public moduleName!:string;
    public abbreviation!:string;
    public variations!: Variation[];
    public cycle!:string;
    public duration!:string;
    public moduleOwner!:string;
    public prof!:string;
    public language!: string;
    public usage!:string;
    public admissionRequirements!:string;
    public knowledgeRequirements!:string;
    public skills!:string;
    public content!:string;
    public examType!:string;
    public certificates!:string;
    public mediaType!:string;
    public literature!:string;
    public maternityProtection!:string;
}
