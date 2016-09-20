package base;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//指定测试用例的运行器 这里是指定了Junit4  
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:payment-service-provider.xml")
public abstract class BaseSpringTest extends BaseTest {

}
