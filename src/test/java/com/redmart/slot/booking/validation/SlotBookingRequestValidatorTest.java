/**
 * 
 */
package com.redmart.slot.booking.validation;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.joda.time.LocalTime;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.redmart.slot.booking.exception.InvalidRequestException;
import com.redmart.slot.booking.model.Item;
import com.redmart.slot.booking.model.Order;
import com.redmart.slot.booking.model.SlotBookingConfig;
import com.redmart.slot.booking.vo.SlotBookingRequest;
import com.redmart.slot.booking.vo.SlotVO;

/**
 * @author rkaranth
 *
 */
public class SlotBookingRequestValidatorTest {

	SlotBookingRequestValidator validator;
	SlotBookingConfig slotBookingConfig;
	
	@Before
	public void setUp() {
		slotBookingConfig = EasyMock.createMock(SlotBookingConfig.class);
		EasyMock.expect(slotBookingConfig.getStartHour()).andReturn(9);
		EasyMock.expect(slotBookingConfig.getEndHour()).andReturn(18);
		EasyMock.expect(slotBookingConfig.getInactiveStartHour()).andReturn(13);
		EasyMock.expect(slotBookingConfig.getInactiveEndHour()).andReturn(14);
		EasyMock.expect(slotBookingConfig.getSlotDuration()).andReturn(2);
		EasyMock.replay(slotBookingConfig);
		
		validator = new SlotBookingRequestValidator();
		ReflectionTestUtils.setField(validator, "slotBookingConfig", slotBookingConfig);
	}
	
	@Test(expected = InvalidRequestException.class)
	public void slotBookingRequestWithNullOrderShouldThrowException() throws InvalidRequestException {
		Order order = null;
		SlotVO slot = new SlotVO(9, 11);
		SlotBookingRequest slotBookingRequest = new SlotBookingRequest(order, slot);
		validator.validateSlotBookingRequest(slotBookingRequest);
	}
	
	@Test(expected = InvalidRequestException.class)
	public void slotBookingRequestWithEmptyItemsShouldThrowException() throws InvalidRequestException {
		Order order = new Order(123, null);
		SlotVO slot = new SlotVO(9, 11);
		SlotBookingRequest slotBookingRequest = new SlotBookingRequest(order, slot);
		validator.validateSlotBookingRequest(slotBookingRequest);
		
		Item item1 = new Item(111, 10.0, 20, 10);
		List<Item> items = new ArrayList<>();
		items.add(item1);
		order = new Order(123, items);
		slotBookingRequest = new SlotBookingRequest(order, slot);
		validator.validateSlotBookingRequest(slotBookingRequest);
	}
	
	@Test(expected = InvalidRequestException.class)
	public void slotBookingRequestWithInvaldSlotTimingsShouldThrowException() throws InvalidRequestException {
		Order order = new Order(123, null);
		
		//Slot start time has already passed
		LocalTime now = LocalTime.now();
		int currentHour = now.getHourOfDay();
		SlotVO slot = new SlotVO(currentHour-1, currentHour+2);
		
		SlotBookingRequest slotBookingRequest = new SlotBookingRequest(order, slot);
		validator.validateSlotBookingRequest(slotBookingRequest);
		
		//Slot starts before the actual delivery start time
		slot = new SlotVO(1, 2);
		slotBookingRequest = new SlotBookingRequest(order, slot);
		validator.validateSlotBookingRequest(slotBookingRequest);
		
		//Slot ends after delivery time
		slot = new SlotVO(17, 19);
		slotBookingRequest = new SlotBookingRequest(order, slot);
		validator.validateSlotBookingRequest(slotBookingRequest);
		
		//Slot duration is not correct
		slot = new SlotVO(14, 20);
		slotBookingRequest = new SlotBookingRequest(order, slot);
		validator.validateSlotBookingRequest(slotBookingRequest);
	}
}
