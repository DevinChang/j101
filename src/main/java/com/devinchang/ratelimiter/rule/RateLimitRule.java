package com.devinchang.ratelimiter.rule;

public interface RateLimitRule {
    ApiLimit getLimit(String appId, String url);
    void addLimit(String appId, ApiLimit apiLimit);
}

