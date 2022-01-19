export interface FPN{
    fpnNumber: number;
    rnNumber: number;
    firstName?: string;
    surname?: string;
    address?: FpnAddress;
    chargeAmount?: number;
    isAppealSubmitted?: boolean;
    offence?: FpnOffence;
}
interface FpnAddress{
    flat?: number;
    houseName?: string;
    houseNumber?: number;
    street?: string;
    locality?: string;
    province?: string;
}
interface FpnOffence{
    title?: string;
    description?: string;
    appealLetterText?: string;
}

export interface searchFPN {
    fpnNumber: number | string;
    rnNumber: number | string;
}

export interface FileValidateOptions {
    maxLength: number;
    maxSize: number;
    allowMimeTypes: string[];
}
