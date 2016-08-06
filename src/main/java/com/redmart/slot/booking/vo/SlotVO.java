/**
 * 
 */
package com.redmart.slot.booking.vo;

/**
 * @author rkaranth
 *
 */
public class SlotVO {

	private int startHour;
	private int endHour;
	
	/**
	 * 
	 */
	public SlotVO() {
	}
	
	/**
	 * @param startHour
	 * @param endHour
	 */
	public SlotVO(int startHour, int endHour) {
		this.startHour = startHour;
		this.endHour = endHour;
	}
	/**
	 * @return the startHour
	 */
	public int getStartHour() {
		return startHour;
	}
	/**
	 * @param startHour the startHour to set
	 */
	public void setStartHour(int startHour) {
		this.startHour = startHour;
	}
	/**
	 * @return the endHour
	 */
	public int getEndHour() {
		return endHour;
	}
	/**
	 * @param endHour the endHour to set
	 */
	public void setEndHour(int endHour) {
		this.endHour = endHour;
	}
}
