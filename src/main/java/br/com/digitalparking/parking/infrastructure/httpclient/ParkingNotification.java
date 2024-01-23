package br.com.digitalparking.parking.infrastructure.httpclient;

import br.com.digitalparking.parking.presentation.dto.ParkingOutputDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "ParkingNotification", url = "${base.url.http-payment-notification}")
public interface ParkingNotification {

  @PutMapping("/notifications/{uuid}")
  void notifyParkingPayment(@PathVariable String uuid, @RequestBody ParkingOutputDto parking);
}
