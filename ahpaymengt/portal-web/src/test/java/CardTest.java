import cn.com.huateng.util.Md5;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2013-08-06
 */
public class CardTest {
    private final static DateTimeFormatter MILLS = DateTimeFormat.forPattern("yyyyMMddHHmmssSSS");
    private final static DateTimeFormatter SECONDS = DateTimeFormat.forPattern("yyyyMMddHHmmss");
    private final static DateTimeFormatter DATES = DateTimeFormat.forPattern("yyyyMMdd");




    public static void main(String[] args) throws Exception{

       /* String orderQueryJfUrl="http://integral.huateng.com.cn/integerServices.do?method=orderqueryJf";
        String jfMerchantid="3101000004";
        String jfMerchantidPwd="466344";
        String jfMerchantidKey="0AC538D11015D0BC";

        String productNo = "18001712010";
        String STRUSERTYPE;

        if (productNo.matches("^(133|153|189|180|181)[0-9]{8}$")) {
            STRUSERTYPE = "7";
        } else {
            STRUSERTYPE = "18";
        }

        String reqTime = "20130814";

        String STRPROVINCEID = "02";

        String macMessage = "MERCHANTID="+jfMerchantid+"&MERCHANTPWD="+jfMerchantidPwd+"&PHONE="
                +productNo+"&STRUSERTYPE="+STRUSERTYPE+"&STRPROVINCEID="
                +STRPROVINCEID+"&REQTIME="+reqTime+"&KEY="+jfMerchantidKey;
        System.out.println(macMessage);

        String mac =  Md5.encode(macMessage);

        System.out.println("MAC:"+mac);

        ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();

        builder.put("MERCHANTID", jfMerchantid)
                .put("MERCHANTPWD", jfMerchantidPwd)
                .put("PHONENO", productNo)
                .put("STRUSERTYPE", STRUSERTYPE)
                .put("STRPROVINCEID", STRPROVINCEID)
                .put("REQTIME", reqTime)
                .put("MAC", mac);

        Map<String, String> params = builder.build();

        String body = HttpRequest.post(orderQueryJfUrl, params, true)
                .trustAllCerts().trustAllHosts().body();
        Map<String, String> result = XMLMapper.cardFromXML(body);
        System.out.println(result);*/

        String cardNo = "160003336402";
        String password = "111111";
        String merchantId = "0018888888";
        //卡鉴权
       /*Map<String, String> params = ImmutableMap.of("MERCHANTID", merchantId,
                "CARDNO", cardNo,
                "CARDPWD", password);
        String body = HttpRequest.post("http://116.228.55.217:8002/cardpay4/denselyqry.do", params, true)
                .trustAllCerts().trustAllHosts().body();
        Map<String, String> result = XMLMapper.cardFromXML(body);
        System.out.println(result);*/

        //充值
        /*DateTime now = new DateTime();
        String mills = MILLS.print(now);
        //商户订单号生成规则：'OS'+ yyyyMMddHHmmssSSS + 随机3位数
        String orderID = "OS" + mills + UUID.randomUUID().toString().substring(0, 3);   //商户订单号

        //商户订单流水号生成规则：orderID + 随机4位数
        String orderReqID = orderID + UUID.randomUUID().toString().substring(0, 4);
        String transDate = DATES.print(now); //交易日期          `

        ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();

        String commKey = "G7AXS7874305BV59";

        String productId = geneProInfo(commKey,"160003336402", "111111", "13411111111");

        String mac = "ACCORGCODE=SL1000000001&MERCHANTID=0018888888" +
                "&SUBMERCHANTID=null&ORDERSEQ=20130816090659"
                + "&ORDERREQSEQ=20130816090659000001&ORDERAMT=1"
                + "&ORDERDATE=20130816&BUSICODE=0001000001"
                + "&TMNUM=13764590131&PRODUCTID=" + productId
                + "&CUSTOMERID=null&PRODUCTDESC=null"
                + "&BANKID=BESTCARD"
                + "&FRONTMERURL=null&BACKMERURL=null&ATTACH=null&DIVDETAILS=null&PEDCNT=null"
                + "&CLIENTIP=192.168.231.231&KEY=G7AXS7874305BV59";

        System.out.println("加密前:"+mac);

        String Mac = Md5.encode(mac);
        System.out.println("加密后:"+Mac);
        builder.put("ACCORGCODE", "SL1000000001")
                .put("MERCHANTID", "0018888888")
                .put("ORDERSEQ", "20130816090659")
                .put("ORDERREQSEQ", "20130816090659000001")
                .put("ORDERAMT", "1")
                .put("ORDERDATE", "20130816")
                .put("BUSICODE", "0001000001")
                .put("TMNUM", "13764590131")
                .put("PRODUCTID", productId)
                .put("BANKID", "BESTCARD")
                .put("CLIENTIP","192.168.231.231")
                .put("MAC", Mac);

        Map<String, String> params = builder.build();
        String body = HttpRequest.post("http://116.228.55.217:8003/bestpayws/integralandRechange.do", params, true).body();
        System.out.println(XMLMapper.cardFromXML(body));*/

        //查询余额
       /* Map<String, String>  params = ImmutableMap.of("MERCHANTID", merchantId,
                "CARDNO", "160003335428");
        HttpRequest post1 = HttpRequest.post("http://116.228.55.217:8002/cardpay4/balqry.do", params, true)
                .trustAllCerts().trustAllHosts();
        String body = post1.body();
        System.out.println(body);
        System.out.println(XMLMapper.cardFromXML(body));*/


        //修改密码
        /*String macStr = "COMMCODE=0018888888&CARDNO=160003335420&CARDTYPE=1&OLDPASSWD=811561&NEWPASSWD=811560&KEY=G7AXS7874305BV59";


        String mac = Md5.encode(macStr);
        ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
        builder.put("COMMCODE", "0018888888").put("CARDNO", "160003335420")
                .put("CARDTYPE", "1").put("OLDPASSWD", "811561")
                .put("NEWPASSWD", "811560").put("MAC", mac);
        Map<String, String>   params = builder.build();
       *//* params = ImmutableMap.of("MERCHANTID", merchantId,
                "CARDNO", cardNo,
                "CARDTYPE","1",
                "OLDPASSWD", password,
                "NEWPASSWD", "905901"
                );*//*
        System.out.println(params);
        //http://cardpay.huateng.com.cn/prepaidCardUpdatePwd.do
        //http://116.228.55.217:8002/cardpay4/cardModifyPwd.do
        HttpRequest post = HttpRequest.post("http://116.228.55.217:8002/cardpay4/cardModifyPwd.do", params, true)
                .trustAllCerts().trustAllHosts();
        String body = post.body();
        System.out.println(body);
        System.out.println(XMLMapper.cardFromXML(body));*/


    }

