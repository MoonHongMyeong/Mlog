package me.portfolio.blog.web;

import lombok.RequiredArgsConstructor;
import me.portfolio.blog.service.PostsService;
import me.portfolio.blog.web.dto.posts.PostsListResponseDto;
import me.portfolio.blog.web.dto.posts.PostsResponseDto;
import me.portfolio.blog.web.dto.posts.PostsSaveRequestDto;
import me.portfolio.blog.web.dto.posts.PostsUpdateRequestDto;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.util.List;

@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8000, http://localhost:3000")
@RequestMapping("/api")
@RestController
public class PostsApiController {

    private final PostsService postsService;

    //포스트 리스트 조회
    @GetMapping("/posts")
    public List<PostsListResponseDto> getPostList() throws Exception{
        return postsService.getPostList();
    }

    //포스트 검색 리스트 조회
    @GetMapping("/posts/search")
    public List<PostsListResponseDto> getListSearchTitle(@RequestParam("search") String title) throws Exception{
        return postsService.getListSearchTitle(title);
    }
    
    //포스트 등록
    @PostMapping("/posts")
    public Long addPost(MultipartHttpServletRequest request,
                        @RequestParam(value = "image", required = false) MultipartFile image,
                        @RequestParam("title")String title,
                        @RequestParam("author") String author,
                        @RequestParam("content") String content) throws Exception {

        //파일 저장
        if(image == null){
            PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                    .title(title)
                    .author(author)
                    .content(content)
                    .imageUrl("/images/default.png")
                    .build();

            return postsService.addPost(requestDto);

        }else {
            //로컬 테스트용
            String baseDir = "D:\\GitHub\\Blog-portfolio\\blog-springboot-react\\blog-frontend\\public\\images";
            //실제 서버 배포용
//            String baseDir = "/home/ec2-user/portfolio-blog.v1/blog-frontend/public/images";
            String filePath = baseDir + "\\" + image.getOriginalFilename();
            image.transferTo(new File(filePath));
            String fileName = image.getOriginalFilename();

            PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                    .title(title)
                    .author(author)
                    .content(content)
                    .imageUrl("/images/"+fileName)
                    .build();

            return postsService.addPost(requestDto);
        }


    }

    //포스트 조회
    @GetMapping("/posts/{postId}")
    public PostsResponseDto getPost(@PathVariable Long postId) throws Exception{
        return postsService.getPost(postId);
    }

    //포스트 수정
    @PutMapping("/posts/{postId}")
    public Long updatePost(@PathVariable Long postId, @RequestBody PostsUpdateRequestDto requestDto) throws Exception{
        return postsService.updatePost(postId, requestDto);
    }

    //포스트 삭제
    @DeleteMapping("/posts/{postId}")
    public String deletePost(@PathVariable Long postId) throws Exception{
        postsService.deletePost(postId);
        return postId+"번 포스트 삭제 완료";
    }


}
