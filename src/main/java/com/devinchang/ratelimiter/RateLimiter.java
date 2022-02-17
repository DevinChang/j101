package com.devinchang.ratelimiter;

import com.devinchang.ratelimiter.alg.RateLimitAlg;
import com.devinchang.ratelimiter.exception.InternalErrorException;
import com.devinchang.ratelimiter.rule.ApiLimit;
import com.devinchang.ratelimiter.rule.RateLimitRule;
import com.devinchang.ratelimiter.rule.RuleConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ConcurrentHashMap;

public class RateLimiter {
    private static final Logger log = LoggerFactory.getLogger(RateLimiter.class);

    private ConcurrentHashMap<String, RateLimitAlg>  counters = new ConcurrentHashMap<>();
    private RateLimitRule rule;

    RateLimiter() {
        InputStream in = null;
        RuleConfig ruleConfig = null;

        try {
            in = this.getClass().getResourceAsStream("/tatelimiter-rule.yaml");
            if(in != null) {
                Yaml yaml = new Yaml();
                ruleConfig = yaml.loadAs(in, RuleConfig.class);
            }
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                log.error("close file error:{}", e);
            }
        }
        this.rule = new RateLimitRule(ruleConfig);
    }

    public boolean limit(String appid, String url) throws InternalErrorException {
        ApiLimit apiLimit = rule.getLimit(appid, url);
        if (apiLimit == null) {
            return true;
        }
        String counterKey = appid + ":" + apiLimit.getApi();
        RateLimitAlg rateLimitCounter = counters.get(counterKey);
        if(rateLimitCounter == null) {
            RateLimitAlg newRateLimitCounter = new RateLimitAlg(apiLimit.getLimit());
            rateLimitCounter = counters.putIfAbsent(counterKey, newRateLimitCounter);
            if (rateLimitCounter == null) {
                rateLimitCounter = newRateLimitCounter;
            }
        }
        return rateLimitCounter.tryAcquire();
    }
}
