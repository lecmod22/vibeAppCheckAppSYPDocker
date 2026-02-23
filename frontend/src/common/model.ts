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
    eventDate: string;
    imageUrl: string;
    avgRating: number;
    ratingCount: number;
    artists?: Artist[];
};

export type Rating = {
    id: number;
    stars: number;
    comment: string;
    createdAt: string;
};


export type CreateRatingDto = {
    stars: number;
    comment: string;
};