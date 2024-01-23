package br.com.digitalparking.parking.infrastructure.httpclient;

import br.com.digitalparking.parking.presentation.dto.ParkingOutputDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "ParkingNotification", url = "http://localhost:8081")
public interface ParkingNotification {

  @RequestMapping(value = "/notifications/{uuid}", method = RequestMethod.PUT)
  void notifyParkingPayment(@PathVariable String uuid, @RequestBody ParkingOutputDto parking);
}
