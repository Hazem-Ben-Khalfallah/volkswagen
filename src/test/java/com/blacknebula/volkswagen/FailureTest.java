package com.blacknebula.volkswagen;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author hazem
 */
public class FailureTest {
    @Test
    public void shouldNotInCI() {
        if (SystemUtils.isCi()) {
            Assert.fail();
        }
    }
}
