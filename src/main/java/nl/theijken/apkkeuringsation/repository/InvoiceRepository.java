package nl.theijken.apkkeuringsation.repository;

import nl.theijken.apkkeuringsation.model.Car;
import nl.theijken.apkkeuringsation.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    public List<Invoice> findByDateAfter(LocalDate date);
}
