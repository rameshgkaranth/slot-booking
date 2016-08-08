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
 * Service class that provides methods to add Order/Item(s) to
 * Carton in Van. Also provides method to check if such addition is 
 * possible
 * 
 * @author rkaranth
 *
 */
@Service
public class CartonService implements ICartonService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CartonService.class);

	/**
	 * Check if Order can be accommodated in Carton
	 * 
	 * @param order
	 * @param carton
	 * @return
	 * 		true if Order can be accommodated in Carton, else false
	 */
	@Override
	public boolean canCartonAccomodateOrder(Order order, Carton carton) {
		synchronized (carton) {
			double volumeOfAllItems = order.getVolumeOfOrder();
			double availableCapacityOfCarton = carton.getAvailableCapacityOfCarton();
			LOGGER.info("Available capacity of Carton #: "+carton.getCartonId()+" is "+availableCapacityOfCarton);
			LOGGER.info("Required capacity for Order is :"+volumeOfAllItems);
			
			if (availableCapacityOfCarton >= volumeOfAllItems) {
				return true;
			}
			
			return false;
		}
	}

	/**
	 * Method to add Order to Carton
	 * 
	 * @param order
	 * @param carton
	 * @return
	 * 		Carton that accommodated the Order
	 */
	@Override
	public Carton addOrderToCarton(Order order, Carton carton) {
		Carton orderFullFillingCarton = null;
		
		synchronized (carton) {
			double volumeOfAllItems = order.getVolumeOfOrder();
			if (canCartonAccomodateOrder(order, carton)) {
				carton.updateCartonCapacityAfterAddingItem(volumeOfAllItems);
				orderFullFillingCarton = carton;
			}
		}
		
		return orderFullFillingCarton;
	}

	/**
	 * Method to check if Item can be added to Carton
	 * 
	 * @param item
	 * @param carton
	 * @return
	 * 		true if Item can be accommodated in Carton, else false
	 */
	@Override
	public boolean canCartonAccomodateItem(Item item, Carton carton) {
		boolean isItemAddedToCarton = false;
		double volumeOfItem = item.getVoulmeOfItem();
		
		synchronized (carton) {
			double availableCapacityOfCarton = carton.getAvailableCapacityOfCarton();
			LOGGER.info("Available capacity of Carton #: "+carton.getCartonId()+availableCapacityOfCarton);
			LOGGER.info("Required capacity for Item #: "+item.getItemId()+" is :"+volumeOfItem);
			
			if (availableCapacityOfCarton >= volumeOfItem) {
				isItemAddedToCarton = true;
			}
		}
		
		return isItemAddedToCarton;
	}

	/**
	 * Method to add Item to Carton
	 * 
	 * @param item
	 * @param carton
	 * @return
	 * 		Carton that accommodated the Item
	 */
	@Override
	public Carton addItemToCarton(Item item, Carton carton) {
		Carton orderFullFillingCarton = null;
		double volumeOfItem = item.getVoulmeOfItem();
		
		synchronized (carton) {
			if (canCartonAccomodateItem(item, carton)) {
				carton.updateCartonCapacityAfterAddingItem(volumeOfItem);
				orderFullFillingCarton = carton;
			}
		}
		
		return orderFullFillingCarton;
	}
}
