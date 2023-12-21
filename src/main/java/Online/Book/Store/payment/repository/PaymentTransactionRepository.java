package Online.Book.Store.payment.repository;

import Online.Book.Store.payment.enums.PaymentStatus;
import Online.Book.Store.payment.model.PaymentTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Long> {

    Optional<PaymentTransaction> findByPaymentReferenceNumber(String paymentReferenceNumber);

    Page<PaymentTransaction> findByPaymentStatusAndCustomer_Id(PaymentStatus status, Long customerId, Pageable pageable);

    Page<PaymentTransaction> findAll(Pageable pageable);

}
