package com.example.mefirst;

public class Child {
   private String Name;
   private int FColour;
   private int BColour;

public String getName() {
	return Name;
}

public void setName(String name) {
	Name = name;
}

   public Child(String name){
	   setName(name);
	   setFColour(android.R.color.black);
	   setBColour(android.R.color.white);
   }
   
   public Child(String name, int fColour, int bColour) {
	   this(name);
	   setFColour(fColour);
	   setBColour(bColour);
   }

public int getBColour() {
	return BColour;
}

public void setBColour(int bColour) {
	BColour = bColour;
}

public int getFColour() {
	return FColour;
}

public void setFColour(int fColour) {
	FColour = fColour;
}
}
