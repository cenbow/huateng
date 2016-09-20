package com.aixforce.user.service;

import com.aixforce.annotations.ParamInfo;
import com.aixforce.user.model.Address;

import java.util.List;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2013-03-14
 */
public interface AddressService {

    List<Address> provinces();

    List<Address> citiesOf(@ParamInfo("provinceId")Integer provinceId);

    List<Address> districtOf(@ParamInfo("cityId")Integer cityId);

    Address findById(@ParamInfo("id")Integer id);

    List<Address> getPedigree(@ParamInfo("anyId")Integer anyId);
}
