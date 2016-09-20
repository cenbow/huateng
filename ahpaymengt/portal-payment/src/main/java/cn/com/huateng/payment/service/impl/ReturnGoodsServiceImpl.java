package cn.com.huateng.payment.service.impl;

import cn.com.huateng.account.service.SeqGeneratorService;
import cn.com.huateng.common.BussErrorCode;
import cn.com.huateng.common.CodeGenerator;
import cn.com.huateng.common.OrderStatus;
import cn.com.huateng.payment.dao.SaleReturnApplyMapper;
import cn.com.huateng.payment.dao.TPortOrderBaseMapper;
import cn.com.huateng.payment.dao.VirtualGoodsMapper;
import cn.com.huateng.payment.model.SaleReturnApply;
import cn.com.huateng.payment.model.TPortOrderBase;
import cn.com.huateng.payment.model.VirtualGoods;
import cn.com.huateng.payment.service.ReturnGoodsService;
import cn.com.huateng.util.DateUtil;
import com.aixforce.annotations.ParamInfo;
import com.google.common.base.Strings;
import com.huateng.p3.component.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * User: 董培基
 * Date: 14-12-5
 * Time: 下午1:42
 */
@Slf4j
@Service
public class ReturnGoodsServiceImpl implements ReturnGoodsService {

    @Autowired
    private VirtualGoodsMapper virtualGoodsMapper;

    @Autowired
    private SaleReturnApplyMapper saleReturnApplyMapper;

    @Autowired
    private SeqGeneratorService seqGeneratorService;

    @Value("#{app.returnGoodsSeq}")
    private String returnGoodsSeq;

    @Autowired
    private TPortOrderBaseMapper tPortOrderBaseMapper;

    /**
     * 退货申请 根据 虚拟商品表 订单号 发起退货
     *
     * @param orderNo          订单号
     * @param electronicNumber 退货电子单号
     * @param count            数量
     * @return 成功失败
     */
    @Override
    public Response<Boolean> returnGoodsApply(String orderNo, String electronicNumber, Long refundAmt, Long count) {
        checkNotNull(count);
        checkNotNull(refundAmt);
        if (Strings.isNullOrEmpty(orderNo)) {
            log.error("退货单号为空 orderNo is null");
            return new Response<>(BussErrorCode.ERROR_CODE_800001.getErrorcode());
        }
        try {
            VirtualGoods virtualGoods = virtualGoodsMapper.findVirtualGoods(new VirtualGoods(orderNo));
            if (null != virtualGoods) {
                log.info("退货开始 orderNo:{} refundAmt:{} electronicNumber:{} count:{}", orderNo, refundAmt, electronicNumber, count);
                insertSaleReturnApply(virtualGoods, orderNo, electronicNumber, refundAmt, count);
                return new Response<>(true);
            } else {
                log.error("parameter orderNo:{} resultSet is null");
                return new Response<>(BussErrorCode.ERROR_CODE_800003.getErrorcode());
            }
        } catch (DataIntegrityViolationException e) {
            //数据库校验数据错误
            String errorCode;
            if (e.toString().contains("ORA-00001")) {
                errorCode = BussErrorCode.ERROR_CODE_900003.getErrorcode();
            } else {
                errorCode = BussErrorCode.ERROR_CODE_900007.getErrorcode();
            }
            log.error("failed to returnGoodsApply ", e);
            return new Response<>(errorCode);

        } catch (CannotAcquireLockException e) {

            log.error("failed to returnGoodsApply", e);
            return new Response<>(BussErrorCode.ERROR_CODE_900006.getErrorcode());

        } catch (Exception e) {

            log.error("failed to returnGoodsApply", e);
            return new Response<>(BussErrorCode.ERROR_CODE_999999.getErrorcode());

        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    private void insertSaleReturnApply(VirtualGoods virtualGoods, String orderNo, String electronicNumber, Long orderAmt, Long count) {
        SaleReturnApply saleReturnApply = new SaleReturnApply();
        String recordNo = CodeGenerator.generateSeqNo(seqGeneratorService.generateOrderNo(returnGoodsSeq));
        saleReturnApply.setRecordNo(recordNo);
        saleReturnApply.setOrderNo(orderNo);
        saleReturnApply.setStatus(virtualGoods.getStatus());
        saleReturnApply.setCheckFlag(OrderStatus.ORDER_STATUS_11.getValue());
        saleReturnApply.setCount(count);
        saleReturnApply.setElectronicNumber(electronicNumber);
        saleReturnApply.setCreateTime(DateUtil.getTime());
        saleReturnApply.setSkuNo(virtualGoods.getSkuNo());
        saleReturnApply.setTxnTime(virtualGoods.getTxnTime());
        saleReturnApply.setRefundAmt(orderAmt);
        saleReturnApply.setGoodsName(virtualGoods.getGoodsName());
        saleReturnApply.setUnifyId(virtualGoods.getUnifyId());
        saleReturnApplyMapper.insertSaleReturnApply(saleReturnApply);
    }

    public static void main(String[] args) {
        Long abc = Long.parseLong("20141211000000000003");
    }
}
