import type { Event } from "../common/model";
import apiClient from "../api/api-client";

export const getEvents = async (params?: {
    page?: number;
    size?: number;
    sort?: string;
}): Promise<Event[]> => {
    const res = await apiClient.get("/api/events", { params });
    return res.data.content ?? res.data;
};