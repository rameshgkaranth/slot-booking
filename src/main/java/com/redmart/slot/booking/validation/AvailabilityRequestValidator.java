/**
 * 
 */
package com.redmart.slot.booking.validation;

import org.springframework.stereotype.Component;

import com.redmart.slot.booking.exception.InvalidRequestException;
import com.redmart.slot.booking.model.Item;
import com.redmart.slot.booking.model.Order;

/**
 * @author rkaranth
 *
 */
@Component
public class AvailabilityRequestValidator {

	public void validateAvailabilityRequest(Order order) throws InvalidRequestException{
		if (null == order || null == order.getItems() ||
				order.getItems().size() == 0) {
			throw new InvalidRequestException("Invalid order present in request");
		}
		
		for (Item item : order.getItems()) {
			if (item.getBreadth() < 0.0 || item.getHeight() < 0.0 || item.getWidth() < 0.0) {
				throw new InvalidRequestException("Invalid item dimensions present in request");
			}
		}
	}
	
}
