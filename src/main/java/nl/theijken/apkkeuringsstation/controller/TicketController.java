package nl.theijken.apkkeuringsstation.controller;

import jakarta.validation.Valid;
import nl.theijken.apkkeuringsstation.dto.TicketDto;
import nl.theijken.apkkeuringsstation.service.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {
    private final TicketService service;

    public TicketController(TicketService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Object> createTicket(@Valid @RequestBody TicketDto ticketDto, BindingResult br) {

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
            ticketDto = service.createTicket(ticketDto);

            URI uri = URI.create(
                    ServletUriComponentsBuilder
                            .fromCurrentRequest()
                            .path("/" + ticketDto.id).toUriString());

            return ResponseEntity.created(uri).body(ticketDto);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TicketDto> updateTicket(@PathVariable("id") Long id, @RequestBody TicketDto ticketDto) {
        return ResponseEntity.ok(service.updateTicket(id, ticketDto));
    }

    @PutMapping("/{id}/action/{actionId}")
    public ResponseEntity<Object> assignActionToTicket(@PathVariable("id") Long id, @PathVariable("actionId") Long actionId) {
        return ResponseEntity.ok(service.assignActionToTicket(id, actionId));
    }

    @GetMapping
    public ResponseEntity<List<TicketDto>> getAllTickets(){
        return ResponseEntity.ok(service.getTickets());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketDto> getTicket(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.getTicket(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTickets(@PathVariable("id") Long id) {
        boolean check = service.deleteTicket(id);
        if (check) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.badRequest().body("No ticket found");
        }
    }
}
