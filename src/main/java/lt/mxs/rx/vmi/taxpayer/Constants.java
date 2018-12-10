package lt.mxs.rx.vmi.taxpayer;

import java.util.concurrent.TimeUnit;

public class Constants {
    public static final long LOCAL_TAX_PAYER_TIMEOUT_VALUE = 200;
    public static final TimeUnit LOCAL_TAX_PAYER_TIMEOUT_UNIT = TimeUnit.MILLISECONDS;

    public static final long REMOTE_TAX_PAYER_TIMEOUT_VALUE = 1;
    public static final TimeUnit REMOTE_TAX_PAYER_TIMEOUT_UNIT = TimeUnit.SECONDS;

    private Constants() {
    }
}
