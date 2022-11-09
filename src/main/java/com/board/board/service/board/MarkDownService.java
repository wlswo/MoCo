package com.board.board.service.board;

import io.github.furstenheim.CopyDown;
import org.springframework.stereotype.Service;

@Service
public class MarkDownService {

    /* Html -> MarkDown */
    public String convertHtmlToMarkDown(String Content) {
        CopyDown converter = new CopyDown();
        return converter.convert(Content);
    }
}
