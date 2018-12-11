package lt.mxs.rx.vmi.taxpayer.impl;

import lt.mxs.rx.vmi.taxpayer.Constants;
import lt.mxs.rx.vmi.taxpayer.ManagedTaxPayers;
import lt.mxs.rx.vmi.taxpayer.RepositoryException;
import lt.mxs.rx.vmi.taxpayer.TaxPayer;
import lt.mxs.rx.vmi.taxpayer.TaxPayerPermissionRepository;
import lt.mxs.rx.vmi.taxpayer.TaxPayerRepository;
import lt.mxs.rx.vmi.taxpayer.TaxPayerService;
import org.springframework.beans.factory.DisposableBean;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class DefaultTaxPayerService implements TaxPayerService, DisposableBean {

    private final TaxPayerRepository localRepository;
    private final TaxPayerRepository remoteRepository;
    private final TaxPayerPermissionRepository taxPayerPermissionRepository;
    private final ExecutorService executorService;

    public DefaultTaxPayerService(TaxPayerRepository localRepository, TaxPayerRepository remoteRepository, TaxPayerPermissionRepository taxPayerPermissionRepository) {
        this.localRepository = localRepository;
        this.remoteRepository = remoteRepository;
        this.taxPayerPermissionRepository = taxPayerPermissionRepository;
        this.executorService = Executors.newCachedThreadPool();
    }

    @Override
    public void destroy() throws Exception {
        executorService.shutdown();
        executorService.awaitTermination(30, TimeUnit.SECONDS);
    }

    @Override
    public Optional<TaxPayer> findTaxPayer(String code) {
        TaxPayer result = findTaxPayerWithTimeout(code, localRepository, Constants.LOCAL_TAX_PAYER_TIMEOUT_VALUE, Constants.LOCAL_TAX_PAYER_TIMEOUT_UNIT);
        if (result == null) {
            result = findTaxPayerWithTimeout(code, remoteRepository, Constants.REMOTE_TAX_PAYER_TIMEOUT_VALUE, Constants.REMOTE_TAX_PAYER_TIMEOUT_UNIT);
        }
        return Optional.ofNullable(result);
    }

    @Override
    public ManagedTaxPayers getManagedTaxPayers(String code) {
        Future<Collection<String>> future = executorService.submit(() -> taxPayerPermissionRepository.getManagedTaxPayerCodes(code));
        try {
            return new ManagedTaxPayers(future.get(Constants.PERMISSIONS_TIMEOUT_VALUE, Constants.PERMISSIONS_TIMEOUT_UNIT), true);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            if (e.getCause() instanceof RepositoryException) {
                return new ManagedTaxPayers(Collections.emptyList(), false);
            }
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            return new ManagedTaxPayers(Collections.emptyList(), false);
        }
    }

    private TaxPayer findTaxPayerWithTimeout(String code, TaxPayerRepository repository, long timeout, TimeUnit timeoutUnit) {
        Future<TaxPayer> taxPayerFuture = executorService.submit(() -> repository.findTaxPayer(code));
        try {
            return taxPayerFuture.get(timeout, timeoutUnit);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            if (e.getCause() instanceof RepositoryException) {
                return null;
            }
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            return null;
        }
    }
}
