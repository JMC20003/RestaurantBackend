package com.bittecsoluciones.restaurantepos.Service;

import com.bittecsoluciones.restaurantepos.Entity.TableRestaurant;

import java.util.List;
import java.util.Optional;

public interface TableRestaurantService {
    List<TableRestaurant> getAllTableRestaurants();
    Optional<TableRestaurant> getTableRestaurantById(Long id);
    TableRestaurant createTableRestaurant(TableRestaurant tableRestaurant);
    TableRestaurant updateTableRestaurant(Long id, TableRestaurant tableRestaurant);
    void deleteTableRestaurant(Long id);
}
