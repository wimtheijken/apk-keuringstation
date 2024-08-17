package nl.theijken.apkkeuringsstation.repository;

import nl.theijken.apkkeuringsstation.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}
