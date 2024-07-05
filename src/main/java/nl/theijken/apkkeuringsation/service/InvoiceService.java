package nl.theijken.apkkeuringsation.service;

import nl.theijken.apkkeuringsation.dto.InvoiceDto;
import nl.theijken.apkkeuringsation.dto.CarPartDto;
import nl.theijken.apkkeuringsation.exceptions.RecordNotFoundException;
import nl.theijken.apkkeuringsation.model.Invoice;
import nl.theijken.apkkeuringsation.model.CarPart;
import nl.theijken.apkkeuringsation.model.Ticket;
import nl.theijken.apkkeuringsation.repository.InvoiceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
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

    // PUT
    public InvoiceDto updateInvoice(Long invoiceNumber, InvoiceDto invoiceDto) {
        if(!invoiceRepository.existsById(invoiceNumber)) {
            throw new RecordNotFoundException("No invoice found");
        }
        Invoice storedInvoice = invoiceRepository.findById(invoiceNumber).orElse(null);
        storedInvoice.setInvoiceNumber(invoiceDto.invoiceNumber);
        storedInvoice.setDate(invoiceDto.date);
        storedInvoice.setVat(invoiceDto.vat);
        storedInvoice.setPrice(invoiceDto.price);
        storedInvoice.setTotal(invoiceDto.total);
        storedInvoice.setTicket(invoiceDto.ticket);
        return invoiceToDto(invoiceRepository.save(storedInvoice));
    }

    // DTO -> MODEL
    private Invoice dtoToInvoice(InvoiceDto invoiceDto) {
        Invoice invoice = new Invoice();
        invoice.setDate(invoiceDto.date);
        invoice.setVat(invoiceDto.vat);
        invoice.setPrice(invoiceDto.price);
        invoice.setTotal(invoiceDto.total);
        invoice.setTicket(invoiceDto.ticket);
        return invoice;
    }

    // MODEL -> DTO
    private InvoiceDto invoiceToDto(Invoice invoice) {
        InvoiceDto invoiceDto = new InvoiceDto();
        invoiceDto.invoiceNumber = invoice.getInvoiceNumber();
        invoiceDto.date = invoice.getDate();
        invoiceDto.vat = invoice.getVat();
        invoiceDto.price = invoice.getPrice();
        invoiceDto.total = invoice.getTotal();
        invoiceDto.ticket = invoice.getTicket();
        return invoiceDto;
    }

}
