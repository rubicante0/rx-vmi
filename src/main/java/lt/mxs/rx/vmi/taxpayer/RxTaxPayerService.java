package lt.mxs.rx.vmi.taxpayer;

import io.reactivex.Maybe;
import io.reactivex.Single;

public interface RxTaxPayerService {

    Maybe<TaxPayer> findTaxPayer(String code);

    Single<ManagedTaxPayers> getManagedTaxPayers(String code);
}
