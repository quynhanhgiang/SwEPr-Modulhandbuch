import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Assignment } from '../shared/Assignments';

@Component({
  selector: 'app-dynamic-list',
  templateUrl: './dynamic-list.component.html',
  styleUrls: ['./dynamic-list.component.scss']
})
export class DynamicListComponent{
  @Input() header: string = "";

  @Input() list!: Assignment[];
  @Output() listChange = new EventEmitter<Assignment[]>();

  newVal: string = "";

  resetVal: string = "";
  itemToEditIndex: number | null = null;

  itemToDeleteIndex: number | null = null;

  dialogVisible: boolean = false;

  valid: boolean = true;

  constructor() {}

  /**
   * Adds a new item to the assignment-list.
   */
  addItem() {
    if (this.newVal.trim().length == 0) {
      this.valid = false;
      setTimeout(() => this.valid = true, 3000);
      this.newVal = "";
      return;
    }

    this.list.push( {
      id: null,
      value: this.newVal.trim()
    });

    this.listChange.emit(this.list);
    this.newVal = "";
  }

  /**
   * Allows the user to edit an item.
   * @param index index of the item to edit.
   */
  editItem(index: number) {
    this.itemToEditIndex = index;
    this.resetVal = this.list[index].value;
  }

  /**
   * Closes the edit-view for the currently edited item.
   */
  closeEdit() {
    this.itemToEditIndex = null;
    this.listChange.emit(this.list);
  }

  /**
   * Resets the currently edited item to its previous value.
   * @param index index of the edited item.
   */
  resetItem(index: number) {
    this.itemToEditIndex = null;
    this.list[index].value = this.resetVal;
  }

  /**
   *
   * @param index
   */
  deleteItem(index: number) {
    this.itemToDeleteIndex = index;
    this.showWarning();
  }

  showWarning() {
    this.dialogVisible = true;
  }

  closeWarning(del: boolean) {
    if (del) {
      this.list.splice(this.itemToDeleteIndex!, 1);
      this.listChange.emit(this.list);
    }

    this.dialogVisible = false;
  }
}
