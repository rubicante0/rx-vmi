package lt.mxs.rx.vmi.web;

import lt.mxs.rx.vmi.taxpayer.ManagedTaxPayers;
import lt.mxs.rx.vmi.taxpayer.TaxPayer;
import lt.mxs.rx.vmi.taxpayer.TaxPayerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/tax-payer")
public class TaxPayerController {

    private final TaxPayerService taxPayerService;

    public TaxPayerController(TaxPayerService taxPayerService) {
        this.taxPayerService = taxPayerService;
    }

    @GetMapping("{code}")
    public TaxPayerResponse getInformation(@PathVariable("code") String code) {
        TaxPayer taxPayer = taxPayerService.findTaxPayer(code)
                .orElseGet(() -> new TaxPayer(code, null));

        ManagedTaxPayers managed = taxPayerService.getManagedTaxPayers(code);

        return new TaxPayerResponse(fromTaxPayer(taxPayer), managed.getCodes().stream()
                .map(managedCode -> taxPayerService.findTaxPayer(managedCode)
                        .orElseGet(() -> new TaxPayer(code, null)))
                .map(this::fromTaxPayer)
                .collect(Collectors.toList()), managed.isAuthoritative());
    }

    private TaxPayerInformation fromTaxPayer(TaxPayer payer) {
        return new TaxPayerInformation(payer.getCode(), payer.getName());
    }
}