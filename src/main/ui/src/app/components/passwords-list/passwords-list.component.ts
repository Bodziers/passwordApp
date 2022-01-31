import {
  AfterViewInit,
  ChangeDetectorRef,
  Component,
  ElementRef,
  HostListener,
  NgZone,
  OnInit,
  ViewChild
} from '@angular/core';
import {Password} from "../../model/password";
import {Subscription} from "rxjs";
import {PasswordService} from "../../service/password.service";
import {MdbTableDirective, MdbTablePaginationComponent} from "angular-bootstrap-md";


@Component({
  selector: 'app-passwords-list',
  templateUrl: './passwords-list.component.html',
  styleUrls: ['./passwords-list.component.scss']
})
export class PasswordsListComponent implements OnInit, AfterViewInit {
  passwordResponse: Password[] = [];
  private subscription: Subscription = new Subscription();

  @ViewChild(MdbTableDirective, { static: true }) mdbTable: MdbTableDirective;
  @ViewChild(MdbTablePaginationComponent, { static: true }) mdbTablePagination: MdbTablePaginationComponent;
  @ViewChild('row', { static: true }) row: ElementRef;

  elements: Password[] = [];
  headElements = ['id', 'password'];

  searchText: string = '';
  previous: string;

  maxVisibleItems: number = 8;

  constructor(private cdRef: ChangeDetectorRef,
              private passwordService: PasswordService,
              private zone: NgZone) {
}

  @HostListener('input') oninput() {
    this.mdbTablePagination.searchText = this.searchText;
  }

  ngAfterViewInit() {
    this.mdbTablePagination.setMaxVisibleItemsNumberTo(this.maxVisibleItems);
    this.mdbTablePagination.calculateFirstItemIndex();
    this.mdbTablePagination.calculateLastItemIndex();
    this.cdRef.detectChanges();
  }

  addNewRow() {
    this.mdbTable.addRow({
      id: this.elements.length.toString(),
      password: 'Password ' + this.elements.length
    });
    this.emitDataSourceChange();
  }

  addNewRowAfter() {
    this.mdbTable.addRowAfter(1, {id: '2', password: 'Nowy'});
    this.mdbTable.getDataSource().forEach((el: any, index: any) => {
      el.id = (index + 1).toString();
    });
    this.emitDataSourceChange();
  }

  removeLastRow() {
    this.mdbTable.removeLastRow();
    this.emitDataSourceChange();
    this.mdbTable.rowRemoved().subscribe((data: any) => {
      console.log(data);
    });
  }

  removeRow() {
    this.mdbTable.removeRow(1);
    this.mdbTable.getDataSource().forEach((el: any, index: any) => {
      el.id = (index + 1).toString();
    });
    this.emitDataSourceChange();
    this.mdbTable.rowRemoved().subscribe((data: any) => {
      console.log(data);
    });
  }

  emitDataSourceChange() {
    this.mdbTable.dataSourceChange().subscribe((data: any) => {
      console.log(data);
    });
  }

  searchItems() {
    const prev = this.mdbTable.getDataSource();

    if (!this.searchText) {
      this.mdbTable.setDataSource(this.previous);
      this.elements = this.mdbTable.getDataSource();
    }

    if (this.searchText) {
      this.elements = this.mdbTable.searchLocalDataBy(this.searchText);
      this.mdbTable.setDataSource(prev);
    }

    this.mdbTablePagination.calculateFirstItemIndex();
    this.mdbTablePagination.calculateLastItemIndex();

    this.mdbTable.searchDataObservable(this.searchText).subscribe(() => {
      this.mdbTablePagination.calculateFirstItemIndex();
      this.mdbTablePagination.calculateLastItemIndex();
    });
  }

  ngOnInit() {
    for (let i = 1; i <= 25; i++) {
        this.elements.push({id: i, password: 'password ' + i});
      }
    this.mdbTable.setDataSource(this.elements);
    this.elements = this.mdbTable.getDataSource();
    this.previous = this.mdbTable.getDataSource();


    this.subscription.add(this.passwordService.getData().subscribe(
      passwords => {
        this.zone.run(() => {
          this.passwordResponse = passwords;
          if (this.passwordResponse.length!=0) {
            this.mdbTable.setDataSource(this.passwordResponse);
            this.elements = this.mdbTable.getDataSource();
            this.previous = this.mdbTable.getDataSource();
          }
        })
      })
    )

  }

  ngOnDestroy() {
    // unsubscribe to ensure no memory leaks
    this.subscription.unsubscribe();
  }


}
