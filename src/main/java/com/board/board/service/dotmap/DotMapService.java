package com.board.board.service.dotmap;

import com.board.board.repository.DotMapRepositoey;
import com.board.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DotMapService {
    private final DotMapRepositoey dotMapRepositoey;
    private final UserRepository userRepository;

    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    /* CREATE (도트맵 구매) */
}
