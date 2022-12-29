import { ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';

import { DynamicListComponent } from './dynamic-list.component';
import { list } from './mock-list-data';

describe('DynamicListComponent', () => {
  let component: DynamicListComponent;
  let fixture: ComponentFixture<DynamicListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DynamicListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DynamicListComponent);
    component = fixture.componentInstance;

    component.header = "Test";
    component.list = list;

    fixture.detectChanges();
  });

  /**
   * Testfall A18.1:UT1 Testen, ob Komponente erzeugt wird.
   */
  it('should create', () => {
    expect(component).toBeTruthy();
  });

  /**
   * Testfall A18.1:UT2 Testen, ob lediglich nicht-leere Zeichenketten zur Liste hinzugefügt werden können.
   */
  it('should only allow not-empty strings to be added to the list', async () => {

    // Hinzufügen eines leeren Wertes
    let inputVal = "    ";
    let length = component.list.length;

    component.newVal = inputVal;
    component.addItem();
    fixture.detectChanges();

    expect(component.newVal).toEqual("");
    expect(component.list.length).toBe(length);
    expect(component.valid).toBeFalse();

    await new Promise(f => setTimeout(f, 3000));

    fixture.detectChanges();
    expect(component.valid).toBeTrue();

    // Hinzufügen eines nicht-leeren Wertes
    inputVal = "Item 6";

    component.newVal = inputVal;
    component.addItem();
    fixture.detectChanges();

    expect(component.newVal).toEqual("");
    expect(component.list.length).toBe(length + 1);
  });

  /**
   * Testfall A18.1:UT3 Testen, ob Items bearbeitet werden können (Eingabe wird bestätigt).
   */
  it('should be possible to edit items', () => {
    let newVal = "Element 0";

    component.editItem(0);
    component.list[component.itemToEditIndex!].value = newVal;
    component.closeEdit(0);

    fixture.detectChanges();

    expect(component.list[0].value).toEqual(newVal);
  });

  /**
   * Testfall A18.1:UT4 Testen, ob Items Bearbeitung rückängig gemacht wird, falls Eingabe nicht bestätigt wird.
   */
  it('should reset the item, if it is not manually accepted (click on green tick)', () => {
    let oldVal = component.list[0].value;

    // neuer Wert ist leerer String
    let newVal = "  ";
    component.editItem(0);
    component.list[component.itemToEditIndex!].value = newVal;
    component.closeEdit(0);

    fixture.detectChanges();

    expect(component.list[0].value).toEqual(oldVal);

    // neuer Wert ist nicht-leer, aber vor Bestätigung wird ein auf ein anderes Item gewechselt
    newVal = "Element 0";

    component.editItem(0);
    component.list[component.itemToEditIndex!].value = newVal;
    component.editItem(1);

    fixture.detectChanges();

    expect(component.list[0].value).toEqual(oldVal);

    // neuer Wert ist nicht-leer, aber es wird auf das X geklickt
    component.editItem(0);
    component.list[component.itemToEditIndex!].value = newVal;
    component.resetItem(0);

    fixture.detectChanges();

    expect(component.list[0].value).toEqual(oldVal);
  });

  /**
   * Testfall A18.1:UT5 Testen, ob Items gelöscht werden (Löschen wird bestätigt).
   */
  it('should be possible to delete items', () => {
    let length = component.list.length;

    expect(component.dialogVisible).toBeFalse();

    component.deleteItem(4);

    expect(component.dialogVisible).toBeTrue();

    component.closeWarning(true);

    expect(component.dialogVisible).toBeFalse();
    expect(component.list.length).toBe(length - 1);
  });

  /**
   * Testfall A18.1:UT6 Testen, ob Items nicht gelöscht werden, wenn die Warning über das X geschlossen wird.
   */
  it('should be posible to cancel the deletion of an item', () => {
    let length = component.list.length;

    expect(component.dialogVisible).toBeFalse();

    component.deleteItem(4);

    expect(component.dialogVisible).toBeTrue();

    component.closeWarning(false);

    expect(component.dialogVisible).toBeFalse();
    expect(component.list.length).toBe(length);
  });
});
