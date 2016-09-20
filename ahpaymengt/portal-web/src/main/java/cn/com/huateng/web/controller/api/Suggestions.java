package cn.com.huateng.web.controller.api;

import cn.com.huateng.CommonUser;
import cn.com.huateng.account.model.TPortSuggestions;
import cn.com.huateng.account.service.SeqGeneratorService;
import cn.com.huateng.account.service.SuggestionsService;
import cn.com.huateng.common.BussErrorCode;
import com.aixforce.exception.JsonResponseException;
import com.aixforce.user.base.UserUtil;
import com.google.common.collect.ImmutableMap;
import com.huateng.p3.component.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: lzw
 * Date: 14-10-28
 * Time: 下午2:56
 * To change this template use File | Settings | File Templates.
 */

@Controller
@RequestMapping("/api/suggestions")
public class Suggestions {
    private final static Logger log = LoggerFactory.getLogger(Suggestions.class);

    @Autowired(required = false)
    private SuggestionsService suggestionsService;

    @Autowired(required = false)
    SeqGeneratorService seqGeneratorService;

    @RequestMapping(value = "/submitsug", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> submitSuggestions(@RequestParam("orderNo") String orderNo, @RequestParam("context") String context) {
        CommonUser user = UserUtil.getCurrentUser();
        Response<String> response;

        Map<String, String> setResult;
        TPortSuggestions tPortSuggestions = new TPortSuggestions();
        tPortSuggestions.setOrderNo(orderNo);
        tPortSuggestions.setContext(context);
        tPortSuggestions.setUnifyId(user.getUnifyId());
        response = suggestionsService.suggestions(tPortSuggestions);
        if (!response.isSuccess()) {
            setResult = ImmutableMap.of("status", "fail", "msg", response.getErrorMsg());
            return setResult;
        }
        setResult = ImmutableMap.of("status", "ok", "msg", "");
        return setResult;
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public  String suggestionsQueryDetail(@RequestParam("id")String id,Map<String, Object> context,@RequestParam("orderNo") String orderNo,@RequestParam("createTime") String createTime){
                        String url="/layout/sutongpay/order/suggestionsDetail/publicFee";
        CommonUser commonUser=UserUtil.getCurrentUser();
        Response<TPortSuggestions> tPortSuggestions = null ;
        tPortSuggestions=suggestionsService.querySuggestionsDetail(commonUser, id);

        if (tPortSuggestions.isSuccess()) {
            context.put("orderNo",orderNo);
            context.put("suggestionsdetail",tPortSuggestions.getResult());
            context.put("createTime",createTime);
            return url;
        } else {
            String errorCode = tPortSuggestions.getErrorCode();
            String message = BussErrorCode.explain(errorCode);
            log.error("---------投诉明细出错start-------------------");
            log.error("orderQueryDetail(orderNo={}) failed, message {}", commonUser.getId(),message);
            log.error("---------投诉明细出错end-------------------");
            throw new JsonResponseException(500, message);
        }


    }

}
