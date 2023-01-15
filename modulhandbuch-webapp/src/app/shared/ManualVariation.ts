import { Assignment } from "./Assignments";
import { FlatModule } from "./FlatModule";

/**
 * "ManualVariation"-Interface
 */
export interface ManualVariation {
    module: FlatModule,
    semester: number | null,
    sws: number  | null,
    ects: number  | null,
    workLoad: string | null,
    moduleType: Assignment  | null,
    segment: Assignment  | null,
    admissionRequirement: Assignment  | null
    isAssigned: boolean | undefined
}
