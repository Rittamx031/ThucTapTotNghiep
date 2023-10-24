package stock.thuctap.stock.repos;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import stock.thuctap.stock.domain.Blog;
import stock.thuctap.stock.domain.SockDetail;


public interface BlogRepository extends JpaRepository<Blog, Integer> {

    Blog findFirstBySockDetailBlogSockDetails(SockDetail sockDetail);

    List<Blog> findAllBySockDetailBlogSockDetails(SockDetail sockDetail);

}
