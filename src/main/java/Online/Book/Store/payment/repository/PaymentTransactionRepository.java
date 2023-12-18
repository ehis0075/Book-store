package Online.Book.Store.payment.repository;

import Online.Book.Store.payment.model.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Long> {

    Optional<PaymentTransaction> findByPaymentReferenceNumber(String paymentReferenceNumber);

}
