/*
 * Copyright 2021 Haulmont.
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

package io.jmix.reportsui.screen.template.edit;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import io.jmix.core.Messages;
import io.jmix.ui.component.ValidationException;
import io.jmix.ui.component.validation.AbstractValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component("report_JsonConfigValidator")
public class JsonConfigValidator extends AbstractValidator<String> {

    protected static final Gson gson;

    static {
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    protected Class caller;
    protected Messages messages;

    public JsonConfigValidator(Class caller) {
        this.caller = caller;
    }

    @Autowired
    protected void setMessages(Messages messages) {
        this.messages = messages;
    }

    @Override
    public void accept(String s) {
        if (!Strings.isNullOrEmpty(s)) {
            try {
                gson.fromJson(s, JsonObject.class);
            } catch (JsonParseException e) {
                throw new ValidationException(messages.getMessage(caller, "chartEdit.invalidJson"));
            }
        }
    }
}
