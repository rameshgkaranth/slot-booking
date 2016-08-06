/**
 * 
 */
package com.redmart.slot.booking.exception;

/**
 * @author rkaranth
 *
 */
public class VanCapacityOutOfBoundsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public VanCapacityOutOfBoundsException() {
		
	}
	
	public VanCapacityOutOfBoundsException(String message) {
		super(message);
	}
	
	public VanCapacityOutOfBoundsException(String message, Throwable cause) {
		super(message, cause);
	}
}
