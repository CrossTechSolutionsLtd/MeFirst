package com.example.mefirst;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import com.example.mefirst.*;

public class MainActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		setScreen();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		try {
			switch (item.getItemId()) {
			case R.id.meFirst:
				Intent it = new Intent(this, OrderActivity.class);
				String selectedChildren = getSelectedChildren();
				it.putExtra("ChildList", selectedChildren);
				startActivity(it);

				break;
			case R.id.reset:
				setScreen();
			default:
				return super.onOptionsItemSelected(item);
			}
		} catch (Exception e) {
			int i = 0; // TODO: Do nothing for now
		}
		return true;
	}

	private void setScreen() {
		TableLayout tl = (TableLayout) findViewById(R.id.childTable);
		Color col = new Color();
		tl.removeAllViews();
		
		Iterator<Child> children = ChildManager.Get().GetChildIterator();
		while(children.hasNext()) {
			Child child = children.next();
			TableRow tr = new TableRow(this);
			tr.setPadding(0,  6, 0, 4);
			Button bt = new Button(this);
			
			bt.setText(child.getName());
			bt.setBackgroundColor(col.rgb(200,  200, 200));

			TableRow.LayoutParams blp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
			blp.weight = 1;
			bt.setOnClickListener(this);
			
			tr.addView(bt, blp);
			TableLayout.LayoutParams lp = new TableLayout.LayoutParams(
					TableLayout.LayoutParams.MATCH_PARENT,
					TableLayout.LayoutParams.MATCH_PARENT);
			lp.weight = 1;
			tl.addView(tr, lp );

			/*
			TextView tv = new TextView(this);
			tv.setTextAppearance(this, android.R.style.TextAppearance_Large);
			tv.setText(child.getName());
			tv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			tv.setGravity(Gravity.CENTER_HORIZONTAL);
			tv.setBackgroundColor(child.getBColour());
			tr.addView(tv);

			CheckBox cb = new CheckBox(this);
			tr.addView(cb);

			tl.addView(tr, new TableLayout.LayoutParams(
					TableLayout.LayoutParams.WRAP_CONTENT,
					TableLayout.LayoutParams.WRAP_CONTENT));
					*/
		}
	}

	private String getSelectedChildren() {
		String selectedChildren = "";

		TableLayout tl = (TableLayout) findViewById(R.id.childTable);

		for (int i = 0; i < tl.getChildCount(); i++) {
			TableRow tr = (TableRow) tl.getChildAt(i);
			Button bt = (Button) tr.getChildAt(0);
			Child child = ChildManager.Get().FindChild(bt.getText().toString());
			Drawable background = bt.getBackground();
			ColorDrawable cd = (ColorDrawable) background;
			
			
			if (child.getBColour() == cd.getColor()) {
				selectedChildren += "," + bt.getText();
			}
		}

/*
		for (int i = 0; i < tl.getChildCount(); i++) {
			TableRow tr = (TableRow) tl.getChildAt(i);
			TextView tv = (TextView) tr.getChildAt(0);
			
			CheckBox cb = (CheckBox) tr.getChildAt(1);

			if (cb.isChecked()) {
				selectedChildren += "," + tv.getText();
			}
		}
*/
		return selectedChildren;
	}
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Button b = (Button) v;
		Child child = ChildManager.Get().FindChild(b.getText().toString());
		Drawable background = b.getBackground();
		ColorDrawable cd = (ColorDrawable) background;
		Color col = new Color();
		
		if (child.getBColour() == cd.getColor()) {
			b.setBackgroundColor(col.rgb(200,200, 200));
		} else {		
		    b.setBackgroundColor(child.getBColour());
		}
	}
}
