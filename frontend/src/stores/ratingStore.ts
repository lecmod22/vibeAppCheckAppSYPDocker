import { create } from "zustand";
import type {Rating } from "../common/model";

type RatingStore = {
    ratings: Rating[];

    loading: boolean;
    error: string|null;
}

export const useRatingStore = create<RatingStore>(() => ({
    ratings: [],
    loading: false,
    error: null
}));