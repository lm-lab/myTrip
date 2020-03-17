import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MyTripAppSharedModule } from 'app/shared/shared.module';
import { tripsRoute } from './mytrip.route';
import { MytripComponent } from './mytrip.component';
// import { MaterialModule } from 'app/material-module';

@NgModule({
  imports: [MyTripAppSharedModule, RouterModule.forChild([tripsRoute])],
  declarations: [MytripComponent]
})
export class MyTripAppHomeModule {}
