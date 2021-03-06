/**
 * 
 */
package com.redmart.slot.booking.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redmart.slot.booking.exception.SlotCapacityOutOfBoundsException;
import com.redmart.slot.booking.exception.VanCapacityOutOfBoundsException;
import com.redmart.slot.booking.model.Carton;
import com.redmart.slot.booking.model.Item;
import com.redmart.slot.booking.model.Order;
import com.redmart.slot.booking.model.Van;

/**
 * Service class that provides methods to add Order/Item(s) 
 * to Carton. Also provides methods to check if such addition is possible
 * 
 * @author rkaranth
 *
 */
@Service
public class VanService implements IVanService {

	@Autowired
	private ICartonService cartonService;
	
	/**
	 * Service method to add Order to Van
	 * 
	 * @param order
	 * @param van
	 * @return 
	 * 		{@code Map<Item, Carton>} 
	 * @throws VanCapacityOutOfBoundsException 
	 * @throws CloneNotSupportedException 
	 * @throws SlotCapacityOutOfBoundsException 
	 */
	@Override
	public Map<Item, Carton> addOrderToVan(Order order, Van van) throws VanCapacityOutOfBoundsException, CloneNotSupportedException, SlotCapacityOutOfBoundsException {
		Map<Item, Carton> itemCartonMap = new HashMap<>();
		Carton carton = null;
		
		//Synchronize based on Van object (equality based on vanId)
		synchronized (van) {
			//Check if all items in Order can accommodated in a single carton of Van
			if ((carton = addOrderToASingleCarton(order, van)) != null) {
				itemCartonMap = prepareItemCartonMap(order.getItems(), carton);
			} else {
				//All Items in Order could not be accommodated in a Single carton
				//Create a clone to check if items can be accommodated in multiple cartons
				Van clonedVan = (Van) van.clone();
				itemCartonMap = addOrderToMultipleCarton(order, clonedVan);
				
				//Check if all items of order are accommodated, else throw exception
				if (null == itemCartonMap || itemCartonMap.keySet().size() != order.getItems().size()) {
					throw new SlotCapacityOutOfBoundsException("Order #:"+order.getOrderId()+" cannot be accomodated in this slot");
				} else {
					//As we are sure that items can be accommodated across multiple cartons, use the original Van
					itemCartonMap = addOrderToMultipleCarton(order, van);
				}
			}
		}
		
		return itemCartonMap;
	}
	
	/**
	 * Service invoked during availability check to determine if an Order can
	 * be accommodated within Slot
	 * 
	 * @param order
	 * @param van
	 * @return
	 * 		true if Order can be accommodated in a Van of a particular Slot, else false
	 * @throws CloneNotSupportedException 
	 * @throws VanCapacityOutOfBoundsException 
	 * @throws SlotCapacityOutOfBoundsException 
	 * 
	 */
	@Override
	public boolean canAddOrderToVan(Order order, Van van) throws CloneNotSupportedException, VanCapacityOutOfBoundsException, SlotCapacityOutOfBoundsException {
		boolean canAddOrderToVan = false;
		
		//Synchronize based on Van object (equality based on vanId)
		synchronized (van) {
			for (Carton carton : van.getCartons()) {
				canAddOrderToVan = cartonService.canCartonAccomodateOrder(order, carton);
				
				//One of the cartons can accommodate the order
				if (canAddOrderToVan) break;
			}
			
			//If all the items cannot be added into a single carton check
			//if items can be separated and added to different Cartons
			if (!canAddOrderToVan) {
				Map<Item, Carton> itemCartonMap = new HashMap<>();
				
				//Create a clone to check if items can be accommodated in multiple cartons
				Van clonedVan = (Van) van.clone();
				itemCartonMap = addOrderToMultipleCarton(order, clonedVan);
				
				if (null == itemCartonMap || itemCartonMap.keySet().size() != order.getItems().size()) {
					throw new SlotCapacityOutOfBoundsException("Order #:"+order.getOrderId()+" cannot be accomodated in this slot");
				}
			}
		}
		
		return canAddOrderToVan;
	}
	
	/**
	 * Method to add Order to a single carton of this Van
	 * 
	 * @param order
	 * @return
	 * 		Carton that accommodated the Order, else null
	 */
	private Carton addOrderToASingleCarton(Order order, Van van) {
		Carton orderFullFillmentCarton = null;
		
		for (Carton carton : van.getCartons()) {
			orderFullFillmentCarton = cartonService.addOrderToCarton(order, carton);
			if (null != orderFullFillmentCarton) {
				break;
			}
		}
		
		return orderFullFillmentCarton;
	}
	
	/**
	 * Method to accommodate Items in Order to multiple Cartons of this Van
	 * 
	 * @param order
	 * @return
	 * 		A Map of Item, Carton for that full fills a given Order
	 */
	private Map<Item, Carton> addOrderToMultipleCarton(Order order, Van van) throws VanCapacityOutOfBoundsException {
		Map<Item, Carton> itemCartonMap = new HashMap<>();
		
		for (Item item : order.getItems()) {
			for (Carton carton : van.getCartons()) {
				if (null != cartonService.addItemToCarton(item, carton)) {
					itemCartonMap.put(item, carton);
					break;
				}
			}
			
			//If the current couldn't be placed in any of the cartons
			//then order cannot be full filled by this van
			if (null == itemCartonMap.get(item)) {
				throw new VanCapacityOutOfBoundsException();
			}
		}
		
		return itemCartonMap;
	}
	
	/**
	 * Method to create Item, Carton mapping. Called in case all items
	 * of an Order are accommodated to a single Carton
	 * 
	 * @param items
	 * @param carton
	 * @return
	 * 		A Map of Item, Carton for that full fills a given Order
	 */
	private Map<Item, Carton> prepareItemCartonMap(List<Item> items, Carton carton) {
		Map<Item, Carton> itemCartonMap = new HashMap<>();
		
		for (Item item : items) {
			itemCartonMap.put(item, carton);
		}
		
		return itemCartonMap;
	}
}
