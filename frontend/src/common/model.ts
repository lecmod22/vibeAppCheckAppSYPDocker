export type Artist = {
    id: number;
    firstname: string;
    lastname: string;
    description: string;
    imageUrl: string;
};

export type Event = {
    id: number;
    title: string;
    location: string;
    eventDate: string; // ISO string (LocalDate from backend)
    imageUrl: string;
    avgRating: number;
    ratingCount: number;
    artists?: Artist[];
};