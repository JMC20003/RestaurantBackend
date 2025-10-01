package com.bittecsoluciones.restaurantepos.ServiceImpl;


import com.bittecsoluciones.restaurantepos.Entity.PaymentMethod;
import com.bittecsoluciones.restaurantepos.Entity.PaymentMethod;
import com.bittecsoluciones.restaurantepos.Exceptions.ResourceNotFoundException;
import com.bittecsoluciones.restaurantepos.Repository.PaymentMethodRepository;
import com.bittecsoluciones.restaurantepos.Service.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentMethodServiceImpl implements PaymentMethodService {
    
    @Autowired
    private PaymentMethodRepository paymentMethodRepository;
    
    @Override
    public List<PaymentMethod> getAllPaymentMethods() {
        return paymentMethodRepository.findAll();
    }

    @Override
    public Optional<PaymentMethod> getPaymentMethodById(Long id) {
        return paymentMethodRepository.findById(id);
    }

    @Override
    public PaymentMethod createPaymentMethod(PaymentMethod paymentMethod) {
        return paymentMethodRepository.save(paymentMethod);
    }

    @Override
    public PaymentMethod updatePaymentMethod(Long id, PaymentMethod paymentMethod) {
        // Verificar que exista
        PaymentMethod existing = paymentMethodRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Metodo de pago con id " + id + " no encontrada"));

        // Actualizar los campos necesarios
        existing.setName(paymentMethod.getName());
        existing.setActive(paymentMethod.getActive());
        return paymentMethodRepository.save(existing);
    }

    @Override
    public void deletePaymentMethod(Long id) {
        paymentMethodRepository.deleteById(id);
    }
}
