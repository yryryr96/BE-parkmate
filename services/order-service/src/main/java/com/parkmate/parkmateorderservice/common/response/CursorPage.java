package com.parkmate.parkmateorderservice.common.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class CursorPage<T> {

    private List<T> content;
    private Boolean hasNext;
    private Long nextCursor;

    @Builder
    private CursorPage(List<T> content, Boolean hasNext, Long nextCursor) {
        this.content = content;
        this.hasNext = hasNext;
        this.nextCursor = nextCursor;
    }

    public <U> CursorPage<U> map(Function<? super T, ? extends U> mapper) {
        List<U> mappedContent = this.content.stream()
                .map(mapper)
                .collect(Collectors.toList());
        return CursorPage.<U>builder()
                .content(mappedContent)
                .hasNext(this.hasNext)
                .nextCursor(this.nextCursor)
                .build();
    }

    public static <T> CursorPage<T> of(List<T> content, Boolean hasNext, Long nextCursor) {
        return CursorPage.<T>builder()
                .content(content)
                .hasNext(hasNext)
                .nextCursor(nextCursor)
                .build();
    }
}
