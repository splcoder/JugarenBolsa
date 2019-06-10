package com.example.jugarenbolsa.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a2019_05_30_listado.helpers.Cache;

import com.example.jugarenbolsa.R;
import com.example.jugarenbolsa.data.Bolsa;
import com.example.jugarenbolsa.data.Jugador;
import com.example.jugarenbolsa.helpers.system.BytesManager;

public class EndActivity extends AppCompatActivity {
	ImageView img;
	TextView txtBye;
	TextView txtNewRecord;
	MediaPlayer mp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_end);

		img = findViewById( R.id.img );
		txtBye = findViewById( R.id.txtBye );
		txtNewRecord = findViewById( R.id.txtNewRecord );

		// 1º Liberar la caché		<<< REQUIRED >>>
		if( mp != null )	mp.release();

		double endValue = (double)Cache.get( "endValue" );
		boolean isNewRecord = checkRecordWith( endValue );
		if( ! isNewRecord )	txtNewRecord.setText( "" );
		if( endValue >= 50 ){
			img.setImageResource( R.drawable.gold_money );
			txtBye.setText( "Has ganado: " + String.format( Bolsa.DECIMALS_FORMAT, endValue ) + " !!!" );
			mp = MediaPlayer.create( this, R.raw.ganar );
		}
		else if( endValue >= 25 ){
			img.setImageResource( R.drawable.silver_money );
			txtBye.setText( "Has ganado: " + String.format( Bolsa.DECIMALS_FORMAT, endValue ) + " !!!" );
			mp = MediaPlayer.create( this, R.raw.ganar );
		}
		else if( endValue >= 5 ){
			img.setImageResource( R.drawable.bronze_money );
			txtBye.setText( "Has ganado: " + String.format( Bolsa.DECIMALS_FORMAT, endValue ) + " !!!" );
			mp = MediaPlayer.create( this, R.raw.ganar );
		}
		else if( endValue >= 0 ){
			img.setImageResource( R.drawable.one_penny_money );
			txtBye.setText( "Try to reach this penny" );
			mp = MediaPlayer.create( this, R.raw.ganar );
		}
		else	mp = MediaPlayer.create( this, R.raw.perder );
		mp.seekTo( 0 );
		mp.start();
	}

	@Override
	protected void onPause() {
		super.onPause();
		// 1º Liberar la caché		<<< REQUIRED >>>
		if( mp != null )	mp.release();
	}

	private boolean checkRecordWith( double value ){
		SharedPreferences sharedPreferences = getSharedPreferences( Jugador.BEST_PLAYER, Context.MODE_PRIVATE );
		double record = BytesManager.toDouble( sharedPreferences.getLong( Jugador.BEST_PLAYER, 0 ) );
		if( record < value ){
			// Save
			SharedPreferences.Editor editor = sharedPreferences.edit();
			editor.putLong( Jugador.BEST_PLAYER, BytesManager.toLong( value ) );
			editor.commit();
			return true;
		}
		return false;
	}
}
