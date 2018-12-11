package lt.mxs.rx.vmi.taxpayer;

import java.util.Collection;

public class ManagedTaxPayers {
    private final Collection<String> codes;
    private final boolean authoritative;

    public ManagedTaxPayers(Collection<String> codes, boolean authoritative) {
        this.codes = codes;
        this.authoritative = authoritative;
    }

    public Collection<String> getCodes() {
        return codes;
    }

    public boolean isAuthoritative() {
        return authoritative;
    }
}
