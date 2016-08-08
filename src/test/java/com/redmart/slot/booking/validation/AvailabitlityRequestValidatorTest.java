/**
 * 
 */
package com.redmart.slot.booking.validation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.redmart.slot.booking.exception.InvalidRequestException;
import com.redmart.slot.booking.model.Item;
import com.redmart.slot.booking.model.Order;

/**
 * @author rkaranth
 *
 */
public class AvailabitlityRequestValidatorTest {
	
	AvailabilityRequestValidator availabilityRequestValidator;
	
	@Before
	public void setUp() {
		availabilityRequestValidator = new AvailabilityRequestValidator();
	}

	@Test(expected = InvalidRequestException.class)
	public void availabilityRequestWithNullOrderShouldThrowException() throws InvalidRequestException {
		Order order = null;
		availabilityRequestValidator.validateAvailabilityRequest(order);
	}
	
	@Test(expected = InvalidRequestException.class)
	public void availabilityRequestWithEmptyItemsShouldThrowException() throws InvalidRequestException {
		Order order = new Order(123, null);
		availabilityRequestValidator.validateAvailabilityRequest(order);;
		
		List<Item> itemList = Collections.emptyList();
		order = new Order(234, itemList);
		availabilityRequestValidator.validateAvailabilityRequest(order);
	}
	
	@Test(expected = InvalidRequestException.class)
	public void availabilityRequestWithInvalidItemDimensionsShouldThrowException() throws InvalidRequestException {
		Item item1 = new Item(111, -11, 34.0, 0);
		Item item2 = new Item(125, 0, 0, 0);
		
		List<Item> itemList = new ArrayList<>();
		itemList.add(item1);
		itemList.add(item2);
		
		Order order = new Order(123, itemList);
		
		availabilityRequestValidator.validateAvailabilityRequest(order);
	}
}
