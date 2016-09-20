package cn.com.huateng.payment.service.impl;

import cn.com.huateng.CommonUser;
import cn.com.huateng.account.service.SeqGeneratorService;
import cn.com.huateng.common.BussErrorCode;
import cn.com.huateng.common.CodeGenerator;
import cn.com.huateng.common.FeeType;
import cn.com.huateng.common.OrderStatus;
import cn.com.huateng.payment.dao.CartMapper;
import cn.com.huateng.payment.manage.CartManage;
import cn.com.huateng.payment.manage.TPortOrderBaseManage;
import cn.com.huateng.payment.manage.VirtualGoodsManage;
import cn.com.huateng.payment.model.Cart;
import cn.com.huateng.payment.model.SubmitWebGatePay;
import cn.com.huateng.payment.model.TPortOrderBase;
import cn.com.huateng.payment.model.VirtualGoods;
import cn.com.huateng.payment.service.CartService;
import cn.com.huateng.payment.service.WebGatePayService;
import cn.com.huateng.util.DateUtil;
import com.aixforce.annotations.ParamInfo;
import com.aixforce.item.model.Item;
import com.aixforce.item.model.Sku;
import com.aixforce.item.service.ItemService;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.huateng.p3.account.common.enummodel.Paging;
import com.huateng.p3.component.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * User: 董培基
 * Date: 14-11-14
 * Time: 下午3:10
 */
@Service
public class CartServiceImpl implements CartService {

    private static final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

    private final Splitter splitter = Splitter.on(',').trimResults().omitEmptyStrings();

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ItemService itemService;

    @Autowired
    private CartManage cartManage;

    @Autowired
    private VirtualGoodsManage virtualGoodsManage;

    @Value("#{app.virtualGoodsSeq}")
    private String virtualGoodsSeq;

    @Value("#{app.orderBaseSeq}")
    private String orderBaseSeq;

    /*购物车序列*/
    @Value("#{app.cartSeq}")
    private String cartSeq;

    @Autowired
    private SeqGeneratorService seqGeneratorService;

    @Autowired
    private WebGatePayService webGatePayService;

