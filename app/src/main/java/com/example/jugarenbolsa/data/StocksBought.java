package com.example.jugarenbolsa.data;

public class StocksBought {
	public int total = 0;
	public double spent = 0;	// Total spent in this Accion
	public Accion stock;
	// TODO array for each accion bought: spent >>> on sell each accion check if wins or not

	public StocksBought(int total, double spent, Accion stock) {
		this.total = total;
		this.spent = spent;
		this.stock = stock;
	}
}
