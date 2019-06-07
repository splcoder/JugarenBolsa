package com.example.jugarenbolsa.data;

import android.os.Handler;
import android.os.Message;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.jugarenbolsa.activities.PlayActivity;

import java.util.Map;

// Controlador para recibir mensajes del hilo
public class Controlador extends Handler {
	//private final int MAX_CACHE = 1048576;	// 1 MB
	private final int MAX_CACHE = 1024;	// 1 KB

	private TextView txtBolsa = null;
	private Spinner spinnerStocks = null;
	private Jugador jugador = null;
	private PlayActivity playActivity = null;

	public Controlador(){}
	public Controlador( PlayActivity playActivity, Jugador jugador ){
		this.playActivity = playActivity;
		this.jugador = jugador;
	}

	public void set( PlayActivity playActivity, Jugador jugador ){
		this.playActivity = playActivity;
		this.jugador = jugador;
	}

	@Override
	public void handleMessage(Message msg) {
		//super.handleMessage(msg);
		long idStock = msg.getData().getLong( GirosAccion.ID );	// Accion/Stock id
		double stockValue = msg.getData().getDouble( GirosAccion.VALUE );
		double change = msg.getData().getDouble( GirosAccion.CHANGE );
		String thread = msg.getData().getString( GirosAccion.THREAD );
		if( playActivity != null && jugador != null ){
			// Show players's money
			playActivity.setAmount( String.valueOf( jugador.getAmountMoney() ) );
			//
			Accion stockSelected = playActivity.getStockSelected();
			// Show notes of Bolsa
			if( playActivity.showAll() || idStock == stockSelected.getId() ){
				String s = playActivity.getNotesBolsa().getText().toString();
				playActivity.setNotesBolsa( thread + "\n\n" + (s.length() > MAX_CACHE ? s.substring( 0, MAX_CACHE ) : s) );
			}
			if( idStock == stockSelected.getId() ){
				if( change < 0 )	playActivity.setStockValueColor( true );
				else				playActivity.setStockValueColor( false );
				// Show the Stock value
				/*playActivity.setStockValue( String.valueOf( stockValue ) );
				// Show players' data
				if( jugador.hasStock( idStock ) ){
					Map<Long, StocksBought> accionesJugador = jugador.getMapCantidadAcciones();
					StocksBought stocksBought = accionesJugador.get( idStock );
					playActivity.setTotalStocksBought( stocksBought.total );
					playActivity.setStockWon( String.valueOf( stocksBought.total * stockValue ) );
				}
				else	playActivity.setTotalStocksBought( 0 );
				*/
				playActivity.showUsersAndStockSelectedData();
			}
		}
	}
}
