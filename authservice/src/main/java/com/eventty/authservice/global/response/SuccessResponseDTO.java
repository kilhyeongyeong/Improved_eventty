package com.eventty.authservice.global.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
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

/* Java 'record'와 일반적인 클래스의 차이점
1. 생성자의 차이: record는 명시적으로 생성자를 작성하지 않아도 자동으로 생성자를 생성합니다.
위의 SuccessResponseDTO record에서는 data 필드에 대한 생성자가 자동으로 만들어졌습니다. 반면 일반 클래스에서는 명시적으로 생성자를 작성해야 합니다.

2. 접근자와 변경자: record는 자동으로 모든 필드에 대한 접근자(getter)를 생성하며, 해당 필드는 변경할 수 없습니다(즉, final입니다).
일반 클래스에서는 Lombok의 @Getter 및 @Setter를 사용하여 접근자와 변경자를 생성해야 합니다.

3. equals(), hashCode(), toString(): record는 이 메서드들을 자동으로 구현합니다. 이는 주로 데이터를 나타내는 클래스에 유용합니다.
Lombok에서 @Data나 @Value 어노테이션을 사용하여 이러한 메서드들을 자동으로 생성할 수 있습니다.
 */
