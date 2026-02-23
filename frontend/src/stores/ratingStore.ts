import { create } from "zustand";
import type {Rating } from "../common/model";

type RatingStore = {
    ratings: Rating[];
}

export const useRatingStore = create<RatingStore>(() => ({
    ratings: []
}));