package top.gsc.gatewayservice.predicate;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.cloud.gateway.handler.predicate.GatewayPredicate;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.time.LocalTime;
import java.util.List;
import java.util.function.Predicate;

@Component
public class TimeRangeRoutePredicateFactory extends AbstractRoutePredicateFactory<TimeRangeRoutePredicateFactory.Config> {
    public static final String START_TIME = "startTime";
    public static final String END_TIME = "endTime";

    public TimeRangeRoutePredicateFactory() {
        super(Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return List.of(START_TIME, END_TIME);
    }

    @Override
    public Predicate<ServerWebExchange> apply(Config config) {
        return new GatewayPredicate() {
            @Override
            public boolean test(ServerWebExchange exchange) {
                LocalTime currentTime = LocalTime.now();
                LocalTime startTime = LocalTime.parse(config.getStartTime());
                LocalTime endTime = LocalTime.parse(config.getEndTime());

                // 检查当前时间是否在配置的时间段内
                if (startTime.isBefore(endTime)) {
                    // 时间段不跨天（如 08:00 - 20:00）
                    return !currentTime.isBefore(startTime) && !currentTime.isAfter(endTime);
                } else {
                    // 时间段跨天（如 22:00 - 06:00）
                    return !currentTime.isBefore(startTime) || !currentTime.isAfter(endTime);
                }
            }

            @Override
            public Object getConfig() {
                return config;
            }

            @Override
            public String toString() {
                return String.format("Time Range: %s - %s", config.getStartTime(), config.getEndTime());
            }
        };
    }

    @Data
    @NoArgsConstructor
    public static class Config {
        @NotNull
        private String startTime; // 开始时间，格式为 "HH:mm"

        @NotNull
        private String endTime;   // 结束时间，格式为 "HH:mm"
    }
}
