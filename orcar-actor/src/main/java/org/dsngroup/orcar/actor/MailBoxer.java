/*
 * Copyright (c) 2017 original authors and authors.
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

package org.dsngroup.orcar.actor;

import com.google.gson.Gson;

/**
 * The MailBoxer resolves the transformation form object to json string or reverse.
 */
public class MailBoxer<T> {

    private T mailObject;
    private String mailString;

    /**
     * The constructor from string to instantiate object.
     * @param mailString The json string of mail object.
     * @throws Exception parsing failed exception.
     */
    public MailBoxer(String mailString) throws Exception {
        this.mailString = mailString;
        Gson gson = new Gson();
        // TODO: Problem may come here, the mailObject may not be transfer.
        this.mailObject = gson.fromJson(mailString, this.getClass().getTypeParameters()[0]);
    }

    /**
     * The constructor from object to json string
     * @param mailObject The class object.
     * @throws Exception Parsing failed.
     */
    public MailBoxer(Object mailObject) throws Exception {
        this.mailObject = (T) mailObject;
        Gson gson = new Gson();
        this.mailString = gson.toJson(this.mailObject);
    }

    /**
     * Get the mail box object.
     * @return mail object with type.
     */
    public T getMailObject() {
        return mailObject;
    }

    /**
     * Equivalent to toString, used for equivalent access style.
     * @return mail string.
     */
    public String getMailString() {
        return mailString;
    }

    /**
     * Override the to string into a json string.
     * @return json string.
     */
    @Override
    public String toString() {
        return mailString;
    }
}
