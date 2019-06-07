package com.example.jugarenbolsa.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Bolsa {
	public static final String DECIMALS_FORMAT = "%.3f";	// String.format( Bolsa.DECIMALS_FORMAT, value );
	private Map<Long, GirosAccion> mapAcciones = new HashMap<>();

	private static boolean RUNNING = true;
	public static boolean isRunning(){
		return RUNNING;
	}

	public Bolsa(){}

	public void addAccion( GirosAccion accion ){
		//mapAcciones.put( accion.getId(), accion );
		mapAcciones.put( accion.getAccion().getId(), accion );
	}
	public Accion getAccion( long id ){
		Accion accion = mapAcciones.get( id ).getAccion();
		return accion;
	}
	public ArrayList<Accion> getAcciones(){
		ArrayList<Accion> acciones = new ArrayList<>();
		for( long id : mapAcciones.keySet() ){
			acciones.add( mapAcciones.get( id ).getAccion() );
		}
		return acciones;
	}
	public String toString(){
		String s = "[Acciones en Bolsa: ";
		for( long id : mapAcciones.keySet() ){
			s += "\n< " + mapAcciones.get( id ).getAccion().toString() + " >";
		}
		return s;
	}

	public void start(){
		RUNNING = true;
		for( long id : mapAcciones.keySet() ){
			((GirosAccion)mapAcciones.get( id )).start();
		}
	}
	public void stop(){
		RUNNING = false;
	}
}
