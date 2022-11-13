import { FlatModule } from "./FlatModule";
import { Spo } from "./spo";

export interface ModuleManual {
    id: number,
    spo: Spo,
    validFrom: Date,
    validTo: Date,
    modules: FlatModule[]
}
