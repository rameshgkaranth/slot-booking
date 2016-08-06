/**
 * 
 */
package com.redmart.slot.booking.service;

import java.util.Map;

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
public interface IOrderShipmentInfoService {

	OrderShipmentInfo addToOrderShipmentInfo(Order order, Slot slot, Van van, Map<Item, Carton> itemCartonMap);
}
