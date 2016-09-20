package com.huateng.p3.account.component;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.huateng.p3.account.common.enummodel.BussErrorCode;
import com.huateng.p3.account.common.exception.BizException;
import com.huateng.p3.account.common.util.StringUtil;

/**
 * Created with IntelliJ IDEA.
 * User: JamesTang
 * Date: 13-12-6
 * Time: 上午10:07
 * To change this template use File | Settings | File Templates.
 */
@Component
public class PinkeyComponent {

    private final Logger log = LoggerFactory.getLogger(PinkeyComponent.class);

    @Value("${encryptorhost}")
    private String host; 
    @Value("${encryptorport}")
    private int port;
    @Value("${testEncrypeIdleConnection}")
    private int testEncrypeIdleConnection = 60 * 60; //

    private Socket socket = null;
    private OutputStream os = null;
    private InputStream is = null;


    /*@PostConstruct
    public void init() {
        try {
            connect();
            // 启动测试空间连接线程
            Executors.newCachedThreadPool().execute(createTask());
        } catch (Exception e) {
            log.error("Ecrypt Machine   connect  error：" + e.getMessage());
        }

    }*/

    private void connect() {
        try {
            socket = new Socket(host, port);
            socket.setSoTimeout(1 * 1000);// 加密机连接超时设置为15秒
            os = socket.getOutputStream();
            is = socket.getInputStream();
            log.info("pinkey socket host:"+host+" port:"+port+" ok");
        } catch (Exception ce) {
            throw new BizException(BussErrorCode.ERROR_CODE_900000.getErrorcode(),
                    BussErrorCode.ERROR_CODE_900000.getErrordesc());
        }
    }


    /**
     * 测试加密机空闲连接
     */
    public synchronized void testIdleConnection() {
        try {
            //byte[]  b0 = BaseEncoding.base32().decode("00");  // TODO    待验证addby        James tang
            byte[] b0 = StringUtil.hexStringToBytes("00");
            os.write(b0);
            byte[] res = new byte[1];
            is.read(res);
            if ((new String(res)).equals("A")) {
                byte[] r2 = new byte[16];
                is.read(r2);
            } else {
                byte[] r2 = new byte[1];
                is.read(r2);
            }
        } catch (Exception e) {
            connect();
        }
    }

    /**
     * 测试加密机空闲连接线程  heart Beaten
     *
     * @return
     */
    private Runnable createTask() {
        return new Runnable() {
            public void run() {
                try {
                    while (true) {
                        Thread.sleep(testEncrypeIdleConnection * 1000);
                        testIdleConnection();
                    }
                } catch (Exception e) {
                	log.error(e.getMessage());
                }
            }
        };
    }

    /**
     * 关键数据密文
     *
     * @param flatMsg
     * @return
     */
    public synchronized String encryptedMsg(String flatMsg) {
        try {
            if (flatMsg == null || flatMsg.length() != 20) {
                return null;
            }
            byte[] b0 = StringUtil
                    .hexStringToBytes("72007000010203040506070101000A"
                            + flatMsg);
            os.write(b0);
            byte[] res = new byte[1];
            is.read(res);
            if ((new String(res)).equals("A")) {
                byte[] r2 = new byte[2];
                is.read(r2);
                int length = Integer.valueOf(StringUtil.bytesToHexString(r2),
                        16);
                byte[] r3 = new byte[length];
                is.read(r3);
                String returnMsg = StringUtil.bytesToHexString(r3)
                        .toUpperCase();
                return returnMsg;
            } else {
                byte[] r2 = new byte[1];
                is.read(r2);
                log.error("加密数据密文，错误返回:" + StringUtil.bytesToHexString(r2));
                return null;
            }
        } catch (Exception e) {

            log.error("加密数据密文，异常:" + e.toString());

            connect();
            throw new BizException(BussErrorCode.ERROR_CODE_900000.getErrorcode(),
                    BussErrorCode.ERROR_CODE_900000.getErrordesc());
        }
    }

    /**
     * 加密密钥
     *
     * @param flatPin
     * @return
     */
    public synchronized String encryptPin(String flatPin, String accountNo) {
        try {
            String s = "0000" + truncAccountNo(accountNo);
            String fp = "06" + flatPin + "FFFFFFFF";
            String xorPin = this.xorString(s, fp);
            byte[] b0 = StringUtil.hexStringToBytes("60DDEA5FD04787897A0071"
                    + xorPin);
            os.write(b0);
            byte[] res = new byte[1];
            is.read(res);
            if ((new String(res)).equals("A")) {
                byte[] r2 = new byte[8];
                is.read(r2);
                String passwd1 = StringUtil.bytesToHexString(r2).toUpperCase();
                return passwd1;
            } else {
                byte[] r2 = new byte[1];
                is.read(r2);
                log.error("加密密钥，错误返回:" + StringUtil.bytesToHexString(r2));
                throw new BizException(BussErrorCode.ERROR_CODE_900000.getErrorcode(),
                        BussErrorCode.ERROR_CODE_900000.getErrordesc());
            }
        } catch (Exception e) {
            log.error("加密密钥，异常:" + e.toString());

            connect();
            throw new BizException(BussErrorCode.ERROR_CODE_900000.getErrorcode(),
                    BussErrorCode.ERROR_CODE_900000.getErrordesc());
        }
    }

