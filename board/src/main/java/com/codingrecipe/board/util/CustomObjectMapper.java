package com.codingrecipe.board.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


/*
* ✅ 직렬화 : 객체들의 데이터를 연속적인 데이터(스트림)로 변형하여 전송 가능한 형태로 만드는 것

✅ 역직렬화 : 직렬화된 데이터를 다시 객체의 형태로 만드는 것

객체 데이터를 통신하기 쉬운 포멧(Byte,CSV,Json..) 형태로 만들어주는 작업을 직렬화라고 볼 수 있고,

역으로, 포멧(Byte,CSV,Json..) 형태에서 객체로 변환하는 과정을 역직렬화라고 할 수 있겠다.
*
* */


/*ObjectMapper
*
* json -> java 변환 혹은 java -> json 변환할때 사용
* */
@Component
public class CustomObjectMapper extends ObjectMapper {


    /*모든 클래스는 UID를 가지고있는데 Class 내용이 변경되면 UID 값이 바뀌어 지므로 고유값을 미리 명시 ,역 직렬화시 직렬화 대상 객체가 동일한 UID 를 가지고있어야 함 */
    private static final long serialVersionUID = -2148669317097583174L;

    public CustomObjectMapper(){
        SimpleModule simpleModule = new SimpleModule(); //SimpleModule : 커스터마이징 된 Serializer/Deserializer를 등록.
        simpleModule.addSerializer(Number.class, new NumberToStringSerializer());
        registerModule(simpleModule);
        getSerializerProvider().setNullValueSerializer(new NullSerializer());
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // 없는 필드로 인한 오류 무시
    }


}
