import { HttpParams } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { emptyStringsToNull } from 'src/app/app.module';
import { MuseumDTO } from 'src/app/models/MuseumDTO';
import { MuseumService } from 'src/app/services/museum.service';

@Component({
  selector: 'app-museums-page',
  templateUrl: './museums-page.component.html',
  styleUrls: ['./museums-page.component.css']
})
export class MuseumsPageComponent implements OnInit {

  museums: MuseumDTO[] = [];

  filterForm: FormGroup;

  constructor(private fb: FormBuilder, 
    private museumService: MuseumService) { 
    
      this.filterForm = fb.group({
        name: null,
        city: null
      });
  }

  ngOnInit(): void {
  }

  submitFilters(): void{
    emptyStringsToNull(this.filterForm);

    const params: HttpParams = new HttpParams({
      fromObject: this.filterForm.value
    });

    this.museumService.getMuseums(params).subscribe(vals => this.museums = vals);
  }
}
