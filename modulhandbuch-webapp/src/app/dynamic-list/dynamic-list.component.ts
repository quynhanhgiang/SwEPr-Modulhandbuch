import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-dynamic-list',
  templateUrl: './dynamic-list.component.html',
  styleUrls: ['./dynamic-list.component.scss']
})
export class DynamicListComponent{
  @Input() header: string = "";

  @Input() list!: string[];
  @Output() listChange = new EventEmitter<string[]>();

  inputVal: string = "";
  valid: boolean = true;

  constructor() { }

  add() {
    if (this.list.includes(this.inputVal)) {
      this.valid = false;
      setTimeout(() => this.valid = true, 3000);
    } else {
      this.list.push(this.inputVal);
      this.listChange.emit(this.list);
    }

    this.inputVal="";
  }

  delete(index: number) {
    this.list.splice(index, 1)
    this.listChange.emit(this.list);
  }
}
