/**
 * 
 */
package com.redmart.slot.booking.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.redmart.slot.booking.exception.VanCapacityOutOfBoundsException;
import com.redmart.slot.booking.model.Carton;
import com.redmart.slot.booking.model.Item;
import com.redmart.slot.booking.model.Order;
import com.redmart.slot.booking.model.Van;


/**
 * @author rkaranth
 *
 */
public class VanServiceTest {

	private IVanService vanService;
	private ICartonService cartonService;
	private Van van;
	private Carton carton1;
	private Carton carton2;
	
	@Before
	public void setUp() {
		vanService = new VanService();
		
		carton1 = new Carton(1, 10.0, 10.0, 10.0);
		carton2 = new Carton(2, 10.0, 10.0, 10.0);
		List<Carton> cartons = new ArrayList<>();
		cartons.add(carton1);
		cartons.add(carton2);
		
		cartonService = EasyMock.createMock(CartonService.class);
		ReflectionTestUtils.setField(vanService, "cartonService", cartonService);
		
		
		van = new Van(1, cartons);
	}
	
	@Test
	public void canAddOrderToVanIfItsCartonsHaveAvailableCapacity() throws Exception {
		Item item1 = new Item(121, 5.0, 5.0, 5.0);
		Item item2 = new Item(123, 6.0, 5.5, 5.0);
		List<Item> items = new ArrayList<>();
		items.add(item1);
		items.add(item2);
		
		Order order = new Order(123, items);
		
		EasyMock.expect(cartonService.addOrderToCarton(EasyMock.isA(Order.class), 
				EasyMock.isA(Carton.class))).andReturn(carton1);
		EasyMock.replay(cartonService);
		
		Map<Item, Carton> itemCartonMap = vanService.addOrderToVan(order, van);
		Assert.assertNotNull(itemCartonMap);
	}
	
	@Test(expected = VanCapacityOutOfBoundsException.class)
	public void throwsExceptionIfVanCannotAccommodateOrder() throws Exception {
		Item item1 = new Item(121, 12.0, 15.0, 15.0);
		Item item2 = new Item(123, 10.0, 10.5, 15.0);
		List<Item> items = new ArrayList<>();
		items.add(item1);
		items.add(item2);
		
		Order order = new Order(123, items);
		
		EasyMock.expect(cartonService.addOrderToCarton(EasyMock.isA(Order.class), 
				EasyMock.isA(Carton.class))).andReturn(null).times(items.size());
		
		EasyMock.expect(cartonService.addItemToCarton(EasyMock.isA(Item.class), 
				EasyMock.isA(Carton.class))).andReturn(null).times(items.size());
		
		EasyMock.replay(cartonService);
		
		Map<Item, Carton> itemCartonMap = vanService.addOrderToVan(order, van);
		Assert.assertNull(itemCartonMap);
	}
}
