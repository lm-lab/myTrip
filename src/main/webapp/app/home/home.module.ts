import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MyTripAppSharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';

@NgModule({
  imports: [MyTripAppSharedModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent]
})
export class MyTripAppHomeModule {}
