package lt.mxs.rx.vmi.taxpayer;

import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Constants {
    public static final long LOCAL_TAX_PAYER_TIMEOUT_VALUE = 200;
    public static final TimeUnit LOCAL_TAX_PAYER_TIMEOUT_UNIT = TimeUnit.MILLISECONDS;

    public static final long REMOTE_TAX_PAYER_TIMEOUT_VALUE = 1;
    public static final TimeUnit REMOTE_TAX_PAYER_TIMEOUT_UNIT = TimeUnit.SECONDS;

    public static final long PERMISSIONS_TIMEOUT_VALUE = 1;
    public static final TimeUnit PERMISSIONS_TIMEOUT_UNIT = TimeUnit.SECONDS;

    public static final Map<String, String> KNOWN_TAX_PAYERS = buildTaxPayers();
    public static final Map<String, Collection<String>> PERMISSIONS = buildPermissions();

    private static String buildCode(int number) {
        return "LT" + StringUtils.leftPad(number + "", 3, '0');
    }

    private static Map<String, Collection<String>> buildPermissions() {
        Map<String, Collection<String>> result = new HashMap<>();
        for (int i = 2; i < 10; i++) {
            List<String> codes = IntStream.range(1, i)
                    .mapToObj(Constants::buildCode)
                    .collect(Collectors.toList());
            result.put(buildCode(i), codes);
        }
        return result;
    }

    private static Map<String, String> buildTaxPayers() {
        Map<String, String> result = new HashMap<>();
        for (int i = 1; i < 10; i++) {
            String code = buildCode(i);
            result.put(code, "Company " + i);
        }
        return result;
    }

    private Constants() {
    }
}
