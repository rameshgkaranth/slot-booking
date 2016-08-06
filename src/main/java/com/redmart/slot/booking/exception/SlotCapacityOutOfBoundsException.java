/**
 * 
 */
package com.redmart.slot.booking.exception;

/**
 * @author rkaranth
 *
 */
public class SlotCapacityOutOfBoundsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SlotCapacityOutOfBoundsException() {
		
	}
	
	public SlotCapacityOutOfBoundsException(String message) {
		super(message);
	}
	
	public SlotCapacityOutOfBoundsException(String message, Throwable cause) {
		super(message, cause);
	}
}
