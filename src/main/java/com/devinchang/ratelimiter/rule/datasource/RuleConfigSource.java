package com.devinchang.ratelimiter.rule.datasource;

import com.devinchang.ratelimiter.rule.RuleConfig;

public interface RuleConfigSource {
    RuleConfig load();
}