/**
 * 
 */
package com.redmart.slot.booking.vo;

import org.springframework.http.HttpStatus;

import com.redmart.slot.booking.model.OrderShipmentInfo;

/**
 * @author rkaranth
 *
 */
public class SlotBookingResponse extends BaseResponse {

	private OrderShipmentInfo orderShipmentInfo;
	
	public SlotBookingResponse(String responseMessage, HttpStatus responseCode) {
		super(responseMessage, responseCode);
	}

	@Override
	public void setPayload(Object object) {
		this.orderShipmentInfo = (OrderShipmentInfo) object;
	}

	@Override
	public Object getPayload() {
		return orderShipmentInfo;
	}
} 