    /**
     * 购物车列表 内容
     *
     * @param commonUser 用户
     * @return 购物车列表
     */
    @Override
    public Response<Paging<Map<String, Object>>> cartList(CommonUser commonUser, Integer startIndex, Integer pageSize, String ids) {
        //必填判断
        if (commonUser == null || Strings.isNullOrEmpty(commonUser.getUnifyId())) {
            return new Response<>(BussErrorCode.ERROR_CODE_800001.getErrorcode());
        }
        try {
            Cart cart = new Cart();
            startIndex = Objects.firstNonNull(startIndex, 1);
            pageSize = Objects.firstNonNull(pageSize, 10000); //默认查询所有
            startIndex = (startIndex - 1) * pageSize;
            Integer endIndex = startIndex + pageSize;
            cart.setUnifyId(commonUser.getUnifyId());
            cart.setStartIndex(startIndex);
            cart.setEndIndex(endIndex);

            List<Long> longList = Lists.newArrayList();
            //购物车确认订单
            if (!Strings.isNullOrEmpty(ids)) {
                Iterable<String> parts = splitter.split(ids);
                if (Iterables.isEmpty(parts)) {
                    logger.warn("no goods need to sort,return directly");
                    return new Response<>(BussErrorCode.ERROR_CODE_800002.getErrorcode());
                }
                Iterable<Long> parsedIds = Iterables.transform(parts, new Function<String, Long>() {
                    @Override
                    public Long apply(String input) {
                        return Long.valueOf(input);
                    }
                });
                longList = ImmutableList.copyOf(parsedIds);
            }
            List<Map<String, Object>> mapList = Lists.newArrayList();
            //列表查询
            List<Cart> cartMapperCartList = cartMapper.findCartList(cart);
            Long total = cartMapper.findCountCartList(cart);
            for (Cart cart1 : cartMapperCartList) {
                if (!Strings.isNullOrEmpty(ids)) {
                    if (longList.contains(cart1.getCartNo())) {
                        Sku sku = itemService.findSkuById(cart1.getSku_no());
                        Item item = itemService.findById(sku.getItemId());
                        int thisCartPrice = cart1.getGoods_sum() * sku.getPrice();
                        cart1.setTotalPrice(thisCartPrice);
                        Map<String, Object> stringObjectMap = Maps.newHashMap();
                        stringObjectMap.put("sku", sku);
                        stringObjectMap.put("item", item);
                        stringObjectMap.put("cart", cart1);
                        mapList.add(stringObjectMap);
                    }
                } else {
                    Sku sku = itemService.findSkuById(cart1.getSku_no());
                    Item item = itemService.findById(sku.getItemId());
                    int thisCartPrice = cart1.getGoods_sum() * sku.getPrice();
                    cart1.setTotalPrice(thisCartPrice);
                    Map<String, Object> stringObjectMap = Maps.newHashMap();
                    stringObjectMap.put("sku", sku);
                    stringObjectMap.put("item", item);
                    stringObjectMap.put("cart", cart1);
                    mapList.add(stringObjectMap);
                }
            }


            Paging<Map<String, Object>> paging = new Paging<>(total, mapList);
            return new Response<>(paging);
        } catch (DataIntegrityViolationException e) {
            //数据库校验数据错误
            String errorCode;
            if (e.toString().contains("ORA-00001")) {
                errorCode = BussErrorCode.ERROR_CODE_900003.getErrorcode();
            } else {
                errorCode = BussErrorCode.ERROR_CODE_900007.getErrorcode();
            }
            logger.error("failed to query cart{}", e);
            return new Response<>(errorCode);

        } catch (CannotAcquireLockException e) {

            logger.error("failed to query cart{}", e);
            return new Response<>(BussErrorCode.ERROR_CODE_900006.getErrorcode());

        } catch (Exception e) {

            logger.error("failed to query cart{}", e);
            return new Response<>(BussErrorCode.ERROR_CODE_999999.getErrorcode());

        }
    }

    /**
     * 通过购物车序号获取购物车列信息
     *
     * @param cartNo 购物车序号
     * @return 购物车列信息
     */
    @Override
    public Response<Cart> getCartByCartNo(Long cartNo) {
        //必填判断
        if (null == cartNo) {
            return new Response<>(BussErrorCode.ERROR_CODE_800001.getErrorcode());
        }
        try {
            Cart cart = new Cart();
            cart.setCartNo(cartNo);
            List<Cart> cartList = cartMapper.findCartListB(cart);
            if (cartList.size() == 1) {
                logger.info("getCartByCartNo number = {}, cartNo = {}", cartList.size(), cartNo);
                return new Response<>(cartList.get(0));
            } else if (cartList.size() == 0) {
                logger.info("getCartByCartNo number = {}, cartNo = {}", cartList.size(), cartNo);
                return new Response<>(BussErrorCode.ERROR_CODE_600001.getErrorcode());
            } else {
                logger.error("getCartByCartNo number = {}, cartNo = {}", cartList.size(), cartNo);
                return new Response<>(BussErrorCode.ERROR_CODE_600010.getErrorcode());
            }
        } catch (DataIntegrityViolationException e) {
            //数据库校验数据错误
            String errorCode;
            if (e.toString().contains("ORA-00001")) {
                errorCode = BussErrorCode.ERROR_CODE_900003.getErrorcode();
            } else {
                errorCode = BussErrorCode.ERROR_CODE_900007.getErrorcode();
            }
            logger.error("failed to query cart{}", e);
            return new Response<>(errorCode);

        } catch (CannotAcquireLockException e) {

            logger.error("failed to query cart{}", e);
            return new Response<>(BussErrorCode.ERROR_CODE_900006.getErrorcode());

        } catch (Exception e) {

            logger.error("failed to query cart{}", e);
            return new Response<>(BussErrorCode.ERROR_CODE_999999.getErrorcode());

        }
    }

