/**
 * 
 */
package com.redmart.slot.booking.service;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.joda.time.LocalTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.test.util.ReflectionTestUtils;

import com.redmart.slot.booking.exception.SlotCapacityOutOfBoundsException;
import com.redmart.slot.booking.model.Carton;
import com.redmart.slot.booking.model.Item;
import com.redmart.slot.booking.model.Order;
import com.redmart.slot.booking.model.ShippingSlots;
import com.redmart.slot.booking.model.Slot;
import com.redmart.slot.booking.model.Van;


/**
 * @author rkaranth
 *
 */
public class SlotBookingServiceTest {

	ISlotBookingService slotBookingService;
	
	IVanService vanService;
	
	IOrderShipmentInfoService orderShipmentInfoService;
	
	ShippingSlots shippingSlots;
	
	@Rule
    public ExpectedException exception = ExpectedException.none();
	
	@Before
	public void setUp() {
		slotBookingService = new SlotBookingService();
		vanService = EasyMock.createMock(VanService.class);
		orderShipmentInfoService = EasyMock.createMock(OrderShipmentInfoService.class);
		shippingSlots = new ShippingSlots();
		
		ReflectionTestUtils.setField(slotBookingService, "vanService", vanService);
		ReflectionTestUtils.setField(slotBookingService, "orderShipmentInfoService", orderShipmentInfoService);
		ReflectionTestUtils.setField(shippingSlots, "slots", populateSlots());
		ReflectionTestUtils.setField(slotBookingService, "shippingSlots", shippingSlots);
	}
	
	@Test
	public void slotsAreAvailableForAnOrderThatCanBeAccommodated() throws Exception {
		Item item1 = new Item(121, 5.0, 5.0, 5.0);
		Item item2 = new Item(123, 6.0, 5.5, 5.0);
		List<Item> items = new ArrayList<>();
		items.add(item1);
		items.add(item2);
		
		Order order = new Order(123, items);
		LocalTime now = LocalTime.now();
		
		EasyMock.expect(vanService.canAddOrderToVan(EasyMock.isA(Order.class), EasyMock.isA(Van.class))).
			andReturn(true).times(1);
		EasyMock.replay();
		
		if (now.getHourOfDay() > 18 || now.getHourOfDay() < 9) {
			exception.expect(SlotCapacityOutOfBoundsException.class);
		}
		
		List<Slot> availableSlots = slotBookingService.getAvaialableSlotsForOrder(order);
		Assert.assertNotNull(availableSlots);
	}
	
	@Test(expected = SlotCapacityOutOfBoundsException.class)
	public void slotsAreAvailableForAnOrderThatCannotBeAccommodated() throws Exception {
		Item item1 = new Item(121, 5.0, 5.0, 5.0);
		Item item2 = new Item(123, 6.0, 5.5, 5.0);
		List<Item> items = new ArrayList<>();
		items.add(item1);
		items.add(item2);
		
		Order order = new Order(123, items);
		
		EasyMock.expect(vanService.canAddOrderToVan(EasyMock.isA(Order.class), EasyMock.isA(Van.class))).
			andReturn(false).times(8);
		EasyMock.replay();
		
		slotBookingService.getAvaialableSlotsForOrder(order);
	}
	
	private List<Slot> populateSlots() {
		List<Slot> slots = new ArrayList<>();
		Slot slot1 = new Slot(1, 9, 11, populateVans());
		Slot slot2 = new Slot(2, 11, 13, populateVans());
		Slot slot3 = new Slot(3, 14, 16, populateVans());
		Slot slot4 = new Slot(4, 16, 18, populateVans());
		slots.add(slot1);
		slots.add(slot2);
		slots.add(slot3);
		slots.add(slot4);
		
		return slots;
	}
	
	private List<Van> populateVans() {
		List<Van> vans = new ArrayList<>();
		
		for (int i = 1; i <= 2; i++){
			Van van = new Van(i, populateCartons());
			vans.add(van);
		}
		
		return vans;
	}
	
	private List<Carton> populateCartons() {
		List<Carton> cartons = new ArrayList<>();
		
		for (int i = 1; i <= 4; i++) {
			Carton carton = new Carton(i, 10, 10, 10);
			cartons.add(carton);
		}
		
		return cartons;
	}
}
