package com.tank.invoice;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import strman.Strman;

import java.beans.Transient;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author tank198435163.com
 */
@Getter
@Setter
@Accessors(chain = true)
public class InvoiceReq implements Serializable {

  /**
   * orgCode : x
   * orgName :
   * transNo :
   * orderNo :
   * oldTransNo : null
   * totalAmt : 89.9
   * buyerTaxNo : null
   * buyerName :
   * buyerEmail :
   * buyerPhone : null
   * buyerCorporateAddress : null
   * buyerCorporatePhone : null
   * buyerBankName : null
   * buyerBankAccount : null
   * remarks : null
   * goodsList : [{"goodsCode":"","goodsName":"","goodsSpec":null,"goodsUnit":null,"goodsQuantity":10,"goodsPrice":10.12,"goodsAmt":10}]
   * orderMergeList : [{"transNo":"","orderNo":null,"totalAmt":10}]
   */

  private String orgCode;
  private String orgName;
  private String transNo;
  private String orderNo;
  private String oldTransNo;
  private Double totalAmt;
  private String buyerTaxNo;
  private String buyerName;
  private String buyerEmail;
  private String buyerPhone;
  private String buyerCorporateAddress;
  private String buyerCorporatePhone;
  private String buyerBankName;
  private String buyerBankAccount;
  private String remarks;
  private Set<GoodsListBean> goods = new HashSet<>();
  private Set<OrderMergeListBean> orderMerges = new HashSet<>();

  /**
   * 判断所属class的字段
   *
   * @param instance
   * @param fields
   * @param <T>
   * @return
   */
  @Transient
  private <T> Optional<String> mustFieldIsOk(@NonNull final T instance, String... fields) {
    Set<String> mustFields = Arrays.stream(fields)
        .map(field -> String.format("set%s", field))
        .map(Strman::toCamelCase)
        .collect(Collectors.toSet());

    Set<Method> methods = Arrays.stream(instance.getClass().getDeclaredMethods())
        .filter(m -> mustFields.contains(m.getName()))
        .collect(Collectors.toSet());

    try {
      for (Method method : methods) {
        Object result = method.invoke(instance);
        if (Objects.isNull(result)) {
          String fieldName = method.getName().substring(3);
          return Optional.ofNullable(String.format("字段[%s]必填", fieldName));
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return Optional.empty();
  }


  @Transient
  public Optional<String> mustFieldsIsOk() {

    /**
     * orgCode	String	36	是
     * orgName	String	150	是
     * transNo	String	36	是
     * totalAmt	Double	10	是
     * buyerName	String	80	是
     * buyerEmail	String	36	是
     */

    Optional<String> result = this.mustFieldIsOk(this,
        "orgCode", "orgName",
        "transNo", "totalAmt",
        "buyerName", "buyerEmail");

    if (result.isPresent()) {
      return result;
    }

    if (this.goods.isEmpty()) {
      return Optional.ofNullable("商品列表是空");
    }

    for (GoodsListBean good : this.goods) {
      if (Objects.isNull(good)) {
        continue;
      }
      result = this.mustFieldIsOk(good, "goodsCode", "goodsName", "goodsQuantity", "goodsPrice", "goodsAmt");
      if (result.isPresent()) {
        return result;
      }
    }
    return Optional.empty();
  }


  @Transient
  public void addGoodsListBean(@NonNull final GoodsListBean goodsListBean) {
    this.goods.add(goodsListBean);
  }

  @Transient
  public void addOrderMergeListBean(@NonNull final OrderMergeListBean orderMergeListBean) {
    this.orderMerges.add(orderMergeListBean);
  }

  @Getter
  @Setter
  @EqualsAndHashCode(of = {"goodsCode"})
  public static class GoodsListBean {
    /**
     * goodsCode :
     * goodsName :
     * goodsSpec : null
     * goodsUnit : null
     * goodsQuantity : 10
     * goodsPrice : 10.12
     * goodsAmt : 10
     */

    private String goodsCode;
    private String goodsName;
    private String goodsSpec;
    private String goodsUnit;
    private int goodsQuantity;
    private double goodsPrice;
    private int goodsAmt;
  }

  @Getter
  @Setter
  @EqualsAndHashCode(of = {"of"})
  public static class OrderMergeListBean {
    /**
     * transNo :
     * orderNo : null
     * totalAmt : 10
     */
    private String transNo;
    private String orderNo;
    private int totalAmt;
  }
}