/**
 * Copyright (C) 2016 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.strata.pricer.credit.cds;

import java.time.LocalDate;

import com.opengamma.strata.basics.ReferenceData;
import com.opengamma.strata.basics.currency.Currency;
import com.opengamma.strata.basics.currency.CurrencyAmount;
import com.opengamma.strata.collect.ArgChecker;
import com.opengamma.strata.collect.tuple.Triple;
import com.opengamma.strata.market.curve.CurveInfoType;
import com.opengamma.strata.market.sensitivity.PointSensitivityBuilder;
import com.opengamma.strata.pricer.common.PriceType;
import com.opengamma.strata.product.credit.ResolvedCds;
import com.opengamma.strata.product.credit.ResolvedCdsIndex;

/**
 * Pricer for CDS portfolio index based on ISDA standard model. 
 * <p>
 * The CDS index is priced as a single name CDS using a single credit curve rather than 
 * credit curves of constituent single names. 
 * <p>
 * {@code CreditRatesProvider} must contain the index credit curve as well as 
 * the information on the relevant recovery rate and index factor. 
 * <p>
 * This pricer invokes the implementation in {@link IsdaCdsProductPricer}. 
 */
public class IsdaHomogenousCdsIndexProductPricer {

  /**
   * Default implementation.
   */
  public static final IsdaHomogenousCdsIndexProductPricer DEFAULT =
      new IsdaHomogenousCdsIndexProductPricer(AccrualOnDefaultFormula.ORIGINAL_ISDA);

  /**
   * The pricer for single name CDS.
   */
  private final IsdaCdsProductPricer underlyingPricer;

  /**
   * Constructor specifying the formula to use for the accrued on default calculation.  
   * 
   * @param formula  the formula
   */
  public IsdaHomogenousCdsIndexProductPricer(AccrualOnDefaultFormula formula) {
    this.underlyingPricer = new IsdaCdsProductPricer(formula);
  }

  //-------------------------------------------------------------------------
  /**
   * Calculates the price of the CDS index product, which is the minus of the present value per unit notional. 
<<<<<<< HEAD
=======
   * <p>
   * This method can calculate the clean or dirty price, see {@link PriceType}. 
   * If calculating the clean price, the accrued interest is calculated based on the step-in date.
>>>>>>> topic/cds-merge
   * 
   * @param cdsIndex  the product
   * @param ratesProvider  the rates provider
   * @param referenceDate  the reference date
   * @param priceType  the price type
   * @param refData  the reference data
   * @return the price
   */
  public double price(
      ResolvedCdsIndex cdsIndex,
      CreditRatesProvider ratesProvider,
      LocalDate referenceDate,
      PriceType priceType,
      ReferenceData refData) {

    ResolvedCds cds = cdsIndex.toSingleNameCds();
    return underlyingPricer.price(cds, ratesProvider, referenceDate, priceType, refData);
  }

  /**
   * Calculates the price sensitivity of the product. 
   * <p>
   * The price sensitivity of the product is the sensitivity of price to the underlying curves.
   * 
   * @param cdsIndex  the product 
   * @param ratesProvider  the rates provider
   * @param referenceDate  the reference date
   * @param refData  the reference data
   * @return the present value sensitivity
   */
  public PointSensitivityBuilder priceSensitivity(
      ResolvedCdsIndex cdsIndex,
      CreditRatesProvider ratesProvider,
      LocalDate referenceDate,
      ReferenceData refData) {

    ResolvedCds cds = cdsIndex.toSingleNameCds();
    return underlyingPricer.priceSensitivity(cds, ratesProvider, referenceDate, refData);
  }

  //-------------------------------------------------------------------------
  /**
   * Calculates the present value of the CDS index product.
   * <p>
   * The present value of the product is based on {@code referenceDate}.
   * This is typically the valuation date, or cash settlement date if the product is associated with a {@code Trade}. 
   * <p>
<<<<<<< HEAD
   * The price type is clean or dirty. The accrued interest is computed based on the valuation date.
=======
   * This method can calculate the clean or dirty present value, see {@link PriceType}. 
   * If calculating the clean value, the accrued interest is calculated based on the step-in date.
>>>>>>> topic/cds-merge
   * 
   * @param cdsIndex  the product
   * @param ratesProvider  the rates provider
   * @param referenceDate  the reference date
   * @param priceType  the price type
   * @param refData  the reference data
   * @return the present value
   */
  public CurrencyAmount presentValue(
      ResolvedCdsIndex cdsIndex,
      CreditRatesProvider ratesProvider,
      LocalDate referenceDate,
      PriceType priceType,
      ReferenceData refData) {

    if (isExpired(cdsIndex, ratesProvider)) {
      return CurrencyAmount.of(cdsIndex.getCurrency(), 0d);
    }
    ResolvedCds cds = cdsIndex.toSingleNameCds();
    LocalDate stepinDate = cds.getStepinDateOffset().adjust(ratesProvider.getValuationDate(), refData);
    LocalDate effectiveStartDate = cds.calculateEffectiveStartDate(stepinDate);
    double recoveryRate = underlyingPricer.recoveryRate(cds, ratesProvider);
    Triple<CreditDiscountFactors, LegalEntitySurvivalProbabilities, Double> rates = reduceDiscountFactors(cds, ratesProvider);
    double protectionLeg = (1d - recoveryRate) *
        underlyingPricer.protectionFull(cds, rates.getFirst(), rates.getSecond(), referenceDate, effectiveStartDate);
    double rpv01 = underlyingPricer.riskyAnnuity(
        cds, rates.getFirst(), rates.getSecond(), referenceDate, stepinDate, effectiveStartDate, priceType);
    double amount =
        cds.getBuySell().normalize(cds.getNotional()) * rates.getThird() * (protectionLeg - rpv01 * cds.getFixedRate());
    return CurrencyAmount.of(cds.getCurrency(), amount);
  }

