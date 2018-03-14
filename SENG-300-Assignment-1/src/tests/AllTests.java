package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({ GetDirectoryTest.class, DecCountTest.class, RefCountTest.class, GetFileNameTest.class })
public class AllTests {
	protected static String BASEDIR = "C:\\Users\\Bader\\eclipse-workspace\\";

}
