import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MuseumDTO } from 'src/app/models/MuseumDTO';
import { WeatherDTO } from 'src/app/models/WeatherDTO';
import { MuseumService } from 'src/app/services/museum.service';

@Component({
  selector: 'app-museum-overview',
  templateUrl: './museum-overview.component.html',
  styleUrls: ['./museum-overview.component.css']
})
export class MuseumOverviewComponent implements OnInit {

  museum!: MuseumDTO;
  weathers: WeatherDTO[] = [];

  constructor(private route: ActivatedRoute, private museumService: MuseumService) { 
    
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      let id = parseInt(params.get('id')!);

      this.museumService.getMuseum(id).subscribe(val => this.museum = val);
      /*this.museumService.getWeathers(id).subscribe(weatherDTOS => {

        weatherDTOS.forEach(cityWeather => {
          cityWeather.weather.forEach(concreteWeather => {
            concreteWeather.icon = `http://openweathermap.org/img/wn/${concreteWeather.icon}@4x.png`;
          })
        })
        this.weathers = weatherDTOS
      });*/
    });
  }

}
