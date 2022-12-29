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
    if (this.itemToEditIndex != null) {
      this.resetItem(this.itemToEditIndex);
    }

    this.itemToEditIndex = index;
    this.resetVal = this.list[index].value;
  }

  /**
   * Closes the edit-view for the currently edited item.
   */
  closeEdit(index: number) {
    if (this.list[index].value.trim().length == 0) {
      this.list[index].value = this.resetVal;
    }

    this.itemToEditIndex = null;
    this.listChange.emit(this.list);
  }

  /**
   * Resets the currently edited item to its previous value.
   * @param index index of the edited item.
   */
  resetItem(index: number) {
    this.list[index].value = this.resetVal;
    this.itemToEditIndex = null;
  }

  /**
   * Sets the item to delete and opens the delete-warning.
   * @param index
   */
  deleteItem(index: number) {
    this.itemToDeleteIndex = index;
    this.showWarning();
  }

  /**
   * Makes the delete-warning visible.
   */
  showWarning() {
    this.dialogVisible = true;
  }

  /**
   * Closes the delete-warning and deletes the item, if the btn-warning-delete was clicked.
   * @param del if true, the item will be removed from the list
   */
  closeWarning(del: boolean) {
    if (del) {
      this.list.splice(this.itemToDeleteIndex!, 1);
      this.listChange.emit(this.list);
    }

    this.dialogVisible = false;
  }
}
