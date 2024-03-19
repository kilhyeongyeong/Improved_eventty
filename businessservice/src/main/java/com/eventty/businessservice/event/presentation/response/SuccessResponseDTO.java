package com.eventty.businessservice.event.presentation.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;


@Getter
public class SuccessResponseDTO<T>{

    private final T data;

    @JsonCreator
    private SuccessResponseDTO(@JsonProperty("data") T data) {
        this.data = data;
    }

    public static <T> SuccessResponseDTO<T> of(T data) {
        return new SuccessResponseDTO<>(data);
    }
}

/* SuccessResponseDTO "역직렬화" 하는 방법
Jackson 라이브러리는 기본 생성자를 필요로 하기 때문에 기본 생성자가 없다면 문제가 발생합니다.

1. @NoArgsConstructor(force = true)

2.   @JsonCreator
    public SuccessResponseDTO(@JsonProperty("data") T data) {
        this.data = data;
    }

3. public record SuccessResponseDTO<T>(T data) {

    public static <T> SuccessResponseDTO<T> of(T data) {return new SuccessResponseDTO<>(data);}
}
*/