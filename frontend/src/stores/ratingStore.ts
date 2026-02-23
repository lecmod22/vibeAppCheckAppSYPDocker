import { create } from "zustand";
import type {Rating } from "../common/model";
import {getRatingsByEvent} from "../services/ratingService.ts";

type RatingStore = {
    ratings: Rating[];

    loading: boolean;
    error: string|null;

    fetchRatings: (eventId: number) => Promise<void>;
}

export const useRatingStore = create<RatingStore>((set) => ({
    ratings: [],

    loading: false,
    error: null,

    fetchRatings: async (eventId: number) => {
        set({ loading: true, error: null });
        try {
            const data = await getRatingsByEvent(eventId);
            set({ ratings: data, loading: false });
        } catch (err) {
            console.log(err);
            set({error: "Failed to load ratings"});
        } finally {
            set({loading: false})
        }
    },
}));