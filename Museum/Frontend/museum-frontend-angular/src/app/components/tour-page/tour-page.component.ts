import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { BankTransactionDTO } from 'src/app/models/BankTransactionDTO';
import { CardTypes } from 'src/app/models/CardTypes';
import { MuseumTourDTO } from 'src/app/models/MuseumTourDTO';
import { TicketPurchaseResponse } from 'src/app/models/TicketPurchaseResponse';
import { StaticResourceType, TourStaticContentDTO } from 'src/app/models/TourStaticContentDTO';
import { BankService } from 'src/app/services/bank.service';
import { MuseumService } from 'src/app/services/museum.service';

import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';

@Component({
  selector: 'app-tour-page',
  templateUrl: './tour-page.component.html',
  styleUrls: ['./tour-page.component.css']
})
export class TourPageComponent implements OnInit, OnDestroy {
  tour!: MuseumTourDTO;
  museumID!: number;
  tourID!: number;

  showBuyTicketButton: boolean = false;
  showTimer: boolean = false;
  showTour: boolean = false;

  showBuyTicketForm: boolean = false;

  disableSendCardDataButton: boolean = false;

  cardFormData: FormGroup;

  cardTypes = CardTypes;

  cardFormMessage: string = '';
  cardFormSuccess: boolean = false;

  remainingDays: number = 0;
  remainingHours: number = 0;
  remainingMinutes: number = 0;
  remainingSeconds: number = 0;

  timer!: any;

  youtubeVideoURL!: SafeResourceUrl;

  pictures: string[] = [];
  video!: TourStaticContentDTO;

  constructor(private museumService: MuseumService,
    private bankService: BankService,
    private activeRoute: ActivatedRoute,
    private fb: FormBuilder,
    private sanitizer: DomSanitizer) { 

      this.cardFormData = fb.group({
        firstName: [null, Validators.required],
        lastName: [null, Validators.required],
        cardNumber: [null, Validators.required],
        cardType: [null, Validators.required],
        cardExpirationDate: [null, Validators.required],
        pin: [null, Validators.required]
      });
    
      this.activeRoute.paramMap.subscribe(map => {
        this.museumID = parseInt(map.get('museumID')!);
        this.tourID = parseInt(map.get('tourID')!);
        
        this.museumService.getTour(this.museumID, this.tourID).subscribe(t => {
          this.tour = t;

          t.startTimestamp = new Date(t.startTimestamp);
          t.endTimestamp = new Date(t.endTimestamp);
          

          if(t.paid){
            const currentTime: Date = new Date();

            if(currentTime < t.startTimestamp){
              this.showTimer = true;
              this.startTimer();
            }
            else{
              this.prepareStaticContent();
            }
          }
          else{
            this.showBuyTicketButton = true;
          }
        });
      })
  }
  ngOnDestroy(): void {
    if(this.timer){
      clearInterval(this.timer);
    }
  }


  ngOnInit(): void {
  }

  private prepareStaticContent(): void{
    this.showTour = true;

    this.tour.staticContent.forEach(c => {
      if(c.isYouTubeVideo == true){
        this.youtubeVideoURL = this.sanitizer.bypassSecurityTrustResourceUrl(c.uri);

      } 
      else if(c.resourceType == StaticResourceType.PICTURE.toString()){
        this.pictures.push(c.uri);
      }
      else{
        this.video = c;
      }
    });
  }

  private refreshTour(): void{
    this.museumService.getTour(this.museumID, this.tourID).subscribe(val => {
      this.tour = val;
      this.showTour = true;

      this.prepareStaticContent();
    });
  }
  
  private startTimer(): void{
    this.showTimer = true;

    const zeroTime: Date = new Date(0);
    let timestampCounter: Date = new Date();

    this.timer = setInterval(()=> {
      timestampCounter.setSeconds(timestampCounter.getSeconds()+1);

      if(timestampCounter >= this.tour.startTimestamp){
        this.showTimer = false;
        this.showTour = true;

        this.refreshTour();
      }
      else{
        let difference: number = (this.tour.startTimestamp.valueOf() - timestampCounter.valueOf()) / 1000;
        this.remainingDays = Math.floor(difference / 86400);

        difference = difference % 86400;
        this.remainingHours = Math.floor(difference / 3600);

        difference = difference % 3600;
        this.remainingMinutes = Math.floor(difference / 60);

        difference = difference % 60;
        this.remainingSeconds = Math.floor(difference);
      }
    }, 1000);
  }

  changeFormVisibility(): void{
    this.showBuyTicketForm = !this.showBuyTicketForm;
  }

  sendCardData(): void{
    console.log(this.cardFormData.value);
    this.disableSendCardDataButton = true;

    this.museumService.buyTicket(this.museumID, this.tourID).subscribe({
      error: (err: any) => {
        this.cardFormMessage = 'Desila se greška na našim serverima.Podaci nisu poslati banci.';
        this.cardFormSuccess = false;
      },
      next: (response: TicketPurchaseResponse) => {
        this.bankService.sendPaymentInfo(this.cardFormData.value, response.redirectURL).subscribe({
          error: (err: any) => {
            this.cardFormMessage = 'Transakcija neuspješna.';
            this.cardFormSuccess = false;
          },
          next: (bankResponse: BankTransactionDTO) => {
            this.cardFormMessage = 'Transakcija uspješna.';
            this.cardFormSuccess = true;

            setTimeout(()=> {
              this.showBuyTicketForm = this.showBuyTicketButton = false;

              this.startTimer();
            }, 2000);
          }
        });
      }
    });
  }
}
