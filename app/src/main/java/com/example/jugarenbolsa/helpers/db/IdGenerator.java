package com.example.jugarenbolsa.helpers.db;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Helper for managing "raw data" with files...
 */
public class IdGenerator {

	private AtomicLong lastId = new AtomicLong(0 );

	public IdGenerator(){}
	public IdGenerator( long id ){ lastId.set( id ); }

	public long getNewId() {
		return lastId.getAndIncrement();
	}

	// Special functions to be used only by a manager
	public void setLastId( long id ) {
		lastId.set( id );
	}
	public long getLastId() {
		return lastId.get();
	}
}