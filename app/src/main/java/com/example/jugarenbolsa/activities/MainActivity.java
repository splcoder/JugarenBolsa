package com.example.jugarenbolsa.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jugarenbolsa.R;
import com.example.jugarenbolsa.data.Accion;
import com.example.jugarenbolsa.data.Bolsa;
import com.example.a2019_05_30_listado.helpers.Cache;
import com.example.jugarenbolsa.data.Controlador;
import com.example.jugarenbolsa.data.GirosAccion;
import com.example.jugarenbolsa.data.Jugador;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

/**
 * Juega en bolsa
 *
 * Realizar una aplicación que simule jugar en bolsa
 * Tenemos una clase Accion con los siguientes campos:
 * Nombre de la empresa
 * Valor de la acción
 * Tendencia, si en los últimos movimientos ha subido o bajado
 * Valor máximo alcanzado
 * Valor mínimo alcanzado
 * Nivel de riesgo de 1 a 4 siendo 1 mínimo riesgo y 4 máximo riesgo
 * Tenemos una clase jugador con los siguientes campos
 * Nombre
 * Capital
 * HashMap<Accion, Integer> que guardará el tipo de acciones y cantidad de cada una que tiene el jugador
 * Al comenzar la partida se le pide el nombre al jugador
 * El jugador cuenta con un capital inicial x que podrá manejar comprando y vendiendo acciones
 * El valor de las acciones va cambiando periodicamente
 * En pantalla se muestra la cartera de valores del jugador
 * El juego dura un tiempo determinado. Al finalizar se comprueba si el jugador ha obtenido un record de dinero ganado, si es así se guardan el nombre y las ganancias del jugador y se le da una copa con un sonido de ganador de fondo
 * Si el jugador no ha obtenido un record se le da una medallita de consuelo con un sonido de fondo
 * Al finalizar la partida se invita a jugar de nuevo
 */
public class MainActivity extends AppCompatActivity {

	EditText txtName;
	Button btnStart;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		txtName = findViewById( R.id.txtName );
		btnStart = findViewById( R.id.btnStart );

		btnStart.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String name = txtName.getText().toString();
				if( name == null || name.isEmpty() ){
					Toasty.error( getApplicationContext(), "You must put a name.", Toast.LENGTH_SHORT, true ).show();
				}
				else{
					Jugador jugador = new Jugador( name, Math.random()*Jugador.INITIAL_MAX_AMOUNT_OF_MONEY );
					Cache.set( Jugador.CACHE_NAME, jugador );
					Intent intent = new Intent( MainActivity.this, PlayActivity.class );
					startActivity( intent );
				}
			}
		});

		// Bolsa
		Bolsa bolsa = new Bolsa();
		Cache.set( "bolsa", bolsa );

		Controlador controlador = new Controlador();
		Cache.set( "controlador", controlador );

		// Create some Accion's
		Accion accionRepsol = new Accion( "Repsol" );
		accionRepsol.setValue( Math.random()*Accion.INITIAL_MAX_VALUE_ACCION );
		GirosAccion girosRepsol = new GirosAccion( accionRepsol, controlador );
		bolsa.addAccion( girosRepsol );

		Accion accionIberdrola = new Accion( "Iberdrola" );
		accionIberdrola.setValue( Math.random()*Accion.INITIAL_MAX_VALUE_ACCION );
		GirosAccion girosIberdrola = new GirosAccion( accionIberdrola, controlador );
		bolsa.addAccion( girosIberdrola );

		Accion accionDia = new Accion( "Dia" );
		accionDia.setValue( Math.random()*Accion.INITIAL_MAX_VALUE_ACCION );
		GirosAccion girosDia = new GirosAccion( accionDia, controlador );
		bolsa.addAccion( girosDia );

		girosRepsol.start();
		girosIberdrola.start();
		girosDia.start();
		//bolsa.start();
	}
}
