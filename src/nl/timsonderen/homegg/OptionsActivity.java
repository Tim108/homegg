package nl.timsonderen.homegg;

import android.app.Activity;
import android.os.Bundle;

public class OptionsActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.options_layout);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
