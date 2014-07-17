/**
 * @(#)SerializationException.java
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

import org.springframework.core.NestedRuntimeException;

/**
 * Generic exception indicating a serialization/deserialization error.
 * 
 * @author Costin Leau
 */
public class SerializationException extends NestedRuntimeException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 104253377830899391L;

	/**
	 * Constructs a new <code>SerializationException</code> instance.
	 * 
	 * @param msg
	 * @param cause
	 */
	public SerializationException(String msg, Throwable cause)
	{
		super(msg, cause);
	}

	/**
	 * Constructs a new <code>SerializationException</code> instance.
	 * 
	 * @param msg
	 */
	public SerializationException(String msg)
	{
		super(msg);
	}
}
