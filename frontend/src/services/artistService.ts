import type { Artist } from "../common/model";
import apiClient from "../api/api-client";

export const getArtists = async (): Promise<Artist[]> => {
    const res = await apiClient.get("/api/artists");
    return res.data;
};