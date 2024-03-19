export interface IEvent {
    id: number;
    hostId: number;
    title: string;
    eventStartAt: Date;
    eventEndAt: Date;
    participateNum: number;
    location: string;
    categoryName: string;
    isActive: boolean;
    isDeleted: boolean;
    image: string;
    originFileName: string;
}

export interface IEventWrite {
    [key:string]: string|any;
    userId: number;
    title: string;
    eventStartAt: Date;
    eventEndAt: Date;
    location: string;
    category: string;
    content: string;
    applyStartAt: Date;
    applyEndAt: Date;
    tickets: IEventTicket[];
    image: File | null;
}

export interface IEventUpdate {
    [key:string]: string|any;
    title: string;
    eventStartAt: Date;
    eventEndAt: Date;
    location: string;
    category: string;
    content: string;
    isActive: boolean;
    applyStartAt: Date;
    applyEndAt: Date;
    ticketList: IEventTicketUpdate[];
}

export interface IEventTicket {
    name: string;
    price: number;
    quantity: number;
}

export interface IEventTicketUpdate {
    id: number;
    name: string;
    price: number;
    quantity: number;
}

export interface IEventTicketDetail extends IEventTicket{
    id: number;
    eventId: number;
    appliedTicketCount: number;
}

export interface IEventDetail{
    id: number;
    hostId: number;
    hostName: string;
    hostPhone: string;
    title: string;
    eventStartAt: Date;
    eventEndAt: Date;
    participateNum: number;
    location: string;
    category: string;
    isActive: boolean;
    content: string;
    applyStartAt: Date;
    applyEndAt: Date;
    views: number;
    tickets: IEventTicketDetail[];
    image: string;
    originFileName: string;
}

export interface IEventBooking {
    eventId: number,
    ticketId: number,
    quantity: number,
    name: string,
    phone: string,
    applicantNum: number,
}

export interface IEventUserBookings{
    applyId: number,
    applicantNum: number,
    date: Date,
    image: string,
    status: string,
    ticketName: string,
    ticketPrice: number,
    title: string,
}

export interface IEventMain {
    Top10CreatedAt: IEvent[],
    Top10ApplyEndAt: IEvent[],
    Top10Views: IEvent[],
}

export interface IEventApplices {
    eventId: string,
    state: string,
    applyId?: string,
    phone?: string,
    order?: string,
    name?: string,
    dateMin?: Date,
    dateMax?: Date,
    priceMin?: string,
    priceMax?: string,
}

export interface IEventApplicesResult {
    applyId: number,
    name: string,
    phone: string,
    price: number,
    date: Date,
    state: string,
}