package com.ourownjava.spring.data.mongo.pagination;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;


/**
 * 
 * @author ourownjava.com
 *
 */
public interface ProductRepository extends PagingAndSortingRepository<Product, String> {
	
	
	public static class PageSpecification {
		/**
		 * 
		 * @param pageIndex
		 * @return
		 */
		public static Pageable constructPageSpecification(final int pageIndex, final int pageSize) {
			Pageable pageSpecification = new PageRequest(pageIndex, pageSize);
			return pageSpecification;
		}

	}
	
}
