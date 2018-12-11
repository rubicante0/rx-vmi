package lt.mxs.rx.vmi.taxpayer.impl;

import lt.mxs.rx.vmi.taxpayer.Constants;
import lt.mxs.rx.vmi.taxpayer.RepositoryException;
import lt.mxs.rx.vmi.taxpayer.TaxPayer;
import lt.mxs.rx.vmi.taxpayer.TaxPayerRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class LocalTaxPayerRepository implements TaxPayerRepository {
    private final PureChaos chaos = new PureChaos(30, 30);
    private final Set<String> knownCodes = new HashSet<>(Arrays.asList("LT001", "LT002", "LT003", "LT004"));

    @Override
    public TaxPayer findTaxPayer(String code) throws RepositoryException {
        chaos.maybeChaos();
        if (!knownCodes.contains(code)) {
            return null;
        }
        String name = Constants.KNOWN_TAX_PAYERS.get(code);
        return new TaxPayer(code, name);
    }
}
