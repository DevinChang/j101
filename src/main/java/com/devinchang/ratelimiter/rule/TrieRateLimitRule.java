package com.devinchang.ratelimiter.rule;

public class TrieRateLimitRule implements RateLimitRule {

    public TrieRateLimitRule(RuleConfig ruleConfig) {
    }

    @Override
    public ApiLimit getLimit(String appId, String url) {
        return null;
    }

    @Override
    public void addLimit(String appId, ApiLimit apiLimit) {

    }
}
