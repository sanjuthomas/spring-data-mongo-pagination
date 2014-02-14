package com.ourownjava.spring.data.mongo.pagination;

import static com.lordofthejars.nosqlunit.mongodb.MongoDbRule.MongoDbRuleBuilder.newMongoDbRule;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.foursquare.fongo.Fongo;
import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import com.lordofthejars.nosqlunit.mongodb.MongoDbRule;
import com.mongodb.Mongo;

/**
 * @author ourownjava.com
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class TestProductRepository {

	private static final int NUMBER_OF_PRODUCTS_PER_PAGE = 2;

	@Rule
	public MongoDbRule mongoDbRule = newMongoDbRule().defaultSpringMongoDb(
			"pagination-test");

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private ProductRepository productRepository;

	@Test
	@UsingDataSet(locations = { "/testData/product.json" })
	public void shouldLoadPageByPage() {
		Page<Product> products = productRepository
				.findAll(ProductRepository.PageSpecification
						.constructPageSpecification(0,
								NUMBER_OF_PRODUCTS_PER_PAGE));
		assertEquals(2, products.getSize());
		assertFalse(products.hasPreviousPage());
		assertTrue(products.hasNextPage());
		assertEquals(3, products.getTotalPages());
		assertEquals(0, products.getNumber());
		assertEquals(2, products.getNumberOfElements());
		assertEquals(6, products.getTotalElements());
		products = productRepository
				.findAll(ProductRepository.PageSpecification
						.constructPageSpecification(1, NUMBER_OF_PRODUCTS_PER_PAGE));
		assertEquals(2, products.getSize());
		assertTrue(products.hasPreviousPage());
		assertTrue(products.hasNextPage());
		assertEquals(3, products.getTotalPages());
		assertEquals(1, products.getNumber());
		assertEquals(2, products.getNumberOfElements());
		assertEquals(6, products.getTotalElements());
		products = productRepository
				.findAll(ProductRepository.PageSpecification
						.constructPageSpecification(2, NUMBER_OF_PRODUCTS_PER_PAGE));
		assertEquals(2, products.getSize());
		assertTrue(products.hasPreviousPage());
		assertFalse(products.hasNextPage());
		assertEquals(3, products.getTotalPages());
		assertEquals(2, products.getNumber());
		assertEquals(2, products.getNumberOfElements());
		assertEquals(6, products.getTotalElements());
	}
	
	@Test
	@UsingDataSet(locations = { "/testData/product.json" })
	public void shouldLoadProductsAndValidateData(){
		
		Page<Product> products = productRepository
				.findAll(ProductRepository.PageSpecification
						.constructPageSpecification(0,
								NUMBER_OF_PRODUCTS_PER_PAGE));
		assertEquals(2, products.getSize());
		assertFalse(products.hasPreviousPage());
		assertTrue(products.hasNextPage());
		assertEquals(3, products.getTotalPages());
		assertEquals(0, products.getNumber());
		assertEquals(2, products.getNumberOfElements());
		assertEquals(6, products.getTotalElements());
		
		assertEquals("p1", products.getContent().get(0).getId());
		assertEquals("pname1", products.getContent().get(0).getName());
		assertEquals(Double.valueOf(11.11), products.getContent().get(0).getPrice());
		
		assertEquals("p2", products.getContent().get(1).getId());
		assertEquals("pname2", products.getContent().get(1).getName());
		assertEquals(Double.valueOf(12.12), products.getContent().get(1).getPrice());
	}

	@Configuration
	@EnableMongoRepositories
	@ComponentScan(basePackageClasses = { ProductRepository.class })
	@PropertySource("classpath:application.properties")
	static class MongoConfiguration extends AbstractMongoConfiguration {

		@Override
		protected String getDatabaseName() {
			return "pagination-test";
		}

		@Override
		public Mongo mongo() {
			return new Fongo("mongo-test").getMongo();
		}

		@Override
		protected String getMappingBasePackage() {
			return "com.ourownjava.spring.data.pagination.*";
		}
	}

}
