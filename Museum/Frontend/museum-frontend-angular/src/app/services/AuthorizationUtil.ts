import { LoginDetails } from "../models/LoginDetails";

export class AuthorizationUtils{
    static userLogin(userDetails: LoginDetails, insertJwtIntoLocalStorage: boolean): void{
        localStorage.setItem("userDetails", JSON.stringify(userDetails));
        if(insertJwtIntoLocalStorage){
            localStorage.setItem("jwt", userDetails.jwt);
        }
    }

    static getUser(): LoginDetails | null{
        const details = localStorage.getItem("userDetails");
        if(!details){
            return null;
        }

        return JSON.parse(details);
    }
}