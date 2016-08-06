/**
 * 
 */
package com.redmart.slot.booking.vo;

import java.util.List;

import org.springframework.http.HttpStatus;

/**
 * @author rkaranth
 *
 */
public class SlotAvailabilityResponse extends BaseResponse {

	private List<SlotVO> slotVO;
	
	public SlotAvailabilityResponse(String responseMessage, HttpStatus responseCode) {
		super(responseMessage, responseCode);
	}

	@Override
	public void setPayload(Object object) {
		slotVO = (List<SlotVO>) object;
	}

	@Override
	public Object getPayload() {
		return slotVO;
	}

}
