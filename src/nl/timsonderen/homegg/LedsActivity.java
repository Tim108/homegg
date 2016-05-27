package nl.timsonderen.homegg;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class LedsActivity extends Activity {
	int r, g, b = 0;
	SeekBar redSB, greenSB, blueSB, whiteSB;
	TextView redTV, greenTV, blueTV, whiteTV;
	TextView cc0, cc1;

	private boolean IsBound = false;
	SocketService BoundService;
	private ServiceConnection Connection = new ServiceConnection(){
		public void onServiceConnected(ComponentName className, IBinder service){
			BoundService = ((SocketService.LocalBinder)service).getService();
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			BoundService = null;
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.leds_layout);

		redSB = (SeekBar) findViewById(R.id.seekBarRed);
		greenSB = (SeekBar) findViewById(R.id.seekBarGreen);
		blueSB = (SeekBar) findViewById(R.id.seekBarBlue);
		whiteSB = (SeekBar) findViewById(R.id.seekBarWhite);

		redSB.setMax(255);
		greenSB.setMax(255);
		blueSB.setMax(255);
		whiteSB.setMax(255);

		redTV = (TextView) findViewById(R.id.redValue);
		greenTV = (TextView) findViewById(R.id.greenValue);
		blueTV = (TextView) findViewById(R.id.blueValue);
		whiteTV = (TextView) findViewById(R.id.whiteValue);
		
		
		doBindService();

		OnSeekBarChangeListener osbcl = new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				TextView targetTV = null;

				switch (seekBar.getId()) {
				case (R.id.seekBarRed):
					redTV.setText(String.valueOf(progress));
					r = progress;
					break;
				case (R.id.seekBarGreen):
					greenTV.setText(String.valueOf(progress));
					g = progress;
					break;
				case (R.id.seekBarBlue):
					blueTV.setText(String.valueOf(progress));
					b = progress;
					break;
				case (R.id.seekBarWhite):
					whiteTV.setText(String.valueOf(progress));
					redTV.setText(String.valueOf(progress));
					greenTV.setText(String.valueOf(progress));
					blueTV.setText(String.valueOf(progress));
					redSB.setProgress(progress);
					greenSB.setProgress(progress);
					blueSB.setProgress(progress);
					r = g = b = progress;
					break;
				}
				refreshColors();
			}
		};
		redSB.setOnSeekBarChangeListener(osbcl);
		greenSB.setOnSeekBarChangeListener(osbcl);
		blueSB.setOnSeekBarChangeListener(osbcl);
		whiteSB.setOnSeekBarChangeListener(osbcl);

		cc0 = (TextView) findViewById(R.id.currentcolor0);
		cc1 = (TextView) findViewById(R.id.currentcolor1);

	}

	private void refreshColors() {
		if(BoundService != null)
			BoundService.sendMessage("ML" + "|" + r + "|" + g + "|" + b);
		cc0.setBackgroundColor(Color.rgb(r, g, b));
		cc0.setTextColor(Color.rgb(255 - r, 255 - g, 255 - b));
		cc1.setBackgroundColor(Color.rgb(r, g, b));
		cc1.setTextColor(Color.rgb(255 - r, 255 - g, 255 - b));
	}

	private void doBindService() {
		bindService(new Intent(LedsActivity.this, SocketService.class),
				Connection, Context.BIND_AUTO_CREATE);
		IsBound = true;
		if (BoundService != null) {
			BoundService.IsBoundable();
		}
	}

	private void doUnbindService() {
		if (IsBound) {
			// Detach our existing connection.
			unbindService(Connection);
			IsBound = false;
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		doUnbindService();
	}

}
