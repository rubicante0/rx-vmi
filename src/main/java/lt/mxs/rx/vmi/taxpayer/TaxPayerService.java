package lt.mxs.rx.vmi.taxpayer;

import java.util.Optional;

public interface TaxPayerService {

    Optional<TaxPayer> findTaxPayer(String code);
}
