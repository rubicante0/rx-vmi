package lt.mxs.rx.vmi.web;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import lt.mxs.rx.vmi.taxpayer.ManagedTaxPayers;
import lt.mxs.rx.vmi.taxpayer.RxTaxPayerService;
import lt.mxs.rx.vmi.taxpayer.TaxPayer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rx-tax-payer")
public class RxTaxPayerController {

    private final RxTaxPayerService taxPayerService;

    public RxTaxPayerController(RxTaxPayerService taxPayerService) {
        this.taxPayerService = taxPayerService;
    }

    @GetMapping("{code}")
    public TaxPayerResponse getInformation(@PathVariable("code") String code) {
        Maybe<TaxPayerInformation> main = taxPayerService.findTaxPayer(code)
                .switchIfEmpty(Maybe.just(new TaxPayer(code, null)))
                .map(taxPayer -> new TaxPayerInformation(taxPayer.getCode(), taxPayer.getName()));

        Maybe<ManagedTaxPayers> managed = taxPayerService.getManagedTaxPayers(code).toMaybe();
        return main.zipWith(managed, (taxPayer, list) -> {
            return Observable.fromIterable(list.getCodes())
                    .flatMapMaybe(managedCode -> taxPayerService.findTaxPayer(managedCode)
                            .switchIfEmpty(Maybe.just(new TaxPayer(managedCode, null))))
                    .map(managedTaxPayer -> new TaxPayerInformation(managedTaxPayer.getCode(), managedTaxPayer.getName()))
                    .toList()
                    .map(converted -> new TaxPayerResponse(taxPayer, converted, list.isAuthoritative()));
        }).flatMapSingle(item -> item)
                .blockingGet();
    }
}
