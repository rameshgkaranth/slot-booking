/**
 * 
 */
package com.redmart.slot.booking.service;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.redmart.slot.booking.model.Carton;
import com.redmart.slot.booking.model.Item;
import com.redmart.slot.booking.model.Order;

/**
 * @author rkaranth
 *
 */
public class CartonServiceTest {

	ICartonService cartonService;
	
	@Before
	public void setUp() {
		cartonService = new CartonService();
	}
	
	@Test
	public void cartonCanAccommodateOrderIfItsVolumeIsWithinItsAvailableCapacity() {
		Carton carton = EasyMock.createMock(Carton.class);
		EasyMock.expect(carton.getCartonId()).andReturn(1);
		EasyMock.expect(carton.getAvailableCapacityOfCarton()).andReturn(1500.0);
		EasyMock.replay(carton);
		
		Item item1 = new Item(121, 5.0, 10.0, 5.0);
		Item item2 = new Item(123, 6.0, 5.5, 5.0);
		List<Item> items = new ArrayList<>();
		items.add(item1);
		items.add(item2);
		
		Order order = new Order(123, items);
		
		Assert.assertTrue(cartonService.canCartonAccomodateOrder(order, carton));
	}
	
	@Test
	public void cartonCannotAccommodatedOversizedOrders() {
		Carton carton = EasyMock.createMock(Carton.class);
		EasyMock.expect(carton.getCartonId()).andReturn(1);
		EasyMock.expect(carton.getAvailableCapacityOfCarton()).andReturn(500.0);
		EasyMock.replay(carton);
		
		Item item1 = new Item(121, 15.0, 10.0, 5.0);
		Item item2 = new Item(123, 6.0, 5.5, 5.0);
		List<Item> items = new ArrayList<>();
		items.add(item1);
		items.add(item2);
		
		Order order = new Order(123, items);
		
		Assert.assertFalse(cartonService.canCartonAccomodateOrder(order, carton));
	}
	
	@Test
	public void canAddOrderToCartonIfItsVolumeIsWithinItsAvailableCapacity() {
		Item item1 = new Item(121, 15.0, 10.0, 5.0);
		Item item2 = new Item(123, 6.0, 5.5, 5.0);
		List<Item> items = new ArrayList<>();
		items.add(item1);
		items.add(item2);
		
		Order order = new Order(123, items);
		Carton carton = new Carton(1, 10.0, 10.0, 10.0);
		
		Carton updatedCarton = cartonService.addOrderToCarton(order, carton);
		Assert.assertNotNull(updatedCarton);
		Assert.assertEquals(85.0, updatedCarton.getAvailableCapacityOfCarton(), 0.0);
	}
	
	@Test
	public void failToAddOrderIfSizeExceedsCartonAvailableCapacity() {
		Item item1 = new Item(121, 15.0, 10.0, 5.0);
		Item item2 = new Item(123, 6.0, 5.5, 5.0);
		List<Item> items = new ArrayList<>();
		items.add(item1);
		items.add(item2);
		
		Order order = new Order(123, items);
		
		Carton carton = new Carton(1, 10.0, 5.0, 10.0);
		
		Carton updatedCarton = cartonService.addOrderToCarton(order, carton);
		Assert.assertNull(updatedCarton);
	}
	
	@Test
	public void canAddItemIfItsVolumeIsWithinItsAvailableCapacity() {
		Item item1 = new Item(121, 5.0, 10.0, 5.0);
		Carton carton = new Carton(1, 10.0, 5.0, 10.0);
		
		Carton updatedCarton = cartonService.addItemToCarton(item1, carton);
		Assert.assertNotNull(updatedCarton);
		Assert.assertEquals(250.0, updatedCarton.getAvailableCapacityOfCarton(), 0.0);
	}
	
	@Test
	public void failToAddItemIfSizeExceedsCartonAvailableCapacity() {
		Item item1 = new Item(121, 5.0, 10.0, 5.0);
		
		Carton carton = new Carton(1, 10.0, 10.0, 10.0);
		Assert.assertTrue(cartonService.canCartonAccomodateItem(item1, carton));
	}
}
