package com.cindypotvin.sidespinnerexample;

import android.os.Bundle;
import android.app.Activity;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
      // Initialize the spinner from code
      SideSpinner fruitsSpinner = (SideSpinner) this
            .findViewById(R.id.sidespinner_fruits);

      CharSequence fruitList[] = { "Apple", "Orange", "Pear", "Grapes" };
      fruitsSpinner.setValues(fruitList);
      fruitsSpinner.setSelectedIndex(1);
	}
}
