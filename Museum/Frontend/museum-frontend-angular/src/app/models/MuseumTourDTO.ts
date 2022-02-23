import { MuseumDTO } from "./MuseumDTO";
import { TourStaticContentDTO } from "./TourStaticContentDTO";

export interface MuseumTourDTO{
    tourID: number;
    museum: MuseumDTO;
    startTimestamp: Date;
    endTimestamp: Date;
    price: number;
    purchased: Date;
    paid: Date;
    staticContent: TourStaticContentDTO[];
}