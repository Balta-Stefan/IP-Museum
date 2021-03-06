interface Weather{
    id: number;
    main: string;
    description: string;
    icon: string;
}

interface Main{
    temp: number;
    feels_like: number;
    temp_min: number;
    temp_max: number;
    pressure: number;
    humidity: number;
}


export interface WeatherDTO{
    weather: Weather[];
    main: Main;
    wind: {
        speed: number;
    }
    name: string;
}