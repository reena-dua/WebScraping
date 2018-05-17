package com.app.webscraper.beans;

import java.util.List;

public class Results {
	private double total;
	private List<ProductDescription> productDetail;
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public List<ProductDescription> getResults() {
		return productDetail;
	}
	public void setResults(List<ProductDescription> results) {
		this.productDetail = results;
	}
	@Override
	public String toString() {
		return "Product [total=" + total + ", results=" + productDetail + "]";
	}
	
}
