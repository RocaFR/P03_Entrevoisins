package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.List;

/**

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.List;

/**
 * Entrevoisins - com.openclassrooms.entrevoisins.service
 * 01/06/20
 * Author Bryan Ferreras-Roca
 */
public interface FavoritesNeighboursApiService {
    /**
     * Get all my Favorites Neighbours
     * @return {@link List}
     */
    List<Neighbour> getFavoritesNeighbours();

    /**
     * Deletes a neighbour from my favorites
     * @param neighbour
     */
    void deleteFavoriteNeighbour(Neighbour neighbour);

    /**
     * Add neighbour to my favorites
     * @param neighbour
     */
    void createFavoriteNeighbour(Neighbour neighbour);
}