    /**
     * 添加购物车
     *
     * @param cart 购物车信息
     * @return 成功失败
     */
    @Override
    public Response<String> addCart(Cart cart) {
        //必填判断
        if (cart == null || Strings.isNullOrEmpty(cart.getUnifyId())
                || null == cart.getSku_no()
                || null == cart.getGoods_sum()
                ) {
            return new Response<>(false, BussErrorCode.ERROR_CODE_800001.getErrorcode());
        }
        try {

            List<Cart> cartList = cartMapper.findCartListB(cart);
            if (cartList.size() == 1) {
                logger.info("unifyId:{} 已经添加商品 sku id{} 到购物车", cart.getUnifyId(), cart.getSku_no());
                Cart cart1 = cartList.get(0);
                cart1.setGoods_sum(cart1.getGoods_sum() + cart.getGoods_sum());
                cartManage.updateCart(cart1);
            } else if (cartList.size() == 0) {
                Long cartNo = seqGeneratorService.generateOrderNo(cartSeq);
                logger.info("unifyId:{} 添加 商品属性序号:{} 商品名称:{}  数量:{} 到购物车,购物车序列号 {}", new Object[]{cart.getUnifyId(), cart.getSku_no(), cart.getGoods_name(), cart.getGoods_sum(), cartNo});
                cart.setCartNo(cartNo);
                cart.setCreate_time(DateUtil.getTime());
                cart.setIs_active("0");
                cartManage.insertCart(cart);
            } else {
                logger.info("unifyId:{} 购物车中 商品 sku id:{} 已经不止一件 数据异常", cart.getUnifyId(), cart.getSku_no());
                return new Response<>(false, BussErrorCode.ERROR_CODE_600010.getErrorcode());
            }
            return new Response<>(true, "success");
        } catch (DataIntegrityViolationException e) {
            //数据库校验数据错误
            String errorCode;
            if (e.toString().contains("ORA-00001")) {
                errorCode = BussErrorCode.ERROR_CODE_900003.getErrorcode();
            } else {
                errorCode = BussErrorCode.ERROR_CODE_900007.getErrorcode();
            }
            logger.error("failed to insert cart{}", e);
            return new Response<>(false, errorCode);

        } catch (CannotAcquireLockException e) {

            logger.error("failed to insert cart{}", e);
            return new Response<>(false, BussErrorCode.ERROR_CODE_900006.getErrorcode());

        } catch (Exception e) {

            logger.error("failed to insert cart{}", e);
            return new Response<>(false, BussErrorCode.ERROR_CODE_999999.getErrorcode());

        }
    }

    /**
     * 添加购物车
     *
     * @param cart 购物车信息
     * @return 成功失败
     */
    @Override
    public Response<String> updateCart(Cart cart) {
        //必填判断
        if (cart == null || null == cart.getCartNo() || Strings.isNullOrEmpty(cart.getUnifyId())
                || null == cart.getSku_no()
                || null == cart.getGoods_sum()
                ) {
            return new Response<>(false, BussErrorCode.ERROR_CODE_800001.getErrorcode());
        }
        try {

            List<Cart> cartList = cartMapper.findCartListB(cart);
            if (cartList.size() == 1) {
                logger.info("unifyId:{} 已经添加商品 sku id{} 到购物车", cart.getUnifyId(), cart.getSku_no());
                cartManage.updateCart(cart);
            } else {
                logger.info("unifyId:{} 购物车中 商品 sku id:{} 被删除或者不止一件 数据异常", cart.getUnifyId(), cart.getSku_no());
                return new Response<>(false, BussErrorCode.ERROR_CODE_600010.getErrorcode());
            }
            return new Response<>(true, "success");
        } catch (DataIntegrityViolationException e) {
            //数据库校验数据错误
            String errorCode;
            if (e.toString().contains("ORA-00001")) {
                errorCode = BussErrorCode.ERROR_CODE_900003.getErrorcode();
            } else {
                errorCode = BussErrorCode.ERROR_CODE_900007.getErrorcode();
            }
            logger.error("failed to update cart{}", e);
            return new Response<>(false, errorCode);

        } catch (CannotAcquireLockException e) {

            logger.error("failed to update cart{}", e);
            return new Response<>(false, BussErrorCode.ERROR_CODE_900006.getErrorcode());

        } catch (Exception e) {

            logger.error("failed to update cart{}", e);
            return new Response<>(false, BussErrorCode.ERROR_CODE_999999.getErrorcode());

        }
    }

