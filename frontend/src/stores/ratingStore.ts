import { create } from "zustand";
import type {Rating } from "../common/model";

type RatingStore = {
    ratings: Rating[];
    loading: boolean;
}

export const useRatingStore = create<RatingStore>(() => ({
    ratings: [],
    loading: false
}));