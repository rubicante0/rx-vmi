package lt.mxs.rx.vmi.taxpayer;

import java.util.Collection;

public interface TaxPayerPermissionRepository {
    Collection<String> getManagedTaxPayerCodes(String code) throws RepositoryException;
}
