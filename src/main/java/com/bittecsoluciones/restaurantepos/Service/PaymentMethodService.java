package com.bittecsoluciones.restaurantepos.Service;


import com.bittecsoluciones.restaurantepos.Entity.PaymentMethod;
import com.bittecsoluciones.restaurantepos.Entity.PaymentMethod;

import java.util.List;
import java.util.Optional;

public interface PaymentMethodService {
    List<PaymentMethod> getAllPaymentMethods();
    Optional<PaymentMethod> getPaymentMethodById(Long id);
    PaymentMethod createPaymentMethod(PaymentMethod paymentMethod);
    PaymentMethod updatePaymentMethod(Long id, PaymentMethod paymentMethod);
    void deletePaymentMethod(Long id);
}
