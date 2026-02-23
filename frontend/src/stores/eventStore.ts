import { create } from "zustand";
import type { Event } from "../common/model";
import {getEvents} from "../services/eventService.ts";


type EventStore = {
    events: Event[];
    selectedEvent: Event | null;

    page: number;
    size: number;
    totalPages: number;
    sort: string;

    loading: boolean;
    error: string | null;

    fetchEvents: () => Promise<void>;
};

export const useEventStore = create<EventStore>((set, get) => ({
    events: [],
    selectedEvent: null,

    page: 0,
    size: 10,
    totalPages: 0,
    sort: "eventDate,asc",

    loading: false,
    error: null,

    fetchEvents: async () => {
        const { page, size, sort } = get();
        set({ loading: true, error: null });

        try {
            const data = await getEvents({ page, size, sort });
            set({
                events: data,
                loading: false,
            });
        } catch (err) {
            console.log(err)
            set({
                error: "Failed to load events",});
        } finally {
            set({loading: false})
        }
    },
}));