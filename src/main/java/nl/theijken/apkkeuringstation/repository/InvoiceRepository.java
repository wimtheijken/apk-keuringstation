package nl.theijken.apkkeuringstation.repository;

import nl.theijken.apkkeuringstation.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}
