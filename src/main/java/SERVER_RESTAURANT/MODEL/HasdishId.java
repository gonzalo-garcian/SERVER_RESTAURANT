package SERVER_RESTAURANT.MODEL;
// Generated 05-jun-2019 4:27:11 by Hibernate Tools 5.1.10.Final

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * HasdishId generated by hbm2java
 */
@Embeddable
public class HasdishId implements java.io.Serializable {

	private int idTicket;
	private int idItemDish;

	public HasdishId() {
	}

	public HasdishId(int idTicket, int idItemDish) {
		this.idTicket = idTicket;
		this.idItemDish = idItemDish;
	}

	@Column(name = "id_ticket", nullable = false)
	public int getIdTicket() {
		return this.idTicket;
	}

	public void setIdTicket(int idTicket) {
		this.idTicket = idTicket;
	}

	@Column(name = "id_item_dish", nullable = false)
	public int getIdItemDish() {
		return this.idItemDish;
	}

	public void setIdItemDish(int idItemDish) {
		this.idItemDish = idItemDish;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof HasdishId))
			return false;
		HasdishId castOther = (HasdishId) other;

		return (this.getIdTicket() == castOther.getIdTicket()) && (this.getIdItemDish() == castOther.getIdItemDish());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getIdTicket();
		result = 37 * result + this.getIdItemDish();
		return result;
	}

}
