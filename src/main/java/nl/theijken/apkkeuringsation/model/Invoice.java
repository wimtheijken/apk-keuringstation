package nl.theijken.apkkeuringsation.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "invoices")
public class Invoice {
    @Id
    @SequenceGenerator(name="invoice_number", initialValue=24000)
    private Long number;

    private LocalDate date;

    private double vat;

    private double price;

    @OneToOne
    private Ticket ticket;


}
