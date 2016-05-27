package nl.timsonderen.homegg;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends ListActivity {

	static final String[] list = new String[] { "LEDs", "Options" };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setListAdapter(new ArrayAdapter(this, R.layout.activity_main, list));

		ListView listView = getListView();
		listView.setTextFilterEnabled(true);

		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView parent, View view,
					int position, long id) {
				String a = (String) ((TextView) view).getText();
				Intent k = new Intent(MainActivity.this, MainActivity.class);
				if (a.equals("LEDs"))
					k = new Intent(MainActivity.this, LedsActivity.class);
				else if (a.equals("Options"))
					k = new Intent(MainActivity.this, OptionsActivity.class);
				startActivity(k);
				Toast.makeText(getApplicationContext(),
						((TextView) view).getText(), Toast.LENGTH_SHORT).show();
			}
		});

	}

}