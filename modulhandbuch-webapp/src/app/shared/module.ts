import { Variation } from "./Variation";

/**
 * "Module"-Interface, for Module-Creation and -Update
 */
export interface Module {
  id: number | null,
  moduleName: string,
  abbreviation: string,
  variations: Variation[],
  cycle: string,
  duration: string,
  moduleOwner: string | number,   // Bei GET: Namen als String -> keine weiteren Abfragen nötig
  prof: string[] | number[],      // Bei POST: ID der Dozenten wird überegeben -> Verweis auf Objekt
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
