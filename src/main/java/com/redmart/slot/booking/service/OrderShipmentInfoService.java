/**
 * 
 */
package com.redmart.slot.booking.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.redmart.slot.booking.model.Carton;
import com.redmart.slot.booking.model.Item;
import com.redmart.slot.booking.model.Order;
import com.redmart.slot.booking.model.OrderShipmentInfo;
import com.redmart.slot.booking.model.Slot;
import com.redmart.slot.booking.model.Van;

/**
 * @author rkaranth
 *
 */
@Service
public class OrderShipmentInfoService implements IOrderShipmentInfoService {

	private List<OrderShipmentInfo> orderShipmentInfoList;
	
	/**
	 * 
	 */
	@Override
	public com.redmart.slot.booking.model.OrderShipmentInfo addToOrderShipmentInfo(Order order, Slot slot, Van van,
			Map<Item, Carton> itemCartonMap) {
		OrderShipmentInfo orderShipmentInfo = new OrderShipmentInfo(order, slot.getSlotId(), van.getVanId(), itemCartonMap);
		
		if (null == orderShipmentInfoList) {
			orderShipmentInfoList = new ArrayList<>();
		}
		
		orderShipmentInfoList.add(orderShipmentInfo);
		
		return orderShipmentInfo;
	}

}
