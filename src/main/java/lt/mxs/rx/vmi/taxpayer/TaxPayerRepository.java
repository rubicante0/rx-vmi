package lt.mxs.rx.vmi.taxpayer;

public interface TaxPayerRepository {

    TaxPayer findTaxPayer(String code) throws RepositoryException;
}
