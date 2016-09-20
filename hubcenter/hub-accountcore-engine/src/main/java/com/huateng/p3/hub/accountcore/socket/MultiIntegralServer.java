package com.huateng.p3.hub.accountcore.socket;

import com.google.common.base.Objects;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.huateng.p3.account.common.bizparammodel.AccountInfo;
import com.huateng.p3.account.common.bizparammodel.CustomerResultObject;
import com.huateng.p3.account.common.bizparammodel.PaymentInfo;
import com.huateng.p3.account.common.enummodel.KeyType;
import com.huateng.p3.account.manager.CustomerQueryService;
import com.huateng.p3.account.service.AccountIntegralService;
import com.huateng.p3.component.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.google.common.base.Preconditions.checkState;

/**
 * 积分socket服务
 * User: dongpeiji
 * Date: 14-9-23
 * Time: 下午12:14
 */
@Slf4j
public class MultiIntegralServer {
    private int port = 8821;
    private ServerSocket serverSocket;
    private ExecutorService executorService;//线程池
    private final int POOL_SIZE = 10;//单个CPU线程池大小

    public MultiIntegralServer() throws IOException {
        serverSocket = new ServerSocket(port);
        //Runtime的availableProcessor()方法返回当前系统的CPU数目.
        executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * POOL_SIZE);
        log.info("integral service started successfully");
    }

    public void service() {
        while (true) {
            Socket socket = null;
            try {
                //接收客户连接,只要客户进行了连接,就会触发accept();从而建立连接
                socket = serverSocket.accept();
                executorService.execute(new Handler(socket));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class Handler implements Runnable {

        @Value("${acceptOrgCode}")
        private String acceptOrgCode;

        @Autowired
        private AccountIntegralService accountIntegralService;

        @Autowired
        private CustomerQueryService customerQueryService;

        private Socket socket;

        public Handler(Socket socket) {
            this.socket = socket;
        }

        private PrintWriter getWriter(Socket socket) throws IOException {
            OutputStream socketOut = socket.getOutputStream();
            return new PrintWriter(socketOut, true);
        }

        private BufferedReader getReader(Socket socket) throws IOException {
            InputStream socketIn = socket.getInputStream();
            return new BufferedReader(new InputStreamReader(socketIn));
        }

        public void run() {
            try {
                log.info("New connection accepted " + socket.getInetAddress() + ":" + socket.getPort());
                BufferedReader br = getReader(socket);
                PrintWriter pw = getWriter(socket);
                String msg = "";
                while (br.readLine() != null) {
                    msg = msg + br.readLine();
                }
                log.info("receive msg:" + msg);
                processMsg(msg, pw);
            } catch (IOException e) {
                log.error("fail to get msg cause by {}", e);
            } catch (Exception e) {
                log.error("fail to process msg cause by {}", e);
            } finally {
                try {
                    if (socket != null)
                        socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
         //(受理机构交易流水|受理机构号|统一编号|类型(查询、充值、消费)|金额|日期|时间|交易渠道(外部))
        //201407081123450000|111310049001138|18701996438|1|3000|20140607|181312|99       131080子账户消费 121110积分充值
        private void processMsg(String Msg, PrintWriter pw) throws Exception {
            Iterable<String> parts = Splitter.on("|").omitEmptyStrings().trimResults()
                    .split(Msg);
            String transSeq = Iterables.get(parts, 0);
            String merchantCode = Iterables.get(parts, 1);
            String unifyId = Iterables.get(parts, 2);
            String type = Iterables.get(parts, 3);
            String amount = Iterables.get(parts, 4);
            String acceptDate = Iterables.get(parts, 5);
            String acceptTime = Iterables.get(parts, 6);
            String channel = Iterables.get(parts, 7);
            AccountInfo accountInfo = new AccountInfo();
            accountInfo.setAccountKey(unifyId);
            accountInfo.setKeyType(KeyType.UNIFY);
            PaymentInfo paymentInfo = new PaymentInfo();
            paymentInfo.setAcceptOrgCode(acceptOrgCode);
            paymentInfo.setMerchantCode(merchantCode);
            paymentInfo.setAcceptTxnSeqNo(transSeq);
            paymentInfo.setAcceptDate(acceptDate);
            paymentInfo.setAcceptTime(acceptTime);
            paymentInfo.setChannel(channel);
            if (Objects.equal(type, "0")) {  //查询
                Response<CustomerResultObject> customerResultObjectResponse = customerQueryService.queryCustomerInfo(accountInfo);
                if (customerResultObjectResponse.isSuccess()) {
                    log.info("success to query integral unifyId:{} balance:{}", unifyId, customerResultObjectResponse.getResult().getIntegral());
                    pw.println(customerResultObjectResponse.getResult().getIntegral());
                } else {
                    log.error("fail to query integral unifyId:{} cause by {}", unifyId, customerResultObjectResponse.getErrorCode() + ":" + customerResultObjectResponse.getErrorMsg());
                    pw.println("查询失败");
                }
            } else if (Objects.equal(type, "1")) { // 新增 充值
                paymentInfo.setAmount(Long.parseLong(amount));
                paymentInfo.setBussinessType("121110");   //积分充值
                accountIntegralService.chargeOrConsume(paymentInfo, accountInfo, true);
                pw.print("充值成功");
            } else if (Objects.equal(type, "2")) { //减少 消费
                paymentInfo.setAmount(Long.parseLong(amount));
                paymentInfo.setBussinessType("131080"); //子账户消费
                accountIntegralService.chargeOrConsume(paymentInfo, accountInfo, false);
                pw.print("扣费成功");
            } else {
                pw.println("参数错误");
            }
        }
    }
}