    /**
     * 将明文密钥转换为OTA密钥
     *
     * @param cardNo
     * @param flatPin
     * @return
     */
    public synchronized String encryptOtaPin(String cardNo, String flatPin) {
        try {
            String pinInfo;
            if (cardNo.substring(4, 6).equals("00")) {
                pinInfo = "9" + cardNo.substring(6, 7) + "01";
            } else {
                pinInfo = "0" + cardNo.substring(4, 7);
            }

            byte[] b0 = StringUtil
                    .hexStringToBytes("B091112233445566778800C602" + pinInfo
                            + "0001FFFFFFFF" + cardNo + "000806" + flatPin
                            + "FFFFFFFF");
            os.write(b0);
            byte[] res = new byte[1];
            is.read(res);
            if ((new String(res)).equals("A")) {
                byte[] r2 = new byte[10];
                is.read(r2);
                byte[] r3 = new byte[8];
                is.read(r3);
                String passwd1 = StringUtil.bytesToHexString(r3).toUpperCase();
                return passwd1;
            } else {
                byte[] r2 = new byte[9];
                is.read(r2);
                log.error("明文密钥转换为OTA密钥，错误返回:"
                        + StringUtil.bytesToHexString(r2));
                return null;
            }
        } catch (Exception e) {

            log.error("明文密钥转换为OTA密钥，异常:" + e.toString());

            connect();
            throw new BizException(BussErrorCode.ERROR_CODE_900000.getErrorcode(),
                    BussErrorCode.ERROR_CODE_900000.getErrordesc());
        }
    }

    /**
     * 将OTA密文反解为明文
     *
     * @param cardNo
     * @param otaPin
     * @return
     */
    public synchronized String decryptOtaPin(String cardNo, String otaPin) {
        try {
            String pinInfo;
            if (cardNo.substring(4, 6).equals("00")) {
                pinInfo = "9" + cardNo.substring(6, 7) + "01";
            } else {
                pinInfo = "0" + cardNo.substring(4, 7);
            }

            byte[] b0 = StringUtil
                    .hexStringToBytes("B061010203040506070800C602" + pinInfo
                            + "0001FFFFFFFF" + cardNo);
            os.write(b0);
            byte[] res = new byte[1];
            is.read(res);
            if ((new String(res)).equals("A")) {
                byte[] r2 = new byte[8];
                is.read(r2);
                byte[] r3 = new byte[16];
                is.read(r3);
                is.read(new byte[4]);
                String flatPin1 = StringUtil.bytesToHexString(r3).toUpperCase();

                b0 = StringUtil.hexStringToBytes("B011010203040506070800C60010"
                        + flatPin1);
                os.write(b0);
                res = new byte[1];
                is.read(res);
                if ((new String(res)).equals("A")) {
                    r2 = new byte[10];
                    is.read(r2);
                    r3 = new byte[16];
                    is.read(r3);
                    String flatPin2 = StringUtil.bytesToHexString(r3)
                            .toUpperCase();

                    b0 = StringUtil
                            .hexStringToBytes("B0AA010203040506070800C602"
                                    + flatPin2 + "0008" + otaPin);
                    os.write(b0);
                    res = new byte[1];
                    is.read(res);
                    if ((new String(res)).equals("A")) {
                        r2 = new byte[10];
                        is.read(r2);
                        r3 = new byte[8];
                        is.read(r3);
                        return StringUtil.bytesToHexString(r3).substring(2, 8)
                                .toUpperCase();
                    } else {
                        r2 = new byte[1];
                        is.read(r2);
                        log.error("加密密钥反解为明文3，错误返回:"
                                + StringUtil.bytesToHexString(r2));
                        return null;
                    }
                } else {
                    r2 = new byte[1];
                    is.read(r2);
                    log.error("加密密钥反解为明文2，错误返回:"
                            + StringUtil.bytesToHexString(r2));
                }
            } else {
                byte[] r2 = new byte[1];
                is.read(r2);
                log.error("加密密钥反解为明文1，错误返回:"
                        + StringUtil.bytesToHexString(r2));
            }

        } catch (Exception e) {

            log.error("加密密钥反解为明文，异常:" + e.toString());

            connect();
            throw new BizException(BussErrorCode.ERROR_CODE_900000.getErrorcode(),
                    BussErrorCode.ERROR_CODE_900000.getErrordesc());
        }
        return null;
    }

