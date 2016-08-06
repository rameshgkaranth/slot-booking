/**
 * 
 */
package com.redmart.slot.booking.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redmart.slot.booking.exception.SlotCapacityOutOfBoundsException;
import com.redmart.slot.booking.exception.VanCapacityOutOfBoundsException;
import com.redmart.slot.booking.model.Carton;
import com.redmart.slot.booking.model.Item;
import com.redmart.slot.booking.model.Order;
import com.redmart.slot.booking.model.OrderShipmentInfo;
import com.redmart.slot.booking.model.ShippingSlots;
import com.redmart.slot.booking.model.Slot;
import com.redmart.slot.booking.model.Van;
import com.redmart.slot.booking.vo.SlotVO;

/**
 * @author rkaranth
 *
 */
@Service
public class SlotBookingService implements ISlotBookingService {
	
	@Autowired
	private ShippingSlots shippingSlots;
	
	@Autowired
	private IVanService vanService;
	
	@Autowired
	private IOrderShipmentInfoService orderShipmentInfoService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SlotBookingService.class);
	
	@Override
	public OrderShipmentInfo bookSlotForOrder(Slot slot, Order order) throws Exception {
		Map<Item, Carton> itemCartonMap = null;
		OrderShipmentInfo orderShipmentInfo = null;
		
		for (Van van : slot.getVans()) {
			try {
				LOGGER.info("Checking capacity of Van #"+van.getVanId());
				itemCartonMap = vanService.addOrderToVan(order, van);
				orderShipmentInfo = orderShipmentInfoService.addToOrderShipmentInfo(order, slot, van, itemCartonMap);
				break;
			} catch (VanCapacityOutOfBoundsException e) {
				LOGGER.info("Order #:"+order.getOrderId()+" could not be accomodated in Van :"+van.getVanId());
			}
		}
		
		return orderShipmentInfo;
	}

	@Override
	public Slot getSlotWithStartAndEndTime(SlotVO slotVO) throws Exception {
		Slot requiredSlot = null;
		for (Slot slot : shippingSlots.getSlots()) {
			if (slot.getStartHour() == slotVO.getStartHour() && 
					slot.getEndHour() == slotVO.getEndHour()) {
				requiredSlot = slot;
				break;
			}
				
		}
		
		return requiredSlot;
	}

	@Override
	public List<Slot> getAvaialableSlotsForOrder(Order order) throws Exception {
		LocalTime now = LocalTime.now();
		List<Slot> availableSlots = new ArrayList<>();
		
		for (Slot slot : shippingSlots.getSlots()) {
			if (slot.getStartHour() >= now.getHourOfDay()) {
				
				for (Van van : slot.getVans()) {
					if (vanService.canAddOrderToVan(order, van)) {
						availableSlots.add(slot);
						break;		//Even if 1 of the vans in slot can accommodate, we are good
					}
				}
			}
		}
		
		if (availableSlots.size() == 0) {
			throw new SlotCapacityOutOfBoundsException("Order #:"+order.getOrderId()+" has not avaialble slots to be accommodated");
		}
		
		return availableSlots;
	}

	
}
