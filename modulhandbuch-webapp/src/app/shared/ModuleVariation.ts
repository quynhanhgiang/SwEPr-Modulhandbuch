import { Assignment } from "./Assignments";
import { ModuleManual } from "./module-manual";

export interface ModuleVariation {
    manual: ModuleManual,
    semester: number,
    sws: number,
    ects: number,
    workLoad: string,
    moduleType: Assignment,
    segment: Assignment,
    admissionRequirement: Assignment
}
