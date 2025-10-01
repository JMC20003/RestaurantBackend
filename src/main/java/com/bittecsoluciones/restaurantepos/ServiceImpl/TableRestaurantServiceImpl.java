package com.bittecsoluciones.restaurantepos.ServiceImpl;

import com.bittecsoluciones.restaurantepos.Entity.TableRestaurant;
import com.bittecsoluciones.restaurantepos.Entity.TableRestaurant;
import com.bittecsoluciones.restaurantepos.Exceptions.ResourceNotFoundException;
import com.bittecsoluciones.restaurantepos.Repository.TableRestaurantRepository;
import com.bittecsoluciones.restaurantepos.Service.TableRestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TableRestaurantServiceImpl implements TableRestaurantService {
    
    @Autowired
    private TableRestaurantRepository tableRestaurantRepository;
    @Override
    public List<TableRestaurant> getAllTableRestaurants() {
        return tableRestaurantRepository.findAll();
    }

    @Override
    public Optional<TableRestaurant> getTableRestaurantById(Long id) {
        return tableRestaurantRepository.findById(id);
    }

    @Override
    public TableRestaurant createTableRestaurant(TableRestaurant tableRestaurant) {
        tableRestaurant.setCreatedAt(LocalDateTime.now());
        return tableRestaurantRepository.save(tableRestaurant);
    }

    @Override
    public TableRestaurant updateTableRestaurant(Long id, TableRestaurant tableRestaurant) {

        // Verificar que exista
        TableRestaurant existing = tableRestaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Area de atencion con id " + id + " no encontrada"));

        // Actualizar los campos necesarios
        existing.setTableNumber(tableRestaurant.getTableNumber());
        existing.setCapacity(tableRestaurant.getCapacity());
        existing.setLocation(tableRestaurant.getLocation());
        existing.setActive(tableRestaurant.getActive());
        return tableRestaurantRepository.save(existing);
    }

    @Override
    public void deleteTableRestaurant(Long id) {
        tableRestaurantRepository.deleteById(id);
    }
}
