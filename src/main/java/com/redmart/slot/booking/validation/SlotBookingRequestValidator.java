/**
 * 
 */
package com.redmart.slot.booking.validation;

import org.joda.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
@Component
public class SlotBookingRequestValidator {
	
	@Autowired
	private SlotBookingConfig slotBookingConfig;

	public void validateSlotBookingRequest(SlotBookingRequest slotBookingRequest) throws InvalidRequestException{
		Order order = slotBookingRequest.getOrder();
		SlotVO slotVO = slotBookingRequest.getSlot();
		LocalTime now = LocalTime.now();
		
		if (null == order || null == order.getItems() ||
				order.getItems().size() == 0) {
			throw new InvalidRequestException("Invalid order present in request");
		}
		
		for (Item item : order.getItems()) {
			if (item.getBreadth() < 0.0 || item.getHeight() < 0.0 || item.getWidth() < 0.0) {
				throw new InvalidRequestException("Invalid item dimensions present in request");
			}
		}
		
		if (null == slotVO || slotVO.getEndHour() > slotBookingConfig.getEndHour() ||
				slotVO.getStartHour() < slotBookingConfig.getStartHour() ||
				slotVO.getStartHour() < now.getHourOfDay() ||
				(slotBookingConfig.getInactiveStartHour() >= slotVO.getStartHour()  && 
					slotVO.getStartHour() < slotBookingConfig.getInactiveEndHour()) ||
				slotVO.getEndHour() != slotVO.getStartHour() + slotBookingConfig.getSlotDuration()) {
			throw new InvalidRequestException("Invalid slot timings present in request");
		}
	}
}
