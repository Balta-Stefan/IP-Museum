import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MuseumTourDTO } from 'src/app/models/MuseumTourDTO';

@Component({
  selector: 'app-tour-card',
  templateUrl: './tour-card.component.html',
  styleUrls: ['./tour-card.component.css']
})
export class TourCardComponent implements OnInit {
  @Input() tour!: MuseumTourDTO;
  @Output() tourSelected = new EventEmitter<MuseumTourDTO>();

  constructor() { }

  ngOnInit(): void {
  }

  onTourSelect(): void{
    this.tourSelected.emit(this.tour);
  }
}
