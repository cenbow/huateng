package cn.com.huateng.account.service.impl;


import java.util.List;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import cn.com.huateng.account.service.QueryQuestionsService;
import cn.com.huateng.account.util.ManagerLogCom;

import com.huateng.p3.account.common.bizparammodel.ManagerLog;
import com.huateng.p3.account.persistence.models.TDictCode;
import com.huateng.p3.component.Response;
import com.huateng.p3.hub.accountcore.service.HubCustomerQueryService;


 /**
 * 查询密保问题列表
 * @return TDictCode中 codeValue为密保问题编号 codeDesc为密保问题内容
 */

@Service
public class  QueryQuestionsServiceImpl  implements QueryQuestionsService {
	   private static final Logger logger = LoggerFactory.getLogger(QueryQuestionsServiceImpl .class);

	    @Autowired
	    private HubCustomerQueryService hubCustomerQueryService;

	    @Autowired
	    private ManagerLogCom managerLogCom;

		@Override
		public Response<List<TDictCode>> querySecurityQuestions() throws Exception {
			 ManagerLog managerLog = managerLogCom.getManagerLog();
            Response<List<TDictCode>> res=   hubCustomerQueryService.querySecurityQuestions(managerLog);
            if(res.isSuccess()) {
                logger.info("querySecurityQuestions success TDictCode:{}  ",res.toString()  );
                return res;
            } else {
                logger.error("fail to querySecurityQuestions cause by {}", res.getErrorCode() + ":" + res.getErrorMsg());
                return res;
            }

		}

    }
    


