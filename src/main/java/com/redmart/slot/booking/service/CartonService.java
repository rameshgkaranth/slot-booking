/**
 * 
 */
package com.redmart.slot.booking.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.redmart.slot.booking.model.Carton;
import com.redmart.slot.booking.model.Item;
import com.redmart.slot.booking.model.Order;

/**
 * @author rkaranth
 *
 */
@Service
public class CartonService implements ICartonService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CartonService.class);

	/**
	 * 
	 */
	@Override
	public boolean canCartonAccomodateOrder(Order order, Carton carton) {
		double volumeOfAllItems = order.getVolumeOfOrder();
		double availableCapacityOfCarton = carton.getAvailableCapacityOfCarton();
		LOGGER.info("Available capacity of Carton #: "+carton.getCartonId()+" is "+availableCapacityOfCarton);
		LOGGER.info("Required capacity for Order is :"+volumeOfAllItems);
		
		if (availableCapacityOfCarton >= volumeOfAllItems) {
			return true;
		}
		
		return false;
	}

	/**
	 * 
	 */
	@Override
	public Carton addOrderToCarton(Order order, Carton carton) {
		Carton orderFullFillingCarton = null;
		double volumeOfAllItems = order.getVolumeOfOrder();
		if (canCartonAccomodateOrder(order, carton)) {
			carton.updateCartonCapacityAfterAddingItem(volumeOfAllItems);
			orderFullFillingCarton = carton;
		}
		
		return orderFullFillingCarton;
	}


	@Override
	public boolean canCartonAccomodateItem(Item item, Carton carton) {
		boolean isItemAddedToCarton = false;
		double volumeOfItem = item.getVoulmeOfItem();
		double availableCapacityOfCarton = carton.getAvailableCapacityOfCarton();
		LOGGER.info("Available capacity of Carton #: "+carton.getCartonId()+availableCapacityOfCarton);
		LOGGER.info("Required capacity for Item #: "+item.getItemId()+" is :"+volumeOfItem);
		
		if (availableCapacityOfCarton >= volumeOfItem) {
			isItemAddedToCarton = true;
		}
		
		return isItemAddedToCarton;
	}

	@Override
	public Carton addItemToCarton(Item item, Carton carton) {
		Carton orderFullFillingCarton = null;
		double volumeOfItem = item.getVoulmeOfItem();
		if (canCartonAccomodateItem(item, carton)) {
			carton.updateCartonCapacityAfterAddingItem(volumeOfItem);
			orderFullFillingCarton = carton;
		}
		
		return orderFullFillingCarton;
	}

}
