package me.portfolio.blog.web.dto.posts;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.File;

@Getter
@NoArgsConstructor
public class ImageRequestDto {
    private File file;
    private String fileName;
}
