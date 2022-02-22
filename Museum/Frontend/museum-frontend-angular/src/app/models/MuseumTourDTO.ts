import { MuseumDTO } from "./MuseumDTO";

export interface MuseumTourDTO{
    tourID: number;
    museum: MuseumDTO;
    startTimestamp: Date;
    endTimestamp: Date;
    price: number;
    purchased: Date;
    paid: Date;
    staticContent: string[];
}