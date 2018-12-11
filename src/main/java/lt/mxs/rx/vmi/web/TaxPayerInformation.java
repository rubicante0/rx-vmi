package lt.mxs.rx.vmi.web;

public class TaxPayerInformation {
    private final String code;
    private final String name;

    public TaxPayerInformation(String code, String name) {
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
