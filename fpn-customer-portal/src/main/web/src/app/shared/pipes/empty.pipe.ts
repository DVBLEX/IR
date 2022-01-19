import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'empty'
})
export class EmptyPipe implements PipeTransform {

  transform(value: unknown, fill?: string): unknown {
    if(fill){
      return value ? value : fill;
    }
    return value ? value : '';
  }

}
