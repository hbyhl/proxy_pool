/**
 * @(#)CacheData.java
 *       
 * 系统名称：tCache
 * 
 * Copyright (c)  TravelSky
 * All Rights Reserved.
 * 
 * 作者：Kay L
 * 创建日期：2013.3.20
 * 
 */

package cn.focus.proxypool.cache;

import java.io.Serializable;

/**
 * @author zhongfeng
 * 
 */
public class CacheData implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Class<?> objType;
	private String content;

	/**
	 * 
	 */
	public CacheData()
	{
	}

	/**
	 * @param objType
	 * @param content
	 */
	public CacheData(Class<?> objType, String content)
	{
		this.objType = objType;
		this.content = content;
	}

	public Class<?> getObjType()
	{
		return objType;
	}

	public void setObjType(Class<?> objType)
	{
		this.objType = objType;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	@Override
	public String toString()
	{
		return "CacheData [content=" + content + ", objType=" + objType + "]";
	}

}
