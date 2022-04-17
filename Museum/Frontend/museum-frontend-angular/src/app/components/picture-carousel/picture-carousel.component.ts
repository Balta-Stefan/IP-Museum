import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';

@Component({
  selector: 'app-picture-carousel',
  templateUrl: './picture-carousel.component.html',
  styleUrls: ['./picture-carousel.component.css']
})
export class PictureCarouselComponent implements OnInit, OnChanges {
  activePicture!: string;

  otherPictures: string[] = [];

  //@Input() pictureURLs!: string[];
  @Input() pictureURLs!: string[];

  constructor() { }

  private setPictures(): void{
    if(this.pictureURLs){
      this.activePicture = this.pictureURLs[0];

      if(this.pictureURLs.length > 1){
        for(let i = 1; i < this.pictureURLs.length; i++){
          this.otherPictures.push(this.pictureURLs[i]);
        }
      }
    }
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.setPictures();
  }

  ngOnInit(): void {
    this.setPictures();
  }

}
