package com.example.jugarenbolsa.helpers.system;

import com.google.android.gms.maps.model.LatLng;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * public static byte[] longToBytes( long x ){
 * 		ByteBuffer buffer = ByteBuffer.allocate( Long.BYTES );
 * 		buffer.putLong( x );
 * 		return buffer.array();
 *        }
 *
 * 	public static long bytesToLong( byte[] bytes ){
 * 		ByteBuffer buffer = ByteBuffer.allocate( Long.BYTES );
 * 		buffer.put( bytes );
 * 		buffer.flip();	// need flip
 * 		return buffer.getLong();
 *    }
 */
public class BytesManager {
	/**
	 * Long
	 */
	public static int byteLength( long value ){
		return 8;
	}
	public static byte[] toByteArray( long value ){
		byte[] bytes = new byte[ 8 ];	// Long.BYTES
		ByteBuffer.wrap( bytes ).putLong( value );
		return bytes;
	}
	public static long toLong( byte[] bytes ){
		return ByteBuffer.wrap( bytes ).getLong();
	}
	public static int toByteArray( long value, byte[] bytes, int startPosition ){
		byte[] res = BytesManager.toByteArray( value );
		for( int i = 0; i < 8; i++ ){	// Long.BYTES
			bytes[ startPosition + i ] = res[ i ];
		}
		return 8;
	}
	public static long toLong( byte[] bytes, int startPosition ){
		byte[] res = new byte[ 8 ];	// Long.BYTES
		for( int i = 0; i < 8; i++ ){
			res[ i ] = bytes[ startPosition + i ];
		}
		return BytesManager.toLong( res );
	}
	public static long toLong( double value ){
		byte[] bytes = new byte[ 8 ];	// Double.BYTES
		ByteBuffer.wrap( bytes ).putDouble( value );
		return ByteBuffer.wrap( bytes ).getLong();
	}
	/**
	 * Double
	 */
	public static int byteLength( double value ){
		return 8;
	}
	public static byte[] toByteArray( double value ){
		byte[] bytes = new byte[ 8 ];	// Double.BYTES
		ByteBuffer.wrap( bytes ).putDouble( value );
		return bytes;
	}
	public static double toDouble( byte[] bytes ){
		return ByteBuffer.wrap( bytes ).getDouble();
	}
	public static int toByteArray( double value, byte[] bytes, int startPosition ){
		byte[] res = BytesManager.toByteArray( value );
		for( int i = 0; i < 8; i++ ){	// Double.BYTES
			bytes[ startPosition + i ] = res[ i ];
		}
		return 8;
	}
	public static double toDouble( byte[] bytes, int startPosition ){
		byte[] res = new byte[ 8 ];	// Double.BYTES
		for( int i = 0; i < 8; i++ ){
			res[ i ] = bytes[ startPosition + i ];
		}
		return BytesManager.toDouble( res );
	}
	public static double toDouble( long value ){
		byte[] bytes = new byte[ 8 ];	// Long.BYTES
		ByteBuffer.wrap( bytes ).putLong( value );
		return ByteBuffer.wrap( bytes ).getDouble();
	}
	//----------------------------------------------------------------------------------------------
	public static byte[] latLngListToByteArray( List<LatLng> lla ){
		int sizeBytes = lla.size() * 16;	// Double.BYTES * 2
		byte[] bytes = new byte[ sizeBytes ];
		int bytePosition = 0;
		LatLng latLng;
		for( int i = 0; i < lla.size(); i++ ){
			latLng = lla.get( i );
			BytesManager.toByteArray( latLng.latitude, bytes, bytePosition );
			bytePosition += 8;
			BytesManager.toByteArray( latLng.longitude, bytes, bytePosition );
			bytePosition += 8;
		}
		return bytes;
	}
	public static List<LatLng> byteArrayToLatLngList( byte[] bytes ){
		if( (bytes.length % 16) != 0 ){		// Double.BYTES * 2
			throw new IllegalArgumentException( "byteArrayToLatLngList -> (bytes.length % 16) != 0" );
		}
		List<LatLng> lla = new ArrayList<>();
		int bytesPosition = 0;
		double rLat, rLng;
		while( bytesPosition < bytes.length ){
			rLat = BytesManager.toDouble( bytes, bytesPosition );
			bytesPosition += 8;	// Double.BYTES
			rLng = BytesManager.toDouble( bytes, bytesPosition );
			bytesPosition += 8;	// Double.BYTES
			lla.add( new LatLng( rLat, rLng ) );
		}
		return lla;
	}
}