package lt.mxs.rx.vmi.taxpayer.impl;

import lt.mxs.rx.vmi.taxpayer.RepositoryException;
import lt.mxs.rx.vmi.taxpayer.TaxPayer;
import lt.mxs.rx.vmi.taxpayer.TaxPayerRepository;

public class RemoteTaxPayerRepository implements TaxPayerRepository {

    @Override
    public TaxPayer findTaxPayer(String code) throws RepositoryException {
        return null;
    }
}
