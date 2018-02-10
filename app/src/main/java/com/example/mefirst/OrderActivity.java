package com.example.mefirst;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;

public class OrderActivity extends Activity {
	private TableLayout mainTableLayout = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		final  ArrayList<Child> candidateChildren = new ArrayList<Child>();
		final ArrayList<Child> pickedChildren = new ArrayList<Child>();

		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			mainTableLayout= (TableLayout) findViewById(R.id.selectedChildTable);
			
		   String candidateChildrenText = extras.getString("ChildList");
		   
		   String[] candidateChildrenList = candidateChildrenText.split(",");

		   ChildManager childManager = ChildManager.Get();
		   for (int pos = 1; pos < candidateChildrenList.length; pos++) {
			    String childName = candidateChildrenList[pos];
			   candidateChildren.add(childManager.FindChild(childName));
		   }
		   
		   setBlankScreen(candidateChildren);

		   final Handler handler = new Handler() {
			   Random random = new Random (System.nanoTime());
			   int counter = 0;
			   int nextStop = -1;
			   @Override
			   public void handleMessage(Message msg) {
				   if (candidateChildren.size() ==0) {
					   return;
				   }
				   if (nextStop < counter) {
					   nextStop = counter + 15 + random.nextInt(candidateChildren.size());
				   }

				   Child candidate = getNextCandidate(candidateChildren, counter++);				   
				   showCandidate(pickedChildren, candidate);
				   
				   if (nextStop == counter) {
				      pickChild(candidateChildren, pickedChildren, candidate);
				   }

				   if (candidateChildren.size() == 1) {
		 	   		  Child child = candidateChildren.get(0);
					   showCandidate(pickedChildren, child);
					   pickChild(candidateChildren, pickedChildren, child);
				   }
			   }
		   };
		   
		   	Runnable runnable = new Runnable() {
		   	  public void run() {
		   		  do {
			   		  try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			   		  Message msg = handler.obtainMessage();
			   		  handler.sendMessage(msg);
		   		  } while (candidateChildren.size() > 0);
		   	  }
		   	};
		   	Thread myThread = new Thread(runnable);
		   	myThread.start();	
           }
	}
	
	private void showCandidate(ArrayList<Child> pickedChildren, Child candidate) {
		TableRow tr = (TableRow) mainTableLayout.getChildAt(pickedChildren.size());
		Button bt = (Button) tr.getChildAt(0);
		bt.setText(candidate.getName());
		bt.setBackgroundColor(candidate.getBColour());
	}
	
	private Child getNextCandidate(ArrayList<Child> candidates, int count) {
		return candidates.get(count % candidates.size());
	}
	
	private void pickChild(ArrayList<Child> candidates, ArrayList<Child> picked, Child child) {
	    for  (int childNo = 0; childNo < candidates.size(); childNo++) {
	       if (child == candidates.get(childNo)) {
	    	      candidates.remove(childNo);
	    	      picked.add(child);
	    	      break;
	       }
	    }
	}
	
	private void setBlankScreen(ArrayList<Child> candidates) {
		Color col = new Color();

		mainTableLayout.removeAllViews();

		for (int childNo = 0; childNo < candidates.size(); childNo++) {
			TableRow tr = new TableRow(this);

			tr.setPadding(0,  6, 0, 4);
			Button bt = new Button(this);
			bt.setBackgroundColor(col.rgb(200,  200, 200));			

			TableRow.LayoutParams blp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
			blp.weight = 1;
			//bt.setOnClickListener(this);
			
			tr.addView(bt, blp);
			mainTableLayout.addView(tr, new TableLayout.LayoutParams(
					TableLayout.LayoutParams.MATCH_PARENT,
					TableLayout.LayoutParams.WRAP_CONTENT));
		}
	}
	
	private void setScreen(ArrayList<Child> children) {
		TableLayout tl = (TableLayout) findViewById(R.id.selectedChildTable);

		tl.removeAllViews();
		Iterator<Child> childIterator = children.iterator();
		int orderNo = 1;
		while(childIterator.hasNext()) {
			Child child = childIterator.next();
			TableRow tr = new TableRow(this);

			tr.setPadding(0,  6, 0, 4);
			Button bt = new Button(this);
			
			bt.setText(child.getName());
			bt.setBackgroundColor(child.getBColour());
			
			TableRow.LayoutParams blp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
			blp.weight = 1;
			//bt.setOnClickListener(this);
			
			tr.addView(bt, blp);
			
			/*
			TextView tv = new TextView(this);
			tv.setTextAppearance(this, android.R.style.TextAppearance_Large);
			tv.setText(Integer.toString(orderNo++));
			tv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			tv.setGravity(Gravity.LEFT);
			tr.addView(tv);
			
			tv = new TextView(this);
			tv.setTextAppearance(this, android.R.style.TextAppearance_Large);
			tv.setText(child.getName());
			tv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			tv.setGravity(Gravity.LEFT);
			tv.setBackgroundColor(child.getBColour());
			tr.addView(tv);
*/
			
			tl.addView(tr, new TableLayout.LayoutParams(
					TableLayout.LayoutParams.MATCH_PARENT,
					TableLayout.LayoutParams.WRAP_CONTENT));
		}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.order, menu);
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
		return super.onOptionsItemSelected(item);
	}
}
