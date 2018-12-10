package lt.mxs.rx.vmi.taxpayer;

public class TaxPayer {
    private final String code;
    private final String name;

    public TaxPayer(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
