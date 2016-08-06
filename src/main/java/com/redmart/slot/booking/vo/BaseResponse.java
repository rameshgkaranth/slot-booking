/**
 * 
 */
package com.redmart.slot.booking.vo;

import org.springframework.http.HttpStatus;

/**
 * @author rkaranth
 *
 */
public abstract class BaseResponse {

	private String responseMessage;
	private HttpStatus responseCode;
	
	/**
	 * @param responseMessage
	 * @param responseCode
	 */
	public BaseResponse(String responseMessage, HttpStatus responseCode) {
		this.responseMessage = responseMessage;
		this.responseCode = responseCode;
	}
	
	/**
	 * @return the responseMessage
	 */
	public String getResponseMessage() {
		return responseMessage;
	}

	/**
	 * @return the responseCode
	 */
	public HttpStatus getResponseCode() {
		return responseCode;
	}
	
	public abstract void setPayload(Object object);
	
	public abstract Object getPayload();
}
