import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-picture-carousel',
  templateUrl: './picture-carousel.component.html',
  styleUrls: ['./picture-carousel.component.css']
})
export class PictureCarouselComponent implements OnInit {
  @Input() pictureURLs!: string[];

  activePicture!: string;

  otherPictures: string[] = [];

  constructor() { }

  ngOnInit(): void {
    this.activePicture = this.pictureURLs[0];

    if(this.pictureURLs.length > 1){
      for(let i = 1; i < this.pictureURLs.length; i++){
        this.otherPictures.push(this.pictureURLs[i]);
      }
    }
  }

}
