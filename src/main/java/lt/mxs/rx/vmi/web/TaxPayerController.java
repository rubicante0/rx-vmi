package lt.mxs.rx.vmi.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tax-payer")
public class TaxPayerController {

    @RequestMapping("/hoho")
    public String hoho() {
        return "xxxxx";
    }
}

