package nl.theijken.apkkeuringsation.repository;

import nl.theijken.apkkeuringsation.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}
