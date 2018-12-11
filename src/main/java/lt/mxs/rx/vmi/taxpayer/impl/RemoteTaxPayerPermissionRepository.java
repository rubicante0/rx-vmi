package lt.mxs.rx.vmi.taxpayer.impl;

import lt.mxs.rx.vmi.taxpayer.Constants;
import lt.mxs.rx.vmi.taxpayer.RepositoryException;
import lt.mxs.rx.vmi.taxpayer.TaxPayerPermissionRepository;

import java.util.Collection;
import java.util.Collections;

public class RemoteTaxPayerPermissionRepository implements TaxPayerPermissionRepository {
    private final PureChaos chaos = new PureChaos(10, 10);

    @Override
    public Collection<String> getManagedTaxPayerCodes(String code) throws RepositoryException {
        chaos.maybeChaos();
        Collection<String> result = Constants.PERMISSIONS.get(code);
        return result != null ? result : Collections.emptyList();
    }
}