    /**
     * 将加密密钥反解为明文
     *
     * @param txnPasswd
     * @return
     */
    public synchronized String decryptTxnPasswd(String txnPasswd,
                                                String accountNo) {
        try {
            byte[] b0 = StringUtil
                    .hexStringToBytes("710071DDEA5FD04787897ADDEA5FD04787897A0000"
                            + "0000000000000000" + "0008" + txnPasswd);
            os.write(b0);
            byte[] res = new byte[1];
            is.read(res);
            if ((new String(res)).equals("A")) {
                byte[] r2 = new byte[2];
                is.read(r2);
                byte[] r3 = new byte[8];
                is.read(r3);
                String flatPin = StringUtil.bytesToHexString(r3).toUpperCase();
                String s = "0000" + truncAccountNo(accountNo);
                String xor = this.xorString(s, flatPin);
                return xor.substring(2, 8);
            } else {
                byte[] r2 = new byte[1];
                is.read(r2);
                log.error("加密密钥反解为明文，错误返回:"
                        + StringUtil.bytesToHexString(r2));
                return null;
            }

        } catch (Exception e) {

            log.error("加密密钥反解为明文，异常:" + e.toString());

            connect();
            throw new BizException(BussErrorCode.ERROR_CODE_900000.getErrorcode(),
                    BussErrorCode.ERROR_CODE_900000.getErrordesc());
        }
    }

    /**
     * 将OCX密文转为加密密钥
     *
     * @param posPinkey
     * @param ocxPinBlock
     * @return
     */
    public synchronized String convertPinBlock(String posPinkey,
                                               String posPinSeq, String ocxPinBlock, String accountNo,
                                               String accountOrCardNo) {
        try {
            if (posPinSeq == null || posPinSeq.trim().equals("")) {
                log.error("OCX密文转为加密密钥，错误返回:PosPinSeq为空");
                return null;
            }

            byte[] b0 = StringUtil.hexStringToBytes("0406"
                    + StringUtil.fillLeft(
                    Integer.toHexString(
                            Integer.parseInt(posPinSeq.trim()))
                            .toUpperCase(), '0', 4) + "08" + posPinkey
                    + "007108DDEA5FD04787897A0101" + ocxPinBlock
                    + StringUtil.bytesToHexString(accountOrCardNo.getBytes())
                    + "3B" + StringUtil.bytesToHexString(accountNo.getBytes())
                    + "3B");
            os.write(b0);
            byte[] res = new byte[1];
            is.read(res);
            if ((new String(res)).equals("A")) {
                byte[] r2 = new byte[8];
                is.read(r2);
                String passwd1 = StringUtil.bytesToHexString(r2).toUpperCase();
                return passwd1;
            } else {
                byte[] r2 = new byte[1];
                is.read(r2);
                log.error("OCX密文转为加密密钥，错误返回:"
                        + StringUtil.bytesToHexString(r2));
                return null;
            }
        } catch (Exception e) {

            log.error("OCX密文转为加密密钥，异常:" + e.toString());

            connect();
            throw new BizException(BussErrorCode.ERROR_CODE_900000.getErrorcode(),
                    BussErrorCode.ERROR_CODE_900000.getErrordesc());
        }
    }


    /**
     * 明文转为OCX密文
     *
     * @param posPinkey
     * @param flatPin
     * @param cardOrAccountNo
     * @return
     */
    public synchronized String convertOcx(String posPinkey, String flatPin,
                                          String cardOrAccountNo) {
        try {
            String s = "0000" + truncAccountNo(cardOrAccountNo);
            String fp = "06" + flatPin + "FFFFFFFF";
            String xorFlatPin = this.xorString(s, fp);
            byte[] b0 = StringUtil.hexStringToBytes("71006F" + posPinkey
                    + posPinkey + "000000000000000001000008" + xorFlatPin);

            os.write(b0);
            byte[] res = new byte[1];
            is.read(res);
            if ((new String(res)).equals("A")) {
                byte[] r2 = new byte[2];
                is.read(r2);
                byte[] r3 = new byte[8];
                is.read(r3);
                String ocx = StringUtil.bytesToHexString(r3).toUpperCase();
                return ocx;
            } else {
                byte[] r2 = new byte[1];
                is.read(r2);
                log.error("明文转为OCX密文，错误返回:"
                        + StringUtil.bytesToHexString(r2));
                return null;
            }
        } catch (Exception e) {

            log.error("明文转为OCX密文，异常:" + e.toString());

            connect();
            throw new BizException(BussErrorCode.ERROR_CODE_900000.getErrorcode(),
                    BussErrorCode.ERROR_CODE_900000.getErrordesc());
        }
    }

