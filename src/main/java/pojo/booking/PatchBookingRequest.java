package pojo.booking;

public class PatchBookingRequest {

	private String lastname;

	public PatchBookingRequest() {
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	@Override
	public String toString() {
		return "PatchBookingRequest{" + "lastname='" + lastname + '\'' + '}';
	}
}