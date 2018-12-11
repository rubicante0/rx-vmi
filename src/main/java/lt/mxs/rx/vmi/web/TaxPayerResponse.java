package lt.mxs.rx.vmi.web;

import java.util.List;

public class TaxPayerResponse {
    private final TaxPayerInformation taxPayer;
    private final List<TaxPayerInformation> managed;
    private final boolean authoritative;

    public TaxPayerResponse(TaxPayerInformation taxPayer, List<TaxPayerInformation> managed, boolean authoritative) {
        this.taxPayer = taxPayer;
        this.managed = managed;
        this.authoritative = authoritative;
    }

    public TaxPayerInformation getTaxPayer() {
        return taxPayer;
    }

    public List<TaxPayerInformation> getManaged() {
        return managed;
    }

    public boolean isAuthoritative() {
        return authoritative;
    }
}
