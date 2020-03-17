import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { MyTripAppSharedModule } from 'app/shared/shared.module';
import { MyTripAppCoreModule } from 'app/core/core.module';
import { MyTripAppAppRoutingModule } from './app-routing.module';
import { MyTripAppHomeModule } from './home/home.module';
import { MyTripAppEntityModule } from './entities/entity.module';
import {FlexLayoutModule} from '@angular/flex-layout';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import 'hammerjs';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
        BrowserAnimationsModule,
        FlexLayoutModule,
        BrowserAnimationsModule,
        FlexLayoutModule,
        BrowserAnimationsModule,
        FlexLayoutModule,
    MyTripAppSharedModule,
    MyTripAppCoreModule,
    MyTripAppHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    MyTripAppEntityModule,
    BrowserAnimationsModule,
    MyTripAppAppRoutingModule
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  bootstrap: [MainComponent]
})
export class MyTripAppAppModule {}
