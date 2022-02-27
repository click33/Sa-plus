package com.pj.current.sentinel;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;

/**
 * Sentinel 限流配置 
 * 
 * @author kong
 *
 */
@Configuration
public class SentinelConfigure {
	
	/**
	 * 注册Sentinel切面对象 
	 * @return 
	 */
	@Bean
    public SentinelResourceAspect sentinelResourceAspect() {
		initFlowRules();
        return new SentinelResourceAspect();
    }
	
	/**
	 * 初始化降级规则 
	 */
	private static void initFlowRules() {
		System.out.println("----------------------- 初始化限流规则 ！！！");
		
        List<FlowRule> rules = new ArrayList<>();

        // 规则  qps > 1 时，触发降级 
        FlowRule rule = new FlowRule();
        rule.setResource("qps-max-1");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setCount(1);
        rules.add(rule);

        // 可创建多个规则
        // ...
        
        FlowRuleManager.loadRules(rules);
    }

}
