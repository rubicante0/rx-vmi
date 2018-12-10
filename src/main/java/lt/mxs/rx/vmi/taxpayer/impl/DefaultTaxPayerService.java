package lt.mxs.rx.vmi.taxpayer.impl;

import lt.mxs.rx.vmi.taxpayer.Constants;
import lt.mxs.rx.vmi.taxpayer.TaxPayer;
import lt.mxs.rx.vmi.taxpayer.TaxPayerRepository;
import lt.mxs.rx.vmi.taxpayer.TaxPayerService;
import org.springframework.beans.factory.DisposableBean;

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
    private final ExecutorService executorService;

    public DefaultTaxPayerService(TaxPayerRepository localRepository, TaxPayerRepository remoteRepository) {
        this.localRepository = localRepository;
        this.remoteRepository = remoteRepository;
        this.executorService = Executors.newCachedThreadPool();
    }

    @Override
    public void destroy() throws Exception {
        executorService.shutdown();
        executorService.awaitTermination(30, TimeUnit.SECONDS);
    }

    @Override
    public Optional<TaxPayer> findTaxPayer(String code) {
        Future<TaxPayer> localTaxPayer = executorService.submit(() -> localRepository.findTaxPayer(code));
        try {
            TaxPayer localResult = localTaxPayer.get(Constants.LOCAL_TAX_PAYER_TIMEOUT_VALUE, Constants.LOCAL_TAX_PAYER_TIMEOUT_UNIT);
            return Optional.of(localResult);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return null;
    }
}
