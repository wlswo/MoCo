package com.board.board.MethodTest;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashSet;

@SpringBootTest
public class HashSetTest {

    @Test
    public void removeSet() {
        /* 기존 해시태그 */
        HashSet<String> s1 = new HashSet<>(Arrays.asList("C","C#","JAVA","SPRING"));
        /* 수정된 해시태그 */
        HashSet<String> s2 = new HashSet<>(Arrays.asList("C","C#","SPRINGBOOT"));

        HashSet<String> substract = new HashSet<>(s1);  // s1으로 substract 생성
        substract.removeAll(s2);        // 차집합 수행
        System.out.println(substract);  // [JAVA, SPRING] 삭제됨

        HashSet<String> substract2 = new HashSet<>(s2);  // s1으로 substract 생성
        substract2.removeAll(s1);        // 차집합 수행
        System.out.println(substract2);  // [SPRINGBOOT] 추가됨
    }
}
