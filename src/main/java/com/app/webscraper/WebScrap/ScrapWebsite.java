package com.app.webscraper.WebScrap;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.app.webscraper.beans.Results;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.app.webscraper.beans.ProductDescription;

public class ScrapWebsite {

	public static void main( String[] args )
	{	
		Results productResult = new Results();
		List<ProductDescription> results = new ArrayList<ProductDescription>();
		Double total= 0.0;
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		try{

			String url = "https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html";
			Document productPage = Jsoup.connect(url).get();
			Elements products = productPage.getElementsByClass("gridItem");
			for (Element product : products) {
				ProductDescription description = new ProductDescription();
				description.setTitle(product.getElementsByTag("a").text());
				description.setUnit_price(Double.parseDouble(product.getElementsByClass("pricePerUnit").text().substring(1,5)));
				total = total + Double.parseDouble(product.getElementsByClass("pricePerUnit").text().substring(1,5));

				Document productDescriptionPage = Jsoup.connect(product.getElementsByTag("a").attr("abs:href")).get();
				description.setDescription(productDescriptionPage.getElementsByClass("productText").first().text());

				Elements productDetails = productDescriptionPage.getElementsContainingOwnText("kcal");
				for (Element productDetail : productDetails) {
					if(productDetail.text().indexOf("/") == -1 && checkNumeric(productDetail.text().split("kcal")[0])) {
						description.setKcal_per_100g(Integer.parseInt(productDetail.text().split("kcal")[0]));
					}
				}
				results.add(description);
			}

			productResult.setTotal(total);
			productResult.setResults(results);
			System.out.println(mapper.writeValueAsString(productResult));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Boolean checkNumeric(String number) {
		boolean numeric = true;

		try {
			Double.parseDouble(number);
		} catch (NumberFormatException e) {
			numeric = false;
		}
		return numeric;
	}

}
