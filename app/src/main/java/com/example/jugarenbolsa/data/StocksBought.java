package com.example.jugarenbolsa.data;

public class StocksBought {
	public int total = 0;
	public double spent = 0;
	public Accion stock;

	public StocksBought(int total, double spent, Accion stock) {
		this.total = total;
		this.spent = spent;
		this.stock = stock;
	}
}
