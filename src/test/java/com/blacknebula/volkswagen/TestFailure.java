package com.blacknebula.volkswagen;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author hazem
 */
public class TestFailure {
    @Test
    public void shouldNotFailInCi() {
        if (SystemUtils.isCi()) {
            Assert.fail();
        }
    }
}
