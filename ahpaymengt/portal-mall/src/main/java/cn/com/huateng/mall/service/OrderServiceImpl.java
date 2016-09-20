package cn.com.huateng.mall.service;

import cn.com.huateng.mall.model.ConfirmItem;
import com.aixforce.annotations.ParamInfo;
import com.aixforce.item.model.Item;
import com.aixforce.item.model.Sku;
import com.aixforce.item.service.ItemService;
import com.google.common.base.Objects;
import com.huateng.p3.component.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * User: AnsonChan
 * Date: 13-8-22
 */
@Service("orderService")
public class OrderServiceImpl implements OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private ItemService itemService;
    public OrderServiceImpl(){}
    public OrderServiceImpl(ItemService itemService){
    	this.itemService = itemService;
    }
    @Override
    public Response<ConfirmItem> confirm(@ParamInfo("skuId") Long skuId, @ParamInfo("count") Integer count) {
        Sku sku = itemService.findSkuById(skuId);
        Item item = itemService.findById(sku.getItemId());
        if(!Objects.equal(item.getStatus(), Item.Status.ON_SHELF.toNumber())){
            log.warn("item(id={}) is not onShelf, skip sku {}", item.getId(), sku);
            return new Response<ConfirmItem>("ITEM_OFF_SHELF");
        }
        ConfirmItem confirmItem = new ConfirmItem();
        confirmItem.setSku(sku);
        confirmItem.setImage(item.getMainImage());
        confirmItem.setName(item.getName());
        confirmItem.setCount(count);

        return new Response<ConfirmItem>(confirmItem);
    }
}
