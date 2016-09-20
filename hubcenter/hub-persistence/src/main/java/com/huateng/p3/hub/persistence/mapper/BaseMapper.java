/**
 * 
 */
package com.huateng.p3.hub.persistence.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;


/**
 * 基础类MAPPER
 * @author cmt
 *
 */
public interface BaseMapper<T> {
	
    /**
     * 获取 序列号 
     * @author cmt
     * @return
     */
    Long getIdValue();

	/**
	 * 插入记录
	 * @author cmt
	 * @param obj
	 * @return
	 */
    int insert(T obj);

    /**
     * 插入记录(有效字段,即非空字段)
     * @author cmt
     * @param obj
     * @return
     */
    int insertSelective(T obj);
    
    /**
     * 插入记录或更新记录（批处理的时候不适合使用<selectKey >,主键自增最好）
     * @param obj
     * @return
     */
    int insertOrUpdate(T obj);
    
    /**
     * 批量插入
     * @param params
     */
    void insertBatch(@Param(value="params")List<T> params);
    
	/**
	 * 物理删除记录
	 * @author cmt
	 * @param seq
	 * @return
	 */
	int deleteByPrimaryKey(Long seq);
	
	/**
	 * 批量删除
	 * @param params
	 */
	void deleteBatch(@Param(value="params")List<Long> params);
	
    /**
     * 更新记录
     * @author cmt
     * @param obj
     * @return
     */
    int updateByPrimaryKey(T obj);

    /**
     * 更新记录(有效字段,即非空字段)
     * @author cmt
     * @param obj
     * @return
     */
    int updateByPrimaryKeySelective(T obj);

    /**
     * 批处理更新
     * @param params
     * @return
     */
    int updateBatch(@Param(value="params")List<T> params);
    
    /**
     * 根据主键 返回记录
     * @author cmt
     * @param seq
     * @return
     */
    T selectByPrimaryKey(Long seq);
    
    /**
     * 根据 条件返回记录
     * @author cmt
     * @param params
     * @return
     */
    T selectByParams(@Param(value="params") Map<String, Object> params);
    
    
    
    /**
     * 根据 条件返回记录集合
     * @author cmt
     * @param params
     * @return
     */
    List<T> selectListByParams(@Param(value="params") Map<String, Object> params);
    
    
    
    
    /**
     * 查询 返回所有符合条件的列表
     * @author cmt
     * @param params
     * @param pageBounds  分页排序条件
     * @return
     */
    List<T> selectAllListByParams(@Param(value="params") Map<String, Object> params
    		, @Param(value="pageBounds")PageBounds pageBounds );
    
    
    /**
     * 查询 符合条件的记录总数
     * @author cmt
     * @param params
     * @return
     */
    int selectCountByParams(@Param(value="params") Map<String, Object> params);
    
    
   
    
    /**
     * 用插件进行分页查询 记录
     * @author cmt
     * @param params 查询条件
     * @param pageBounds 分页条件
     *  int page = 1; //页号 
     *  int pageSize = 20; //每页数据条数 
     *  String sortString = "age.asc,gender.desc";//如果你想排序的话逗号分隔可以排序多列 
     *  PageBounds pageBounds = new PageBounds(page, pageSize , Order.formString(sortString)); 
     * @return
     */
    List<T> selectListByPageBounds(@Param(value="params") Map<String, Object> params, @Param(value="pageBounds")PageBounds pageBounds 
    						 );
    
   
    
   
   
    
}
