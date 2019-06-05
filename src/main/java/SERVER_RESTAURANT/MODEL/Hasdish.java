package SERVER_RESTAURANT.MODEL;
// Generated 05-jun-2019 4:27:11 by Hibernate Tools 5.1.10.Final

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Hasdish generated by hbm2java
 */
@Entity
@Table(name = "hasdish", catalog = "RESTAURANT")
public class Hasdish implements java.io.Serializable {

	private HasdishId id;
	private Integer quantityItem;

	public Hasdish() {
	}

	public Hasdish(HasdishId id) {
		this.id = id;
	}

	public Hasdish(HasdishId id, Integer quantityItem) {
		this.id = id;
		this.quantityItem = quantityItem;
	}

	@EmbeddedId

	@AttributeOverrides({ @AttributeOverride(name = "idTicket", column = @Column(name = "id_ticket", nullable = false)),
			@AttributeOverride(name = "idItemDish", column = @Column(name = "id_item_dish", nullable = false)) })
	public HasdishId getId() {
		return this.id;
	}

	public void setId(HasdishId id) {
		this.id = id;
	}

	@Column(name = "quantity_item")
	public Integer getQuantityItem() {
		return this.quantityItem;
	}

	public void setQuantityItem(Integer quantityItem) {
		this.quantityItem = quantityItem;
	}

}
