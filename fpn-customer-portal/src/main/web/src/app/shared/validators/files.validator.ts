import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';
import { FileValidateOptions } from '../interfaces/fpn.interface';


const options: FileValidateOptions = {
    maxLength: 5,
    maxSize: 5000000,
    allowMimeTypes: [
        "image/png",
        "image/jpg",
        "image/jpeg",
        "application/pdf"
    ]
};

export function ValidateFiles(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
        if (typeof control.value === "string") {
            return null;
        } else {
            switch (true) {
                case (control.value.length == 0):
                    return null;
                case (control.value.length > options.maxLength):
                    return {
                        maxLength: true
                    };
                case (!control.value.every((file: File) => file.size < options.maxSize)):
                    return {
                        maxSize: true
                    };
                case (!control.value.every((file: File) => options.allowMimeTypes.includes(file.type))):
                    return {
                        allowMimeTypes: true
                    };
                default:
                    return null;
            };
        };
    };
};