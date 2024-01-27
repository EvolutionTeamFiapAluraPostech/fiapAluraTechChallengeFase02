package br.com.digitalparking.parking.model.message;

public final class ParkingMessages {

  public static final String PARKING_PARKING_TYPE_INVALID = "Parking type is invalid.";
  public static final String PARKING_PARKING_TIME_INVALID = "Parking time is invalid. It must be greater than zero.";
  public static final String PARKING_NOT_FOUND = "Parking is not found with ID %s.";
  public static final String PARKING_PIX_PAYMENT_METHOD_INVALID = "Pix payment method is invalid for parking type %s.";
  public static final String PARKING_PAYMENT_VALUE_INVALID = "Payment value must be greater than zero.";
  public static final String PARKING_PAYMENT_HAS_ALREADY_BEEN_PAID = "Payment has already been paid.";
  public static final String PARKING_PAYMENT_WAS_NOT_PAID = "Payment was not paid.";
  public static final String PARKING_HAS_ALREADY_BEEN_CLOSED = "Parking has already been closed.";
}
