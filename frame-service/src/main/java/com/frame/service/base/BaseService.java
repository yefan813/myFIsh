package com.frame.service.base;

import java.io.Serializable;
import java.util.List;

import com.frame.domain.common.Page;


/**
 * service基类<实体,主键>
 * 
 * @author
 * @since 2013-12-30
 * @param <T>
 *            实体
 * @param <KEY>
 *            主键
 */
public interface BaseService<T, KEY extends Serializable> {

	/**
	 * 添加对象
	 * 
	 * @param t
	 * @return
	 */
	int insertEntry(T... t);

	/**
	 * 添加对象并且设置主键ID值(需要事务支持)
	 * 
	 * @param t
	 * @return
	 */
	int insertEntryCreateId(T t);

	/**
	 * 删除对象,主键
	 * 
	 * @param key
	 *            主键数组
	 * @return 影响条数
	 */
	int deleteByKey(KEY... key);

	/**
	 * 按条件删除对象
	 * 
	 * @param condtion
	 * @return 影响条数
	 */
	int deleteByCondtion(T condtion);

	/**
	 * 更新对象,条件主键Id
	 * 
	 * @param condtion
	 *            更新对象
	 * @return 影响条数
	 */
	int updateByKey(T condtion);

	/**
	 * 保存或更新对象(条件主键Id)
	 * 
	 * @param t
	 *            需更新的对象
	 * @return 影响条数
	 */
	int saveOrUpdate(T t);

	int save(T t);

	int update(T t);

	/**
	 * 查询对象,条件主键
	 * 
	 * @param key
	 * @return 实体对象
	 */
	T selectEntry(KEY key);

	/**
	 * 查询对象列表,主键数组
	 * 
	 * @param key
	 * @return 对象列表
	 */
	List<T> selectEntryList(KEY... key);

	/**
	 * 查询对象,只要不为NULL与空则为条件
	 * 
	 * @param condtion
	 *            查询条件
	 * @return 对象列表
	 */
	List<T> selectEntryList(T condtion);
	
	/**
	 * 查询对象总数
	 * @param condtion 查询条件
	 * @return 对象总数
	 */
	Integer selectEntryListCount(T condtion);

	/**
	 * 分页查询
	 * 
	 * @param condtion
	 *            查询条件
	 * @return 分页对象
	 */
	Page<T> selectPage(T condtion, Page<T> page);
}
