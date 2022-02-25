export enum PaymentStatus {
    SUCCESSFUL='SUCCESSFUL',
    UNSUCCESSFUL='UNSUCCESSFUL'
};

export interface BankTransactionDTO{
    status: PaymentStatus;
    id: string;
    receiverID: number;
    amount: number;
    timestamp: string;
    redirect: string;
    notificationURL: string;
    senderID: number;
    scratchString: string;
}