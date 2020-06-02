package com.openclassrooms.entrevoisins.events;

import com.openclassrooms.entrevoisins.model.Neighbour;

/**
 * Entrevoisins - com.openclassrooms.entrevoisins.events
 * 02/06/20
 * Author Bryan Ferreras-Roca
 */
public class AddNeighbourToFavEvent {
    /**
     * Neighbour to add to favorites
     */
    private Neighbour mNeighbour;

    /**
     * Constructor of POJO
     * @param pNeighbour
     */
    public AddNeighbourToFavEvent(Neighbour pNeighbour) {
        this.mNeighbour = pNeighbour;
    }

    /**
     * Get the neighbour to add
     * @return
     */
    public Neighbour getNeighbour() {
        return mNeighbour;
    }
}
