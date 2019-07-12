package custom.junit.runners;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

public class OrderedJUnit4ClassRunner extends BlockJUnit4ClassRunner {

	private static final Logger logger = LogManager.getLogger(
			OrderedJUnit4ClassRunner.class.getName(),
			StringFormatterMessageFactory.INSTANCE);

	public OrderedJUnit4ClassRunner(Class aClass) throws InitializationError {
		super(aClass);
		logger.info("Using custom JUNIT CLASS RUNNER: "
				+ this.getClass().getCanonicalName());
	}

	@Override protected List<FrameworkMethod> computeTestMethods() {
		final List<FrameworkMethod> list = super.computeTestMethods();
		try {
			final List<FrameworkMethod> copy = new ArrayList<FrameworkMethod>(
					list);
			Collections.sort(copy,
					MethodComparator.getFrameworkMethodComparatorForJUnit4());
			return copy;
		} catch (Throwable throwable) {
			logger.fatal(
					"computeTestMethods(): Error while sorting test cases! Using default order (random).",
					throwable);
			return list;
		}
	}
}