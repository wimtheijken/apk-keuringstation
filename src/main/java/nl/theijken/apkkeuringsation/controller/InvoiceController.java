package nl.theijken.apkkeuringsation.controller;

import jakarta.validation.Valid;
import nl.theijken.apkkeuringsation.dto.InvoiceDto;
import nl.theijken.apkkeuringsation.service.InvoiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    private final InvoiceService service;

    public InvoiceController(InvoiceService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Object> createInvoice(@Valid @RequestBody InvoiceDto  invoiceDto, BindingResult br) {

        if (br.hasFieldErrors()) {
            StringBuilder sb = new StringBuilder();
            for (FieldError fe : br.getFieldErrors()) {
                sb.append(fe.getField());
                sb.append(" : ");
                sb.append(fe.getDefaultMessage());
                sb.append("\n");
            }
            return ResponseEntity.badRequest().body(sb.toString());
        } else {
            invoiceDto = service.createInvoice(invoiceDto);

            URI uri = URI.create(
                    ServletUriComponentsBuilder
                            .fromCurrentRequest()
                            .path("/" +  invoiceDto.invoiceNumber).toUriString());

            return ResponseEntity.created(uri).body(invoiceDto);
        }
    }

    @PutMapping("/{invoiceNumber}")
    public ResponseEntity<InvoiceDto> updateInvoice(@PathVariable("invoiceNumber") Long invoiceNumber, @RequestBody InvoiceDto  invoiceDto) {
        return ResponseEntity.ok(service.updateInvoice(invoiceNumber,  invoiceDto));
    }

    @GetMapping
    public ResponseEntity<List<InvoiceDto>> getAllInvoices() {
        return ResponseEntity.ok(service.getInvoices());
    }

    @GetMapping("/{invoiceNumber}")
    public ResponseEntity<InvoiceDto> getInvoice(@PathVariable("invoiceNumber") Long invoiceNumber) {
        return ResponseEntity.ok(service.getInvoice(invoiceNumber));
    }

    @DeleteMapping("/{invoiceNumber}")
    public ResponseEntity<Object> deleteInvoice(@PathVariable("invoiceNumber") Long invoiceNumber) {
        boolean check = service.deleteInvoice(invoiceNumber);
        if (check) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.badRequest().body("No invoice found");
        }
    }

}
