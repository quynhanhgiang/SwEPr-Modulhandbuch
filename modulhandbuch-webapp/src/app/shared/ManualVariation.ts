import { FlatModule } from "./FlatModule";

/**
 * "ManualVariation"-Interface
 */
export interface ManualVariation {
    module: FlatModule,
    semester: number | null,
    sws: number  | null,
    ects: number  | null,
    workLoad: string  | null,
    moduleType: string  | null,
    segment: string  | null,
    admissionRequirement: string  | null
    isAssigned: boolean | undefined
}
