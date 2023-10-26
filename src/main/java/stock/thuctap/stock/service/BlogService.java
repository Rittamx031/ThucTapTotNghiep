package stock.thuctap.stock.service;

import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import stock.thuctap.stock.domain.Blog;
import stock.thuctap.stock.domain.SockDetail;
import stock.thuctap.stock.model.BlogDTO;
import stock.thuctap.stock.repos.BlogRepository;
import stock.thuctap.stock.repos.SockDetailRepository;
import stock.thuctap.stock.util.NotFoundException;

@Service
@Transactional
public class BlogService {

    private final BlogRepository blogRepository;
    private final SockDetailRepository sockDetailRepository;

    public BlogService(final BlogRepository blogRepository,
            final SockDetailRepository sockDetailRepository) {
        this.blogRepository = blogRepository;
        this.sockDetailRepository = sockDetailRepository;
    }

    public List<BlogDTO> findAll() {
        final List<Blog> blogs = blogRepository.findAll(Sort.by("id"));
        return blogs.stream()
                .map(blog -> mapToDTO(blog, new BlogDTO()))
                .toList();
    }

    public BlogDTO get(final Integer id) {
        return blogRepository.findById(id)
                .map(blog -> mapToDTO(blog, new BlogDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final BlogDTO blogDTO) {
        blogDTO.setDateCreate(LocalDate.now());
        final Blog blog = new Blog();
        mapToEntity(blogDTO, blog);
        return blogRepository.save(blog).getId();
    }

    public void update(final Integer id, final BlogDTO blogDTO) {
        blogDTO.setDateUpdate(LocalDate.now());
        final Blog blog = blogRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(blogDTO, blog);
        blogRepository.save(blog);
    }

    public void delete(final Integer id) {
        blogRepository.deleteById(id);
    }

    private BlogDTO mapToDTO(final Blog blog, final BlogDTO blogDTO) {
        blogDTO.setId(blog.getId());
        blogDTO.setTitle(blog.getTitle());
        blogDTO.setPath(blog.getPath());
        blogDTO.setDateCreate(blog.getDateCreate());
        blogDTO.setDateUpdate(blog.getDateUpdate());
        blogDTO.setContent(blog.getContent());
        blogDTO.setStatus(blog.getStatus());
        blogDTO.setSockDetailBlogSockDetails(blog.getSockDetailBlogSockDetails().stream()
                .map(sockDetail -> sockDetail.getId())
                .toList());
        return blogDTO;
    }

    private Blog mapToEntity(final BlogDTO blogDTO, final Blog blog) {
        blog.setTitle(blogDTO.getTitle());
        blog.setPath(blogDTO.getPath());
        blog.setDateCreate(blogDTO.getDateCreate());
        blog.setDateUpdate(blogDTO.getDateUpdate());
        blog.setContent(blogDTO.getContent());
        blog.setStatus(blogDTO.getStatus());
        final List<SockDetail> sockDetailBlogSockDetails = sockDetailRepository.findAllById(
                blogDTO.getSockDetailBlogSockDetails() == null ? Collections.emptyList()
                        : blogDTO.getSockDetailBlogSockDetails());
        if (sockDetailBlogSockDetails.size() != (blogDTO.getSockDetailBlogSockDetails() == null ? 0
                : blogDTO.getSockDetailBlogSockDetails().size())) {
            throw new NotFoundException("one of sockDetailBlogSockDetails not found");
        }
        blog.setSockDetailBlogSockDetails(sockDetailBlogSockDetails.stream().collect(Collectors.toSet()));
        return blog;
    }

}
