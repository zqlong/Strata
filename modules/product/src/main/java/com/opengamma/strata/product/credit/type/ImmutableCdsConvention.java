/**
 * Copyright (C) 2015 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.strata.product.credit.type;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.joda.beans.Bean;
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

import com.opengamma.strata.basics.BuySell;
import com.opengamma.strata.basics.currency.Currency;
import com.opengamma.strata.basics.currency.CurrencyAmount;
import com.opengamma.strata.basics.currency.Payment;
import com.opengamma.strata.basics.date.BusinessDayAdjustment;
import com.opengamma.strata.basics.date.DayCount;
import com.opengamma.strata.basics.schedule.Frequency;
import com.opengamma.strata.basics.schedule.RollConvention;
import com.opengamma.strata.basics.schedule.StubConvention;
import com.opengamma.strata.product.credit.Cds;
import com.opengamma.strata.product.credit.CdsTrade;
import com.opengamma.strata.product.credit.FeeLeg;
import com.opengamma.strata.product.credit.PeriodicPayments;
import com.opengamma.strata.product.credit.ReferenceInformation;

/**
 * A market convention for credit default swap (CDS) trades.
 * <p>
 * This defines the market convention for a CDS.
 */
@BeanDefinition
public final class ImmutableCdsConvention
    implements CdsConvention, ImmutableBean, Serializable {

  /**
   * The convention name, such as 'USD-European'.
   */
  @PropertyDefinition(validate = "notNull", overrideGet = true)
  private final String name;
  /**
   * The currency of the CDS.
   */
  @PropertyDefinition(validate = "notNull", overrideGet = true)
  private final Currency currency;
  /**
   * The day count convention.
   */
  @PropertyDefinition(validate = "notNull", overrideGet = true)
  private final DayCount dayCount;
  /**
   * The business day adjustment.
   */
  @PropertyDefinition(validate = "notNull", overrideGet = true)
  private final BusinessDayAdjustment businessDayAdjustment;
  /**
   * The payment frequency.
   */
  @PropertyDefinition(validate = "notNull", overrideGet = true)
  private final Frequency paymentFrequency;
  /**
   * The roll convention.
   */
  @PropertyDefinition(validate = "notNull", overrideGet = true)
  private final RollConvention rollConvention;
  /**
   * Whether the accrued premium is paid in the event of a default.
   */
  @PropertyDefinition(overrideGet = true)
  private final boolean payAccruedOnDefault;
  /**
   * The stub convention.
   */
  @PropertyDefinition(validate = "notNull", overrideGet = true)
  private final StubConvention stubConvention;
  /**
   * The number of step-in days.
   * <p>
   * This is the date from which the issuer is deemed to be on risk.
   */
  @PropertyDefinition(overrideGet = true)
  private final int stepInDays;
  /**
   * The settlement lag in days.
   * <p>
   * This is the number of days after the start date that any upfront fees are paid.
   */
  @PropertyDefinition(overrideGet = true)
  private final int settleLagDays;

  //-------------------------------------------------------------------------
  @Override
  public CdsTrade toTrade(
      LocalDate startDate,
      LocalDate endDate,
      BuySell buySell,
      double notional,
      double coupon,
      ReferenceInformation referenceInformation,
      double upfrontFeeAmount,
      LocalDate upfrontFeePaymentDate) {

    return CdsTrade.builder()
        .product(Cds.builder()
            .startDate(startDate)
            .endDate(endDate)
            .buySellProtection(buySell)
            .businessDayAdjustment(getBusinessDayAdjustment())
            .referenceInformation(referenceInformation)
            .feeLeg(
                FeeLeg.of(
                    Payment.of(
                        getCurrency(),
                        upfrontFeeAmount,
                        upfrontFeePaymentDate),
                    PeriodicPayments.of(
                        CurrencyAmount.of(getCurrency(), notional),
                        coupon,
                        getDayCount(),
                        getPaymentFrequency(),
                        getStubConvention(),
                        getRollConvention())))
            .payAccruedOnDefault(true)
            .build())
        .build();
  }

  @Override
  public String toString() {
    return getName();
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code ImmutableCdsConvention}.
   * @return the meta-bean, not null
   */
  public static ImmutableCdsConvention.Meta meta() {
    return ImmutableCdsConvention.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(ImmutableCdsConvention.Meta.INSTANCE);
  }

  /**
   * The serialization version id.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Returns a builder used to create an instance of the bean.
   * @return the builder, not null
   */
  public static ImmutableCdsConvention.Builder builder() {
    return new ImmutableCdsConvention.Builder();
  }

  private ImmutableCdsConvention(
      String name,
      Currency currency,
      DayCount dayCount,
      BusinessDayAdjustment businessDayAdjustment,
      Frequency paymentFrequency,
      RollConvention rollConvention,
      boolean payAccruedOnDefault,
      StubConvention stubConvention,
      int stepInDays,
      int settleLagDays) {
    JodaBeanUtils.notNull(name, "name");
    JodaBeanUtils.notNull(currency, "currency");
    JodaBeanUtils.notNull(dayCount, "dayCount");
    JodaBeanUtils.notNull(businessDayAdjustment, "businessDayAdjustment");
    JodaBeanUtils.notNull(paymentFrequency, "paymentFrequency");
    JodaBeanUtils.notNull(rollConvention, "rollConvention");
    JodaBeanUtils.notNull(stubConvention, "stubConvention");
    this.name = name;
    this.currency = currency;
    this.dayCount = dayCount;
    this.businessDayAdjustment = businessDayAdjustment;
    this.paymentFrequency = paymentFrequency;
    this.rollConvention = rollConvention;
    this.payAccruedOnDefault = payAccruedOnDefault;
    this.stubConvention = stubConvention;
    this.stepInDays = stepInDays;
    this.settleLagDays = settleLagDays;
  }

  @Override
  public ImmutableCdsConvention.Meta metaBean() {
    return ImmutableCdsConvention.Meta.INSTANCE;
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
   * Gets the convention name, such as 'USD-European'.
   * @return the value of the property, not null
   */
  @Override
  public String getName() {
    return name;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the currency of the CDS.
   * @return the value of the property, not null
   */
  @Override
  public Currency getCurrency() {
    return currency;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the day count convention.
   * @return the value of the property, not null
   */
  @Override
  public DayCount getDayCount() {
    return dayCount;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the business day adjustment.
   * @return the value of the property, not null
   */
  @Override
  public BusinessDayAdjustment getBusinessDayAdjustment() {
    return businessDayAdjustment;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the payment frequency.
   * @return the value of the property, not null
   */
  @Override
  public Frequency getPaymentFrequency() {
    return paymentFrequency;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the roll convention.
   * @return the value of the property, not null
   */
  @Override
  public RollConvention getRollConvention() {
    return rollConvention;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets whether the accrued premium is paid in the event of a default.
   * @return the value of the property
   */
  @Override
  public boolean isPayAccruedOnDefault() {
    return payAccruedOnDefault;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the stub convention.
   * @return the value of the property, not null
   */
  @Override
  public StubConvention getStubConvention() {
    return stubConvention;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the number of step-in days.
   * <p>
   * This is the date from which the issuer is deemed to be on risk.
   * @return the value of the property
   */
  @Override
  public int getStepInDays() {
    return stepInDays;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the settlement lag in days.
   * <p>
   * This is the number of days after the start date that any upfront fees are paid.
   * @return the value of the property
   */
  @Override
  public int getSettleLagDays() {
    return settleLagDays;
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
      ImmutableCdsConvention other = (ImmutableCdsConvention) obj;
      return JodaBeanUtils.equal(name, other.name) &&
          JodaBeanUtils.equal(currency, other.currency) &&
          JodaBeanUtils.equal(dayCount, other.dayCount) &&
          JodaBeanUtils.equal(businessDayAdjustment, other.businessDayAdjustment) &&
          JodaBeanUtils.equal(paymentFrequency, other.paymentFrequency) &&
          JodaBeanUtils.equal(rollConvention, other.rollConvention) &&
          (payAccruedOnDefault == other.payAccruedOnDefault) &&
          JodaBeanUtils.equal(stubConvention, other.stubConvention) &&
          (stepInDays == other.stepInDays) &&
          (settleLagDays == other.settleLagDays);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash = hash * 31 + JodaBeanUtils.hashCode(name);
    hash = hash * 31 + JodaBeanUtils.hashCode(currency);
    hash = hash * 31 + JodaBeanUtils.hashCode(dayCount);
    hash = hash * 31 + JodaBeanUtils.hashCode(businessDayAdjustment);
    hash = hash * 31 + JodaBeanUtils.hashCode(paymentFrequency);
    hash = hash * 31 + JodaBeanUtils.hashCode(rollConvention);
    hash = hash * 31 + JodaBeanUtils.hashCode(payAccruedOnDefault);
    hash = hash * 31 + JodaBeanUtils.hashCode(stubConvention);
    hash = hash * 31 + JodaBeanUtils.hashCode(stepInDays);
    hash = hash * 31 + JodaBeanUtils.hashCode(settleLagDays);
    return hash;
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code ImmutableCdsConvention}.
   */
  public static final class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code name} property.
     */
    private final MetaProperty<String> name = DirectMetaProperty.ofImmutable(
        this, "name", ImmutableCdsConvention.class, String.class);
    /**
     * The meta-property for the {@code currency} property.
     */
    private final MetaProperty<Currency> currency = DirectMetaProperty.ofImmutable(
        this, "currency", ImmutableCdsConvention.class, Currency.class);
    /**
     * The meta-property for the {@code dayCount} property.
     */
    private final MetaProperty<DayCount> dayCount = DirectMetaProperty.ofImmutable(
        this, "dayCount", ImmutableCdsConvention.class, DayCount.class);
    /**
     * The meta-property for the {@code businessDayAdjustment} property.
     */
    private final MetaProperty<BusinessDayAdjustment> businessDayAdjustment = DirectMetaProperty.ofImmutable(
        this, "businessDayAdjustment", ImmutableCdsConvention.class, BusinessDayAdjustment.class);
    /**
     * The meta-property for the {@code paymentFrequency} property.
     */
    private final MetaProperty<Frequency> paymentFrequency = DirectMetaProperty.ofImmutable(
        this, "paymentFrequency", ImmutableCdsConvention.class, Frequency.class);
    /**
     * The meta-property for the {@code rollConvention} property.
     */
    private final MetaProperty<RollConvention> rollConvention = DirectMetaProperty.ofImmutable(
        this, "rollConvention", ImmutableCdsConvention.class, RollConvention.class);
    /**
     * The meta-property for the {@code payAccruedOnDefault} property.
     */
    private final MetaProperty<Boolean> payAccruedOnDefault = DirectMetaProperty.ofImmutable(
        this, "payAccruedOnDefault", ImmutableCdsConvention.class, Boolean.TYPE);
    /**
     * The meta-property for the {@code stubConvention} property.
     */
    private final MetaProperty<StubConvention> stubConvention = DirectMetaProperty.ofImmutable(
        this, "stubConvention", ImmutableCdsConvention.class, StubConvention.class);
    /**
     * The meta-property for the {@code stepInDays} property.
     */
    private final MetaProperty<Integer> stepInDays = DirectMetaProperty.ofImmutable(
        this, "stepInDays", ImmutableCdsConvention.class, Integer.TYPE);
    /**
     * The meta-property for the {@code settleLagDays} property.
     */
    private final MetaProperty<Integer> settleLagDays = DirectMetaProperty.ofImmutable(
        this, "settleLagDays", ImmutableCdsConvention.class, Integer.TYPE);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "name",
        "currency",
        "dayCount",
        "businessDayAdjustment",
        "paymentFrequency",
        "rollConvention",
        "payAccruedOnDefault",
        "stubConvention",
        "stepInDays",
        "settleLagDays");

    /**
     * Restricted constructor.
     */
    private Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 3373707:  // name
          return name;
        case 575402001:  // currency
          return currency;
        case 1905311443:  // dayCount
          return dayCount;
        case -1065319863:  // businessDayAdjustment
          return businessDayAdjustment;
        case 863656438:  // paymentFrequency
          return paymentFrequency;
        case -10223666:  // rollConvention
          return rollConvention;
        case -43782841:  // payAccruedOnDefault
          return payAccruedOnDefault;
        case -31408449:  // stubConvention
          return stubConvention;
        case -1890516728:  // stepInDays
          return stepInDays;
        case 1060862270:  // settleLagDays
          return settleLagDays;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public ImmutableCdsConvention.Builder builder() {
      return new ImmutableCdsConvention.Builder();
    }

    @Override
    public Class<? extends ImmutableCdsConvention> beanType() {
      return ImmutableCdsConvention.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code name} property.
     * @return the meta-property, not null
     */
    public MetaProperty<String> name() {
      return name;
    }

    /**
     * The meta-property for the {@code currency} property.
     * @return the meta-property, not null
     */
    public MetaProperty<Currency> currency() {
      return currency;
    }

    /**
     * The meta-property for the {@code dayCount} property.
     * @return the meta-property, not null
     */
    public MetaProperty<DayCount> dayCount() {
      return dayCount;
    }

    /**
     * The meta-property for the {@code businessDayAdjustment} property.
     * @return the meta-property, not null
     */
    public MetaProperty<BusinessDayAdjustment> businessDayAdjustment() {
      return businessDayAdjustment;
    }

    /**
     * The meta-property for the {@code paymentFrequency} property.
     * @return the meta-property, not null
     */
    public MetaProperty<Frequency> paymentFrequency() {
      return paymentFrequency;
    }

    /**
     * The meta-property for the {@code rollConvention} property.
     * @return the meta-property, not null
     */
    public MetaProperty<RollConvention> rollConvention() {
      return rollConvention;
    }

    /**
     * The meta-property for the {@code payAccruedOnDefault} property.
     * @return the meta-property, not null
     */
    public MetaProperty<Boolean> payAccruedOnDefault() {
      return payAccruedOnDefault;
    }

    /**
     * The meta-property for the {@code stubConvention} property.
     * @return the meta-property, not null
     */
    public MetaProperty<StubConvention> stubConvention() {
      return stubConvention;
    }

    /**
     * The meta-property for the {@code stepInDays} property.
     * @return the meta-property, not null
     */
    public MetaProperty<Integer> stepInDays() {
      return stepInDays;
    }

    /**
     * The meta-property for the {@code settleLagDays} property.
     * @return the meta-property, not null
     */
    public MetaProperty<Integer> settleLagDays() {
      return settleLagDays;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 3373707:  // name
          return ((ImmutableCdsConvention) bean).getName();
        case 575402001:  // currency
          return ((ImmutableCdsConvention) bean).getCurrency();
        case 1905311443:  // dayCount
          return ((ImmutableCdsConvention) bean).getDayCount();
        case -1065319863:  // businessDayAdjustment
          return ((ImmutableCdsConvention) bean).getBusinessDayAdjustment();
        case 863656438:  // paymentFrequency
          return ((ImmutableCdsConvention) bean).getPaymentFrequency();
        case -10223666:  // rollConvention
          return ((ImmutableCdsConvention) bean).getRollConvention();
        case -43782841:  // payAccruedOnDefault
          return ((ImmutableCdsConvention) bean).isPayAccruedOnDefault();
        case -31408449:  // stubConvention
          return ((ImmutableCdsConvention) bean).getStubConvention();
        case -1890516728:  // stepInDays
          return ((ImmutableCdsConvention) bean).getStepInDays();
        case 1060862270:  // settleLagDays
          return ((ImmutableCdsConvention) bean).getSettleLagDays();
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
   * The bean-builder for {@code ImmutableCdsConvention}.
   */
  public static final class Builder extends DirectFieldsBeanBuilder<ImmutableCdsConvention> {

    private String name;
    private Currency currency;
    private DayCount dayCount;
    private BusinessDayAdjustment businessDayAdjustment;
    private Frequency paymentFrequency;
    private RollConvention rollConvention;
    private boolean payAccruedOnDefault;
    private StubConvention stubConvention;
    private int stepInDays;
    private int settleLagDays;

    /**
     * Restricted constructor.
     */
    private Builder() {
    }

    /**
     * Restricted copy constructor.
     * @param beanToCopy  the bean to copy from, not null
     */
    private Builder(ImmutableCdsConvention beanToCopy) {
      this.name = beanToCopy.getName();
      this.currency = beanToCopy.getCurrency();
      this.dayCount = beanToCopy.getDayCount();
      this.businessDayAdjustment = beanToCopy.getBusinessDayAdjustment();
      this.paymentFrequency = beanToCopy.getPaymentFrequency();
      this.rollConvention = beanToCopy.getRollConvention();
      this.payAccruedOnDefault = beanToCopy.isPayAccruedOnDefault();
      this.stubConvention = beanToCopy.getStubConvention();
      this.stepInDays = beanToCopy.getStepInDays();
      this.settleLagDays = beanToCopy.getSettleLagDays();
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
      switch (propertyName.hashCode()) {
        case 3373707:  // name
          return name;
        case 575402001:  // currency
          return currency;
        case 1905311443:  // dayCount
          return dayCount;
        case -1065319863:  // businessDayAdjustment
          return businessDayAdjustment;
        case 863656438:  // paymentFrequency
          return paymentFrequency;
        case -10223666:  // rollConvention
          return rollConvention;
        case -43782841:  // payAccruedOnDefault
          return payAccruedOnDefault;
        case -31408449:  // stubConvention
          return stubConvention;
        case -1890516728:  // stepInDays
          return stepInDays;
        case 1060862270:  // settleLagDays
          return settleLagDays;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
    }

    @Override
    public Builder set(String propertyName, Object newValue) {
      switch (propertyName.hashCode()) {
        case 3373707:  // name
          this.name = (String) newValue;
          break;
        case 575402001:  // currency
          this.currency = (Currency) newValue;
          break;
        case 1905311443:  // dayCount
          this.dayCount = (DayCount) newValue;
          break;
        case -1065319863:  // businessDayAdjustment
          this.businessDayAdjustment = (BusinessDayAdjustment) newValue;
          break;
        case 863656438:  // paymentFrequency
          this.paymentFrequency = (Frequency) newValue;
          break;
        case -10223666:  // rollConvention
          this.rollConvention = (RollConvention) newValue;
          break;
        case -43782841:  // payAccruedOnDefault
          this.payAccruedOnDefault = (Boolean) newValue;
          break;
        case -31408449:  // stubConvention
          this.stubConvention = (StubConvention) newValue;
          break;
        case -1890516728:  // stepInDays
          this.stepInDays = (Integer) newValue;
          break;
        case 1060862270:  // settleLagDays
          this.settleLagDays = (Integer) newValue;
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
    public ImmutableCdsConvention build() {
      return new ImmutableCdsConvention(
          name,
          currency,
          dayCount,
          businessDayAdjustment,
          paymentFrequency,
          rollConvention,
          payAccruedOnDefault,
          stubConvention,
          stepInDays,
          settleLagDays);
    }

    //-----------------------------------------------------------------------
    /**
     * Sets the convention name, such as 'USD-European'.
     * @param name  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder name(String name) {
      JodaBeanUtils.notNull(name, "name");
      this.name = name;
      return this;
    }

    /**
     * Sets the currency of the CDS.
     * @param currency  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder currency(Currency currency) {
      JodaBeanUtils.notNull(currency, "currency");
      this.currency = currency;
      return this;
    }

    /**
     * Sets the day count convention.
     * @param dayCount  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder dayCount(DayCount dayCount) {
      JodaBeanUtils.notNull(dayCount, "dayCount");
      this.dayCount = dayCount;
      return this;
    }

    /**
     * Sets the business day adjustment.
     * @param businessDayAdjustment  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder businessDayAdjustment(BusinessDayAdjustment businessDayAdjustment) {
      JodaBeanUtils.notNull(businessDayAdjustment, "businessDayAdjustment");
      this.businessDayAdjustment = businessDayAdjustment;
      return this;
    }

    /**
     * Sets the payment frequency.
     * @param paymentFrequency  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder paymentFrequency(Frequency paymentFrequency) {
      JodaBeanUtils.notNull(paymentFrequency, "paymentFrequency");
      this.paymentFrequency = paymentFrequency;
      return this;
    }

    /**
     * Sets the roll convention.
     * @param rollConvention  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder rollConvention(RollConvention rollConvention) {
      JodaBeanUtils.notNull(rollConvention, "rollConvention");
      this.rollConvention = rollConvention;
      return this;
    }

    /**
     * Sets whether the accrued premium is paid in the event of a default.
     * @param payAccruedOnDefault  the new value
     * @return this, for chaining, not null
     */
    public Builder payAccruedOnDefault(boolean payAccruedOnDefault) {
      this.payAccruedOnDefault = payAccruedOnDefault;
      return this;
    }

    /**
     * Sets the stub convention.
     * @param stubConvention  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder stubConvention(StubConvention stubConvention) {
      JodaBeanUtils.notNull(stubConvention, "stubConvention");
      this.stubConvention = stubConvention;
      return this;
    }

    /**
     * Sets the number of step-in days.
     * <p>
     * This is the date from which the issuer is deemed to be on risk.
     * @param stepInDays  the new value
     * @return this, for chaining, not null
     */
    public Builder stepInDays(int stepInDays) {
      this.stepInDays = stepInDays;
      return this;
    }

    /**
     * Sets the settlement lag in days.
     * <p>
     * This is the number of days after the start date that any upfront fees are paid.
     * @param settleLagDays  the new value
     * @return this, for chaining, not null
     */
    public Builder settleLagDays(int settleLagDays) {
      this.settleLagDays = settleLagDays;
      return this;
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder(352);
      buf.append("ImmutableCdsConvention.Builder{");
      buf.append("name").append('=').append(JodaBeanUtils.toString(name)).append(',').append(' ');
      buf.append("currency").append('=').append(JodaBeanUtils.toString(currency)).append(',').append(' ');
      buf.append("dayCount").append('=').append(JodaBeanUtils.toString(dayCount)).append(',').append(' ');
      buf.append("businessDayAdjustment").append('=').append(JodaBeanUtils.toString(businessDayAdjustment)).append(',').append(' ');
      buf.append("paymentFrequency").append('=').append(JodaBeanUtils.toString(paymentFrequency)).append(',').append(' ');
      buf.append("rollConvention").append('=').append(JodaBeanUtils.toString(rollConvention)).append(',').append(' ');
      buf.append("payAccruedOnDefault").append('=').append(JodaBeanUtils.toString(payAccruedOnDefault)).append(',').append(' ');
      buf.append("stubConvention").append('=').append(JodaBeanUtils.toString(stubConvention)).append(',').append(' ');
      buf.append("stepInDays").append('=').append(JodaBeanUtils.toString(stepInDays)).append(',').append(' ');
      buf.append("settleLagDays").append('=').append(JodaBeanUtils.toString(settleLagDays));
      buf.append('}');
      return buf.toString();
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
