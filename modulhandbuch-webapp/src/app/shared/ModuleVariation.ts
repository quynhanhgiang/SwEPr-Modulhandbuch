import { ModuleManual } from "./module-manual";

export interface ModuleVariation {
    manual: ModuleManual,
    semester: number,
    sws: number,
    ects: number,
    workLoad: string,
    category:string,
    moduleType: string,
    segment: string,
    admissionRequirement: string
}