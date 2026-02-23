import type { Rating } from "../common/model";
import apiClient from "../api/api-client";


export const getRatingsByEvent = async (eventId: number): Promise<Rating[]> => {
    const res = await apiClient.get(`/api/ratings/event/${eventId}`);
    return res.data;
};