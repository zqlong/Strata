/**
 * Copyright (C) 2015 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.strata.market.curve;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.joda.beans.Bean;
import org.joda.beans.BeanBuilder;
import org.joda.beans.BeanDefinition;
import org.joda.beans.ImmutableBean;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectFieldsBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

/**
 * Simple curve node metadata that defines a date and label.
 */
@BeanDefinition(builderScope = "private")
public final class SimpleCurveNodeMetadata
    implements DatedCurveParameterMetadata, ImmutableBean, Serializable {

  /**
   * The date of the curve node.
   * <p>
   * This is the date that the node on the curve is defined as.
   * There is not necessarily a direct relationship with a date from an underlying instrument.
   * It may be the effective date or the maturity date but equally it may not.
   */
  @PropertyDefinition(validate = "notNull", overrideGet = true)
  private final LocalDate date;
  /**
   * The label that describes the node.
   */
  @PropertyDefinition(validate = "notEmpty", overrideGet = true)
  private final String label;

  //-------------------------------------------------------------------------
  /**
   * Creates node metadata using date and label.
   * 
   * @param date  the date of the curve node
   * @param label  the label to use
   * @return node metadata based on a tenor
   */
  public static SimpleCurveNodeMetadata of(LocalDate date, String label) {
    return new SimpleCurveNodeMetadata(date, label);
  }

  //-------------------------------------------------------------------------
  /**
   * Gets the identifier, which is the label.
   *
   * @return the label
   */
  @Override
  public String getIdentifier() {
    return label;
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code SimpleCurveNodeMetadata}.
   * @return the meta-bean, not null
   */
  public static SimpleCurveNodeMetadata.Meta meta() {
    return SimpleCurveNodeMetadata.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(SimpleCurveNodeMetadata.Meta.INSTANCE);
  }

  /**
   * The serialization version id.
   */
  private static final long serialVersionUID = 1L;

  private SimpleCurveNodeMetadata(
      LocalDate date,
      String label) {
    JodaBeanUtils.notNull(date, "date");
    JodaBeanUtils.notEmpty(label, "label");
    this.date = date;
    this.label = label;
  }

  @Override
  public SimpleCurveNodeMetadata.Meta metaBean() {
    return SimpleCurveNodeMetadata.Meta.INSTANCE;
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
   * Gets the date of the curve node.
   * <p>
   * This is the date that the node on the curve is defined as.
   * There is not necessarily a direct relationship with a date from an underlying instrument.
   * It may be the effective date or the maturity date but equally it may not.
   * @return the value of the property, not null
   */
  @Override
  public LocalDate getDate() {
    return date;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the label that describes the node.
   * @return the value of the property, not empty
   */
  @Override
  public String getLabel() {
    return label;
  }

  //-----------------------------------------------------------------------
  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      SimpleCurveNodeMetadata other = (SimpleCurveNodeMetadata) obj;
      return JodaBeanUtils.equal(date, other.date) &&
          JodaBeanUtils.equal(label, other.label);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash = hash * 31 + JodaBeanUtils.hashCode(date);
    hash = hash * 31 + JodaBeanUtils.hashCode(label);
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(96);
    buf.append("SimpleCurveNodeMetadata{");
    buf.append("date").append('=').append(date).append(',').append(' ');
    buf.append("label").append('=').append(JodaBeanUtils.toString(label));
    buf.append('}');
    return buf.toString();
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code SimpleCurveNodeMetadata}.
   */
  public static final class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code date} property.
     */
    private final MetaProperty<LocalDate> date = DirectMetaProperty.ofImmutable(
        this, "date", SimpleCurveNodeMetadata.class, LocalDate.class);
    /**
     * The meta-property for the {@code label} property.
     */
    private final MetaProperty<String> label = DirectMetaProperty.ofImmutable(
        this, "label", SimpleCurveNodeMetadata.class, String.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "date",
        "label");

    /**
     * Restricted constructor.
     */
    private Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 3076014:  // date
          return date;
        case 102727412:  // label
          return label;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends SimpleCurveNodeMetadata> builder() {
      return new SimpleCurveNodeMetadata.Builder();
    }

    @Override
    public Class<? extends SimpleCurveNodeMetadata> beanType() {
      return SimpleCurveNodeMetadata.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code date} property.
     * @return the meta-property, not null
     */
    public MetaProperty<LocalDate> date() {
      return date;
    }

    /**
     * The meta-property for the {@code label} property.
     * @return the meta-property, not null
     */
    public MetaProperty<String> label() {
      return label;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 3076014:  // date
          return ((SimpleCurveNodeMetadata) bean).getDate();
        case 102727412:  // label
          return ((SimpleCurveNodeMetadata) bean).getLabel();
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
   * The bean-builder for {@code SimpleCurveNodeMetadata}.
   */
  private static final class Builder extends DirectFieldsBeanBuilder<SimpleCurveNodeMetadata> {

    private LocalDate date;
    private String label;

    /**
     * Restricted constructor.
     */
    private Builder() {
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
      switch (propertyName.hashCode()) {
        case 3076014:  // date
          return date;
        case 102727412:  // label
          return label;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
    }

    @Override
    public Builder set(String propertyName, Object newValue) {
      switch (propertyName.hashCode()) {
        case 3076014:  // date
          this.date = (LocalDate) newValue;
          break;
        case 102727412:  // label
          this.label = (String) newValue;
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
    public SimpleCurveNodeMetadata build() {
      return new SimpleCurveNodeMetadata(
          date,
          label);
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder(96);
      buf.append("SimpleCurveNodeMetadata.Builder{");
      buf.append("date").append('=').append(JodaBeanUtils.toString(date)).append(',').append(' ');
      buf.append("label").append('=').append(JodaBeanUtils.toString(label));
      buf.append('}');
      return buf.toString();
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
