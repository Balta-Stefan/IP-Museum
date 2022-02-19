import { LoginDetails } from "../models/LoginDetails";

export class AuthorizationUtils{
    static userLogin(userDetails: LoginDetails): void{
        localStorage.setItem("userDetails", JSON.stringify(userDetails));
    }

    static getUser(): LoginDetails | null{
        const details = localStorage.getItem("userDetails");
        if(!details){
            return null;
        }

        return JSON.parse(details);
    }
}