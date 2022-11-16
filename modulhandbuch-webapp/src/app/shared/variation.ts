import { Spo } from "./spo";

export interface Variation {
    spo: Spo;
    semester: number;
    sws: number;
    ects: number;
    workload: String;
    category:string;
}
