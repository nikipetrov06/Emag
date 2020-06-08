package com.example.emag.utils;

import java.math.BigDecimal;

public final class Constants {

  private Constants() {
    throw new AssertionError();
  }

  public static final String SESSION_KEY_LOGGED_USER = "logged_user";
  public static final String FIRST_NAME = "^[A-Za-z][A-Za-z]*$";
  public static final String LAST_NAME = "^[A-Za-z][A-Za-z]*$";
  public static final String EMAIL = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
  public static final String PASSWORD = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
  public static final BigDecimal MIN_PRICE_OF_PRODUCT = BigDecimal.ZERO;
  public static final BigDecimal MAX_PRICE_OF_PRODUCT = BigDecimal.valueOf(Integer.MAX_VALUE);
}
