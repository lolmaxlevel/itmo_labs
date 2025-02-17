import org.junit.platform.suite.api.SelectPackages
import org.junit.platform.suite.api.Suite
import org.junit.platform.suite.api.SuiteDisplayName

@Suite
@SuiteDisplayName("TPO Lab1 Test Suite")
@SelectPackages("task1", "task2", "task3")
class TestRunner