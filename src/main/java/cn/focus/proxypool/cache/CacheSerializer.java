package cn.focus.proxypool.cache;

import com.alibaba.fastjson.JSON;

public class CacheSerializer {
	
    public static String encode(Object obj)
    {

        CacheData cacheData = buildCacheData(obj);
        return serializeToString(cacheData);
    }
	
    public static Object decode(String cachestr)
    {
        if (cachestr == null)
        {
            return null;
        }
        CacheData cacheData = deserializeFromString(cachestr, CacheData.class);
        Object value = deserializeFromString(cacheData.getContent(), cacheData.getObjType());
        return value;
    }
	
	public static CacheData buildCacheData(Object obj)
	{
		Class<?> cls = obj.getClass();
		String jsonStr = serializeToString(obj);
		CacheData cacheData = new CacheData(cls, jsonStr);
		return cacheData;
	}
	
	public static <T> String serializeToString(T t) throws SerializationException
	{
		if (t == null)
		{
			return "";
		}
		try
		{
			return JSON.toJSONString(t);
		}
		catch (Exception ex)
		{
			throw new SerializationException("Could not write JSON: " + ex.getMessage(), ex);
		}
	}
	
	public static <T> T deserializeFromString(String content, Class<T> t) throws SerializationException
	{
		if (content == null)
		{
			return null;
		}
		try
		{
			return JSON.parseObject(content, t);
		}
		catch (Exception ex)
		{
			throw new SerializationException("Could not read JSON: " + ex.getMessage(), ex);
		}
	}
}