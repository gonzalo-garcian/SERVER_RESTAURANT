package SERVER_RESTAURANT.MODEL;
// Generated 29-may-2019 16:28:58 by Hibernate Tools 5.1.10.Final

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Attend generated by hbm2java
 */
@Entity
@Table(name = "attend", catalog = "RESTAURANT")
public class Attend implements java.io.Serializable {

	private AttendId id;

	public Attend() {
	}

	public Attend(AttendId id) {
		this.id = id;
	}

	@EmbeddedId

	@AttributeOverrides({ @AttributeOverride(name = "idTable", column = @Column(name = "id_table", nullable = false)),
			@AttributeOverride(name = "dniWaiter", column = @Column(name = "dni_waiter", nullable = false, length = 9)) })
	public AttendId getId() {
		return this.id;
	}

	public void setId(AttendId id) {
		this.id = id;
	}

}
