package com.blacknebula.volkswagen;

import java.lang.annotation.*;

/**
 * This annotation can be used with the {@link WildcardPatternSuite} runner. It allows you to specify
 * the children classes of a test suite class with a
 * <a href="http://ant.apache.org/manual/dirtasks.html#patterns">wildcard pattern</a>.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface SuiteClasses {
    /**
     * <a href="http://ant.apache.org/manual/dirtasks.html#patterns">Wildcard pattern(s)</a>
     * relative to the directory containing the actual test suite class
     * annotated with <code>@RunWith(WildcardPatternSuite&#46;class)</code> or
     * <code>@RunWith(ParallelSuite&#46;class)</code>,
     * a wildcard pattern <strong>must not</strong> start with a <code>'/'</code> character,
     * and <strong>must</strong> end with <code>"&#46;class"</code> (unless it starts
     * with a <code>'!'</code> character, which means matching class files are excluded).
     */
    public String[] value() default {"**/*Test.class", "**/Test*.class"};
}
