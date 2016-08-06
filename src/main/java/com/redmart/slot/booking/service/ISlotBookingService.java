/**
 * 
 */
package com.redmart.slot.booking.service;

import java.util.List;

import com.redmart.slot.booking.model.Order;
import com.redmart.slot.booking.model.OrderShipmentInfo;
import com.redmart.slot.booking.model.Slot;
import com.redmart.slot.booking.vo.SlotVO;

/**
 * @author rkaranth
 *
 */
public interface ISlotBookingService {

	OrderShipmentInfo bookSlotForOrder(Slot slot, Order order) throws Exception;
	
	Slot getSlotWithStartAndEndTime(SlotVO slotVO) throws Exception;
	
	List<Slot> getAvaialableSlotsForOrder(Order order) throws Exception;
}