    /**
     * 通过购物车序号删除购物车列信息
     *
     * @param cartNo 购物车序号
     * @return 成功或者失败
     */
    @Override
    public Response<String> deleteCart(Long cartNo) {
        //必填判断
        if (null == cartNo) {
            return new Response<>(false, BussErrorCode.ERROR_CODE_800001.getErrorcode());
        }
        try {
            Cart cart = new Cart();
            cart.setCartNo(cartNo);
            cartManage.deleteCart(cart);
            return new Response<>(true, "000000");
        } catch (DataIntegrityViolationException e) {
            //数据库校验数据错误
            String errorCode;
            if (e.toString().contains("ORA-00001")) {
                errorCode = BussErrorCode.ERROR_CODE_900003.getErrorcode();
            } else {
                errorCode = BussErrorCode.ERROR_CODE_900007.getErrorcode();
            }
            logger.error("failed to query cart{}", e);
            return new Response<>(false, errorCode);

        } catch (CannotAcquireLockException e) {

            logger.error("failed to query cart{}", e);
            return new Response<>(false, BussErrorCode.ERROR_CODE_900006.getErrorcode());

        } catch (Exception e) {

            logger.error("failed to query cart{}", e);
            return new Response<>(false, BussErrorCode.ERROR_CODE_999999.getErrorcode());

        }
    }

    /**
     * 通过购物车序号列表批量删除购物车列信息
     *
     * @param lists 购物车序号列表
     * @return 成功或者失败
     */
    @Override
    public Response<String> patchDeleteCart(List<Long> lists) {
        //必填判断
        if (null == lists || lists.size() == 0) {
            return new Response<>(false, BussErrorCode.ERROR_CODE_800001.getErrorcode());
        }
        try {
            cartManage.patchDeleteCart(lists);
            return new Response<>(true, "000000");
        } catch (DataIntegrityViolationException e) {
            //数据库校验数据错误
            String errorCode;
            if (e.toString().contains("ORA-00001")) {
                errorCode = BussErrorCode.ERROR_CODE_900003.getErrorcode();
            } else {
                errorCode = BussErrorCode.ERROR_CODE_900007.getErrorcode();
            }
            logger.error("failed to query cart{}", e);
            return new Response<>(false, errorCode);

        } catch (CannotAcquireLockException e) {

            logger.error("failed to query cart{}", e);
            return new Response<>(false, BussErrorCode.ERROR_CODE_900006.getErrorcode());

        } catch (Exception e) {

            logger.error("failed to query cart{}", e);
            return new Response<>(false, BussErrorCode.ERROR_CODE_999999.getErrorcode());

        }
    }

