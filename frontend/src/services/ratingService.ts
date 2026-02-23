import type {CreateRatingDto, Rating} from "../common/model";
import apiClient from "../api/api-client";


export const getRatingsByEvent = async (eventId: number): Promise<Rating[]> => {
    const res = await apiClient.get(`/api/ratings/event/${eventId}`);
    return res.data;
};

export const createRating = async (
    eventId: number,
    data: CreateRatingDto
): Promise<Rating> => {
    const res = await apiClient.post(`/api/ratings/${eventId}`, data);
    return res.data;
};