/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.item.service;

import com.aixforce.item.model.Spu;

import java.util.List;

public interface SpuService {

    void create(Spu spu);

    void update(Spu spu);

    Spu findById(Long id);

    /**
     * when delete a spu, then its properties should also deleted
     * @param id spu id
     */
    void delete(Long id);

    List<Spu> findByCategoryId(Long categoryId);

}