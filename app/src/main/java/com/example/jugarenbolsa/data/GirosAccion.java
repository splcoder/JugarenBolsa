package com.example.jugarenbolsa.data;

import android.os.Bundle;
import android.os.Message;

public class GirosAccion extends Thread {
	public static final String ID = "id";
	public static final String VALUE = "value";
	public static final String CHANGE = "change";
	public static final String TREND = "trend";
	public static final String THREAD = "thread";

	public static final int MAX_TIME_INTERVAL = 10000;	// 10 seconds
	public static final double MAX_VALUE_CHANGE_STEP = 10;

	Controlador handler;
	Accion accion;

	public GirosAccion( Accion accion, Controlador handler ){
		this.accion = accion;
		this.handler = handler;
	}

	public Accion getAccion(){
		return accion;
	}

	@Override
	public void run(){
		while( Bolsa.isRunning() ){
			int sleepTime = (int)(Math.random() * MAX_TIME_INTERVAL);
			try {
				Thread.sleep( sleepTime );
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// How much will change the value of the Accion ?
			double rChange = (Math.random() * MAX_VALUE_CHANGE_STEP) - (Math.random() * MAX_VALUE_CHANGE_STEP);
			accion.addValue( rChange );
			accion.setNewChange( rChange );
			double rActualValue = accion.getValue();
			if( rActualValue < accion.getMinReached() )		accion.setMinReached( rActualValue );
			if( accion.getMaxReached() < rActualValue )		accion.setMaxReached( rActualValue );
			//--------------------------------------------------------------------------------------
			// Crear el mensaje para enviar al controlador
			Message msg = handler.obtainMessage();
			// Insert data in the msg
			Bundle bundle = new Bundle();
			bundle.putLong( ID, accion.getId() );
			bundle.putDouble( VALUE, accion.getValue() );
			bundle.putDouble( CHANGE, rChange );
			bundle.putDouble( TREND, accion.getTrend() );
			//bundle.putString( THREAD, currentThread().toString() + ", Accion: " + accion.toString() + ", Change: " + rChange );
			bundle.putString( THREAD, currentThread().toString() + ", Accion: " + accion.toStringAll() + ", Change: " + String.format( Bolsa.DECIMALS_FORMAT, rChange ) );
			msg.setData( bundle );
			handler.sendMessage( msg );
		}
	}
}