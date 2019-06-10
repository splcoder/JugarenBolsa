package com.example.jugarenbolsa.data;

import com.example.jugarenbolsa.helpers.db.IdGenerator;

import java.util.HashMap;
import java.util.Map;

public class Jugador {
	private static IdGenerator idGenerator = new IdGenerator();

	public static final String CACHE_NAME = "jugador";
	public static final double INITIAL_MAX_AMOUNT_OF_MONEY = 10000;
	public static final String BEST_PLAYER = "best_player";

	private long id;
	private String name;
	private double amountMoney = 0;
	private double initialAmountMoney = 0;
	private Map<Long, StocksBought> mapCantidadAcciones = new HashMap<>();	// <id Accion, cantidad>

	public Jugador(){
		id = idGenerator.getNewId();
	}

	public Jugador(String name, double amountMoney) {
		this();
		this.name = name;
		this.amountMoney = amountMoney;
		this.initialAmountMoney = amountMoney;
	}

	public Jugador(long id, String name, double amountMoney) {
		this.id = id;
		this.name = name;
		this.amountMoney = amountMoney;
		this.initialAmountMoney = amountMoney;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getAmountMoney() {
		return amountMoney;
	}
	public double getInitialAmountMoney() {
		return initialAmountMoney;
	}

	public void setAmountMoney(double amountMoney) {
		this.amountMoney = amountMoney;
	}

	public Map<Long, StocksBought> getMapCantidadAcciones() {
		return mapCantidadAcciones;
	}

	public void setMapCantidadAcciones( Map<Long, StocksBought> mapCantidadAcciones ) {
		for( long i : mapCantidadAcciones.keySet() ){
			this.mapCantidadAcciones.put( i, mapCantidadAcciones.get( i ) );
		}
	}

	public boolean hasStock( long idStock ){ return mapCantidadAcciones.containsKey( idStock ); }
	public boolean buyStock( Accion accion ){
		double stockValue = accion.getValue();
		if( stockValue > amountMoney )	return false;
		amountMoney -= stockValue;
		// save
		StocksBought stocksBought = mapCantidadAcciones.get( accion.getId() );
		if( stocksBought == null ){
			mapCantidadAcciones.put( accion.getId(), new StocksBought( 1, stockValue, accion ) );
		}
		else{
			stocksBought.total++;
			stocksBought.spent += stockValue;
		}
		return true;
	}
	public int sellStock( Accion accion ){
		StocksBought stocksBought = mapCantidadAcciones.get( accion.getId() );
		if( stocksBought == null )			return -1;
		else{
			if( stocksBought.total == 0 )	return 0;
			else{
				double stockValue = accion.getValue();
				stocksBought.total--;
				stocksBought.spent -= stockValue;
				amountMoney += stockValue;
			}
		}
		return 1;
	}
	public double getTotalStocksWon(){
		double total = 0;
		for( long id : mapCantidadAcciones.keySet() ){
			StocksBought stocksBought = mapCantidadAcciones.get( id );
			total += stocksBought.total * stocksBought.stock.getValue();
		}
		return total;
	}
	public double getTotalWon(){
		double total = 0;
		for( long id : mapCantidadAcciones.keySet() ){
			StocksBought stocksBought = mapCantidadAcciones.get( id );
			total += (stocksBought.total * stocksBought.stock.getValue() - stocksBought.spent);
		}
		return total;
	}
	public double getTotalWonInStock( long idStock ){
		StocksBought stocksBought = mapCantidadAcciones.get( idStock );
		if( stocksBought == null )	return 0;
		return( stocksBought.total * stocksBought.stock.getValue() - stocksBought.spent );
	}
}
