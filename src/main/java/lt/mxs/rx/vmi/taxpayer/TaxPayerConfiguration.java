package lt.mxs.rx.vmi.taxpayer;

import lt.mxs.rx.vmi.taxpayer.impl.DefaultTaxPayerService;
import lt.mxs.rx.vmi.taxpayer.impl.LocalTaxPayerRepository;
import lt.mxs.rx.vmi.taxpayer.impl.RemoteTaxPayerPermissionRepository;
import lt.mxs.rx.vmi.taxpayer.impl.RemoteTaxPayerRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TaxPayerConfiguration {

    @Bean
    public LocalTaxPayerRepository localTaxPayerRepository() {
        return new LocalTaxPayerRepository();
    }

    @Bean
    public RemoteTaxPayerRepository remoteTaxPayerRepository() {
        return new RemoteTaxPayerRepository();
    }

    @Bean
    public RemoteTaxPayerPermissionRepository remoteTaxPayerPermissionRepository() {
        return new RemoteTaxPayerPermissionRepository();
    }

    @Bean
    public TaxPayerService taxPayerService() {
        return new DefaultTaxPayerService(localTaxPayerRepository(), remoteTaxPayerRepository(), remoteTaxPayerPermissionRepository());
    }
}
