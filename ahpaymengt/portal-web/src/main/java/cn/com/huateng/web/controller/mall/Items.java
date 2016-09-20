package cn.com.huateng.web.controller.mall;

import com.aixforce.exception.JsonResponseException;
import com.aixforce.item.dto.RichAttribute;
import com.aixforce.item.model.Item;
import com.aixforce.item.model.ItemDetail;
import com.aixforce.item.model.Sku;
import com.aixforce.item.service.ItemService;
import com.aixforce.site.model.redis.Site;
import com.aixforce.site.service.SiteService;
import com.aixforce.user.base.UserUtil;
import com.aixforce.web.misc.MessageSources;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Splitter;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.web.util.HtmlUtils.htmlEscape;
import static org.springframework.web.util.JavaScriptUtils.javaScriptEscape;

/**
 * Created by IntelliJ IDEA.
 * User: AnsonChan
 * Date: 13-8-21
 */
@Controller
@RequestMapping("/api/mall/items")
public class Items {
    private final static Logger log = LoggerFactory.getLogger(Items.class);
    private final Splitter splitter = Splitter.on(',').trimResults().omitEmptyStrings();

    @Autowired
    private ItemService itemService;

    @Autowired
    private MessageSources messageSources;

    @Autowired
    private SiteService siteService;


    @RequestMapping(value = "/{itemId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> load(@PathVariable Long itemId) {
        try {
            return itemService.findWithDetailsById(itemId);
        } catch (Exception e) {
            Throwables.propagateIfInstanceOf(e, JsonResponseException.class);
            log.error("failed to load item and its properties for item id = {},cause:{}", itemId, e);
            throw new JsonResponseException(500, messageSources.get("item.query.fail"));
        }
    }

    @RequestMapping(value = "/{itemId}/attributes", method= RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<RichAttribute> attributesOf(@PathVariable("itemId")Long itemId){
        try{
            return itemService.attributesOf(itemId);
        }catch (Exception e){
            log.error("failed to find attributes of item(id={}),cause:{}",itemId,Throwables.getStackTraceAsString(Throwables.getRootCause(e)));
            throw new JsonResponseException(500,messageSources.get("item.query.attributes.fail"));
        }
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String create(@RequestBody ItemDto itemDto) {
        Long userId = UserUtil.getUserId();
        try {
            Item item = itemDto.getItem();
            item.setUserId(userId);
            item.setTradeType(Item.TradeType.BUY_OUT.toNumber());
            item.setStatus(Item.Status.INIT.toNumber());
            item.setSoldQuantity(0);
            Site shop =siteService.findShopByUserId(userId);
            item.setSiteId(shop.getId());
            //item.setPrice(Objects.firstNonNull(item.getPrice(),0)*100);
            //escape the name
            item.setName(javaScriptEscape(htmlEscape(item.getName())));

            ItemDetail itemDetail = itemDto.getItemDetail();
            List<Sku> skus = itemDto.getSkus();
            int quantity = 0;
            for (Sku sku : skus) {
                //sku.setPrice(Objects.firstNonNull(sku.getPrice(),0)*100);
                quantity+=sku.getStock();
            }
            item.setQuantity(quantity);
            itemService.create(item, itemDetail,skus);
            return messageSources.get("item.create.success");
        } catch (Exception e) {
            Throwables.propagateIfInstanceOf(e,JsonResponseException.class);
            log.error("failed to create item from {},user(id={}), cause:{}",
                    itemDto, userId, Throwables.getStackTraceAsString(Throwables.getRootCause(e)));
            throw new JsonResponseException(500, messageSources.get("item.create.fail"));
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String update(@PathVariable Long id, @RequestBody ItemDto itemDto) {
        Long userId = UserUtil.getUserId();
        try {
            Item existed = itemService.findById(id);
            if(existed == null){
                throw new JsonResponseException(500,messageSources.get("item.not.exist"));
            }
            if(!Objects.equal(existed.getUserId(), userId)){
                throw new JsonResponseException(401,messageSources.get("item.not.owner"));
            }
            Item item = itemDto.getItem();
            item.setUserId(null);
            item.setSiteId(null);
            item.setStatus(null);

            item.setSoldQuantity(null);
            item.setName(javaScriptEscape(htmlEscape(item.getName())));
            item.setId(id);
            ItemDetail itemDetail = itemDto.getItemDetail();
            itemDetail.setItemId(item.getId());

            List<Sku> skus = itemDto.getSkus();
            int quantity = 0;
            for (Sku sku : skus) {
                quantity+=sku.getStock();
            }
            item.setQuantity(quantity);
            itemService.update(item, itemDetail,skus);
            return messageSources.get("item.update.success");
        } catch (Exception e) {
            log.error("failed to update item(id={}) from {},cause:{}",
                    id, itemDto, Throwables.getStackTraceAsString(Throwables.getRootCause(e)));
            throw new JsonResponseException(500, messageSources.get("item.update.fail"));
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String delete(@PathVariable Long id) {
        Long userId = UserUtil.getUserId();
        try {
            itemService.delete(userId,id);
            return messageSources.get("item.delete.success");
        } catch (Exception e) {
            log.error("failed to delete item whose id={},cause:{}", id, e);
            throw new JsonResponseException(500, messageSources.get("item.delete.fail"));
        }
    }

    @RequestMapping(value = "/updateStatus",method = RequestMethod.PUT)
    @ResponseBody
    public String bulkUpdateStatus(@RequestParam("status") Integer status, @RequestParam("ids") String ids) {

        Long userId = UserUtil.getUserId();
        try {
            Iterable<String> parts = splitter.split(ids);
            if (Iterables.isEmpty(parts)) {
                log.warn("no items need to update,return directly");
                return messageSources.get("item.ids.empty");
            }
            Iterable<Long> parsedIds = Iterables.transform(parts, new Function<String, Long>() {
                @Override
                public Long apply(String input) {
                    return Long.valueOf(input);
                }
            });
            itemService.bulkUpdateStatus(userId,status, ImmutableList.copyOf(parsedIds));
            return messageSources.get("item.update.success");
        } catch (Exception e) {
            log.error("failed to bulk update item status to {} for item ids ({}),cause:{}",
                    status, ids, Throwables.getStackTraceAsString(Throwables.getRootCause(e)));
            throw new JsonResponseException(500, messageSources.get("item.update.fail"));
        }
    }

    public static class ItemDto {

        private Item item;


        private List<Sku> skus;


        private ItemDetail itemDetail;

        public Item getItem() {
            return item;
        }

        public void setItem(Item item) {
            this.item = item;
        }

        public List<Sku> getSkus() {
            return skus;
        }

        public void setSkus(List<Sku> skus) {
            this.skus = skus;
        }

        public ItemDetail getItemDetail() {
            return itemDetail;
        }

        public void setItemDetail(ItemDetail itemDetail) {
            this.itemDetail = itemDetail;
        }

        @Override
        public String toString() {
            return Objects.toStringHelper(this).add("item",item).add("skus",skus)
                    .add("detail", itemDetail).omitNullValues().toString();
        }
    }
}
