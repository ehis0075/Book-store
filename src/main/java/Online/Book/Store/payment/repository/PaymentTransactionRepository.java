package Online.Book.Store.payment.repository;

import Online.Book.Store.payment.enums.PAYMENTSTATUS;
import Online.Book.Store.payment.model.PaymentTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Long> {

    Optional<PaymentTransaction> findByPaymentReferenceNumber(String paymentReferenceNumber);

    Page<PaymentTransaction> findByPaymentStatusAndCustomerEmail(PAYMENTSTATUS status, String customerEmail, Pageable pageable);

}
