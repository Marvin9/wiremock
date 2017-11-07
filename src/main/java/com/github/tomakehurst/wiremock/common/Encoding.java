/*
 * Copyright (C) 2011 Thomas Akehurst
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.tomakehurst.wiremock.common;

import java.util.Base64; 

public class Encoding {

    public static byte[] decodeBase64(String base64) {
        return base64 != null ?
            Base64.getDecoder().decode(base64) :
            null;
    }

    public static String encodeBase64(byte[] content) {
        return content != null ?
            Base64.getEncoder().encodeToString(content) :
            null;
    }
}
