package br.com.digitalparking.shared.api;

import br.com.digitalparking.user.model.entity.User;
import java.util.List;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

public class PageUtil {

  public static final int PAGE_NUMBER = 0;
  public static final int PAGE_SIZE = 1;

  public static PageImpl<User> generatePageOfUser(User user) {
    var pageNumber = 0;
    var pageSize = 2;
    var totalItems = 3;
    var pageable = PageRequest.of(pageNumber, pageSize);
    return new PageImpl<>(List.of(user), pageable, totalItems);
  }
}
