import { Component, Input, OnInit } from '@angular/core';
import { MuseumDTO } from 'src/app/models/MuseumDTO';

@Component({
  selector: 'app-museum-list-card',
  templateUrl: './museum-list-card.component.html',
  styleUrls: ['./museum-list-card.component.css']
})
export class MuseumListCardComponent implements OnInit {
  @Input() museum!: MuseumDTO;

  constructor() { }

  ngOnInit(): void {
  }

}
