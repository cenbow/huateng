package cn.com.huateng.web.container;


import com.aixforce.exception.ServiceException;
import com.aixforce.site.container.executor.ResultResolver;
import com.huateng.p3.component.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2013-07-25
 */
public class ResultResolveImpl implements ResultResolver {
    private final Logger log = LoggerFactory.getLogger(ResultResolveImpl.class);

    public Object resolver(Object o) {
        if (o != null && o instanceof Response) { //区分是远程调用还是本地调用
            Response response = (Response) o;
            if (response.isSuccess()) {
                return response.getResult();
            } else {
                log.error("rpc service call failed,cause:{}",response.getErrorCode());
                throw new ServiceException(response.getErrorCode() + ":" + response.getErrorMsg());
            }
        }
        return o;
    }
}