    /**
     * 创建 购物车订单
     *
     * @param lists 购物车序号
     * @return 订单号
     */
    @Override
    public Response<SubmitWebGatePay> createOrder(List<Long> lists, String requestIp, String unifyId) {
        //必填判断
        if (null == lists || lists.size() == 0) {
            return new Response<>(BussErrorCode.ERROR_CODE_800001.getErrorcode());
        }
        try {
            List<Cart> cartList = cartMapper.findByIds(lists);
            //构建调用网关 业务对象
            SubmitWebGatePay submitWebGatePay = new SubmitWebGatePay();
            String orderId = "";
            submitWebGatePay.setFeeType(FeeType.VIRTUALGOODS.getValue());
            submitWebGatePay.setUnifyId(unifyId);
            submitWebGatePay.setClientIp(requestIp);
            if (null != cartList && cartList.size() != 0) {
                for (Cart cart : cartList) {
                    VirtualGoods virtualGoods = new VirtualGoods();
                    String virtualGoodSeq = CodeGenerator.generateSeqNo(seqGeneratorService.generateOrderNo(virtualGoodsSeq));
                    virtualGoods.setOrderNo(virtualGoodSeq);
                    virtualGoods.setCreateTime(DateUtil.getTime());
                    virtualGoods.setGoodsName(cart.getGoods_name());
                    virtualGoods.setSkuNo(cart.getSku_no());
                    virtualGoods.setNumber(cart.getGoods_sum().longValue());
                    virtualGoods.setStatus(OrderStatus.ORDER_STATUS_1.getValue());//待支付
                    if (Strings.isNullOrEmpty(orderId)) {
                        orderId = orderId + virtualGoodSeq;
                    } else {
                        orderId = orderId + "|" + virtualGoodSeq;
                    }
                    Sku sku = itemService.findSkuById(cart.getSku_no());
                    virtualGoods.setOrderAmt(cart.getGoods_sum() * sku.getPrice().longValue());
                    virtualGoods.setUnifyId(cart.getUnifyId());
                    virtualGoods.setTxnTime(DateUtil.getTime());
                    TPortOrderBase tPortOrderBase = new TPortOrderBase();
                    tPortOrderBase.setOrderSeq(CodeGenerator.generateSeqNo(seqGeneratorService.generateOrderNo(orderBaseSeq)));
                    tPortOrderBase.setOrderReqTranSeq(virtualGoodSeq);
                    tPortOrderBase.setUnifyId(cart.getUnifyId());
                    tPortOrderBase.setStatus(OrderStatus.ORDER_STATUS_1.getValue());
                    tPortOrderBase.setCreateTime(DateUtil.getDate());
                    tPortOrderBase.setFeeType(FeeType.VIRTUALGOODS.getValue());
                    tPortOrderBase.setOrderAmount(cart.getGoods_sum() * sku.getPrice().longValue());
                    virtualGoodsManage.insertVirtualGoods(virtualGoods, tPortOrderBase);
                }
            } else {
                return new Response<>(BussErrorCode.ERROR_CODE_600010.getErrorcode());
            }
            //批量删除购物车已支付商品
            cartManage.patchDeleteCart(lists);
            submitWebGatePay.setOrderId(orderId);
            //取网关调用对象
            Response<SubmitWebGatePay> submitWebGatePayResponse = webGatePayService.orderPay(submitWebGatePay);
            if (submitWebGatePayResponse.isSuccess()) {
                SubmitWebGatePay submitWebGatePay1 = submitWebGatePayResponse.getResult();

                return new Response<>(submitWebGatePay1);
            } else {
                return new Response<>(BussErrorCode.ERROR_CODE_400000.getErrorcode());
            }
        } catch (DataIntegrityViolationException e) {
            //数据库校验数据错误
            String errorCode;
            if (e.toString().contains("ORA-00001")) {
                errorCode = BussErrorCode.ERROR_CODE_900003.getErrorcode();
            } else {
                errorCode = BussErrorCode.ERROR_CODE_900007.getErrorcode();
            }
            logger.error("failed to query cart{}", e);
            return new Response<>(errorCode);

        } catch (CannotAcquireLockException e) {

            logger.error("failed to query cart{}", e);
            return new Response<>(BussErrorCode.ERROR_CODE_900006.getErrorcode());

        } catch (Exception e) {

            logger.error("failed to query cart{}", e);
            return new Response<>(BussErrorCode.ERROR_CODE_999999.getErrorcode());

        }
    }
}
