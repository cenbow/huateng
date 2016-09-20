package com.aixforce.redis.utils;

import com.google.common.base.CaseFormat;
import com.google.common.base.Charsets;
import com.google.common.base.Strings;

import static com.google.common.base.Preconditions.checkArgument;

/*
* Author: jlchen
* Date: 2012-11-14
*/
public abstract class KeyUtils {

    /********************* siteCategory *******************/
    public static String allSiteCategories(){
        return "site-category:all";
    }

    /*********************  site  *************************/
    public static String site(final long siteId){
        return "site:"+siteId;
    }

    public static String allSites(){
        return "site:all";
    }


    public static String sites(final long userId){
        return "user:"+userId+":sites";
    }

    public static String domain(final String domain){
        checkArgument(Strings.emptyToNull(domain)!=null,"domain can not be null");
        return "site:domain:"+domain;
    }

    public static String subDomain(final String subDomain){
        checkArgument(Strings.emptyToNull(subDomain)!=null,"subDomain can not be null");
        return "site:sub-domain:"+subDomain;
    }

    public static String siteInstances(final long siteId){
        return "site:"+siteId+":instances";
    }

    public static String siteTodayPv(final long siteId){
        return "site:"+siteId+":today-pv";
    }

    //成功案例
    public static String goodCase() {
        return "site:good-case";
    }
    
    /********************* star ****************************/
    //用户star了哪些站点
    public static String staredSites(final long userId){
        return "user:"+userId+":stars";
    }

    //站点被哪些用户star了
    public static String staredUser(final long siteId){
        return "site:"+siteId+":stared";
    }

    /******************* follow ***************************/
    //用户的偶像
    public static String userFollowed(final long userId){
        return "user:"+userId+":followed";
    }

    //用户的粉丝
    public static String userFollowers(final long userId){
        return "user:"+userId+":followers";
    }

    /********************* siteInstance ********************/

    public static String pages(final long siteInstanceId){
        return "site-instance:"+siteInstanceId+":pages";
    }

    public static String headerWidgets(final long siteInstanceId){
        return "site-instance:"+siteInstanceId+":header-widgets";
    }

    public static String footerWidgets(final long siteInstanceId){
        return "site-instance:"+siteInstanceId+":footer-widgets";
    }

    public static String siteWidgets(final long belongId) {
        return "site-instance:"+belongId+":site-widgets";
    }

    /******************** page  ************************/

    public static String widgets(final long pageId){
        return "page:"+pageId+":widgets";
    }

    /****************** component *********************/
    public static String components(final long compCategoryId){
        return "component:category:"+compCategoryId;
    }

    public static String component(String path){
        return "component:path:"+path;
    }

    /****************** itemWidget *******************/
    public static String itemWidgets(final long itemId){
        return "item-widgets:"+itemId;
    }

    /***************** component category *********************/

    public static String componentCategoryChildrenOf(long parentId){
        return "component-category:parent:"+parentId;
    }

    /*****************  user *********************************/
    public static String email(final String email){
        checkArgument(!Strings.isNullOrEmpty(email),"email can not be null");
        return "user:email:"+email;
    }

    public static String userName(final String name){
        checkArgument(!Strings.isNullOrEmpty(name),"user name can not be null");
        return "user:name:"+name;
    }



    /***************** category ****************************/
    public static String subCategoryOf(long categoryId){
        return "category:"+categoryId+":children";
    }

    public static String categorySpu(long categoryId) {
        return "category:"+categoryId+":spus";
    }
    
    public static String attributeKeys(long categoryId){
        return "category:"+categoryId+":keys";
    }


    public static String attributeValues(long categoryId,long attributeKeyId){
        return "category:"+categoryId+":key:"+attributeKeyId+":values";
    }
    

    /****************** attribute **************************/
    public static String attributeKey(final String name) {
        checkArgument(!Strings.isNullOrEmpty(name),"attributeKey name can not be null");
        return "attribute:key:"+name;
    }

    public static String attributeValue(final String value){
        checkArgument(!Strings.isNullOrEmpty(value),"attribute value can not be null");
        return "attribute:value:"+value;
    }

    /****************** SPU *****************************/
    public static String spuAttributes(final long spuId){
        return "spu:"+spuId+":attributes";
    }

    public static String skuAttributes(final long skuId) {
        return "sku:"+skuId+":attributes";
    }

    public static String spuKeysOf(final long spuId) {
        return "spu:"+spuId+":spu-keys";
    }

    public static String skuKeysOf(final long spuId) {
        return "spu:"+spuId+":sku-keys";
    }



    /**************** designer ************************/

    public static String templatesOf(final long designerId) {
        return "designer:"+designerId+":templates";
    }

    /**************** shop-cart *********************/
    public static String shopCart(final String key){
        checkArgument(!Strings.isNullOrEmpty(key), "shop-cart key can not be null");
        return "shop-cart:"+key;
    }
    /****************  common *********************************/
    public static <T> String entityCount(Class<T> entityClass){
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN,entityClass.getSimpleName())+":count";
    }

    public static String entityField(String entityName,String fieldName){
        checkArgument(!Strings.isNullOrEmpty(entityName));
        checkArgument(!Strings.isNullOrEmpty(fieldName));
        return entityName + ":*->" + fieldName;
    }

    public static <T> String entityId(Class<T> entityClass,long id){
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN,entityClass.getSimpleName())+":"+id;
    }

    public static <T> String entityId(Class<T> entityClass,final String id){
        checkArgument(!Strings.isNullOrEmpty(id),"id can not be null");
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN,entityClass.getSimpleName())+":"+id;
    }

    public static byte[] rawKey(String key){
        checkArgument(!Strings.isNullOrEmpty(key),"can not be null");
        return key.getBytes(Charsets.UTF_8);
    }
}