  /**
   * Calculates the present value sensitivity of the product. 
   * <p>
   * The present value sensitivity of the product is the sensitivity of present value to the underlying curves.
   * 
   * @param cdsIndex  the product 
   * @param ratesProvider  the rates provider
   * @param referenceDate  the reference date
   * @param refData  the reference data
   * @return the present value sensitivity
   */
  public PointSensitivityBuilder presentValueSensitivity(
      ResolvedCdsIndex cdsIndex,
      CreditRatesProvider ratesProvider,
      LocalDate referenceDate,
      ReferenceData refData) {

    if (isExpired(cdsIndex, ratesProvider)) {
      return PointSensitivityBuilder.none();
    }
    ResolvedCds cds = cdsIndex.toSingleNameCds();
    LocalDate stepinDate = cds.getStepinDateOffset().adjust(ratesProvider.getValuationDate(), refData);
    LocalDate effectiveStartDate = cds.calculateEffectiveStartDate(stepinDate);
    double recoveryRate = underlyingPricer.recoveryRate(cds, ratesProvider);
    Triple<CreditDiscountFactors, LegalEntitySurvivalProbabilities, Double> rates = reduceDiscountFactors(cds, ratesProvider);

    double signedNotional = cds.getBuySell().normalize(cds.getNotional());
    PointSensitivityBuilder protectionLegSensi = underlyingPricer.protectionLegSensitivity(
        cds, rates.getFirst(), rates.getSecond(), referenceDate, effectiveStartDate, recoveryRate);
    protectionLegSensi = protectionLegSensi.multipliedBy(signedNotional * rates.getThird());
    PointSensitivityBuilder riskyAnnuitySensi = underlyingPricer.riskyAnnuitySensitivity(
        cds, rates.getFirst(), rates.getSecond(), referenceDate, stepinDate, effectiveStartDate);
    riskyAnnuitySensi = riskyAnnuitySensi.multipliedBy(-cds.getFixedRate() * signedNotional * rates.getThird());

    return protectionLegSensi.combinedWith(riskyAnnuitySensi);
  }

  //-------------------------------------------------------------------------
  /**
   * Calculates the par spread of the CDS index product.
   * <p>
   * The par spread is a coupon rate such that the clean PV is 0. 
   * The result is represented in decimal form. 
   * 
   * @param cdsIndex  the product
   * @param ratesProvider  the rates provider
   * @param referenceDate  the reference date
   * @param refData  the reference data
   * @return the par spread
   */
  public double parSpread(
      ResolvedCdsIndex cdsIndex,
      CreditRatesProvider ratesProvider,
      LocalDate referenceDate,
      ReferenceData refData) {

    ResolvedCds cds = cdsIndex.toSingleNameCds();
    return underlyingPricer.parSpread(cds, ratesProvider, referenceDate, refData);
  }

  /**
   * Calculates the par spread sensitivity of the product.
   * <p>
   * The par spread sensitivity of the product is the sensitivity of par spread to the underlying curves.
   * The resulting sensitivity is based on the currency of the CDS index product.
   * 
   * @param cdsIndex  the product
   * @param ratesProvider  the rates provider
   * @param referenceDate  the reference date
   * @param refData  the reference data
   * @return the par spread
   */
  public PointSensitivityBuilder parSpreadSensitivity(
      ResolvedCdsIndex cdsIndex,
      CreditRatesProvider ratesProvider,
      LocalDate referenceDate,
      ReferenceData refData) {

    ResolvedCds cds = cdsIndex.toSingleNameCds();
    return underlyingPricer.parSpreadSensitivity(cds, ratesProvider, referenceDate, refData);
  }

