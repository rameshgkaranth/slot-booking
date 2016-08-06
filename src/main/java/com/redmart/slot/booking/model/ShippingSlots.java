/**
 * 
 */
package com.redmart.slot.booking.model;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

/**
 * @author rkaranth
 *
 */
@Component
public class ShippingSlots {

	@Resource
	private List<Slot> slots;

	/**
	 * @return the slots
	 */
	public List<Slot> getSlots() {
		return slots;
	}
}
