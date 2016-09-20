package com.aixforce.user.service;

import com.aixforce.user.model.Address;
import com.aixforce.user.mysql.AddressDao;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2013-03-14
 */
@Service
public class AddressServiceImpl implements AddressService{

    private final LoadingCache<Integer,List<Address>> children;

    private final LoadingCache<Integer,Address> self;

    private final AddressDao addressDao;

    private final List<Address> provinces;

    @Autowired
    public AddressServiceImpl(final AddressDao dao) {
        this.addressDao = dao;
        this.provinces = addressDao.findByLevel(1);
        this.children = CacheBuilder.newBuilder().build(new CacheLoader<Integer, List<Address>>() {
            @Override
            public List<Address> load(Integer id) throws Exception {
                return addressDao.findByParentId(id);
            }
        });
        this.self = CacheBuilder.newBuilder().build(new CacheLoader<Integer, Address>() {
            @Override
            public Address load(Integer id) throws Exception {
                return addressDao.findById(id);
            }
        });
    }

    @Override
    public Address findById(Integer id){
        return self.getUnchecked(id);
    }

    @Override
    public List<Address> provinces() {
        return provinces;
    }

    @Override
    public List<Address> citiesOf(Integer provinceId) {
        checkArgument(provinceId!=null,"provinceId can not be null");
        return children.getUnchecked(provinceId);
    }

    @Override
    public List<Address> districtOf(Integer cityId) {
        checkArgument(cityId!=null,"cityId can not be null");
        return children.getUnchecked(cityId);
    }

    @Override
    public List<Address> getPedigree(Integer anyId) {
        checkArgument(anyId != null, "provided anyId cant be null");
        List<Address> result = Lists.newArrayList();
        for (;;) {
            if (anyId == 1) break;
            Address address = findById(anyId);
            if (address == null) break;
            result.add(0, address);
            anyId = address.getParentId();
        }
        return result;
    }
}
