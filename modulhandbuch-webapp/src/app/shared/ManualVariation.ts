import { FlatModule } from "./FlatModule";

/**
 * "ManualVariation"-Interface
 */
export interface ManualVariation {
    module: FlatModule,
    semester: number,
    sws: number,
    ects: number,
    workLoad: string,
    moduleType: string,
    segment: string,
    admissionRequirement: string
}
