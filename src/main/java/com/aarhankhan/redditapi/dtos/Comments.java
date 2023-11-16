package com.aarhankhan.redditapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comments {
    private String text;
    private String author;
    private List<Comments> children;
}
