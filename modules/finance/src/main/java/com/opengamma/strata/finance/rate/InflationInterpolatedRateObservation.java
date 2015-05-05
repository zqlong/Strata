package com.opengamma.strata.finance.rate;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.joda.beans.Bean;
import org.joda.beans.BeanDefinition;
import org.joda.beans.ImmutableBean;
import org.joda.beans.ImmutableValidator;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectFieldsBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.opengamma.strata.basics.index.Index;
import com.opengamma.strata.basics.index.PriceIndex;
import com.opengamma.strata.basics.index.PriceIndices;
import com.opengamma.strata.collect.ArgChecker;

/**
 * Defines the observation of inflation figures from a price index.
 * <p>
 * the reference index is linearly interpolated between two months. 
 * The interpolation is done with the number of days of the payment month.   
 * The most common implementations of price indexes are provided in {@link PriceIndices}.
 */
@BeanDefinition
public class InflationInterpolatedRateObservation
    implements RateObservation, ImmutableBean, Serializable {

  /**
  * The day count convention applicable.
  * <p>
  * This is used to convert dates to a numerical value.
  * <p>
  * When building, this will default to the day count of the index if not specified.
  */
  @PropertyDefinition(validate = "notNull")
  private final PriceIndex index;
  /**
   * The reference dates for the index at the coupon start. 
   * <p>
   * Two months are required for the interpolation.
   * There is usually a difference of two or three month between the reference date and the accrual start date.
   */
  @PropertyDefinition(validate = "notNull")
  private final LocalDate[] referenceStartDates;
  /**
   * The reference dates for the index at the coupon end. 
   * <p>
   * Two months are required for the interpolation.
   * There is usually a difference of two or three month between the reference date and the payment date.
   */
  @PropertyDefinition(validate = "notNull")
  private final LocalDate[] referenceEndDates;
  /**
   * The weight for the index value in the interpolation.
   * <p>
   * This must be non-negative. 
   */
  @PropertyDefinition(validate = "ArgChecker.notNegative")
  private final double weight;

  /**
   * Creates an {@code InflationInterpolatedRateObservation} from an index, reference start dates 
   * and reference end dates.
   * @param index The index
   * @param referenceStartDates The reference start dates. 
   * @param referenceEndDates The reference end dates. 
   * @param weight The weight. 
   * @return The inflation rate observation
   */
  public static InflationInterpolatedRateObservation of(
      PriceIndex index,
      LocalDate[] referenceStartDates,
      LocalDate[] referenceEndDates,
      double weight) {
    return InflationInterpolatedRateObservation.builder()
        .index(index)
        .referenceStartDates(referenceStartDates)
        .referenceEndDates(referenceEndDates)
        .weight(weight)
        .build();
  }

  @ImmutableValidator
  private void validate() {
    ArgChecker.isTrue(referenceStartDates.length == 2, "referenceStartDates should be length 2");
    ArgChecker.isTrue(referenceEndDates.length == 2, "referenceEndDates should be length 2");
    for (int i = 0; i < 2; ++i) {
      ArgChecker.inOrderNotEqual(
          referenceStartDates[i], referenceEndDates[i], "referenceStartDates", "referenceEndDates");
    }
  }

  @Override
  public void collectIndices(com.google.common.collect.ImmutableSet.Builder<Index> builder) {
    builder.add(index);
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code InflationInterpolatedRateObservation}.
   * @return the meta-bean, not null
   */
  public static InflationInterpolatedRateObservation.Meta meta() {
    return InflationInterpolatedRateObservation.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(InflationInterpolatedRateObservation.Meta.INSTANCE);
  }

  /**
   * The serialization version id.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Returns a builder used to create an instance of the bean.
   * @return the builder, not null
   */
  public static InflationInterpolatedRateObservation.Builder builder() {
    return new InflationInterpolatedRateObservation.Builder();
  }

  /**
   * Restricted constructor.
   * @param builder  the builder to copy from, not null
   */
  protected InflationInterpolatedRateObservation(InflationInterpolatedRateObservation.Builder builder) {
    JodaBeanUtils.notNull(builder.index, "index");
    JodaBeanUtils.notNull(builder.referenceStartDates, "referenceStartDates");
    JodaBeanUtils.notNull(builder.referenceEndDates, "referenceEndDates");
    ArgChecker.notNegative(builder.weight, "weight");
    this.index = builder.index;
    this.referenceStartDates = builder.referenceStartDates;
    this.referenceEndDates = builder.referenceEndDates;
    this.weight = builder.weight;
    validate();
  }

  @Override
  public InflationInterpolatedRateObservation.Meta metaBean() {
    return InflationInterpolatedRateObservation.Meta.INSTANCE;
  }

  @Override
  public <R> Property<R> property(String propertyName) {
    return metaBean().<R>metaProperty(propertyName).createProperty(this);
  }

  @Override
  public Set<String> propertyNames() {
    return metaBean().metaPropertyMap().keySet();
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the day count convention applicable.
   * <p>
   * This is used to convert dates to a numerical value.
   * <p>
   * When building, this will default to the day count of the index if not specified.
   * @return the value of the property, not null
   */
  public PriceIndex getIndex() {
    return index;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the reference dates for the index at the coupon start.
   * <p>
   * Two months are required for the interpolation.
   * There is usually a difference of two or three month between the reference date and the accrual start date.
   * @return the value of the property, not null
   */
  public LocalDate[] getReferenceStartDates() {
    return referenceStartDates;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the reference dates for the index at the coupon end.
   * <p>
   * Two months are required for the interpolation.
   * There is usually a difference of two or three month between the reference date and the payment date.
   * @return the value of the property, not null
   */
  public LocalDate[] getReferenceEndDates() {
    return referenceEndDates;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the weight for the index value in the interpolation.
   * <p>
   * This must be non-negative.
   * @return the value of the property
   */
  public double getWeight() {
    return weight;
  }

  //-----------------------------------------------------------------------
  /**
   * Returns a builder that allows this bean to be mutated.
   * @return the mutable builder, not null
   */
  public Builder toBuilder() {
    return new Builder(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      InflationInterpolatedRateObservation other = (InflationInterpolatedRateObservation) obj;
      return JodaBeanUtils.equal(getIndex(), other.getIndex()) &&
          JodaBeanUtils.equal(getReferenceStartDates(), other.getReferenceStartDates()) &&
          JodaBeanUtils.equal(getReferenceEndDates(), other.getReferenceEndDates()) &&
          JodaBeanUtils.equal(getWeight(), other.getWeight());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash = hash * 31 + JodaBeanUtils.hashCode(getIndex());
    hash = hash * 31 + JodaBeanUtils.hashCode(getReferenceStartDates());
    hash = hash * 31 + JodaBeanUtils.hashCode(getReferenceEndDates());
    hash = hash * 31 + JodaBeanUtils.hashCode(getWeight());
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(160);
    buf.append("InflationInterpolatedRateObservation{");
    int len = buf.length();
    toString(buf);
    if (buf.length() > len) {
      buf.setLength(buf.length() - 2);
    }
    buf.append('}');
    return buf.toString();
  }

  protected void toString(StringBuilder buf) {
    buf.append("index").append('=').append(JodaBeanUtils.toString(getIndex())).append(',').append(' ');
    buf.append("referenceStartDates").append('=').append(JodaBeanUtils.toString(getReferenceStartDates())).append(',').append(' ');
    buf.append("referenceEndDates").append('=').append(JodaBeanUtils.toString(getReferenceEndDates())).append(',').append(' ');
    buf.append("weight").append('=').append(JodaBeanUtils.toString(getWeight())).append(',').append(' ');
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code InflationInterpolatedRateObservation}.
   */
  public static class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code index} property.
     */
    private final MetaProperty<PriceIndex> index = DirectMetaProperty.ofImmutable(
        this, "index", InflationInterpolatedRateObservation.class, PriceIndex.class);
    /**
     * The meta-property for the {@code referenceStartDates} property.
     */
    private final MetaProperty<LocalDate[]> referenceStartDates = DirectMetaProperty.ofImmutable(
        this, "referenceStartDates", InflationInterpolatedRateObservation.class, LocalDate[].class);
    /**
     * The meta-property for the {@code referenceEndDates} property.
     */
    private final MetaProperty<LocalDate[]> referenceEndDates = DirectMetaProperty.ofImmutable(
        this, "referenceEndDates", InflationInterpolatedRateObservation.class, LocalDate[].class);
    /**
     * The meta-property for the {@code weight} property.
     */
    private final MetaProperty<Double> weight = DirectMetaProperty.ofImmutable(
        this, "weight", InflationInterpolatedRateObservation.class, Double.TYPE);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "index",
        "referenceStartDates",
        "referenceEndDates",
        "weight");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 100346066:  // index
          return index;
        case -1314817810:  // referenceStartDates
          return referenceStartDates;
        case 1852311253:  // referenceEndDates
          return referenceEndDates;
        case -791592328:  // weight
          return weight;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public InflationInterpolatedRateObservation.Builder builder() {
      return new InflationInterpolatedRateObservation.Builder();
    }

    @Override
    public Class<? extends InflationInterpolatedRateObservation> beanType() {
      return InflationInterpolatedRateObservation.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code index} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<PriceIndex> index() {
      return index;
    }

    /**
     * The meta-property for the {@code referenceStartDates} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<LocalDate[]> referenceStartDates() {
      return referenceStartDates;
    }

    /**
     * The meta-property for the {@code referenceEndDates} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<LocalDate[]> referenceEndDates() {
      return referenceEndDates;
    }

    /**
     * The meta-property for the {@code weight} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Double> weight() {
      return weight;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 100346066:  // index
          return ((InflationInterpolatedRateObservation) bean).getIndex();
        case -1314817810:  // referenceStartDates
          return ((InflationInterpolatedRateObservation) bean).getReferenceStartDates();
        case 1852311253:  // referenceEndDates
          return ((InflationInterpolatedRateObservation) bean).getReferenceEndDates();
        case -791592328:  // weight
          return ((InflationInterpolatedRateObservation) bean).getWeight();
      }
      return super.propertyGet(bean, propertyName, quiet);
    }

    @Override
    protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
      metaProperty(propertyName);
      if (quiet) {
        return;
      }
      throw new UnsupportedOperationException("Property cannot be written: " + propertyName);
    }

  }

  //-----------------------------------------------------------------------
  /**
   * The bean-builder for {@code InflationInterpolatedRateObservation}.
   */
  public static class Builder extends DirectFieldsBeanBuilder<InflationInterpolatedRateObservation> {

    private PriceIndex index;
    private LocalDate[] referenceStartDates;
    private LocalDate[] referenceEndDates;
    private double weight;

    /**
     * Restricted constructor.
     */
    protected Builder() {
    }

    /**
     * Restricted copy constructor.
     * @param beanToCopy  the bean to copy from, not null
     */
    protected Builder(InflationInterpolatedRateObservation beanToCopy) {
      this.index = beanToCopy.getIndex();
      this.referenceStartDates = beanToCopy.getReferenceStartDates();
      this.referenceEndDates = beanToCopy.getReferenceEndDates();
      this.weight = beanToCopy.getWeight();
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
      switch (propertyName.hashCode()) {
        case 100346066:  // index
          return index;
        case -1314817810:  // referenceStartDates
          return referenceStartDates;
        case 1852311253:  // referenceEndDates
          return referenceEndDates;
        case -791592328:  // weight
          return weight;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
    }

    @Override
    public Builder set(String propertyName, Object newValue) {
      switch (propertyName.hashCode()) {
        case 100346066:  // index
          this.index = (PriceIndex) newValue;
          break;
        case -1314817810:  // referenceStartDates
          this.referenceStartDates = (LocalDate[]) newValue;
          break;
        case 1852311253:  // referenceEndDates
          this.referenceEndDates = (LocalDate[]) newValue;
          break;
        case -791592328:  // weight
          this.weight = (Double) newValue;
          break;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
      return this;
    }

    @Override
    public Builder set(MetaProperty<?> property, Object value) {
      super.set(property, value);
      return this;
    }

    @Override
    public Builder setString(String propertyName, String value) {
      setString(meta().metaProperty(propertyName), value);
      return this;
    }

    @Override
    public Builder setString(MetaProperty<?> property, String value) {
      super.setString(property, value);
      return this;
    }

    @Override
    public Builder setAll(Map<String, ? extends Object> propertyValueMap) {
      super.setAll(propertyValueMap);
      return this;
    }

    @Override
    public InflationInterpolatedRateObservation build() {
      return new InflationInterpolatedRateObservation(this);
    }

    //-----------------------------------------------------------------------
    /**
     * Sets the {@code index} property in the builder.
     * @param index  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder index(PriceIndex index) {
      JodaBeanUtils.notNull(index, "index");
      this.index = index;
      return this;
    }

    /**
     * Sets the {@code referenceStartDates} property in the builder.
     * @param referenceStartDates  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder referenceStartDates(LocalDate... referenceStartDates) {
      JodaBeanUtils.notNull(referenceStartDates, "referenceStartDates");
      this.referenceStartDates = referenceStartDates;
      return this;
    }

    /**
     * Sets the {@code referenceEndDates} property in the builder.
     * @param referenceEndDates  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder referenceEndDates(LocalDate... referenceEndDates) {
      JodaBeanUtils.notNull(referenceEndDates, "referenceEndDates");
      this.referenceEndDates = referenceEndDates;
      return this;
    }

    /**
     * Sets the {@code weight} property in the builder.
     * @param weight  the new value
     * @return this, for chaining, not null
     */
    public Builder weight(double weight) {
      ArgChecker.notNegative(weight, "weight");
      this.weight = weight;
      return this;
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder(160);
      buf.append("InflationInterpolatedRateObservation.Builder{");
      int len = buf.length();
      toString(buf);
      if (buf.length() > len) {
        buf.setLength(buf.length() - 2);
      }
      buf.append('}');
      return buf.toString();
    }

    protected void toString(StringBuilder buf) {
      buf.append("index").append('=').append(JodaBeanUtils.toString(index)).append(',').append(' ');
      buf.append("referenceStartDates").append('=').append(JodaBeanUtils.toString(referenceStartDates)).append(',').append(' ');
      buf.append("referenceEndDates").append('=').append(JodaBeanUtils.toString(referenceEndDates)).append(',').append(' ');
      buf.append("weight").append('=').append(JodaBeanUtils.toString(weight)).append(',').append(' ');
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
