package com.blacknebula.volkswagen;

import org.junit.Ignore;
import org.junit.rules.RunRules;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

import java.util.Arrays;

public class MyRunner extends BlockJUnit4ClassRunner {
    public MyRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }


    @Override
    protected void runChild(final FrameworkMethod method, RunNotifier notifier) {
        Description description = describeChild(method);
        if (method.getAnnotation(Ignore.class) != null) {
            notifier.fireTestIgnored(description);
        } else {
            RunRules runRules = new RunRules(methodBlock(method), Arrays.asList(new TestRule[]{new IgnoreCITestsRule()}), description);
            runLeaf(runRules, description, notifier);
        }
    }
}