package com.mycompany.mapa;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import Paquetes.HojaDeRuta;

public class Mapa {
	public static HojaDeRuta obtenerRuta(Poblacion origen, Poblacion destino) {
		Random rand = new Random();
		Date now = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(now);
		c.add(Calendar.HOUR, rand.nextInt(100));
		Date salida = c.getTime();
		c.add(Calendar.HOUR, rand.nextInt(10));
		Date llegada = c.getTime();
		return new HojaDeRuta(salida, llegada, origen, destino);
	}
}
