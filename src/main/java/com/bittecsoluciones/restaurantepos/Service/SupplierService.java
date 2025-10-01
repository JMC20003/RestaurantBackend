package com.bittecsoluciones.restaurantepos.Service;

import com.bittecsoluciones.restaurantepos.Entity.Supplier;
import com.bittecsoluciones.restaurantepos.Entity.UnitMeasure;

import java.util.List;
import java.util.Optional;

public interface SupplierService {
    List<Supplier> getAllSuppliers();
    Optional<Supplier> getSupplierById(Long id);
    Supplier createSupplier(Supplier supplier);
    Supplier updateSupplier(Long id, Supplier supplier);
    void deleteSupplier(Long id);
    Optional<Supplier> findByEmail(String email);
}
