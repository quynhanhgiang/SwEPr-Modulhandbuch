import { CollegeEmployee } from "./CollegeEmployee";
import { ModuleVariation } from "./ModuleVariation";

/**
 * "Module"-Interface, for Module-Creation and -Update
 */
export interface Module {
  id: number | null,
  moduleName: string,
  abbreviation: string,
  variations: ModuleVariation[],
  cycle: string,
  duration: string,
  moduleOwner: CollegeEmployee,   // Bei GET: Namen als String -> keine weiteren Abfragen nötig
  profs: CollegeEmployee[],      // Bei POST: ID der Dozenten wird überegeben -> Verweis auf Objekt
  language: string,
  usage: string,
  admissionRequirements: string,
  knowledgeRequirements: string,
  skills: string,
  content: string,
  examType: string,
  certificates: string,
  mediaType: string,
  literature: string,
  maternityProtection: string
}
