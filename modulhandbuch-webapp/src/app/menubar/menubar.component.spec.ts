import { ComponentFixture, TestBed } from '@angular/core/testing';
import { DOCUMENT } from '@angular/common';
import { MenubarComponent } from './menubar.component';

describe('MenubarComponent', () => {
  let component: MenubarComponent;
  let fixture: ComponentFixture<MenubarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MenubarComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MenubarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  /**
   * Testfall A2:UT1 Testen, ob Komponente erzeugt wird.
   */
  it('should create', () => {
    expect(component).toBeTruthy();
  });

  /**
   * Testfall A2:UT2 Testen, ob ein Click auf das Hamburger-Icon die Seitenleiste toggelt.
   */
  it('should toggle the showSidebar-flag if the hamburger-icon is clicked', () => {
    let document = TestBed.inject(DOCUMENT);
    let flag = component.showSidebar;

    let btn = document.getElementById("btn-hamburger") as HTMLButtonElement;
    btn.click();

    expect(component.showSidebar).toBe(!flag);
  });

  /**
   * Testfall A2:UT3 Testen, ob das Hamburger-Icon nach einem Click für 500ms deaktiviert bleibt.
   */
  it('should disable the hamburger-icon for 500ms after a click', async () => {
    let document = TestBed.inject(DOCUMENT);
    let flag = component.showSidebar;

    let btn = document.getElementById("btn-hamburger") as HTMLButtonElement;
    expect(btn.disabled).toBeFalse();
    btn.click();

    expect(component.showSidebar).toBe(!flag);

    await new Promise(f => setTimeout(f, 250));
    expect(btn.disabled).toBeTrue();
    btn.click();

    expect(component.showSidebar).toBe(!flag);

    await new Promise(f => setTimeout(f, 250));
    expect(btn.disabled).toBeFalse();
    btn.click();

    expect(component.showSidebar).toBe(flag);
  });
});
