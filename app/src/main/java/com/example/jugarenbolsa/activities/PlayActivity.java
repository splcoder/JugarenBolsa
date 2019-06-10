package com.example.jugarenbolsa.activities;

import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.CountDownTimer;
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
import java.util.Timer;

import es.dmoral.toasty.Toasty;

// TODO trend	<<< Sum last 5 variations
// TODO speed of change
public class PlayActivity extends AppCompatActivity implements View.OnClickListener {
	PlayActivity that = this;

	CountDownTimer countDownTimer;
	String sActionbarTitle = "";
	final long TOTAL_AVAILABLE_TIME = 60000;	// mls
	long remaining = TOTAL_AVAILABLE_TIME;

	Bolsa bolsa;
	Controlador controlador;

	TextView txtPlayersName;
	Jugador jugador;

	CheckBox cbShowAll;
	Spinner spinnerStocks;
	TextView txtAmount;
	TextView txtAmountDiference;
	TextView txtStockValue;
	TextView txtTotalStocksBought;
	TextView txtStockWon;
	TextView txtTotalStocksWon;
	TextView txtTotalWon;
	TextView txtArrow;

	TextView txtNotesBolsa;

	Button btnBuy, btnSell;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);

		sActionbarTitle = getTitle().toString();

		txtPlayersName = findViewById( R.id.txtPlayersName );
		txtAmount = findViewById( R.id.txtAmount );
		txtAmountDiference = findViewById( R.id.txtAmountDiference );
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
		txtArrow = findViewById( R.id.txtArrow );

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

		startTimer();
	}

	public void setAmount( String txt ){ txtAmount.setText( txt ); }
	public void setAmountDiference( String txt ){ txtAmountDiference.setText( txt ); }
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
		setAmount( String.format( Bolsa.DECIMALS_FORMAT, jugador.getAmountMoney() ) );
		double diference = jugador.getAmountMoney() - jugador.getInitialAmountMoney();
		setAmountDiference( String.format( Bolsa.DECIMALS_FORMAT, diference ) );
		if( diference < 0 )	txtAmountDiference.setTextColor( getResources().getColor( R.color.red ) );
		else				txtAmountDiference.setTextColor( getResources().getColor( R.color.green ) );
		setTotalStocksWon( String.format( Bolsa.DECIMALS_FORMAT, jugador.getTotalStocksWon() ) );
		setTotalWon( String.format( Bolsa.DECIMALS_FORMAT, jugador.getTotalWon() ) );

		Accion accion = getStockSelected();
		setStockValue( String.format( Bolsa.DECIMALS_FORMAT, accion.getValue() ) );
		double trend = accion.getTrend();
		if( trend > 0 ){
			txtArrow.setText( getApplicationContext().getResources().getText( R.string.arrow_up ) );
			txtArrow.setTextColor( getApplicationContext().getResources().getColor( R.color.green ) );
		}
		else if( trend == 0 ){
			txtArrow.setText( getApplicationContext().getResources().getText( R.string.arrow_equal ) );
			txtArrow.setTextColor( getApplicationContext().getResources().getColor( R.color.blue ) );
		}
		else{
			txtArrow.setText( getApplicationContext().getResources().getText( R.string.arrow_down ) );
			txtArrow.setTextColor( getApplicationContext().getResources().getColor( R.color.red ) );
		}

		long idStock =  accion.getId();
		if( jugador.hasStock( idStock ) ){
			// TODO getTotalWonInStock ???
			Map<Long, StocksBought> accionesJugador = jugador.getMapCantidadAcciones();
			StocksBought stocksBought = accionesJugador.get( idStock );

			setTotalStocksBought( stocksBought.total );
			setStockWon( String.format( Bolsa.DECIMALS_FORMAT, stocksBought.total * accion.getValue() ) );
		}
		else{
			setTotalStocksBought( 0 );
			setStockWon( String.valueOf( 0 ) );
		}
	}
	public void setStockValueColor( boolean red ){
		if( red )	txtStockValue.setTextColor( getResources().getColor( R.color.red ) );
		else		txtStockValue.setTextColor( getResources().getColor( R.color.green ) );
	}

	private void startTimer(){
		countDownTimer = new CountDownTimer( TOTAL_AVAILABLE_TIME, 1000 ){
			public void onTick(long millisUntilFinished) {
				remaining -= 1000;
				setTitle( sActionbarTitle + ": " + (remaining/1000) + " seconds" );
			}
			public void onFinish() {
				Cache.set( "endValue", (jugador.getAmountMoney() - jugador.getInitialAmountMoney()) );
				Intent intent = new Intent( PlayActivity.this, EndActivity.class );
				startActivity( intent );
				that.finish();	// << For restarting all the play
			}
		}.start();
	}
}