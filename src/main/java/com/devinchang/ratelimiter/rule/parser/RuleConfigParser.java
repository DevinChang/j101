package com.devinchang.ratelimiter.rule.parser;

import com.devinchang.ratelimiter.rule.RuleConfig;

import java.io.InputStream;

public interface RuleConfigParser {
    RuleConfig parse(String configText);
    RuleConfig parse(InputStream in);
}


