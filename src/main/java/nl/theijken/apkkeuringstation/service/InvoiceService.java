package nl.theijken.apkkeuringstation.service;

import nl.theijken.apkkeuringstation.dto.InvoiceDto;
import nl.theijken.apkkeuringstation.exceptions.RecordNotFoundException;
import nl.theijken.apkkeuringstation.model.*;
import nl.theijken.apkkeuringstation.repository.CustomerRepository;
import nl.theijken.apkkeuringstation.repository.InvoiceRepository;
import nl.theijken.apkkeuringstation.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.text.DecimalFormat;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final TicketRepository ticketRepository;
    private final CustomerRepository customerRepository;
    private final TicketService ticketService;
    private final CustomerService customerService;

    private static final DecimalFormat decfor = new DecimalFormat("0.00");

    public InvoiceService(InvoiceRepository invoiceRepository,
                          TicketRepository ticketRepository,
                          CustomerRepository customerRepository,
                          TicketService ticketService,
                          CustomerService customerService) {
        this.invoiceRepository = invoiceRepository;
        this.ticketRepository = ticketRepository;
        this.customerRepository = customerRepository;
        this.ticketService = ticketService;
        this.customerService = customerService;
    }

    // POST
    public InvoiceDto createInvoice(InvoiceDto invoiceDto) {
        Invoice invoice = dtoToInvoice(invoiceDto);
        Invoice savedInvoice = invoiceRepository.save(invoice);
        return invoiceToDto(savedInvoice);
    }

    // GET ALL
    public List<InvoiceDto> getInvoices() {
        List<Invoice> invoices = invoiceRepository.findAll();
        List<InvoiceDto> invoiceDtos = new ArrayList<>();
        for( Invoice invoice : invoices ) {
            InvoiceDto invoiceDto = invoiceToDto(invoice);
            invoiceDtos.add(invoiceDto);
        }
        return invoiceDtos;
    }

    // GET ONE
    public InvoiceDto getInvoice(Long invoiceNumber) {
        Optional<Invoice> invoice = invoiceRepository.findById(invoiceNumber);
        if( invoice.isPresent() ){
            return invoiceToDto(invoice.get());
        } else {
            throw new RecordNotFoundException("No invoice found");
        }
    }

    // DELETE
    public boolean deleteInvoice(Long invoiceNumber) {
        if(invoiceRepository.existsById(invoiceNumber)) {
            invoiceRepository.deleteById(invoiceNumber);
            return true;
        }
        return false;
    }

    // DTO -> MODEL
    private Invoice dtoToInvoice(InvoiceDto invoiceDto) {
        Invoice invoice = new Invoice();
        invoice.setDate(LocalDate.now());
        invoice.setVatPercentage(invoiceDto.vatPercentage);
        if(!ticketRepository.existsById(invoiceDto.ticketId)) {
            throw new RecordNotFoundException("No ticket found");
        }
        Ticket ticket = ticketRepository.findById(invoiceDto.ticketId).orElse(null);
        List<Invoice> invoices = invoiceRepository.findAll();
        for (Invoice invoice2 : invoices){
            if(ticket == invoice2.getTicket()) {
                throw new RecordNotFoundException("Ticket is already used");
            }
        }
        assert ticket != null;
        invoice.setPrice(ticket.getPrice());
        double rounded = Math.round(ticket.getPrice() * invoiceDto.vatPercentage);
        double vat = rounded / 100;
        invoice.setVat(vat);
        invoice.setTotal(ticket.getPrice() + vat );
        if(invoiceDto.ticketId  != null){
            Optional<Ticket> ticket2 = ticketRepository.findById(invoiceDto.ticketId);
            ticket2.ifPresent(invoice::setTicket);
        }
        Optional<Customer> customer = customerRepository.findById(ticket.getCustomerId());
        customer.ifPresent(invoice::setCustomer);
        return invoice;
    }

    // MODEL -> DTO
    private InvoiceDto invoiceToDto(Invoice invoice) {
        InvoiceDto invoiceDto = new InvoiceDto();
        invoiceDto.invoiceNumber = invoice.getInvoiceNumber();
        invoiceDto.date = invoice.getDate();
        invoiceDto.vatPercentage = invoice.getVatPercentage();
        invoiceDto.price = invoice.getPrice();
        invoiceDto.vat = invoice.getVat();
        invoiceDto.total = invoice.getTotal();
        if (invoice.getTicket() != null) {
        invoiceDto.ticket = ticketService.ticketToDto(invoice.getTicket());
        }
        if (invoice.getCustomer() != null) {
            invoiceDto.customer = customerService.customerToDto(invoice.getCustomer());
        }
        return invoiceDto;
    }
}
