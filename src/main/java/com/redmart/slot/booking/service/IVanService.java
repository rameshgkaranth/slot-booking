/**
 * 
 */
package com.redmart.slot.booking.service;

import java.util.Map;

import com.redmart.slot.booking.exception.VanCapacityOutOfBoundsException;
import com.redmart.slot.booking.model.Carton;
import com.redmart.slot.booking.model.Item;
import com.redmart.slot.booking.model.Order;
import com.redmart.slot.booking.model.Van;

/**
 * @author rkaranth
 *
 */
public interface IVanService {

	Map<Item, Carton> addOrderToVan(Order order, Van van) throws Exception;
	
	boolean canAddOrderToVan(Order order, Van van) throws Exception;
}
