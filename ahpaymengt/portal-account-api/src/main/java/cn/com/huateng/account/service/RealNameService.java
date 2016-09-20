package cn.com.huateng.account.service;
import cn.com.huateng.CommonUser;

import com.aixforce.annotations.ParamInfo;
import com.huateng.p3.account.persistence.models.TRealnameApply;
import com.huateng.p3.component.Response;

import java.util.List;

public interface RealNameService {

    /**
     * 证件实名查询
     *
     * @param commonUser 公共用户对象  必填
     * @return 如果操作成功, 则返回实名申请记录, 否则返回失败原因
     */
    Response<List<TRealnameApply>> queryRealnameAuthStatus(@ParamInfo("commonUser") CommonUser commonUser);


    /**
     * 证件实名申请
     *
     * @param commonUser 公共用户对象  必填
     * @param name        姓名 必填
     * @param idType      证件类型 必填
     * @param idNo        证件号码 必填
     * @param photo       照片(二进制流) 必填
     * @param nationality 国籍
     * @param profession 职业
     *@param certExpiryDate 证件有效期
     * @param address 地址
     * @return 如果操作成功, 则返回SUCCESS, 否则返回失败原因
     */
    Response<String> identifyRealnameApply(@ParamInfo("commonUser") CommonUser commonUser, @ParamInfo("name") String name,
                                           @ParamInfo("idType") String idType, @ParamInfo("idNo") String idNo, @ParamInfo("photo") String photo, @ParamInfo("fileName")String fileName, @ParamInfo("loginIp")String loginIp,
                                           @ParamInfo("nationality") String nationality, @ParamInfo("profession") String profession,
                                           @ParamInfo("certExpiryDate") String certExpiryDate,@ParamInfo("address") String address);

}
