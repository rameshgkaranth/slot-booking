/**
 * 
 */
package com.redmart.slot.booking.model;

import java.util.Map;

/**
 * @author rkaranth
 *
 */
public class OrderShipmentInfo {

	private Order order;
	private int slotId;
	private int vanId;
	private Map<Item, Carton> itemCartonMap;
	
	/**
	 * @param order
	 * @param slotId
	 * @param vanId
	 * @param itemCartonMap
	 */
	public OrderShipmentInfo(Order order, int slotId, int vanId, Map<Item, Carton> itemCartonMap) {
		super();
		this.order = order;
		this.slotId = slotId;
		this.vanId = vanId;
		this.itemCartonMap = itemCartonMap;
	}
	
	/**
	 * @return the order
	 */
	public Order getOrder() {
		return order;
	}
	/**
	 * @return the slotId
	 */
	public int getSlotId() {
		return slotId;
	}
	/**
	 * @return the vanId
	 */
	public int getVanId() {
		return vanId;
	}
	/**
	 * @return the itemCartonMap
	 */
	public Map<Item, Carton> getItemCartonMap() {
		return itemCartonMap;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((order == null) ? 0 : order.hashCode());
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
		OrderShipmentInfo other = (OrderShipmentInfo) obj;
		if (order == null) {
			if (other.order != null)
				return false;
		} else if (!order.equals(other.order))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OrderShipmentInfo [order=" + order.getOrderId() + ", slotId=" + slotId + ", vanId=" + vanId + ", itemCartonMap="
				+ itemCartonMap + "]";
	}
	
	
}
