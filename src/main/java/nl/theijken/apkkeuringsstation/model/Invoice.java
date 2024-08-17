package nl.theijken.apkkeuringsstation.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long invoiceNumber;

    private LocalDate date;

    public double vatPercentage;

    private double vat;

    private double price;

    private double total;

    @OneToOne
    private Ticket ticket;

    @ManyToOne
    private Customer customer;

    public Long getInvoiceNumber() { return invoiceNumber; }

    public void setInvoiceNumber(Long invoiceNumber) { this.invoiceNumber = invoiceNumber; }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getVatPercentage() { return vatPercentage; }

    public void setVatPercentage(double vatPercentage) { this.vatPercentage = vatPercentage; }

    public double getVat() {
        return vat;
    }

    public void setVat(double vat) {
        this.vat = vat;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Ticket getTicket() { return ticket; }

    public void setTicket(Ticket ticket) { this.ticket = ticket; }

    public Customer getCustomer() { return customer; }

    public void setCustomer(Customer customer) { this.customer = customer; }
}
