package nl.theijken.apkkeuringsation.controller;

import jakarta.validation.Valid;
import nl.theijken.apkkeuringsation.dto.CustomerDto;
import nl.theijken.apkkeuringsation.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Object> createCustomer(@Valid @RequestBody CustomerDto customerDto, BindingResult br) {

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
            customerDto = service.createCustomer(customerDto);

            URI uri = URI.create(
                    ServletUriComponentsBuilder
                            .fromCurrentRequest()
                            .path("/" + customerDto.id).toUriString());

            return ResponseEntity.created(uri).body(customerDto);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable("id") Long id, @RequestBody CustomerDto customerDto) {
        return ResponseEntity.ok(service.updateCustomer(id, customerDto));
    }

//    @PutMapping("/{id}/invoice/{invoiceId}")
//    public ResponseEntity<Object> assignInvoiceToCustomer(@PathVariable("id") Long id, @PathVariable("invoiceId") Long invoiceId) {
//        return ResponseEntity.ok(service.assignInvoiceToCustomer(id, invoiceId));
//    }

    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAllCustomers(){
        return ResponseEntity.ok(service.getCustomers());
    }


    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getAction(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.getCustomer(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable("id") Long id) {
        boolean check = service.deleteCustomer(id);
        if (check) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.badRequest().body("No customer found");
        }
    }
}
