package lt.mxs.rx.vmi.taxpayer.impl;

import io.reactivex.Maybe;
import io.reactivex.Single;
import lt.mxs.rx.vmi.taxpayer.Constants;
import lt.mxs.rx.vmi.taxpayer.ManagedTaxPayers;
import lt.mxs.rx.vmi.taxpayer.RepositoryException;
import lt.mxs.rx.vmi.taxpayer.RxTaxPayerService;
import lt.mxs.rx.vmi.taxpayer.TaxPayer;
import lt.mxs.rx.vmi.taxpayer.TaxPayerPermissionRepository;
import lt.mxs.rx.vmi.taxpayer.TaxPayerRepository;

import java.util.Collections;

public class DefaultRxTaxPayerService implements RxTaxPayerService {

    private final TaxPayerRepository localRepository;
    private final TaxPayerRepository remoteRepository;
    private final TaxPayerPermissionRepository taxPayerPermissionRepository;

    public DefaultRxTaxPayerService(TaxPayerRepository localRepository, TaxPayerRepository remoteRepository, TaxPayerPermissionRepository taxPayerPermissionRepository) {
        this.localRepository = localRepository;
        this.remoteRepository = remoteRepository;
        this.taxPayerPermissionRepository = taxPayerPermissionRepository;
    }

    @Override
    public Maybe<TaxPayer> findTaxPayer(String code) {
        Maybe<TaxPayer> remote = findTaxPayer(code, remoteRepository)
                .onErrorResumeNext(ex -> ex instanceof RepositoryException ? Maybe.empty() : Maybe.error(ex))
                .timeout(Constants.REMOTE_TAX_PAYER_TIMEOUT_VALUE, Constants.REMOTE_TAX_PAYER_TIMEOUT_UNIT, Maybe.empty());

        return findTaxPayer(code, localRepository)
                .timeout(Constants.LOCAL_TAX_PAYER_TIMEOUT_VALUE, Constants.LOCAL_TAX_PAYER_TIMEOUT_UNIT, remote)
                .onErrorResumeNext(ex -> ex instanceof RepositoryException ? remote : Maybe.error(ex));
    }

    private Maybe<TaxPayer> findTaxPayer(String code, TaxPayerRepository repository) {
        return Maybe.defer(() -> {
           TaxPayer result = repository.findTaxPayer(code);
           if (result != null) {
               return Maybe.just(result);
           }
           return Maybe.empty();
        });
    }

    @Override
    public Single<ManagedTaxPayers> getManagedTaxPayers(String code) {
        return Single.defer(() -> Single.just(taxPayerPermissionRepository.getManagedTaxPayerCodes(code)))
                .map(items -> new ManagedTaxPayers(items, true))
                .onErrorResumeNext(ex -> ex instanceof RepositoryException
                        ? Single.just(new ManagedTaxPayers(Collections.emptyList(), false))
                        : Single.error(ex))
                .timeout(Constants.PERMISSIONS_TIMEOUT_VALUE, Constants.PERMISSIONS_TIMEOUT_UNIT, Single.just(new ManagedTaxPayers(Collections.emptyList(), false)));
    }
}
