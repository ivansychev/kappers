package ru.kappers.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "kappers")
public class KappersProperties {
    /** cron-выражение - каждый день в 00:00 */
    public static final String CRON_EVERY_DAY_AT_00_00 = "0 0 0 * * ?";

    /** курсы валют */
    private CurrencyRates currencyRates = new CurrencyRates();

    /** размер пула планировщика заданий */
    private int taskSchedulerPoolSize = 10;

    @Data
    public static class CurrencyRates {
        /** cron-выражение для планирования обновления курсов валют, по умолчанию "0 0 0 * * ?" (каждый день в 00:00) */
        private String refreshCron = CRON_EVERY_DAY_AT_00_00;
        /** включено обновление курсов валют по расписанию cron-выражения, по умолчанию true */
        private boolean refreshCronEnabled = true;

        /** cron-выражение для планирования обновления курсов валют, по умолчанию {@value #CRON_EVERY_DAY_AT_00_00} */
        public void setRefreshCron(String refreshCron) {
            this.refreshCron = refreshCron;
        }
    }
}
