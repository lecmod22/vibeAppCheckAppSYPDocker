import { create } from "zustand";
import type { Artist } from "../common/model";
import { getArtists } from "../services/artistService";

type ArtistStore = {
    artists: Artist[];
    loading: boolean;
    error: string | null;
    fetchArtists: () => Promise<void>;
};

export const useArtistStore = create<ArtistStore>((set) => ({
    artists: [],
    loading: false,
    error: null,

    fetchArtists: async () => {
        set({ loading: true, error: null });

        try {
            const data = await getArtists();
            set({ artists: data, loading: false });
        } catch (err){
            console.log(err)
            set({ error: "Failed to load artists"});
        } finally {
            set({loading: false})
        }
    },
}));