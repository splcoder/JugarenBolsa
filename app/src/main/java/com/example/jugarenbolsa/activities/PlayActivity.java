package com.example.jugarenbolsa.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jugarenbolsa.R;
import com.example.jugarenbolsa.data.Accion;
import com.example.jugarenbolsa.data.Bolsa;
import com.example.jugarenbolsa.data.Controlador;
import com.example.jugarenbolsa.data.Jugador;
import com.example.a2019_05_30_listado.helpers.Cache;
import com.example.jugarenbolsa.data.StocksBought;

import java.util.ArrayList;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class PlayActivity extends AppCompatActivity implements View.OnClickListener {

	Bolsa bolsa;
	Controlador controlador;

	TextView txtPlayersName;
	Jugador jugador;

	CheckBox cbShowAll;
	Spinner spinnerStocks;
	TextView txtAmount;
	TextView txtStockValue;
	TextView txtTotalStocksBought;
	TextView txtStockWon;
	TextView txtTotalStocksWon;
	TextView txtTotalWon;

	TextView txtNotesBolsa;

	Button btnBuy, btnSell;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);

		txtPlayersName = findViewById( R.id.txtPlayersName );
		txtAmount = findViewById( R.id.txtAmount );
		txtTotalStocksBought = findViewById( R.id.txtTotalStocksBought );
		txtStockValue = findViewById( R.id.txtStockValue);
		txtStockWon = findViewById( R.id.txtStockWon);
		cbShowAll = findViewById( R.id.cbShowAll );
		spinnerStocks = findViewById( R.id.spinnerStocks );
		txtNotesBolsa = findViewById( R.id.txtNotesBolsa);
		btnBuy = findViewById( R.id.btnBuy );
		btnSell = findViewById( R.id.btnSell );
		txtTotalStocksWon = findViewById( R.id.txtTotalStocksWon );
		txtTotalWon = findViewById( R.id.txtTotalWon );

		jugador = (Jugador)Cache.get( Jugador.CACHE_NAME );
		txtPlayersName.setText( jugador.getName() );

		btnBuy.setOnClickListener( this );
		btnSell.setOnClickListener( this );

		bolsa = (Bolsa)Cache.get( "bolsa" );
		controlador = (Controlador)Cache.get( "controlador" );
		controlador.set( this, jugador );

		// Fill the stocks
		ArrayList<Accion> aStocks = bolsa.getAcciones();
		ArrayAdapter<Accion> spinnerArrayAdapter = new ArrayAdapter<Accion>( this, android.R.layout.simple_spinner_item, aStocks );
		spinnerArrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
		spinnerStocks.setAdapter( spinnerArrayAdapter );

		spinnerStocks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				showUsersAndStockSelectedData();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				//
			}
		});
	}

	public void setAmount( String txt ){ txtAmount.setText( txt ); }
	public void setTotalStocksBought( int totalBought ){ txtTotalStocksBought.setText( String.valueOf( totalBought ) ); }
	public void setStockValue( String txt ){ txtStockValue.setText( txt ); }
	public void setStockWon( String txt ){ txtStockWon.setText( txt ); }
	public void setTotalStocksWon( String txt ){ txtTotalStocksWon.setText( txt ); }
	public void setTotalWon( String txt ){ txtTotalWon.setText( txt ); }
	public TextView getNotesBolsa(){ return txtNotesBolsa; }
	public void setNotesBolsa( String txt ){ txtNotesBolsa.setText( txt ); }
	public Accion getStockSelected(){
		return (Accion)spinnerStocks.getSelectedItem();
	}
	public boolean showAll(){
		return cbShowAll.isChecked();
	}

	@Override
	public void onClick(View v) {
		Accion accion = getStockSelected();
		switch( v.getId() ){
			case R.id.btnBuy: {
				if( ! jugador.buyStock( accion ) ){
					Toasty.error( getApplicationContext(), "Sorry, not enought money.", Toast.LENGTH_SHORT, true ).show();
				}
				break;
			}
			case R.id.btnSell: {
				if( jugador.sellStock( accion ) < 1 ){
					Toasty.error( getApplicationContext(), "You don't have stocks of this enterprise.", Toast.LENGTH_SHORT, true ).show();
				}
				break;
			}
		}
		showUsersAndStockSelectedData();
	}

	public void showUsersAndStockSelectedData(){
		setAmount( String.valueOf( jugador.getAmountMoney() ) );
		setTotalStocksWon( String.valueOf( jugador.getTotalStocksWon() ) );
		setTotalWon( String.valueOf( jugador.getTotalWon() ) );

		Accion accion = getStockSelected();
		setStockValue( String.valueOf( accion.getValue() ) );

		long idStock =  accion.getId();
		if( jugador.hasStock( idStock ) ){
			// TODO getTotalWonInStock
			Map<Long, StocksBought> accionesJugador = jugador.getMapCantidadAcciones();
			StocksBought stocksBought = accionesJugador.get( idStock );

			setTotalStocksBought( stocksBought.total );
			setStockWon( String.valueOf( stocksBought.total * accion.getValue() ) );
		}
		else{
			setTotalStocksBought( 0 );
			setStockWon( String.valueOf( 0 ) );
		}
	}
}