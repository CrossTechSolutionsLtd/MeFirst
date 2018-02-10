package com.example.mefirst;

import java.util.ArrayList;
import java.util.Iterator;

import android.graphics.Color;

public  class ChildManager  {
	    private static ChildManager ChildManager = null;
	    private ArrayList<Child> Children = null;
	    
		private ChildManager() {
			Children = new ArrayList<Child>();
		}
		
		public static ChildManager Get() {
		   if (ChildManager == null) {
			   ChildManager = new ChildManager();
			   ChildManager.LoadChildren();
		   }
		   return ChildManager;
		}
		
		private void LoadChildren() {
			Children = new ArrayList<Child>();
			Child newChild;
			
			Color c = new Color();

			
			newChild = new Child("Emily");
			newChild.setBColour(c.rgb(0, 200, 0));
			Children.add(newChild);
			newChild = new Child("William");
			newChild.setBColour(c.rgb(0, 0, 200));
			Children.add(newChild);
			newChild = new Child("Henry");
			newChild.setBColour(c.rgb(0, 128, 255));
			Children.add(newChild);
			newChild = new Child("Katie");
			newChild.setBColour(c.rgb(230, 230, 0));
			Children.add(newChild);
			newChild = new Child("Lucy");
			newChild.setBColour(c.rgb(0, 128, 0));
			Children.add(newChild);
			newChild = new Child("Daisy");
			newChild.setBColour(c.rgb(0, 200, 255));
			Children.add(newChild);
			newChild = new Child("Daddy");
			newChild.setBColour(c.rgb(255, 0, 0));
			Children.add(newChild);
		}
		
		public Child FindChild(String name) {
			for (Child child : Children) {
				if (child.getName().equals(name)) {
					return child;
				}
			}
			return null;
		}

        public Iterator<Child> GetChildIterator() {
        	    return Children.iterator();
        }
}
