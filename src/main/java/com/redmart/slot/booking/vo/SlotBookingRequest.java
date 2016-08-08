/**
 * 
 */
package com.redmart.slot.booking.vo;

import com.redmart.slot.booking.model.Order;

/**
 * @author rkaranth
 *
 */
public class SlotBookingRequest {

	private Order order;
	private SlotVO slot;
	
	public SlotBookingRequest() {
		
	}
	
	/**
	 * @param order
	 * @param slot
	 */
	public SlotBookingRequest(Order order, SlotVO slot) {
		this.order = order;
		this.slot = slot;
	}
	/**
	 * @return the order
	 */
	public Order getOrder() {
		return order;
	}
	/**
	 * @param order the order to set
	 */
	public void setOrder(Order order) {
		this.order = order;
	}
	/**
	 * @return the slot
	 */
	public SlotVO getSlot() {
		return slot;
	}
	/**
	 * @param slot the slot to set
	 */
	public void setSlot(SlotVO slot) {
		this.slot = slot;
	}
}
