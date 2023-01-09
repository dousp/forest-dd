package com.dtflys.test.http.address;

import com.dtflys.forest.annotation.Address;
import com.dtflys.forest.annotation.Headers;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Address(host = "localhost", port = "{port}", basePath = "abc")
@Headers({"Accept: text/plain"})
public @interface MyClient {
}
