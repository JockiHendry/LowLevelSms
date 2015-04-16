/*
 * Copyright 2015 Jocki Hendry
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jocki.com.lowlevelsms.domain;

public class SmsInfo {

    private Long id;
    private String format;
    private Long subscription;
    private Integer slot;
    private Integer phone;
    private Integer errorCode;
    private Object pdus[];

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Object[] getPdus() {
        return pdus;
    }

    public void setPdus(Object[] pdus) {
        this.pdus = pdus;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Long getSubscription() {
        return subscription;
    }

    public void setSubscription(Long subscription) {
        this.subscription = subscription;
    }

    public Integer getSlot() {
        return slot;
    }

    public void setSlot(Integer slot) {
        this.slot = slot;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Id: ");
        result.append(getId());
        result.append(", Format: ");
        result.append(format);
        if (getSubscription() != null) {
            result.append(", Subsr: ");
            result.append(getSubscription());
        }
        if (getSlot() != null) {
            result.append(", Slot:");
            result.append(getSlot());
        }
        if (getPhone() != null) {
            result.append(", Phone: ");
            result.append(getPhone());
        }
        if (getErrorCode() != null) {
            result.append(", Err: ");
            result.append(getErrorCode());
        }
        return result.toString();
    }

}
