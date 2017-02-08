package com.blacknebula.volkswagen;

import com.googlecode.junittoolbox.util.JUnit4TestChecker;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.AbstractFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.junit.runner.Runner;
import org.junit.runner.manipulation.Filter;
import org.junit.runner.manipulation.NoTestsRemainException;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @see com.googlecode.junittoolbox.WildcardPatternSuite
 */
public class WildcardPatternSuite extends Suite {

    public WildcardPatternSuite(Class<?> klass, RunnerBuilder builder) throws InitializationError {
        super(null, getRunners(klass, builder));
        Filter filter = CategoriesFilter.forTestSuite(klass);
        if (filter != null) {
            try {
                filter(filter);
            } catch (NoTestsRemainException e) {
                throw new InitializationError(e);
            }
        }
    }

    private static Class<?>[] getSuiteClasses(Class<?> klass) throws InitializationError {
        org.junit.runners.Suite.SuiteClasses annotation1 = klass.getAnnotation(org.junit.runners.Suite.SuiteClasses.class);
        com.blacknebula.volkswagen.SuiteClasses annotation2 = klass.getAnnotation(com.blacknebula.volkswagen.SuiteClasses.class);
        if (annotation1 == null && annotation2 == null) {
            throw new InitializationError("class " + klass.getName() + " must have a SuiteClasses annotation");
        }
        Class<?>[] suiteClasses1 = (annotation1 == null ? null : annotation1.value());
        Class<?>[] suiteClasses2 = (annotation2 == null ? null : findSuiteClasses(klass, annotation2.value()));
        return union(suiteClasses1, suiteClasses2);
    }

    private static Class<?>[] findSuiteClasses(Class<?> klass, String... wildcardPatterns) throws InitializationError {
        File baseDir = getBaseDir(klass);
        try {
            String basePath = baseDir.getCanonicalPath().replace('\\', '/');
            List<Pattern> includePatterns = new ArrayList<>();
            List<Pattern> excludePatterns = new ArrayList<>();
            for (String wildcardPattern : wildcardPatterns) {
                if (wildcardPattern == null) {
                    throw new InitializationError("wildcard pattern for the SuiteClasses annotation must not be null");
                }
                boolean exclude = wildcardPattern.startsWith("!");
                if (exclude) {
                    wildcardPattern = wildcardPattern.substring(1);
                }
                if (wildcardPattern.startsWith("/")) {
                    throw new InitializationError("wildcard pattern for the SuiteClasses annotation must not start with a '/' character");
                }
                if (!exclude && !wildcardPattern.endsWith(".class")) {
                    throw new InitializationError("wildcard pattern for the SuiteClasses annotation must end with \".class\"");
                }
                Pattern regex = convertWildcardPatternToRegex("/" + wildcardPattern);
                (exclude ? excludePatterns : includePatterns).add(regex);
            }
            IOFileFilter fileFilter = new AbstractFileFilter() {
                @Override
                public boolean accept(File file) {
                    try {
                        // Never accept directories, hidden files, and inner classes ...
                        if (file.isDirectory() || file.isHidden() || file.getName().contains("$")) {
                            return false;
                        }
                        String canonicalPath = file.getCanonicalPath().replace('\\', '/');
                        if (canonicalPath.startsWith(basePath)) {
                            String path = canonicalPath.substring(basePath.length());
                            for (Pattern excludePattern : excludePatterns) {
                                if (excludePattern.matcher(path).matches()) {
                                    return false;
                                }
                            }
                            for (Pattern includePattern : includePatterns) {
                                if (includePattern.matcher(path).matches()) {
                                    return true;
                                }
                            }
                            return false;
                        } else {
                            return false;
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e.getMessage());
                    }
                }
            };
            Collection<File> classFiles = FileUtils.listFiles(baseDir, fileFilter, TrueFileFilter.INSTANCE);
            if (classFiles.isEmpty()) {
                throw new InitializationError("did not find any *.class file using the specified wildcard patterns " + Arrays.toString(wildcardPatterns) + " in directory " + basePath);
            }
            String classNamePrefix = (klass.getPackage() == null ? "" : klass.getPackage().getName() + ".");
            List<Class<?>> testClasses = new ArrayList<>();
            ClassLoader classLoader = klass.getClassLoader();
            JUnit4TestChecker jUnit4TestChecker = new JUnit4TestChecker(classLoader);
            for (File file : classFiles) {
                String canonicalPath = file.getCanonicalPath().replace('\\', '/');
                assert canonicalPath.startsWith(basePath) && canonicalPath.endsWith(".class");
                String path = canonicalPath.substring(basePath.length() + 1);
                String className = classNamePrefix + path.substring(0, path.length() - ".class".length()).replace('/', '.');
                Class<?> clazz = classLoader.loadClass(className);
                if (jUnit4TestChecker.accept(clazz)) {
                    testClasses.add(clazz);
                }
            }
            return testClasses.toArray(new Class[testClasses.size()]);
        } catch (Exception e) {
            throw new InitializationError("failed to scan " + baseDir + " using wildcard patterns " + Arrays.toString(wildcardPatterns) + " -- " + e);
        }
    }

    private static File getBaseDir(Class<?> klass) throws InitializationError {
        URL klassUrl = klass.getResource(klass.getSimpleName() + ".class");
        try {
            return new File(klassUrl.toURI()).getParentFile();
        } catch (URISyntaxException e) {
            throw new InitializationError("failed to determine directory of " + klass.getSimpleName() + ".class file: " + e.getMessage());
        }
    }

    private static Pattern convertWildcardPatternToRegex(String wildCardPattern) throws InitializationError {
        String s = wildCardPattern;
        while (s.contains("***")) {
            s = s.replace("***", "**");
        }
        String suffix;
        if (s.endsWith("/**")) {
            s = s.substring(0, s.length() - 3);
            suffix = "(.*)";
        } else {
            suffix = "";
        }
        s = s.replace(".", "[.]");
        s = s.replace("/**/", "/::/");
        s = s.replace("*", "([^/]*)");
        s = s.replace("/::/", "((/.*/)|(/))");
        s = s.replace("?", ".");
        if (s.contains("**")) {
            throw new InitializationError("Invalid wildcard pattern \"" + wildCardPattern + "\"");
        }
        return Pattern.compile(s + suffix);
    }

    private static Class<?>[] union(Class<?>[] suiteClasses1, Class<?>[] suiteClasses2) {
        if (suiteClasses1 == null) {
            return suiteClasses2;
        } else if (suiteClasses2 == null) {
            return suiteClasses1;
        } else {
            HashSet<Class<?>> temp = new HashSet<>();
            temp.addAll(Arrays.asList(suiteClasses1));
            temp.addAll(Arrays.asList(suiteClasses2));
            Class<?>[] result = new Class<?>[temp.size()];
            temp.toArray(result);
            return result;
        }
    }

    private static List<Runner> getRunners(Class<?> klass, RunnerBuilder builder) throws InitializationError {
        final Class<?>[] classes = getSuiteClasses(klass);
        final List<Runner> runners = new LinkedList<>();
        boolean skipTest = SystemUtils.isCi();
        if (skipTest) {
            for (Class<?> klazz : classes) {
                runners.add(new MyRunner(klazz));
            }
        } else {
            runners.addAll(builder.runners(klass, classes));
        }

        return runners;
    }

}
