package cn.com.huateng.web.controller;

import com.google.common.base.Charsets;
import com.google.common.base.Objects;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import com.google.common.io.LineProcessor;
import com.google.common.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Set;

import static com.google.common.base.Preconditions.checkState;

/**
 * Created with IntelliJ IDEA.
 * User: AP
 * Date: 13-10-30
 * Time: 下午3:35
 */
public class Targets{
    private final static Logger log = LoggerFactory.getLogger(Targets.class);

    private String prePath;

    private String directPath;

    public Targets(String prePath,String directPath){
        this.prePath = prePath;
        this.directPath = directPath;
    }

    public static Set<Targets> targetList;

    /**
     *target路径处理 防止session失效,登录后跳转请求失败
     * @param target 目标地址 必填
     * @return 处理结果
     */
    public static  String targetProcess(String target){
        targetList = Sets.newHashSet();
        try {
            Resources.readLines(Resources.getResource("/target_list"), Charsets.UTF_8, new LineProcessor<Void>() {
                @Override
                public boolean processLine(String line) throws IOException {
                    if (!Strings.isNullOrEmpty(line)) {
                        Iterable<String> parts = Splitter.on(':').trimResults().split(line);
                        checkState(Iterables.size(parts) == 2, "illegal target_list configuration [%s]", line);
                        String prePath = Iterables.get(parts, 0);
                        String directPath = Iterables.get(parts, 1);
                        targetList.add(new Targets(prePath,directPath));
                    }
                    return true;
                }

                @Override
                public Void getResult() {
                    return null;
                }
            });
        } catch (Exception e) {
            log.error("读取target产品配置文件失败 cause by {}", e.getMessage());
            return "error";
        }
        for(Targets target1:targetList){
            //假如目标地址在过滤文件里
            if(Objects.equal(target,target1.prePath)){
                return target1.directPath;
            }
        }
        return  target;
    }
}
