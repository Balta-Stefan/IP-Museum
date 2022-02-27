export enum StaticResourceType{
    VIDEO="VIDEO", 
    PICTURE="PICTURE"
}

export interface TourStaticContentDTO{
    staticContentID: number;
    uri: string;
    isYouTubeVideo: boolean;
    resourceType: StaticResourceType;
}