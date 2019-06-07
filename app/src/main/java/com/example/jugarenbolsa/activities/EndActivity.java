package com.example.jugarenbolsa.activities;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a2019_05_30_listado.helpers.Cache;

import com.example.jugarenbolsa.R;
import com.example.jugarenbolsa.data.Bolsa;

public class EndActivity extends AppCompatActivity {
	ImageView img;
	TextView txtBye;
	MediaPlayer mp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_end);

		img = findViewById( R.id.img );
		txtBye = findViewById( R.id.txtBye );

		// 1º Liberar la caché		<<< REQUIRED >>>
		if( mp != null )	mp.release();

		double endValue = (double)Cache.get( "endValue" );
		if( endValue >= 0 ){
			img.setImageResource( R.drawable.gold_money );
			txtBye.setText( "Has ganado: " + String.format( Bolsa.DECIMALS_FORMAT, endValue ) + " !!!" );
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
}
