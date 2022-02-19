import { MuseumType } from "./MuseumType";

export interface MuseumDTO{
    museumID: number;
    name: string;
    address: string;
    phone: string;
    city: string;
    country: string;
    longitude: number;
    latitude: number;
    thumbnail: string;
    type: MuseumType;
}