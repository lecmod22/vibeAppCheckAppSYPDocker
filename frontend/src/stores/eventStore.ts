import { create } from "zustand";
import type { Event } from "../common/model";


type EventStore = {
    events: Event[];
    selectedEvent: Event | null;

    page: number;
    size: number;
};

export const useEventStore = create<EventStore>(() => ({
    events: [],
    selectedEvent: null,

    page: 0,
    size: 10
}));