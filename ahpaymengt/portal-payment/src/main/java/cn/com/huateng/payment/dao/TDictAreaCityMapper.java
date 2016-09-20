package cn.com.huateng.payment.dao;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import cn.com.huateng.payment.model.TDictAreaCity;

/**
 * User: 胡文杰
 * Date: 13-7-30
 * Time: 上午10:00
 */

@Repository
public class TDictAreaCityMapper  extends SqlSessionDaoSupport{
	
	
	/**
	 * 获取所有地区城市信息
	 * @return
	 */
	public List<TDictAreaCity> selectAllDictAreaCity(){
        return getSqlSession().selectList("TDictAreaCity.selectAllDictAreaCity");
    }
	
	/**
	 * 获取所有城市信息
	 * @return
	 */
	public List<TDictAreaCity> selectAllDictCity(){
        return getSqlSession().selectList("TDictAreaCity.selectAllDictCity");
    }
	
	
	/**
	 * 获取所有地区城市信息
	 * @return
	 */
	public TDictAreaCity findAreaNameByCode(String cityCode){
        return getSqlSession().selectOne("TDictAreaCity.findAreaNameByCode",cityCode);
    }

}