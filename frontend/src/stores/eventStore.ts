import { create } from "zustand";
import type { Event } from "../common/model";
import {getEventById, getEvents, getEventsByArtist} from "../services/eventService.ts";


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
    fetchEventsByArtist: (artistId: number) => Promise<void>;
    fetchEventById: (eventId: number) => Promise<void>;

    setPage: (page: number) => void;
    setSort: (sort: string) => void;
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
            set({error: "Failed to load events"});
        } finally {
            set({loading: false})
        }
    },

    fetchEventsByArtist: async (artistId: number) => {
        const { page, size, sort } = get();
        set({ loading: true, error: null });

        try {
            const data = await getEventsByArtist(artistId, { page, size, sort });
            set({
                events: data,
                loading: false,
            });
        } catch (err) {
            console.log(err)
            set({error: "Failed to load events by artist"});
        } finally {
            set({loading: false});
        }
    },

    fetchEventById: async (eventId: number) => {
        set({ loading: true, error: null });

        try {
            const data = await getEventById(eventId);
            set({
                selectedEvent: data,
                loading: false,
            });
        } catch (err) {
            console.log(err)
            set({error: "Failed to load event"});
        } finally {
            set({loading: false});
        }
    },

    setPage: (page: number) => set({page}),
    setSort: (sort: string) => set({sort})
}));