    /**
     * 校验卡TAC
     *
     * @param cardNo
     * @param cardTac
     * @param tacLabel
     * @return
     */
    public synchronized boolean checkTac(String cardNo, String cardTac,
                                         String tacLabel) {
        try {
            String pinInfo;
            if (cardNo.substring(4, 6).equals("00")) {
                pinInfo = "9" + cardNo.substring(6, 7) + "01";
            } else {
                pinInfo = "0" + cardNo.substring(4, 7);
            }

            byte[] b0 = StringUtil
                    .hexStringToBytes("B08411223344556677880000A902" + pinInfo
                            + "0001FFFFFFFF" + cardNo + "0000000000000000"
                            + cardTac + "0016" + tacLabel);
            os.write(b0);
            byte[] res = new byte[1];
            is.read(res);
            if ((new String(res)).equals("A")) {
                byte[] r2 = new byte[8];
                is.read(r2);
                return true;
            } else {
                byte[] r2 = new byte[9];
                is.read(r2);
                log.error("校验卡TAC，错误返回:" + StringUtil.bytesToHexString(r2));
                return false;
            }
        } catch (Exception e) {

            log.error("校验卡TAC，异常:" + e.toString());

            connect();
            throw new BizException(BussErrorCode.ERROR_CODE_900000.getErrorcode(),
                    BussErrorCode.ERROR_CODE_900000.getErrordesc());
        }
    }

    /**
     * 将卡号或帐号与交易密码明文进行异或
     *
     * @param s1
     * @param s2
     * @return
     */
    public String xorString(String s1, String s2) {
        byte buf1[] = s1.getBytes();
        byte buf2[] = s2.getBytes();
        byte buf3[] = new byte[16];
        int n = 0;
        for (int i = 0; i < 16; i = i + 2) {
            int b = (((int) buf1[i] - 48) << 4) | ((int) buf1[i + 1] - 48);
            int c = 0;
            if (((int) buf2[i] > 57) && ((int) buf2[i + 1] > 57)) {
                c = (((int) buf2[i] - 55) << 4) | ((int) buf2[i + 1] - 55);
            } else if (((int) buf2[i] > 57) && ((int) buf2[i + 1] < 57)) {
                c = (((int) buf2[i] - 55) << 4) | ((int) buf2[i + 1] - 48);
            } else if (((int) buf2[i] < 57) && ((int) buf2[i + 1] > 57)) {
                c = (((int) buf2[i] - 48) << 4) | ((int) buf2[i + 1] - 55);
            } else {
                c = (((int) buf2[i] - 48) << 4) | ((int) buf2[i + 1] - 48);
            }
            int x = b ^ c;
            int y = x;
            int left = (x >> 4) + 48;
            if (left > 57) {
                left += 7;
            }
            int right = (y & 15) + 48;
            if (right > 57) {
                right += 7;
            }
            buf3[n++] = (byte) (left);
            buf3[n++] = (byte) (right);
        }
        return new String(buf3);
    }

    public String truncAccountNo(String accountNo) {
        if (accountNo == null || accountNo.trim().length() < 16) {
            log.error("卡号或帐号截取非法");
            throw new BizException(BussErrorCode.ERROR_CODE_900000.getErrorcode(), BussErrorCode.ERROR_CODE_900000.getErrordesc());
        }
        return accountNo.substring(accountNo.length() - 13,
                accountNo.length() - 1);
    }


    /**
     * 密码加密密钥
     *
     * @param rand
     * @param acceptOrgCode
     * @return
     */
    public synchronized String generateOrgPinkey(String rand,
                                                 String acceptOrgCode) {
        try {
            if (acceptOrgCode == null || acceptOrgCode.length() != 15) {
                return null;
            }
            byte[] b0 = StringUtil
                    .hexStringToBytes("72006F000102030405060701000008" + rand);
            os.write(b0);
            byte[] res = new byte[1];
            is.read(res);
            if ((new String(res)).equals("A")) {
                byte[] r2 = new byte[2];
                is.read(r2);
                int length = Integer.valueOf(StringUtil.bytesToHexString(r2),
                        16);
                byte[] r3 = new byte[length];
                is.read(r3);
                String returnMsg = StringUtil.bytesToHexString(r3)
                        .toUpperCase();
                System.out.println("返回:" + returnMsg);
                return returnMsg;
            } else {
                byte[] r2 = new byte[1];
                is.read(r2);
                log.error("加密数据密文，错误返回:" + StringUtil.bytesToHexString(r2));
                return null;
            }
        } catch (Exception e) {
            log.error("加密数据密文，异常:" + e.toString());
            connect();
            return null;
        }
    }
}
