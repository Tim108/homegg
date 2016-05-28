package nl.timsonderen.homegg;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import methods.TMD;

public class MainActivity extends ListActivity {

    //	static final String[] list = new String[] { "LEDs", "Options" };
    static final String[] list = new String[]{"Decision Tree", "Random Forest", "K Nearest Neighbours"};

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
//				Toast.makeText(getApplicationContext(),
//						((TextView) view).getText(), Toast.LENGTH_SHORT).show();
                boolean success = false;
                if (a.equals("Decision Tree")) {
                    Toast.makeText(getApplicationContext(),
                            "Starting decision tree test", Toast.LENGTH_SHORT).show();
                    success = TMD.testIt("decisiontree");
                } else if (a.equals("Random Forest")) {
                    Toast.makeText(getApplicationContext(),
                            "Starting random forest test", Toast.LENGTH_SHORT).show();
                    success = TMD.testIt("randomforest");
                } else if (a.equals("K Nearest Neighbours")) {
                    Toast.makeText(getApplicationContext(),
                            "Starting knn test", Toast.LENGTH_SHORT).show();
                    success = TMD.testIt("knn");
                }
                if (success) {
                    Toast.makeText(getApplicationContext(),
                            "Test Done", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}