    /**
     * 生成产品标识
     *
     * @param productId    支付产品号
     * @param productPwd   支付产品密码
     * @param busProductId 被充值账户
     * @return 2013-05-28
     */
    public static String geneProInfo(String commKey,String productId, String productPwd, String busProductId) {

        /***产品信息加密规则***/
        StringBuffer sbparamPROIFO = new StringBuffer();
        sbparamPROIFO.append("PRODUCTID=").append(productId);  //支付产品号
        sbparamPROIFO.append("&PRODUCTPWD=").append(productPwd);   //支付产品密码
        sbparamPROIFO.append("&CVV2=");
        sbparamPROIFO.append("&VALIDATE=");//有效期(信用卡时须填写)
        sbparamPROIFO.append("&USERCARDTYPE=").append("07"); //证件号：07 身份证
        sbparamPROIFO.append("&USERCARDID=");
        sbparamPROIFO.append("&USERCARDNAME=");
        sbparamPROIFO.append("&MOBILE=");
        sbparamPROIFO.append("&BUSPRODUCTID=").append(busProductId); //被充值账户
        sbparamPROIFO.append("&BUSPRODUCTPWD=");
        sbparamPROIFO.append("&STRPROVINCEID=").append("19");
        sbparamPROIFO.append("&KEY=" + commKey);

        String key30 = commKey;
        for (int i = 0; i < (30 - commKey.length()); i++) {
            key30 = key30 + "0";
        }
        //生成产品标识
        String proInfo = "";
        try {
            System.out.println("===================生成产品标识 Start====================>>");
            System.out.println(sbparamPROIFO.toString());
            proInfo = Md5.desEncrypt(sbparamPROIFO.toString(), key30);
        } catch (Exception e) {
            System.out.println("生成产品标识出错：");
            e.printStackTrace();
            return proInfo;
        }
        System.out.println("proInfo:" + proInfo);
        System.out.println("===================生成产品标识 End====================>>");
        return proInfo;
    }


}
