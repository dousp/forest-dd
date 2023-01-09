package com.dtflys.test.http.annmerge;

import com.dtflys.forest.annotation.Address;
import com.dtflys.forest.annotation.Headers;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Headers({
        "Accept: text/plain",
        "Content-Type: application/json"
})
@Address(port = "${port}")
public @interface MyHeaders {
}
