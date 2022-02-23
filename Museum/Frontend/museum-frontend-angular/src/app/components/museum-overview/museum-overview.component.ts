import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MuseumDTO } from 'src/app/models/MuseumDTO';
import { MuseumTourDTO } from 'src/app/models/MuseumTourDTO';
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
  tours: MuseumTourDTO[] = [];

  constructor(private route: ActivatedRoute, 
    private museumService: MuseumService, 
    private router: Router) { 
    
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      let id = parseInt(params.get('id')!);

      this.museumService.getMuseum(id).subscribe(val => this.museum = val);
      this.museumService.getTours(id).subscribe(val => this.tours = val);
      this.museumService.getWeathers(id).subscribe(weatherDTOS => this.weathers = weatherDTOS);
    });
  }

  tourSelected(tour: MuseumTourDTO): void{
    this.router.navigate([`tour/${tour.tourID}`], {relativeTo: this.route});
  }
}
