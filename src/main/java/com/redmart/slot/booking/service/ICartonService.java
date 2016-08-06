/**
 * 
 */
package com.redmart.slot.booking.service;

import com.redmart.slot.booking.model.Carton;
import com.redmart.slot.booking.model.Item;
import com.redmart.slot.booking.model.Order;

/**
 * @author rkaranth
 *
 */
public interface ICartonService {

	boolean canCartonAccomodateOrder(Order order, Carton carton);
	Carton addOrderToCarton(Order order, Carton carton);
	boolean canCartonAccomodateItem(Item item, Carton carton);
	Carton addItemToCarton(Item item, Carton carton);
}
