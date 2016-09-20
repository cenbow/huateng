package com.aixforce.web.controller;

import com.aixforce.common.model.Paging;
import com.aixforce.exception.JsonResponseException;
import com.aixforce.site.model.redis.Site;
import com.aixforce.site.service.SiteService;
import com.aixforce.user.base.UserUtil;
import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/*
 * Author: jlchen
 * Date: 2012-12-10
 */
@Controller
@RequestMapping("/sites")
public class Sites {
    @Autowired
    private SiteService siteService;


    @RequestMapping(method = RequestMethod.GET)
    public String list(@RequestParam(value = "p",defaultValue = "1")Integer p,@RequestParam(value = "size",defaultValue = "10")Integer size,
                       @RequestParam(value = "q",defaultValue = "")String keywords, @RequestParam(value = "queryType",defaultValue = "byUserId")String queryType,
                       Map<String, Object> map) {

        Paging<Site> result;
        if(Strings.isNullOrEmpty(keywords)){
           result = siteService.pagination(p,size);

        }else{
            if(Objects.equal(queryType, "byId")){
                Site site = siteService.findById(Long.parseLong(keywords));
                if(site == null){
                    result = new Paging<Site>(0L, Collections.<Site>emptyList());
                }else{
                    result = new Paging<Site>(1L, ImmutableList.of(site));
                }
            }else{
                List<Site> sites = siteService.findByUserId(Long.parseLong(keywords));
                result = new Paging<Site>((long)sites.size(),sites);
            }
        }
        map.put("sites", result.getData());
        map.put("total",result.getTotal());
        map.put("userName", UserUtil.getCurrentUser().getPrincipal());
        map.put("keywords",keywords);
        return "admin/sites/index";
    }


    @RequestMapping(value = "/add",method=RequestMethod.GET)
    public String create(){
        Long userId = UserUtil.getUserId();
        Site site = new Site();
        Long id = siteService.create(userId, site);
        return "redirect:/sites/" + id+"/edit";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String view(@PathVariable("id") Long id, Map<String, Object> map) {
        Site site = siteService.findById(id);
        map.put("site", site);
        map.put("userName",UserUtil.getCurrentUser().getPrincipal());
        return "admin/sites/show";
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String edit(@PathVariable("id") Long id, Map<String, Object> map) {
        Site site = siteService.findById(id);
        map.put("site", site);
        map.put("userName",UserUtil.getCurrentUser().getPrincipal());
        return "admin/sites/edit";
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public String update(@PathVariable Long id,Site site){
        siteService.update(site);
        siteService.changeOwner(UserUtil.getCurrentUser(),id,site.getUserId());
        return "redirect:/sites/"+ id;
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String delete(@PathVariable Long id){
        Site site = siteService.findById(id);
        if(site != null){
            siteService.delete(site.getUserId(),id);
            return "delete ok";
        }
        throw new JsonResponseException("site.not.exist");
    }
}
