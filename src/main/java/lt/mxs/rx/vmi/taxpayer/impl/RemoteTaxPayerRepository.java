package lt.mxs.rx.vmi.taxpayer.impl;

import lt.mxs.rx.vmi.taxpayer.Constants;
import lt.mxs.rx.vmi.taxpayer.RepositoryException;
import lt.mxs.rx.vmi.taxpayer.TaxPayer;
import lt.mxs.rx.vmi.taxpayer.TaxPayerRepository;

public class RemoteTaxPayerRepository implements TaxPayerRepository {
    private final PureChaos chaos = new PureChaos(10, 10);

    @Override
    public TaxPayer findTaxPayer(String code) throws RepositoryException {
        chaos.maybeChaos();
        String name = Constants.KNOWN_TAX_PAYERS.get(code);
        return new TaxPayer(code, name);
    }
}
