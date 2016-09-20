package com.huateng.p3.account.persistence;

import org.apache.ibatis.annotations.Param;

public interface TSequenceProduceMapper {
  String produceSequence(@Param(value = "seqName") String seqName);
}