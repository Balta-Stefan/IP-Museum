import { UserRole } from "./UserRole";

export interface LoginDetails{
    userID: number;
    username: string;
    firstName: string;
    lastName: string;
    password: string;
    email: string;
    role: string;
    active: boolean;
    token: string;
}