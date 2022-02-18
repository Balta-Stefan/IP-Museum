import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MuseumDTO } from 'src/app/models/MuseumDTO';
import { MuseumService } from 'src/app/services/museum.service';

@Component({
  selector: 'app-museum-overview',
  templateUrl: './museum-overview.component.html',
  styleUrls: ['./museum-overview.component.css']
})
export class MuseumOverviewComponent implements OnInit {

  museum!: MuseumDTO;

  constructor(private route: ActivatedRoute, private museumService: MuseumService) { 

  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      let id = parseInt(params.get('id')!);

      this.museumService.getMuseum(id).subscribe(val => this.museum = val);
    });
  }

}
