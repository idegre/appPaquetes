package com.mycompany.apppaquetes;

import java.util.Random;

public interface WithID {
	public static int IDGen() {
		Random rand = new Random();
        return 999 + rand.nextInt((99999 - 999) + 1);
	}
	
	public abstract boolean equals(int ID);
}
