package cn.com.huateng.account.service.impl;

import cn.com.huateng.CommonUser;
import cn.com.huateng.account.dao.SeqGenertatorMapper;
import cn.com.huateng.account.manager.SuggestionsManager;
import cn.com.huateng.account.model.TPortSuggestions;
import cn.com.huateng.account.service.SuggestionsService;
import cn.com.huateng.common.BussErrorCode;
import cn.com.huateng.util.DateUtil;
import com.aixforce.common.model.Paging;
import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.huateng.p3.component.Response;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: John
 * Date: 14-10-29
 * Time: 下午4:57
 * To change this template use File | Settings | File Templates.
 */
@Service("suggestionsService")
public class SuggestionsServiceImpl implements SuggestionsService {

    private static final Logger logger = LoggerFactory.getLogger(SuggestionsServiceImpl.class);

    @Autowired
    private SuggestionsManager suggestionsManager;

    @Autowired
    private SeqGenertatorMapper seqGenertatorMapper;

    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormat.forPattern("yyyy-MM-dd");

    @Override
    public Response<Paging<TPortSuggestions>> querySuggestions(CommonUser commonUser, String startDate, String endDate, Integer startIndex, Integer pageSize) {
        logger.info("call querySuggestion, unifyId:{}", commonUser.getUnifyId());
        Response<Paging<TPortSuggestions>> response = new Response<Paging<TPortSuggestions>>();


        if (Strings.isNullOrEmpty(commonUser.getUnifyId())) {
            response.setErrorCode(BussErrorCode.ERROR_CODE_800001.getErrorcode());
            return response;
        }
        try {
            TPortSuggestions tPortSuggestions =new TPortSuggestions();
            startIndex = Objects.firstNonNull(startIndex, 1);
            pageSize = Objects.firstNonNull(pageSize, 10);
            startIndex = (startIndex - 1) * pageSize;
            Integer endIndex = startIndex + pageSize;
            tPortSuggestions.setStartDate(!Strings.isNullOrEmpty(startDate) ? DATE_TIME_FORMAT.parseDateTime(startDate).toDate() : null);
            tPortSuggestions.setEndDate(!Strings.isNullOrEmpty(endDate) ? DATE_TIME_FORMAT.parseDateTime(endDate).plusDays(1).toDate() : null);
            tPortSuggestions.setStartIndex(startIndex);
            tPortSuggestions.setEndIndex(endIndex);
            tPortSuggestions.setUnifyId(commonUser.getUnifyId());

           List<TPortSuggestions> tPortSuggestionsList=suggestionsManager.querySuggestions(tPortSuggestions);
            Long    total= suggestionsManager.findCountTPortSuggestions(tPortSuggestions);
            Paging <TPortSuggestions> tPortOrderBasePagingList = new Paging<TPortSuggestions>(total, tPortSuggestionsList);
            response.setResult(tPortOrderBasePagingList);
        } catch (Exception e) {
            logger.error("failed to querySuggestions, parameter:{}, cause:{}", commonUser.getUnifyId(),
                    e);
            response.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
        }
        return response;
    }


    @Override
    public Response<TPortSuggestions> querySuggestionsDetail(CommonUser commonUser,String id) {
        logger.info("call querySuggestionsDetail id:{}, unifyId:{}", id,commonUser.getUnifyId());
        Response<TPortSuggestions> response = new Response<TPortSuggestions>();


        if (Strings.isNullOrEmpty(commonUser.getUnifyId())) {
            response.setErrorCode(BussErrorCode.ERROR_CODE_800001.getErrorcode());
            return response;
        }
        TPortSuggestions tPortSuggestions =new TPortSuggestions();
        try {

            tPortSuggestions.setUnifyId(commonUser.getUnifyId());
            tPortSuggestions.setId(id);

           TPortSuggestions newPortSuggestions=suggestionsManager.querySuggestionsDetail(tPortSuggestions);;
            response.setResult(newPortSuggestions);
        } catch (Exception e) {
            logger.error("failed to querySuggestionsDetail, parameter:{}, cause:{}", tPortSuggestions,
                    e);
            response.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
        }
        return response;
    }

    @Override
    public Response<String> suggestions(TPortSuggestions tPortSuggestions) {
        logger.info("call suggestions, PARAMETER:{}", tPortSuggestions);
        Response<String> result = new Response<String>();
        try {
            tPortSuggestions.setId(seqGenertatorMapper.getNextValue("PORTAL.S_T_INFO_SUGGESTIONS").toString());
            tPortSuggestions.setCreateTime(DateUtil.getDate());
            suggestionsManager.suggestions(tPortSuggestions);
            logger.info("success to suggestions,PARAMETER:{}, RESULT:{}", new Object[]{tPortSuggestions, result});
            result.setResult("000000");
            return result;
        } catch (Exception e) {
            logger.error("failed to suggestions,PARAMETER:{} CAUSE:{}", new Object[]{tPortSuggestions,e});
            result.setErrorCode(BussErrorCode.ERROR_CODE_999999.getErrorcode());
            return result;
        }

    }
}
