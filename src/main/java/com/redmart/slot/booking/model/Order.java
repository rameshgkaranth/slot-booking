/**
 * 
 */
package com.redmart.slot.booking.model;

import java.util.List;

/**
 * @author rkaranth
 *
 */
public class Order {

	private int orderId;
	private List<Item> items;
	
	/**
	 * Method to calculate the volume of Order
	 * 
	 * @param items
	 * @return
	 * 		Volume of Order as double
	 */
	public double getVolumeOfOrder() {
		double voulmeOfItems = 0.0;
		for (Item item : this.items) {
			voulmeOfItems += item.getVoulmeOfItem();
		}
		
		return voulmeOfItems;
	}

	/**
	 * @return the orderId
	 */
	public int getOrderId() {
		return orderId;
	}

	/**
	 * @return the items
	 */
	public List<Item> getItems() {
		return items;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + orderId;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (orderId != other.orderId)
			return false;
		return true;
	}
}
