/**
 * Copyright (C) 2015 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.strata.market.key;

import java.io.Serializable;
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

import com.opengamma.strata.basics.currency.Currency;
import com.opengamma.strata.basics.market.MarketDataFeed;
import com.opengamma.strata.basics.market.MarketDataId;
import com.opengamma.strata.basics.market.SimpleMarketDataKey;
import com.opengamma.strata.market.curve.IsdaYieldCurveParRates;
import com.opengamma.strata.market.id.IsdaYieldCurveParRatesId;

/**
 * Market data key identifying a set of par rates to be used in the ISDA credit model's yield
 * yield curve calibration for a currency.
 */
@BeanDefinition(builderScope = "private")
public final class IsdaYieldCurveParRatesKey
    implements SimpleMarketDataKey<IsdaYieldCurveParRates>, ImmutableBean, Serializable {

  /**
   * The currency.
   */
  @PropertyDefinition(validate = "notNull")
  private final Currency currency;

  //-------------------------------------------------------------------------
  /**
   * Creates an instance based on a currency.
   * 
   * @param currency  the currency
   * @return the key
   */
  public static IsdaYieldCurveParRatesKey of(Currency currency) {
    return new IsdaYieldCurveParRatesKey(currency);
  }

  //-------------------------------------------------------------------------
  @Override
  public Class<IsdaYieldCurveParRates> getMarketDataType() {
    return IsdaYieldCurveParRates.class;
  }

  @Override
  public MarketDataId<IsdaYieldCurveParRates> toMarketDataId(MarketDataFeed marketDataFeed) {
    return IsdaYieldCurveParRatesId.of(currency);
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code IsdaYieldCurveParRatesKey}.
   * @return the meta-bean, not null
   */
  public static IsdaYieldCurveParRatesKey.Meta meta() {
    return IsdaYieldCurveParRatesKey.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(IsdaYieldCurveParRatesKey.Meta.INSTANCE);
  }

  /**
   * The serialization version id.
   */
  private static final long serialVersionUID = 1L;

  private IsdaYieldCurveParRatesKey(
      Currency currency) {
    JodaBeanUtils.notNull(currency, "currency");
    this.currency = currency;
  }

  @Override
  public IsdaYieldCurveParRatesKey.Meta metaBean() {
    return IsdaYieldCurveParRatesKey.Meta.INSTANCE;
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
   * Gets the currency.
   * @return the value of the property, not null
   */
  public Currency getCurrency() {
    return currency;
  }

  //-----------------------------------------------------------------------
  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      IsdaYieldCurveParRatesKey other = (IsdaYieldCurveParRatesKey) obj;
      return JodaBeanUtils.equal(currency, other.currency);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash = hash * 31 + JodaBeanUtils.hashCode(currency);
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(64);
    buf.append("IsdaYieldCurveParRatesKey{");
    buf.append("currency").append('=').append(JodaBeanUtils.toString(currency));
    buf.append('}');
    return buf.toString();
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code IsdaYieldCurveParRatesKey}.
   */
  public static final class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code currency} property.
     */
    private final MetaProperty<Currency> currency = DirectMetaProperty.ofImmutable(
        this, "currency", IsdaYieldCurveParRatesKey.class, Currency.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "currency");

    /**
     * Restricted constructor.
     */
    private Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 575402001:  // currency
          return currency;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends IsdaYieldCurveParRatesKey> builder() {
      return new IsdaYieldCurveParRatesKey.Builder();
    }

    @Override
    public Class<? extends IsdaYieldCurveParRatesKey> beanType() {
      return IsdaYieldCurveParRatesKey.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code currency} property.
     * @return the meta-property, not null
     */
    public MetaProperty<Currency> currency() {
      return currency;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 575402001:  // currency
          return ((IsdaYieldCurveParRatesKey) bean).getCurrency();
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
   * The bean-builder for {@code IsdaYieldCurveParRatesKey}.
   */
  private static final class Builder extends DirectFieldsBeanBuilder<IsdaYieldCurveParRatesKey> {

    private Currency currency;

    /**
     * Restricted constructor.
     */
    private Builder() {
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
      switch (propertyName.hashCode()) {
        case 575402001:  // currency
          return currency;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
    }

    @Override
    public Builder set(String propertyName, Object newValue) {
      switch (propertyName.hashCode()) {
        case 575402001:  // currency
          this.currency = (Currency) newValue;
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
    public IsdaYieldCurveParRatesKey build() {
      return new IsdaYieldCurveParRatesKey(
          currency);
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder(64);
      buf.append("IsdaYieldCurveParRatesKey.Builder{");
      buf.append("currency").append('=').append(JodaBeanUtils.toString(currency));
      buf.append('}');
      return buf.toString();
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
