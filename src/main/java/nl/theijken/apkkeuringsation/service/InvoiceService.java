package nl.theijken.apkkeuringsation.service;

import nl.theijken.apkkeuringsation.repository.InvoiceRepository;
import org.springframework.stereotype.Service;

@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepos;

    public InvoiceService(InvoiceRepository repos) {
        this.invoiceRepos = repos;
    }

}
