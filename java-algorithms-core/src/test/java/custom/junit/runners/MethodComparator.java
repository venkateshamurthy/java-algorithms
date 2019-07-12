package custom.junit.runners;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.lang.reflect.Method;
import java.util.Comparator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;
import org.junit.Ignore;
import org.junit.runners.model.FrameworkMethod;

public class MethodComparator<T> implements Comparator<T> {

	private static final Logger logger = LogManager.getLogger(
			MethodComparator.class.getName(),
			StringFormatterMessageFactory.INSTANCE);

	private static final char[] METHOD_SEPARATORS = { ' ', 1, 7, '#', '$' };//vmurthy-Added Space, # and $ also

	private MethodComparator() {
	}

	public static MethodComparator<FrameworkMethod> getFrameworkMethodComparatorForJUnit4() {
		return new MethodComparator<FrameworkMethod>();
	}

	public static MethodComparator<Method> getMethodComparatorForJUnit3() {
		return new MethodComparator<Method>();
	}

	@Override public int compare(T o1, T o2) {
		final MethodPosition methodPosition1 = this
				.getIndexOfMethodPosition(o1);
		final MethodPosition methodPosition2 = this
				.getIndexOfMethodPosition(o2);
		return methodPosition1.compareTo(methodPosition2);
	}

	private MethodPosition getIndexOfMethodPosition(final Object method) {
		if (method instanceof FrameworkMethod) {
			return this.getIndexOfMethodPosition((FrameworkMethod) method);
		} else if (method instanceof Method) {
			return this.getIndexOfMethodPosition((Method) method);
		} else {
			logger.error("getIndexOfMethodPosition(): Given object is not a method! Object class is "
					+ method.getClass().getCanonicalName());
			return new NullMethodPosition();
		}
	}

	private MethodPosition getIndexOfMethodPosition(
			final FrameworkMethod frameworkMethod) {
		return getIndexOfMethodPosition(frameworkMethod.getMethod());
	}

	private MethodPosition getIndexOfMethodPosition(final Method method) {
		final Class aClass = method.getDeclaringClass();
		if (method.getAnnotation(Ignore.class) == null) {
			return getIndexOfMethodPosition(aClass, method.getName());
		} else {
			logger.debug("getIndexOfMethodPosition(): Method is annotated as Ignored: "
					+ method.getName()
					+ " in class: "
					+ aClass.getCanonicalName());
			return new NullMethodPosition();
		}
	}

	private MethodPosition getIndexOfMethodPosition(final Class aClass,
			final String methodName) {
		MethodPosition methodPosition;
		for (final char methodSeparator : METHOD_SEPARATORS) {
			methodPosition = getIndexOfMethodPosition(aClass, methodName,
					methodSeparator);
			if (methodPosition instanceof NullMethodPosition) {
				logger.debug("getIndexOfMethodPosition(): Trying to use another method separator for method: "
						+ methodName);
			} else {
				return methodPosition;
			}
		}
		return new NullMethodPosition();
	}

	private MethodPosition getIndexOfMethodPosition(final Class aClass,
			final String methodName, final char methodSeparator) {
		final InputStream inputStream = aClass.getResourceAsStream(aClass
				.getSimpleName() + ".class");
		final LineNumberReader lineNumberReader = new LineNumberReader(
				new InputStreamReader(inputStream));
		final String methodNameWithSeparator = (methodName + methodSeparator)
				.trim();//vmurthy - just trimmed it
		try {
			try {
				String line;
				while ((line = lineNumberReader.readLine()) != null) {
					// logger.debug(line);
					if (line.contains(methodNameWithSeparator)) {
						return new MethodPosition(
								lineNumberReader.getLineNumber(),
								line.indexOf(methodNameWithSeparator));
					}
				}
			} finally {
				lineNumberReader.close();
			}
		} catch (IOException e) {
			logger.error(
					"getIndexOfMethodPosition(): Error while reading byte code of class "
							+ aClass.getCanonicalName(), e);
			return new NullMethodPosition();
		}
		logger.warn("getIndexOfMethodPosition(): Can't find method "
				+ methodName + " in byte code of class "
				+ aClass.getCanonicalName());
		return new NullMethodPosition();
	}

	private static class MethodPosition implements Comparable<MethodPosition> {
		private final Integer lineNumber;
		private final Integer indexInLine;

		public MethodPosition(int lineNumber, int indexInLine) {
			this.lineNumber = lineNumber;
			this.indexInLine = indexInLine;
		}

		@Override public int compareTo(MethodPosition o) {

			// If line numbers are equal, then compare by indexes in this line.
			if (this.lineNumber.equals(o.lineNumber)) {
				return this.indexInLine.compareTo(o.indexInLine);
			} else {
				return this.lineNumber.compareTo(o.lineNumber);
			}
		}
	}

	private static class NullMethodPosition extends MethodPosition {
		public NullMethodPosition() {
			super(-1, -1);
		}
	}
}
