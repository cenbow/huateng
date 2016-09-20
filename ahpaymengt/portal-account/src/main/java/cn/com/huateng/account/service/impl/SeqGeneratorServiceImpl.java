package cn.com.huateng.account.service.impl;

import cn.com.huateng.account.dao.SeqGenertatorMapper;
import cn.com.huateng.account.service.SeqGeneratorService;
import com.google.common.base.Strings;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: 董培基
 * Date: 13-7-30
 * Time: 下午7:15
 */
@Service("seqGeneratorService")
public class SeqGeneratorServiceImpl implements SeqGeneratorService {

    @Autowired
    private SeqGenertatorMapper seqGenertatorMapper;

    @Override
    public Long generateOrderNo(String seqName) {
        return seqGenertatorMapper.getNextValue(seqName);
    }


    @Override
    public String TransferOrderSeq(String seqName){
        StringBuffer orderSeq=new StringBuffer(20);
        String datastr = DateTime.now().toString("yyyyMMdd");
        orderSeq.append(datastr);
        orderSeq.append(StringUtils.right(Strings.padStart(
                seqGenertatorMapper.getNextValue(seqName)+"", 10, '0'), 10));
        return orderSeq.toString();
    }
}
