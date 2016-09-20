package com.huateng.p3.account.base;


import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.FastDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huateng.p3.account.persistence.TSequenceProduceMapper;

@Service
public class CodeGeneratorService  {

	@Autowired
    private TSequenceProduceMapper tSequenceProduceMapper;


	public String generateSeq(String appcode, String bussCode, String bussType,
			String seqName) {
		StringBuffer txnSeq = new StringBuffer(35);
		//txnSeq.append(appcode).append(bussCode).append(bussType).append("000");
		txnSeq.append("A");
		String datastr = FastDateFormat.getInstance("MMddHHmmss").format(
				new Date());
		txnSeq.append(datastr);
		txnSeq.append(StringUtils.right(StringUtils.leftPad(
                tSequenceProduceMapper.produceSequence(seqName), 9, '0'), 9));
		return txnSeq.toString();
	
	}
	/**
	 * 营销规则长度由35修改为20
	 */
	/*@Override
	public String generateSeq(String appcode, String bussCode, String bussType,
			String seqName) {
		StringBuffer txnSeq = new StringBuffer(35);
		txnSeq.append(appcode).append(bussCode).append(bussType).append("000");
		String datastr = FastDateFormat.getInstance("yyyyMMddHHmmss").format(
				new Date());
		txnSeq.append(datastr);
		txnSeq.append(StringUtils.right(StringUtils.leftPad(
				tsequenceproducemapper.produceSequence(seqName), 9, '0'), 9));
		return txnSeq.toString();
		
	
	}*/

}
