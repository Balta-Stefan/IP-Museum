import { Component, Input, OnInit } from '@angular/core';
import { WeatherDTO } from 'src/app/models/WeatherDTO';

@Component({
  selector: 'app-weather-card',
  templateUrl: './weather-card.component.html',
  styleUrls: ['./weather-card.component.css']
})
export class WeatherCardComponent implements OnInit {
  @Input() weather!: WeatherDTO;
  
  constructor() { }

  ngOnInit(): void {
  }

}
