/**
 *
 */
package org.kuali.ext.mm.util;

import org.kuali.rice.core.api.util.type.AbstractKualiDecimal;
import org.kuali.rice.core.api.util.type.KualiDecimal;

import java.math.BigDecimal;

/**
 * @author schneppd
 *
 */
public class MMDecimal extends AbstractKualiDecimal<MMDecimal> {

	/**
     *
     */
    private static final long serialVersionUID = 453880975442162188L;

    public static final int SCALE = 4;

	public static final MMDecimal ZERO = new MMDecimal(BigDecimal.ZERO);

    /**
     * No-arg constructor for serialization purposes
     */
    public MMDecimal() {
    }

	/**
	 * This is the base constructor, used by constructors that take other types
	 *
	 * @param value
	 *            String containing numeric value
	 * @throws IllegalArgumentException
	 *             if the given String is null
	 */
	public MMDecimal(String value) {
		super(value, SCALE);
	}

	public MMDecimal(int value) {
		super(value, SCALE);
	}

	public MMDecimal(double value) {
		super(value, SCALE);
	}

	public MMDecimal(BigDecimal value) {
		super(value, SCALE);
	}

	public MMDecimal(KualiDecimal value) {
		super(value.bigDecimalValue(), SCALE);
	}

	public MMDecimal setScale() {
		return new MMDecimal(value, SCALE);
	}

	protected MMDecimal(String value, int scale) {
		super(value, scale);
	}

	protected MMDecimal(int value, int scale) {
		super(value, scale);
	}

	protected MMDecimal(double value, int scale) {
		super(value, scale);
	}

	protected MMDecimal(BigDecimal value, int scale) {
		super(value, scale);
	}

	protected MMDecimal(KualiDecimal value, int scale) {
		super(value.bigDecimalValue(), scale);
	}

	@Override
	protected MMDecimal newInstance(String value) {
		return new MMDecimal(value);
	}

	@Override
	protected MMDecimal newInstance(double value) {
		return new MMDecimal(value);
	}

    @Override
    protected MMDecimal newInstance(double value, int scale) {
        return new MMDecimal(value, scale);
    }

	@Override
	protected MMDecimal newInstance(BigDecimal value) {
		return new MMDecimal(value);
	}

	@Override
	protected MMDecimal newInstance(BigDecimal value, int scale) {
		return new MMDecimal(value, scale);
	}

	/**
	 * @return the value of this instance as a BigDecimal.
	 */
	public KualiDecimal kualiDecimalValue() {
		return new KualiDecimal(this.value.doubleValue());
	}

}
