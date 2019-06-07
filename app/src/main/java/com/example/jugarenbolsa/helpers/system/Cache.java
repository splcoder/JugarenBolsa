package com.example.a2019_05_30_listado.helpers;

import java.util.HashMap;
import java.util.Map;

public class Cache {
	private static Cache _cache = null;
	// Internal Cache var
	private Map<String, Object> mapCache = new HashMap<String, Object>();
	// Constructor (private)
	private Cache(){}
	/**
	 * Access for direct Cache's values/functions (not mapCache)
	 * @return Cache
	 */
	public static synchronized Cache getCache(){
		if( _cache == null ){
			_cache = new Cache();
		}
		return _cache;
	}
	/**
	 * Cache map set
	 * @param key
	 * @param value
	 */
	public static void set( String key, Object value ){
		getCache().mapCache.put( key, value );
	}
	/**
	 * Cache map access
	 * @param key
	 * @return Object
	 */
	public static Object get( String key ){
		return getCache().mapCache.get( key );
	}
	//----------------------------------------------------------------------------------------------
	// Another vars/functions
}