  //-------------------------------------------------------------------------
  /**
   * Calculates the risky PV01 of the CDS index product. 
   * <p>
   * RPV01 is defined as minus of the present value sensitivity to coupon rate.
   * 
   * @param cdsIndex  the product
   * @param ratesProvider  the rates provider
   * @param referenceDate  the reference date
   * @param priceType  the price type
   * @param refData  the reference date
   * @return the RPV01
   */
  public CurrencyAmount rpv01(
      ResolvedCdsIndex cdsIndex,
      CreditRatesProvider ratesProvider,
      LocalDate referenceDate,
      PriceType priceType,
      ReferenceData refData) {

    if (isExpired(cdsIndex, ratesProvider)) {
      return CurrencyAmount.of(cdsIndex.getCurrency(), 0d);
    }
    ResolvedCds cds = cdsIndex.toSingleNameCds();
    LocalDate stepinDate = cds.getStepinDateOffset().adjust(ratesProvider.getValuationDate(), refData);
    LocalDate effectiveStartDate = cds.calculateEffectiveStartDate(stepinDate);
    Triple<CreditDiscountFactors, LegalEntitySurvivalProbabilities, Double> rates = reduceDiscountFactors(cds, ratesProvider);
    double riskyAnnuity = underlyingPricer.riskyAnnuity(
        cds, rates.getFirst(), rates.getSecond(), referenceDate, stepinDate, effectiveStartDate, priceType);
    double amount = cds.getBuySell().normalize(cds.getNotional()) * riskyAnnuity * rates.getThird();
    return CurrencyAmount.of(cds.getCurrency(), amount);
  }

  //-------------------------------------------------------------------------
  /**
   * Calculates the recovery01 of the CDS index product.
   * <p>
   * The recovery01 is defined as the present value sensitivity to the recovery rate.
   * Since the ISDA standard model requires the recovery rate to be constant throughout the lifetime of the CDS index,  
   * one currency amount is returned by this method.
   * 
   * @param cdsIndex  the product
   * @param ratesProvider  the rates provider
   * @param referenceDate  the reference date
   * @param refData  the reference data
   * @return the recovery01
   */
  public CurrencyAmount recovery01(
      ResolvedCdsIndex cdsIndex,
      CreditRatesProvider ratesProvider,
      LocalDate referenceDate,
      ReferenceData refData) {

    if (isExpired(cdsIndex, ratesProvider)) {
      return CurrencyAmount.of(cdsIndex.getCurrency(), 0d);
    }
    ResolvedCds cds = cdsIndex.toSingleNameCds();
    LocalDate stepinDate = cds.getStepinDateOffset().adjust(ratesProvider.getValuationDate(), refData);
    LocalDate effectiveStartDate = cds.calculateEffectiveStartDate(stepinDate);
    underlyingPricer.validateRecoveryRates(cds, ratesProvider);
    Triple<CreditDiscountFactors, LegalEntitySurvivalProbabilities, Double> rates = reduceDiscountFactors(cds, ratesProvider);
    double protectionFull =
        underlyingPricer.protectionFull(cds, rates.getFirst(), rates.getSecond(), referenceDate, effectiveStartDate);
    double amount = -cds.getBuySell().normalize(cds.getNotional()) * protectionFull * rates.getThird();
    return CurrencyAmount.of(cds.getCurrency(), amount);
  }

  //-------------------------------------------------------------------------
  boolean isExpired(ResolvedCdsIndex index, CreditRatesProvider ratesProvider) {
    return !index.getProtectionEndDate().isAfter(ratesProvider.getValuationDate());
  }

  Triple<CreditDiscountFactors, LegalEntitySurvivalProbabilities, Double> reduceDiscountFactors(
      ResolvedCds cds,
      CreditRatesProvider ratesProvider) {

    Currency currency = cds.getCurrency();
    CreditDiscountFactors discountFactors = ratesProvider.discountFactors(currency);
    ArgChecker.isTrue(discountFactors.isIsdaCompliant(), "discount factors must be IsdaCompliantZeroRateDiscountFactors");
    LegalEntitySurvivalProbabilities survivalProbabilities =
        ratesProvider.survivalProbabilities(cds.getLegalEntityId(), currency);
    ArgChecker.isTrue(survivalProbabilities.getSurvivalProbabilities().isIsdaCompliant(),
        "survival probabilities must be IsdaCompliantZeroRateDiscountFactors");
    ArgChecker.isTrue(discountFactors.getDayCount().equals(survivalProbabilities.getSurvivalProbabilities().getDayCount()),
        "day count conventions of discounting curve and credit curve must be the same");
    double indexFactor = ((IsdaCompliantZeroRateDiscountFactors) survivalProbabilities.getSurvivalProbabilities())
        .getCurve().getMetadata().getInfo(CurveInfoType.CDS_INDEX_FACTOR);
    return Triple.of(discountFactors, survivalProbabilities, indexFactor);
  }

}
