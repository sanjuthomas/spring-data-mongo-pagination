package com.ourownjava.spring.data.mongo.pagination;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 
 * @author ourownjava.com
 *
 */
@Document(collection="product")
public class Product {
	
	@Id
	private String id;
	private String name;
	private Double price;
	
	public String getId() {
		return id;
	}
	public void setId(final String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(final String name) {
		this.name = name;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(final Double price) {
		this.price = price;
	}
}
