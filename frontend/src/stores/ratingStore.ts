import { create } from "zustand";
import type {CreateRatingDto, Rating} from "../common/model";
import {createRating, getRatingsByEvent} from "../services/ratingService.ts";

type RatingStore = {
    ratings: Rating[];

    loading: boolean;
    error: string|null;

    fetchRatings: (eventId: number) => Promise<void>;

    addRating: (eventId: number, data: CreateRatingDto) => Promise<void>;
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

    addRating: async (eventId: number, data: CreateRatingDto) => {
        set({ loading: true, error: null });
        try {
            await createRating(eventId, data);
            const refreshed = await getRatingsByEvent(eventId);
            set({ ratings: refreshed, loading: false });
        } catch (err) {
            console.log(err)
            set({error: "Failed to add rating"});
        } finally {
            set({loading: false})
        }
    },
}));