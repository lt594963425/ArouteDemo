/*
 * Copyright 2016 jeasonlzy(廖子尧)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.modulebase.data.model;

import java.io.Serializable;

public class ServerResponse<T> implements Serializable {

    public int code;
    private String msg_customer;
    private String msg_programmer;
    public T data;

    public String getMsg_customer() {
        return msg_customer;
    }

    public void setMsg_customer(String msg_customer) {
        this.msg_customer = msg_customer;
    }

    public String getMsg_programmer() {
        return msg_programmer;
    }

    public void setMsg_programmer(String msg_programmer) {
        this.msg_programmer = msg_programmer;
    }

    @Override
    public String toString() {
        return "ServerResponse{" +
                "code=" + code +
                ", msg_customer='" + msg_customer + '\'' +
                ", msg_programmer='" + msg_programmer + '\'' +
                ", data=" + data +
                '}';
    }
}
