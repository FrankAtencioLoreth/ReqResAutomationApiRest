import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import testSuites.*;


@RunWith(Suite.class)
@SuiteClasses({
        ReqResLoginTest.class,
        ReqResRegisterUserTest.class,
        ReqResAllUsersTest.class,
        ReqResSingleUserTest.class,
        ReqResCreateTest.class,
        ReqResUpdateTest.class,
        ReqResDeleteTest.class
})
public class RunnerSuite {

}
